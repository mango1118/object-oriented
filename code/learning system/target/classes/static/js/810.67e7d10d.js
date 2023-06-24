"use strict";(self["webpackChunkvue"]=self["webpackChunkvue"]||[]).push([[810],{1810:function(e,t,a){a.r(t),a.d(t,{default:function(){return h}});var o=function(){var e=this,t=e._self._c;return t("div",[t("div",{staticStyle:{margin:"10px 0"}},[t("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"请输入书名","suffix-icon":"el-icon-search"},model:{value:e.bookName,callback:function(t){e.bookName=t},expression:"bookName"}}),t("el-input",{staticClass:"ml-5",staticStyle:{width:"200px"},attrs:{placeholder:"请输入作者","suffix-icon":"el-icon-book"},model:{value:e.bookAuthor,callback:function(t){e.bookAuthor=t},expression:"bookAuthor"}}),t("el-input",{staticClass:"ml-5",staticStyle:{width:"200px"},attrs:{placeholder:"请输入出版社","suffix-icon":"el-icon-message"},model:{value:e.bookPublisher,callback:function(t){e.bookPublisher=t},expression:"bookPublisher"}}),t("el-button",{staticClass:"ml-5",attrs:{type:"primary"},on:{click:e.handleSearch}},[e._v("搜索")]),t("el-button",{staticClass:"ml-5",attrs:{type:"warning"},on:{click:e.reload}},[e._v("重置")])],1),t("el-table",{attrs:{data:e.tableData,"border:":"",stripe:"","header-cell-class-name":e.headerBg,"row-key":"id"}},[t("el-table-column",{attrs:{prop:"bookId",label:"ID",width:"50"}}),t("el-table-column",{attrs:{prop:"bookName",label:"书名",width:"250"}}),t("el-table-column",{attrs:{prop:"bookAuthor",label:"作者",width:"200"}}),t("el-table-column",{attrs:{prop:"bookPublisher",label:"出版社",width:"200"}}),t("el-table-column",{attrs:{prop:"bookDescription",label:"简述"}}),t("el-table-column",{attrs:{label:"操作",width:"200",align:"center"},scopedSlots:e._u([{key:"default",fn:function(a){return[t("el-button",{attrs:{type:"success"},on:{click:function(t){return e.handleBorrow(a.row)}}},[e._v("借阅"),t("i",{staticClass:"el-icon-edit"})])]}}])})],1),t("div",{staticStyle:{padding:"10px 0"}},[t("el-pagination",{attrs:{"current-page":e.pageNum,"page-sizes":[5,10,20],"page-size":e.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}},[e._v(" > ")])],1)],1)},l=[],s={name:"BookBook",data(){return{tableData:[],total:0,pageNum:1,pageSize:5,bookName:null,bookAuthor:null,bookPublisher:null,headerBg:"headerBg",readerNow:{}}},methods:{collapse(){this.isCollapse=!this.isCollapse,this.isCollapse?(this.logoTextShow=!1,this.sideWidth=64,this.collapseBtnClass="el-icon-s-unfold"):(this.logoTextShow=!0,this.sideWidth=200,this.collapseBtnClass="el-icon-s-fold")},async sendReq(){const e=await this.axios.get("books/pageLike?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&name=null&author=null&publisher=null");this.tableData=e.data.data,this.total=e.data.total},async sendLikeReq(){const e=await this.axios.get(`/books/pageLike?pageNum=${this.pageNum}\n      &pageSize=${this.pageSize}&name=${this.bookName}&author=${this.bookAuthor}&publisher=${this.bookPublisher}`);console.log(e),this.tableData=e.data.data,this.total=e.data.total},reload(){this.pageNum=1,this.pageSize=5,this.bookAuthor=null,this.bookName=null,this.bookPublisher=null,this.sendReq()},handleSearch(){this.pageNum=1,this.sendLikeReq()},async handleBorrow(e){this.readerNow=JSON.parse(localStorage.getItem("user")),this.axios.get(`/readers/borrowBook?readerId=${this.readerNow.readerId}&bookId=${e.bookId}`).then((e=>{e.data?this.$message.success("借阅成功"):this.$message.error(`借阅失败，原因：${e.msg}`)}))},handleSizeChange(e){this.pageSize=e,this.sendLikeReq()},handleCurrentChange(e){this.pageNum=e,this.sendLikeReq()}},mounted:function(){this.sendLikeReq()}},i=s,n=a(1001),r=(0,n.Z)(i,o,l,!1,null,null,null),h=r.exports}}]);
//# sourceMappingURL=810.67e7d10d.js.map