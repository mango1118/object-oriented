<template>


  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入书本ID" suffix-icon="el-icon-search"
                v-model="bookId"></el-input>
      <el-button class="ml-5" type="primary" @click="handleSearch">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>

    <el-table v-bind:data="tableData" border: stripe :header-cell-class-name="headerBg" row-key="id">
      <el-table-column prop="bookId" label="图书ID" width="100"></el-table-column>
      <el-table-column prop="stackId" label="书库ID" width="100"></el-table-column>
      <el-table-column prop="bookName" label="书名" width="400"></el-table-column>
      <el-table-column prop="stackAddress" label="书库地址" width="400"></el-table-column>
      <el-table-column prop="bookNum" label="图书数量"></el-table-column>
    </el-table>
<!--    <div style="padding: 10px 0">-->
<!--      <el-pagination-->
<!--          @size-change="handleSizeChange"-->
<!--          @current-change="handleCurrentChange"-->
<!--          :current-page="pageNum"-->
<!--          :page-sizes="[5, 10, 20]"-->
<!--          :page-size="pageSize"-->
<!--          layout="total, sizes, prev, pager, next, jumper"-->
<!--          :total=total>-->
<!--        >-->
<!--      </el-pagination>-->
<!--    </div>-->
  </div>
</template>

<script>
export default {
  name: "BookSearchView",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      bookId: null,
      bookName: null,
      bookAuthor: null,
      bookPublisher: null,
      headerBg: 'headerBg'
    }
  },
  methods: {
    collapse() {// 点击收缩按钮触发
      this.isCollapse = !this.isCollapse;
      if (this.isCollapse) { // 收缩
        this.logoTextShow = false;
        this.sideWidth = 64;
        this.collapseBtnClass = 'el-icon-s-unfold';
      } else { // 展开
        this.logoTextShow = true;
        this.sideWidth = 200;
        this.collapseBtnClass = 'el-icon-s-fold';
      }
    },
    // async sendReq() {
    //   // this.pageNum = 1;
    //   const resp = await this.axios.get("books/pageLike?pageNum=" + this.pageNum
    //       + "&pageSize=" + this.pageSize + "&name=null&author=null&publisher=null");
    //   // console.log(resp);
    //   this.tableData = resp.data.data;
    //   this.total = resp.data.total;
    // },
    async sendLikeReq() {
      // this.pageNum = 1;
      const resp = await this.axios.get(`/books/bookStack?bookId=${this.bookId}`);
      console.log(resp);
      if(resp.data === null){
        this.$message.error(resp.msg)
      }else{
        this.$message.success("查询成功！")
        this.tableData = resp.data.data
      }
      // this.tableData = resp.data.data;
      // console.log(resp.data)
      // console.log(this.tableData)
      // this.total = resp.data.total;
    },
    reload() {
      this.pageNum = 1;
      this.pageSize = 5;
      this.bookAuthor = null;
      this.bookName = null;
      this.bookPublisher = null;
      // this.sendReq();
      this.tableData = {};
    },
    handleSearch() {
      this.pageNum = 1;
      this.sendLikeReq();
    },
    // handleSizeChange(pageSize) {
    //   this.pageSize = pageSize;
    //   this.sendLikeReq();
    // },
    // handleCurrentChange(pageNum) {
    //   this.pageNum = pageNum;
    //   this.sendLikeReq();
    // },
  },
  mounted: function () {
    // this.sendLikeReq();
  }
}

</script>

<style scoped>

</style>