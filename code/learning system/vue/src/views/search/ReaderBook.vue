<template>

  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入书名" suffix-icon="el-icon-search"
                v-model="bookName"></el-input>
      <el-input style="width: 200px" placeholder="请输入作者" suffix-icon="el-icon-book" v-model="bookAuthor"
                class="ml-5"></el-input>
      <el-input style="width: 200px" placeholder="请输入出版社" suffix-icon="el-icon-message"
                v-model="bookPublisher"
                class="ml-5"></el-input>
      <el-button class="ml-5" type="primary" @click="handleSearch">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>

    <el-table v-bind:data="tableData" border: stripe :header-cell-class-name="headerBg" row-key="id">
      <el-table-column prop="bookId" label="ID" width="50"></el-table-column>
      <el-table-column prop="bookName" label="书名" width="250"></el-table-column>
      <el-table-column prop="bookAuthor" label="作者" width="200"></el-table-column>
      <el-table-column prop="bookPublisher" label="出版社" width="200"></el-table-column>
      <el-table-column prop="bookDescription" label="简述"></el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleBorrow(scope.row)">借阅<i class="el-icon-edit"></i></el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total=total>
        >
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "BookBook",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      bookName: null,
      bookAuthor: null,
      bookPublisher: null,
      headerBg: 'headerBg',
      readerNow: {}
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
    async sendReq() {
      // this.pageNum = 1;
      const resp = await this.axios.get("books/pageLike?pageNum=" + this.pageNum
          + "&pageSize=" + this.pageSize + "&name=null&author=null&publisher=null");
      // console.log(resp);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
    },
    async sendLikeReq() {
      // this.pageNum = 1;
      const resp = await this.axios.get(`/books/pageLike?pageNum=${this.pageNum}
      &pageSize=${this.pageSize}&name=${this.bookName}&author=${this.bookAuthor}&publisher=${this.bookPublisher}`);
      console.log(resp);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
    },
    reload() {
      this.pageNum = 1;
      this.pageSize = 5;
      this.bookAuthor = null;
      this.bookName = null;
      this.bookPublisher = null;
      this.sendReq();
    },
    handleSearch() {
      this.pageNum = 1;
      this.sendLikeReq();
    },
    async handleBorrow(row) {
      this.readerNow = JSON.parse(localStorage.getItem("user"))
      // console.log(this.readerNow.id)
      this.axios.get(`/readers/borrowBook?readerId=${ this.readerNow.readerId }&bookId=${row.bookId}`).then(res => {
        if(res.data){
          this.$message.success("借阅成功")
        }else{
          this.$message.error(`借阅失败，原因：${res.msg}`);
        }
      })
      // const resp = await this.axios.get(`/readers/borrowBook?readerId=${ this.readerNow.id }&bookId=${row.id}`)
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.sendLikeReq();
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.sendLikeReq();
    }
  },
  mounted: function () {
    this.sendLikeReq();
  }
}
</script>

<style>
.headerBg {
  background-color: #ccc !important;
}
</style>