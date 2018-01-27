package com.yizhen.coffee.biz.wechat;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author muying.xx
 * @Date 21/01/2018 12:42
 */
public class WeChatUtil {

    private static final Logger logger = LoggerFactory.getLogger(WeChatUtil.class);


    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串，排除空值
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }


    /**
     * 生成签名结果
     *
     * @param sPara 要签名的字典
     * @return 签名结果字符串
     * @throws UnsupportedEncodingException
     */
    public static String buildRequestSign(Map<String, String> sPara, String key) throws UnsupportedEncodingException {
        String prestr = createLinkString(sPara) + "&key=" + key; // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        logger.info("排序后的str = {}",prestr);
        return DigestUtils.md5Hex(prestr.getBytes("UTF-8")).toUpperCase();
    }

    public static String buildRequestSign(WxPayData request, String key) throws Exception {
        try {
            Map<String, String> sPara = BeanUtils.describe(request);
            cleanMap(sPara);
            return buildRequestSign(sPara, key);
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 清除Map里的空值，以及由 BeanUtils.describe 产生的class键
     */
    public static void cleanMap(Map<String, String> map) {
        List<String> keys = new ArrayList<String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() == null || entry.getValue().equals("")) {
                keys.add(entry.getKey());
            }
        }
        for (String key : keys) {
            map.remove(key);
        }
        if (map.keySet().contains("class")) {
            map.remove("class");
        }
    }


    /*
     * xml和对象的互相转换
     */
    public static <T> String convertObjectToXml(Object obj, Class<T> type) {
        XStream xstream = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
        xstream.alias("xml", type);
        String xml = xstream.toXML(obj);
        return xml;
    }

    public static <T> T convertXmlToObject(Class<T> type,String xml) {
        XStream xstream =  new XStream(new DomDriver());
        xstream.alias("xml", type);
        T response = (T)xstream.fromXML(xml);
        return response;
    }




    /*******************************************************************************************/



    /**
     * 统一下单
     * 获得PrePayId
     * @param body   商品或支付单简要描述
     * @param out_trade_no 商户系统内部的订单号,32个字符内、可包含字母
     * @param total_fee  订单总金额，单位为分
     * @param IP    APP和网页支付提交用户端ip
     * @param notify_url 接收微信支付异步通知回调地址
     * @param openid 用户openId
     * @throws IOException
     */
    public static String unifiedorder(String body,String out_trade_no,Integer total_fee,String ip,String notify_url,String openId)throws IOException {
        /**
         * 设置访问路径
         */


        HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");
        String nonce_str = getNonceStr();//随机数据
        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
        /**
         * 组装请求参数
         * 按照ASCII排序
         */
        parameters.put("appid","");
        parameters.put("body", body);
        parameters.put("mch_id", WxPayConfig.MCH_ID );
        parameters.put("nonce_str", nonce_str);
        parameters.put("out_trade_no", out_trade_no);
        parameters.put("notify_url", notify_url);
        parameters.put("spbill_create_ip", ip);
        parameters.put("total_fee",total_fee.toString() );
        parameters.put("trade_type", WxPayConfig.TRADE_TYPE );
        parameters.put("openid", openId);

        String sign = WxSign.createSign(parameters, WxPayConfig.KEY);

        /**
         * 组装XML
         */
        StringBuilder sb = new StringBuilder("");
        sb.append("<xml>");
        setXmlKV(sb,"appid","");
        setXmlKV(sb,"body",body);
        setXmlKV(sb,"mch_id", WxPayConfig.MCH_ID);
        setXmlKV(sb,"nonce_str",nonce_str);
        setXmlKV(sb,"notify_url",notify_url);
        setXmlKV(sb,"out_trade_no",out_trade_no);
        setXmlKV(sb,"spbill_create_ip",ip);
        setXmlKV(sb,"total_fee",total_fee.toString());
        setXmlKV(sb,"trade_type", WxPayConfig.TRADE_TYPE);
        setXmlKV(sb,"sign",sign);
        setXmlKV(sb,"openid",openId);
        sb.append("</xml>");

        StringEntity reqEntity = new StringEntity(new String (sb.toString().getBytes("UTF-8"),"ISO8859-1"));//这个处理是为了防止传中文的时候出现签名错误
        httppost.setEntity(reqEntity);
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = httpclient.execute(httppost);
        String strResult = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));

        return strResult;

    }


    //获得随机字符串
    public static String getNonceStr(){
        Random random = new Random();
        return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
    }

    //插入XML标签
    public static StringBuilder setXmlKV(StringBuilder sb,String Key,String value){
        sb.append("<");
        sb.append(Key);
        sb.append(">");

        sb.append(value);

        sb.append("</");
        sb.append(Key);
        sb.append(">");

        return sb;
    }

    //解析XML  获得 PrePayId
    public static String getPrePayId(String xml){
        int start = xml.indexOf("<prepay_id>");
        int end = xml.indexOf("</prepay_id>");
        if(start < 0 && end < 0){
            return null;
        }
        return xml.substring(start + "<prepay_id>".length(),end).replace("<![CDATA[","").replace("]]>","");
    }

    //商户订单号
    public static String getOut_trade_no(){
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return df.format(new Date()) + getNonceStr().substring(0,7);
    }

    //时间戳
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    //随机4位数字
    public static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    public static String inputStream2String(InputStream inStream, String encoding){
        String result = null;
        try {
            if(inStream != null){
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] tempBytes = new byte[1024];
                int count = -1;
                while((count = inStream.read(tempBytes, 0, 1024)) != -1){
                    outStream.write(tempBytes, 0, count);
                }
                tempBytes = null;
                outStream.flush();
                result = new String(outStream.toByteArray(), encoding);
            }
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getNonceStr());
    }




}
