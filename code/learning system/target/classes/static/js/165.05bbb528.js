"use strict";(self["webpackChunkvue"]=self["webpackChunkvue"]||[]).push([[165],{3165:function(t,e,a){a.r(e),a.d(e,{default:function(){return u}});var l=function(){var t=this,e=t._self._c;return e("div",[e("div",{staticStyle:{margin:"10px 0"}},[e("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"请输入图书ID","suffix-icon":"el-icon-search"},model:{value:t.putBook,callback:function(e){t.putBook=e},expression:"putBook"}}),e("el-input",{staticClass:"ml-5",staticStyle:{width:"200px"},attrs:{placeholder:"请输入书库ID","suffix-icon":"el-icon-put"},model:{value:t.putStack,callback:function(e){t.putStack=e},expression:"putStack"}}),e("el-button",{staticClass:"ml-5",attrs:{type:"primary"},on:{click:t.handleSearch}},[t._v("搜索")]),e("el-button",{staticClass:"ml-5",attrs:{type:"warning"},on:{click:t.reload}},[t._v("重置")])],1),e("el-table",{attrs:{data:t.tableData,"border:":"",stripe:"","header-cell-class-name":t.headerBg,"row-key":"id"}},[e("el-table-column",{attrs:{prop:"putId",label:"ID",width:"50"}}),e("el-table-column",{attrs:{prop:"putBook",label:"图书ID",width:"100"}}),e("el-table-column",{attrs:{prop:"putStack",label:"书库ID",width:"100"}}),e("el-table-column",{attrs:{prop:"putNum",label:"库存量"}})],1),e("div",{staticStyle:{padding:"10px 0"}},[e("el-pagination",{attrs:{"current-page":t.pageNum,"page-sizes":[5,10,20],"page-size":t.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:t.total},on:{"size-change":t.handleSizeChange,"current-change":t.handleCurrentChange}},[t._v(" > ")])],1)],1)},s=[],i={name:"PutView",data(){return{tableData:[],total:0,pageNum:1,pageSize:5,putBook:null,putStack:null,headerBg:"headerBg"}},methods:{collapse(){this.isCollapse=!this.isCollapse,this.isCollapse?(this.logoTextShow=!1,this.sideWidth=64,this.collapseBtnClass="el-icon-s-unfold"):(this.logoTextShow=!0,this.sideWidth=200,this.collapseBtnClass="el-icon-s-fold")},async sendReq(){const t=await this.axios.get("puts/pageLike?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&book=null&stack=null");this.tableData=t.data.data,this.total=t.data.total},async sendLikeReq(){const t=await this.axios.get(`/puts/pageLike?pageNum=${this.pageNum}\n      &pageSize=${this.pageSize}&book=${this.putBook}&stack=${this.putStack}`);this.tableData=t.data.data,this.total=t.data.total},reload(){this.pageNum=1,this.pageSize=5,this.putStack=null,this.putBook=null,this.sendReq()},handleSearch(){this.pageNum=1,this.sendLikeReq()},handleSizeChange(t){this.pageSize=t,this.sendLikeReq()},handleCurrentChange(t){this.pageNum=t,this.sendLikeReq()}},mounted:function(){this.sendLikeReq()}},n=i,o=a(1001),p=(0,o.Z)(n,l,s,!1,null,null,null),u=p.exports}}]);
//# sourceMappingURL=165.05bbb528.js.map