<template>
  <div>
    <el-table v-bind:data="tableData" border: stripe :header-cell-class-name="headerBg" row-key="id">
      <el-table-column prop="borrowReader" label="读者ID" width="100"></el-table-column>
      <el-table-column prop="borrowBook" label="图书ID" width="100"></el-table-column>
      <el-table-column  label="起始日" width="200">
        <template slot-scope="scope">
          <span v-text="formatDate(scope.row.borrowStart)"></span>
        </template>
      </el-table-column>
      <el-table-column  label="还书日" >
        <template slot-scope="scope">
          <span v-text="formatDate(scope.row.borrowEnd)"></span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleRenew(scope.row)">续借<i class="el-icon-edit"></i></el-button>
          <el-button type="primary" @click="handleReturn(scope.row)">归还<i class="el-icon-edit"></i></el-button>
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

import * as dateUtils from "../../../utils/date"

export default {
  name: "ReaderOwnBorrow",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      readerId: null,
      readerNow: {},
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
    async sendReq() {
      // this.pageNum = 1;
      const resp = await this.axios.get("books/pageLike?pageNum=" + this.pageNum
          + "&pageSize=" + this.pageSize);
      // console.log(resp);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
    },
    async sendLikeReq() {
      this.readerNow = JSON.parse(localStorage.getItem("user"))
      // console.log(readerNow)
      // this.pageNum = 1;
      const resp = await this.axios.get(`/readers/myBook?pageNum=${this.pageNum}
      &pageSize=${this.pageSize}&readerId=${this.readerNow.readerId}`);
      // console.log(resp);
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
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.sendLikeReq();
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.sendLikeReq();
    },
    handleRenew(row){
      this.axios.get(`http://localhost:9090/readers/renewBook?readerId=${row.borrowReader}&bookId=${row.borrowBook}`).then(res => {
        if (res.data) {
          this.$message.success("续借成功");
          this.sendLikeReq();
        } else {
          this.$message.error(`续借失败，原因：${res.msg}`);
        }
      })
    },
    handleReturn(row){
      this.axios.get(`http://localhost:9090/readers/returnBook?readerId=${row.borrowReader}&bookId=${row.borrowBook}`).then(res => {
        if (res.data) {
          this.$message.success("归还成功");
          this.sendLikeReq();
        } else {
          this.$message.error(`归还失败，原因：${res.msg}`);
        }
      })
    },
    formatDate(dateStr){
      let date = new Date(dateStr);
      return dateUtils.formatDate(date, 'yyyy-MM-dd');
    }

  },
  mounted: function () {
    this.sendLikeReq();
  }
}
</script>

<style scoped>

</style>