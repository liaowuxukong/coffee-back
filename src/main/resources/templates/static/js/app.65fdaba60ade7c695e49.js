webpackJsonp([1,0],[function(t,e,s){"use strict";function n(t){return t&&t.__esModule?t:{default:t}}Object.defineProperty(e,"__esModule",{value:!0});var o=s(4),i=n(o),a=s(42),r=n(a),c=s(41),l=n(c),u=s(26),d=n(u),f=s(28),p=n(f);s(14),i.default.use(r.default),i.default.use(l.default);var v=[{path:"/index",name:"index",component:d.default,children:[{path:"/goods",component:p.default}]}],h=new r.default({linkActiveClass:"active",routes:v}),_=new i.default({router:h}).$mount("#app");h.push({path:"/goods"}),e.default=_},,,function(t,e,s){s(18);var n=s(1)(s(6),s(36),null,null);t.exports=n.exports},,function(t,e,s){"use strict";function n(t){return t&&t.__esModule?t:{default:t}}Object.defineProperty(e,"__esModule",{value:!0});var o=s(29),i=n(o);e.default={components:{"v-header":i.default},created:function(){}}},function(t,e,s){"use strict";function n(t){return t&&t.__esModule?t:{default:t}}Object.defineProperty(e,"__esModule",{value:!0});var o=s(4),i=n(o);e.default={props:{food:{type:Object}},methods:{addCart:function(t){t._constructed&&(this.food.count?this.food.count++:i.default.set(this.food,"count",1),this.$emit("increment",t.target))},decreaseCart:function(t){t._constructed&&(this.food.count--,this.food.count<0&&(this.food.count=0))}}}},function(t,e,s){"use strict";function n(t){return t&&t.__esModule?t:{default:t}}Object.defineProperty(e,"__esModule",{value:!0});var o=s(2),i=n(o),a=s(3),r=n(a),c=s(32),l=n(c),u=s(30),d=n(u),f=s(4),p=n(f),v=s(13),h=2;e.default={props:{food:{type:Object}},data:function(){return{showFlag:!1,selectType:h,onlyContent:!0,desc:{all:"全部",positive:"推荐",negative:"吐槽"}}},methods:{show:function(){var t=this;this.showFlag=!0,this.selectType=h,this.onlyContent=!0,this.$nextTick(function(){t.scroll?t.scroll.refresh():t.scroll=new i.default(t.$el,{click:!0})})},incrementTotal:function(t,e){var s=this;this[t]=e,this.$nextTick(function(){s.scroll.refresh()})},hide:function(){this.showFlag=!1},addFirst:function(t){t._constructed&&p.default.set(this.food,"count",1)},needShow:function(t,e){return!(this.onlyContent&&!e)&&(this.selectType===h||t===this.selectType)}},filters:{formatDate:function(t){var e=new Date(t);return(0,v.formatDate)(e,"yyyy-MM-dd hh:mm")}},components:{cartControl:r.default,ratingselect:d.default,split:l.default}}},function(t,e,s){"use strict";function n(t){return t&&t.__esModule?t:{default:t}}Object.defineProperty(e,"__esModule",{value:!0});var o=s(2),i=n(o),a=s(31),r=n(a),c=s(3),l=n(c),u=s(27),d=n(u);e.default={props:[],data:function(){return{goods:[],listHeight:[],scrolly:0,selectedFood:{},httpback:""}},created:function(){this.goods=[],this.$http.get("http://www.ealam.cn/goodList").then(function(t){var e=this;this.goods=t.body,this.$nextTick(function(){e._initScroll(),e._calculateHeight()})}),this.classMap=["decrease","discount","special","invoice","guarantee"]},mounted:function(){},computed:{currentIndex:function(){for(var t=0;t<this.listHeight.length;t++){var e=this.listHeight[t],s=this.listHeight[t+1];if(!s||this.scrolly>=e&&this.scrolly<s)return t}return 0},selectFoods:function(){var t=[];return this.goods.forEach(function(e){e.foods.forEach(function(e){e.count&&t.push(e)})}),t}},methods:{_initScroll:function(){var t=this;this.menuScroll=new i.default(this.$refs.menuWrapper,{click:!0}),this.foodScroll=new i.default(this.$refs.foodWrapper,{probeType:3,click:!0}),this.foodScroll.on("scroll",function(e){t.scrolly=Math.abs(Math.round(e.y))})},_calculateHeight:function(){var t=this.$refs.foodWrapper.getElementsByClassName("food-list-hook"),e=0;this.listHeight.push(e);for(var s=0;s<t.length;s++){var n=t[s];e+=n.clientHeight,this.listHeight.push(e)}},selectMenu:function(t,e){if(e._constructed){var s=this.$refs.foodWrapper.getElementsByClassName("food-list-hook"),n=s[t];this.foodScroll.scrollToElement(n,300)}},selectFood:function(t,e){e._constructed&&(this.selectedFood=t,this.$refs.food.show())},incrementTotal:function(t){this.$refs.shopCart.drop(t)}},components:{shopCart:r.default,cartControl:l.default,food:d.default}}},function(t,e){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={props:[],data:function(){return{detailShow:!1,banner:""}},methods:{showDetail:function(){this.detailShow=!0},hideDetail:function(){this.detailShow=!1}},created:function(){this.classMap=["decrease","discount","special","invoice","guarantee"]},mounted:function(){this.$http.get("http://www.ealam.cn/bannerList").then(function(t){this.banner=t.body[0]})},components:{}}},function(t,e){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=0,n=1,o=0;e.default={props:{ratings:{type:Array,default:function(){return[]}},selectType:{type:Number,default:o},onlyContent:{type:Boolean,default:!1},desc:{type:Object,default:function(){return{all:"全部",positive:"满意",negative:"吐槽"}}}},computed:{positives:function(){return this.ratings.filter(function(t){return t.rateType===s})},nagatives:function(){return this.ratings.filter(function(t){return t.rateType===n})}},methods:{select:function(t,e){e._constructed&&(this.selectType=t,this.$emit("increment","selectType",t))},toggleContent:function(t){t._constructed&&(this.onlyContent=!this.onlyContent,this.$emit("increment","onlyContent",this.onlyContent))},needShow:function(t,e){return!(this.onlyContent&&!e)&&(this.selectType===o||t===this.selectType)}}}},function(t,e,s){"use strict";function n(t){return t&&t.__esModule?t:{default:t}}function o(t){var e=new RegExp("(^|&)"+t+"=([^&]*)(&|$)"),s=window.location.search.substr(1).match(e);return null!=s?unescape(s[2]):null}function i(){WeixinJSBridge.invoke("getBrandWCPayRequest",{appId:c,paySign:r,timeStamp:l,nonceStr:u,package:d,signType:f},function(t){"get_brand_wcpay_request:ok"==t.err_msg?alert("支付成功"):"get_brand_wcpay_request:cancel"==t.err_msg?alert("支付取消"):"get_brand_wcpay_request:fail"==t.err_msg&&alert("支付失败")})}function a(){"undefined"==typeof WeixinJSBridge?document.addEventListener?document.addEventListener("WeixinJSBridgeReady",i,!1):document.attachEvent&&(document.attachEvent("WeixinJSBridgeReady",i),document.attachEvent("onWeixinJSBridgeReady",i)):i()}Object.defineProperty(e,"__esModule",{value:!0});var r,c,l,u,d,f,p=s(3),v=n(p),h=s(2),_=n(h),m="http://www.ealam.cn";e.default={props:{selectFoods:{type:Array,default:function(){return[{price:20,count:2}]}}},data:function(){return{balls:[{show:!1},{show:!1},{show:!1},{show:!1},{show:!1}],dropBalls:[],fold:!0}},computed:{totalPrice:function(){var t=0;return this.selectFoods.forEach(function(e){t+=e.price*e.count}),t},totalCount:function(){var t=0;return this.selectFoods.forEach(function(e){t+=e.count}),t},payDesc:function(){return"结算"},payClass:function(){return"enough"},listShow:function(){var t=this;if(!this.totalCount)return this.fold=!0,!1;var e=!this.fold;return e&&this.$nextTick(function(){t.scroll?t.scroll.refresh():t.scroll=new _.default(t.$refs.listContent,{click:!0})}),e}},methods:{toggleList:function(){this.totalCount&&(this.fold=!this.fold)},empty:function(){this.selectFoods.forEach(function(t){t.count=0})},hideList:function(){this.fold=!1},pay:function(){var t=[];this.selectFoods.forEach(function(e){t.push(e.id)});var e=m+"/wxpay/submitWXOrderForm";this.$http.post(e,{totalFee:100*this.totalPrice}).then(function(t){var e=t.body;window.alert(e),c=e.appid,r=e.sign,l=e.timeStamp,u=e.nonce_str,d=e.packageStr,f=e.signType,a()}),window.alert("支付"+this.totalPrice+"元,选择了如下id:"+t.join(";")+",货架id:"+o("shelfId")),this.$http.post("http://www.ealam.cn/buy",{ids:t}).then(function(t){"success"===t.bodyText?window.alert("ok"):window.alert("fail")})},drop:function(t){for(var e=0;e<this.balls.length;e++){var s=this.balls[e];if(!s.show)return s.show=!0,s.el=t,void this.dropBalls.push(s)}},beforeEnter:function(t){for(var e=this.balls.length;e--;){var s=this.balls[e];if(s.show){var n=s.el.getBoundingClientRect(),o=n.left-32,i=-(window.innerHeight-n.top-22);t.style.display="",t.style.webkitTransform="translate3d(0,"+i+"px,0)",t.style.transform="translate3d(0,"+i+"px,0)";var a=t.getElementsByClassName("inner-hook")[0];a.style.webkitTransform="translate3d("+o+"px,0,0)",a.style.transform="translate3d("+o+"px,0,0)"}}},enter:function(t){this.$nextTick(function(){t.style.webkitTransform="translate3d(0,0,0)",t.style.transform="translate3d(0,0,0)";var e=t.getElementsByClassName("inner-hook")[0];e.style.webkitTransform="translate3d(0,0,0)",e.style.transform="translate3d(0,0,0)"})},afterEnter:function(t){var e=this.dropBalls.shift();e&&(e.show=!1,t.style.display="none")}},components:{cartControl:v.default}}},function(t,e){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={}},function(t,e){"use strict";function s(t,e){/(y+)/.test(e)&&(e=e.replace(RegExp.$1,(t.getFullYear()+"").substr(4-RegExp.$1.length)));var s={"M+":t.getMonth()+1,"d+":t.getDate(),"h+":t.getHours(),"m+":t.getMinutes(),"s+":t.getSeconds()};for(var o in s)if(new RegExp("("+o+")").test(e)){var i=s[o]+"";e=e.replace(RegExp.$1,1===RegExp.$1.length?i:n(i))}return e}function n(t){return("00"+t).substr(t.length)}Object.defineProperty(e,"__esModule",{value:!0}),e.formatDate=s},function(t,e){},function(t,e){},function(t,e){},function(t,e){},function(t,e){},function(t,e){},function(t,e){},function(t,e){},function(t,e){},,,,function(t,e,s){s(17);var n=s(1)(s(5),s(35),null,null);t.exports=n.exports},function(t,e,s){s(20);var n=s(1)(s(7),s(38),null,null);t.exports=n.exports},function(t,e,s){s(21);var n=s(1)(s(8),s(39),null,null);t.exports=n.exports},function(t,e,s){s(15);var n=s(1)(s(9),s(33),null,null);t.exports=n.exports},function(t,e,s){s(22);var n=s(1)(s(10),s(40),null,null);t.exports=n.exports},function(t,e,s){s(16);var n=s(1)(s(11),s(34),null,null);t.exports=n.exports},function(t,e,s){s(19);var n=s(1)(s(12),s(37),null,null);t.exports=n.exports},function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"header"},[s("div",{staticClass:"content-wrapper"}),t._v(" "),t._m(0,!1,!1),t._v(" "),s("div",{staticClass:"background"},[s("img",{attrs:{src:t.banner.picUrl,alt:"",width:"100%",height:"100%"}})])])},staticRenderFns:[function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"bulletin-wrapper"},[s("span",{staticClass:"bulletin-title"})])}]}},function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",[s("div",{staticClass:"shopCart"},[s("div",{staticClass:"content",on:{click:function(e){t.toggleList(e)}}},[s("div",{staticClass:"content-left"},[s("div",{staticClass:"logo-wrapper"},[s("div",{staticClass:"logo",class:{highlight:t.totalCount>0}},[s("i",{staticClass:"iconfont icon-gouwuche",class:{highlight:t.totalCount>0}})]),t._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:t.totalCount>0,expression:"totalCount > 0"}],staticClass:"num"},[t._v(t._s(t.totalCount))])]),t._v(" "),s("div",{staticClass:"price",class:{highlight:t.totalPrice>0}},[t._v("￥"+t._s(t.totalPrice))])]),t._v(" "),s("div",{staticClass:"content-right",on:{click:function(e){e.stopPropagation(),e.preventDefault(),t.pay(e)}}},[s("div",{staticClass:"pay",class:t.payClass},[t._v("\n          "+t._s(t.payDesc)+"\n        ")])])]),t._v(" "),s("div",{staticClass:"ball-container"},t._l(t.balls,function(e){return s("div",[s("transition",{attrs:{name:"drop"},on:{"before-enter":t.beforeEnter,enter:t.enter,"after-enter":t.afterEnter}},[s("div",{directives:[{name:"show",rawName:"v-show",value:e.show,expression:"ball.show"}],staticClass:"ball"},[s("div",{staticClass:"inner inner-hook"})])])],1)})),t._v(" "),s("transition",{attrs:{name:"fade"}},[s("div",{directives:[{name:"show",rawName:"v-show",value:t.listShow,expression:"listShow"}],staticClass:"shopcart-list"},[s("div",{staticClass:"list-header"},[s("h1",{staticClass:"title"},[t._v("已选商品")]),t._v(" "),s("span",{staticClass:"empty",on:{click:t.empty}},[t._v("清空")])]),t._v(" "),s("div",{ref:"listContent",staticClass:"list-content"},[s("ul",t._l(t.selectFoods,function(e){return s("li",{staticClass:"shopcart-food"},[s("span",{staticClass:"name"},[t._v(t._s(e.name))]),t._v(" "),s("div",{staticClass:"price"},[s("span",[t._v("￥"+t._s(e.price*e.count))])]),t._v(" "),s("div",{staticClass:"cartControl-wrapper"},[s("cartControl",{attrs:{food:e}})],1)])}))])])])],1),t._v(" "),s("transition",{attrs:{name:"fade"}},[s("div",{directives:[{name:"show",rawName:"v-show",value:t.listShow,expression:"listShow"}],staticClass:"list-mask",on:{click:function(e){t.hideList()}}})])],1)},staticRenderFns:[]}},function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",[s("v-header"),t._v(" "),s("keep-alive",[s("router-view")],1)],1)},staticRenderFns:[]}},function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"cartControl"},[s("transition",{attrs:{name:"fade"}},[s("div",{directives:[{name:"show",rawName:"v-show",value:t.food.count>0,expression:"food.count>0"}],staticClass:"cart-decrease",on:{click:function(e){e.stopPropagation(),e.preventDefault(),t.decreaseCart(e)}}},[s("transition",{attrs:{name:"inner"}},[s("span",{staticClass:"inner iconfont icon-jian"})])],1)]),t._v(" "),s("span",{directives:[{name:"show",rawName:"v-show",value:t.food.count>0,expression:"food.count > 0 "}],staticClass:"cart-count"},[t._v("\n    "+t._s(t.food.count)+"\n  ")]),t._v(" "),s("span",{staticClass:"iconfont icon-jia cart-add",on:{click:function(e){e.stopPropagation(),e.preventDefault(),t.addCart(e)}}})],1)},staticRenderFns:[]}},function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"split"})},staticRenderFns:[]}},function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("transition",{attrs:{name:"fade"}},[s("div",{directives:[{name:"show",rawName:"v-show",value:t.showFlag,expression:"showFlag"}],staticClass:"food"},[s("div",{staticClass:"fond-content"},[s("div",{staticClass:"image-header"},[s("img",{attrs:{src:t.food.image,alt:""}}),t._v(" "),s("div",{staticClass:"back",on:{click:t.hide}},[s("i",{staticClass:"iconfont icon-weibiaoti6-copy"})])]),t._v(" "),s("div",{staticClass:"content"},[s("h1",{staticClass:"title"},[t._v(t._s(t.food.name))]),t._v(" "),s("div",{staticClass:"detail"},[s("span",{staticClass:"sell-count"},[t._v("月售"+t._s(t.food.sellCount)+"份")]),t._v(" "),s("span",{staticClass:"rating"},[t._v(" 好评率"+t._s(t.food.rating)+"%")])]),t._v(" "),s("div",{staticClass:"price"},[s("span",{staticClass:"now"},[t._v("￥"+t._s(t.food.price))]),t._v(" "),s("span",{directives:[{name:"show",rawName:"v-show",value:t.food.oldPrice,expression:"food.oldPrice"}],staticClass:"old"},[t._v("￥"+t._s(t.food.oldPrice))])]),t._v(" "),s("div",{staticClass:"cartControl-wrapper"},[s("cartControl",{attrs:{food:t.food}})],1),t._v(" "),s("transition",{attrs:{name:"buy"}},[s("div",{directives:[{name:"show",rawName:"v-show",value:!t.food.count||0===t.food.count,expression:"!food.count || food.count === 0"}],staticClass:"buy",on:{click:function(e){e.stopPropagation(),e.preventDefault(),t.addFirst(e)}}},[t._v("\n            加入购物车\n          ")])])],1),t._v(" "),s("split"),t._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:t.food.info,expression:"food.info"}],staticClass:"info"},[s("h1",{staticClass:"title"},[t._v("商品信息")]),t._v(" "),s("p",{staticClass:"text"},[t._v(t._s(t.food.info))])]),t._v(" "),s("split"),t._v(" "),s("div",{staticClass:"rating"},[s("h1",{staticClass:"title"},[t._v("商品评价")]),t._v(" "),s("ratingselect",{attrs:{"select-type":t.selectType,"only-content":t.onlyContent,desc:t.desc,ratings:t.food.ratings},on:{increment:t.incrementTotal}}),t._v(" "),s("div",{staticClass:"rating-wrapper"},[s("ul",{directives:[{name:"show",rawName:"v-show",value:t.food.ratings&&t.food.ratings.length,expression:"food.ratings && food.ratings.length"}]},t._l(t.food.ratings,function(e){return s("li",{directives:[{name:"show",rawName:"v-show",value:t.needShow(e.rateType,e.text),expression:"needShow(rating.rateType, rating.text)"}],staticClass:"rating-item border-1px"},[s("div",{staticClass:"user"},[s("span",{staticClass:"name"},[t._v(t._s(e.username))]),t._v(" "),s("img",{staticClass:"avatar",attrs:{width:"12",height:"12",src:e.avatar,alt:""}})]),t._v(" "),s("div",{staticClass:"time"},[t._v(t._s(t._f("formatDate")(e.rateTime)))]),t._v(" "),s("p",{staticClass:"text"},[s("i",{staticClass:"iconfont",class:{"icon-damuzhi":0===e.rateType,"icon-down":1===e.rateType}}),t._v("\n                "+t._s(e.text)+"\n              ")])])})),t._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:!t.food.ratings||0===t.food.ratings.length,expression:"!food.ratings || food.ratings.length === 0"}],staticClass:"no-rating"})])],1)],1)])])},staticRenderFns:[]}},function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"good"},[s("div",{ref:"menuWrapper",staticClass:"menu-wrapper"},[s("ul",t._l(t.goods,function(e,n){return s("li",{staticClass:"menu-item border-1px",class:{current:t.currentIndex===n},on:{click:function(e){t.selectMenu(n,e)}}},[s("span",{staticClass:"text"},[s("span",{directives:[{name:"show",rawName:"v-show",value:e.type>0,expression:"item.type>0"}],staticClass:" icon",class:t.classMap[e.type]}),t._v(t._s(e.name)+"\n        ")])])}))]),t._v(" "),s("div",{ref:"foodWrapper",staticClass:"foods-wrapper"},[s("ul",t._l(t.goods,function(e){return s("li",{staticClass:"food-list food-list-hook"},[s("h1",{staticClass:"title"},[t._v(t._s(e.name))]),t._v(" "),s("ul",t._l(e.foods,function(e){return s("li",{staticClass:"food-item"},[s("div",{staticClass:"icon"},[s("img",{attrs:{src:e.image,alt:"",width:"57"}})]),t._v(" "),s("div",{staticClass:"content"},[s("h2",{staticClass:"name"},[t._v(t._s(e.name))]),t._v(" "),s("p",{staticClass:"desc"},[t._v(t._s(e.description))]),t._v(" "),s("div",{staticClass:"price"},[s("span",{staticClass:"now"},[t._v("￥"+t._s(e.price))]),t._v(" "),s("span",{directives:[{name:"show",rawName:"v-show",value:e.oldPrice,expression:"food.oldPrice"}],staticClass:"old"},[t._v("￥"+t._s(e.oldPrice))])]),t._v(" "),s("div",{staticClass:"cartControl-wrapper"},[s("cartControl",{attrs:{food:e},on:{increment:t.incrementTotal}})],1)])])}))])}))]),t._v(" "),s("div",[s("shopCart",{ref:"shopCart",attrs:{"select-foods":t.selectFoods}}),t._v(" "),s("food",{ref:"food",attrs:{food:t.selectedFood}})],1),t._v("\n  "+t._s(t.httpback)+"\n")])},staticRenderFns:[]}},function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"ratingselect"},[s("div",{staticClass:"rating-type border-1px"},[s("span",{staticClass:"block positive",class:{active:2===t.selectType},on:{click:function(e){t.select(2,e)}}},[t._v(t._s(t.desc.all)),s("span",{staticClass:"count"},[t._v(t._s(t.ratings.length))])]),t._v(" "),s("span",{staticClass:"block positive",class:{active:0===t.selectType},on:{click:function(e){t.select(0,e)}}},[t._v(t._s(t.desc.positive)),s("span",{staticClass:"count"},[t._v(t._s(t.positives.length))])]),t._v(" "),s("span",{staticClass:"block negative",class:{active:1===t.selectType},on:{click:function(e){t.select(1,e)}}},[t._v(t._s(t.desc.negative)),s("span",{staticClass:"count"},[t._v(t._s(t.nagatives.length))])])]),t._v(" "),s("div",{staticClass:"switch",class:{on:t.onlyContent},on:{click:function(e){t.toggleContent(e)}}},[s("i",{staticClass:"iconfont icon-gou"}),t._v(" "),s("span",{staticClass:"text"},[t._v("只看有内容的评价")])])])},staticRenderFns:[]}},,,function(t,e){}]);
//# sourceMappingURL=app.65fdaba60ade7c695e49.js.map