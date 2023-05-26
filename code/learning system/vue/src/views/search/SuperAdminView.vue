<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入超级管理员账户" suffix-icon="el-icon-search"
                v-model="superAdminAccount"></el-input>
      <el-input style="width: 200px" placeholder="请输入超级管理员姓名" suffix-icon="el-icon-superAdmin" v-model="superAdminName"
                class="ml-5"></el-input>
      <el-input style="width: 200px" placeholder="请输入超级管理员邮箱" suffix-icon="el-icon-message"
                v-model="superAdminEmail"
                class="ml-5"></el-input>
      <el-button class="ml-5" type="primary" @click="handleSearch">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>

    <el-table v-bind:data="tableData" border: stripe :header-cell-class-name="headerBg" row-key="id">
      <el-table-column prop="superAdminId" label="ID" width="50"></el-table-column>
      <el-table-column prop="superAdminAccount" label="账户" width="150"></el-table-column>
      <el-table-column prop="superAdminName" label="姓名" width="100"></el-table-column>
      <el-table-column prop="superAdminEmail" label="邮箱"></el-table-column>
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
  name: "SuperAdminView",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      superAdminAccount: null,
      superAdminName: null,
      superAdminEmail: null,
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
      const resp = await this.axios.get("superAdmins/pageLike?pageNum=" + this.pageNum
          + "&pageSize=" + this.pageSize + "&account=null&name=null&email=null");
      console.log(resp);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
    },
    async sendLikeReq() {
      const resp = await this.axios.get(`/superAdmins/pageLike?pageNum=${this.pageNum}
      &pageSize=${this.pageSize}&account=${this.superAdminAccount}&name=${this.superAdminName}&email=${this.superAdminEmail}`);
      // console.log(resp);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
    },
    reload() {
      this.superAdminName = null;
      this.superAdminAccount = null;
      this.superAdminEmail = null;
      this.sendReq();
    },
    handleSearch() {
      this.pageNum = 1;
      this.sendLikeReq();
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