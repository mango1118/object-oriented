<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入书库类型" suffix-icon="el-icon-search"
                v-model="stackType"></el-input>
      <el-input style="width: 200px" placeholder="请输入书库地址" suffix-icon="el-icon-put" v-model="stackAddress"
                class="ml-5"></el-input>
      <el-button class="ml-5" type="primary" @click="handleSearch">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>

    <el-table v-bind:data="tableData" border: stripe :header-cell-class-name="headerBg" row-key="id">
      <el-table-column prop="stackId" label="ID" width="50"></el-table-column>
      <el-table-column prop="stackType" label="书库类型" width="200"></el-table-column>
      <el-table-column prop="stackAddress" label="书库地址"></el-table-column>
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
  name: "StackView",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      stackType: null,
      stackAddress: null,
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
      const resp = await this.axios.get("stacks/pageLike?pageNum=" + this.pageNum
          + "&pageSize=" + this.pageSize + "&type=null&address=null");
      // console.log(resp);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
    },
    async sendLikeReq() {
      // this.pageNum = 1;
      const resp = await this.axios.get(`/stacks/pageLike?pageNum=${this.pageNum}
      &pageSize=${this.pageSize}&type=${this.stackType}&address=${this.stackAddress}`);
      // console.log(resp);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
    },
    reload() {
      this.pageNum = 1;
      this.pageSize = 5;
      this.stackType = null;
      this.stackAddress = null;
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