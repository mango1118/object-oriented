"use strict";(self["webpackChunkvue"]=self["webpackChunkvue"]||[]).push([[877],{9877:function(a,e,t){t.r(e),t.d(e,{default:function(){return c}});var i=function(){var a=this,e=a._self._c;return e("div",[e("div",{staticStyle:{margin:"10px 0"}},[e("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"请输入管理员账户","suffix-icon":"el-icon-search"},model:{value:a.adminAccount,callback:function(e){a.adminAccount=e},expression:"adminAccount"}}),e("el-input",{staticClass:"ml-5",staticStyle:{width:"200px"},attrs:{placeholder:"请输入管理员姓名","suffix-icon":"el-icon-admin"},model:{value:a.adminName,callback:function(e){a.adminName=e},expression:"adminName"}}),e("el-input",{staticClass:"ml-5",staticStyle:{width:"200px"},attrs:{placeholder:"请输入管理员邮箱","suffix-icon":"el-icon-message"},model:{value:a.adminEmail,callback:function(e){a.adminEmail=e},expression:"adminEmail"}}),e("el-button",{staticClass:"ml-5",attrs:{type:"primary"},on:{click:a.handleSearch}},[a._v("搜索")]),e("el-button",{staticClass:"ml-5",attrs:{type:"warning"},on:{click:a.reload}},[a._v("重置")])],1),e("el-table",{attrs:{data:a.tableData,"border:":"",stripe:"","header-cell-class-name":a.headerBg,"row-key":"id"}},[e("el-table-column",{attrs:{prop:"adminId",label:"ID",width:"50"}}),e("el-table-column",{attrs:{prop:"adminAccount",label:"账户",width:"150"}}),e("el-table-column",{attrs:{prop:"adminName",label:"姓名",width:"100"}}),e("el-table-column",{attrs:{prop:"adminEmail",label:"邮箱"}})],1),e("div",{staticStyle:{padding:"10px 0"}},[e("el-pagination",{attrs:{"current-page":a.pageNum,"page-sizes":[5,10,20],"page-size":a.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:a.total},on:{"size-change":a.handleSizeChange,"current-change":a.handleCurrentChange}},[a._v(" > ")])],1)],1)},l=[],n={name:"AdminView",data(){return{tableData:[],total:0,pageNum:1,pageSize:5,adminAccount:null,adminName:null,adminEmail:null,headerBg:"headerBg"}},methods:{collapse(){this.isCollapse=!this.isCollapse,this.isCollapse?(this.logoTextShow=!1,this.sideWidth=64,this.collapseBtnClass="el-icon-s-unfold"):(this.logoTextShow=!0,this.sideWidth=200,this.collapseBtnClass="el-icon-s-fold")},async sendReq(){const a=await this.axios.get("admins/pageLike?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&account=null&name=null&email=null");this.tableData=a.data.data,this.total=a.data.total},async sendLikeReq(){const a=await this.axios.get(`/admins/pageLike?pageNum=${this.pageNum}\n      &pageSize=${this.pageSize}&account=${this.adminAccount}&name=${this.adminName}&email=${this.adminEmail}`);this.tableData=a.data.data,this.total=a.data.total},reload(){this.adminName=null,this.adminAccount=null,this.adminEmail=null,this.sendReq()},handleSearch(){this.pageNum=1,this.sendLikeReq()},handleSizeChange(a){this.pageSize=a,this.sendLikeReq()},handleCurrentChange(a){this.pageNum=a,this.sendLikeReq()}},mounted:function(){this.sendLikeReq()}},s=n,d=t(1001),o=(0,d.Z)(s,i,l,!1,null,null,null),c=o.exports}}]);
//# sourceMappingURL=877.bc95d08a.js.map