<template>

  <div>
    <h2>学生试卷查询</h2>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入考试id" suffix-icon="el-icon-search"
                v-model="paperId"></el-input>
      <el-input style="width: 200px" placeholder="请输入考试名" suffix-icon="el-icon-studentPaper"
                v-model="paperName"
                class="ml-5"></el-input>
      <el-button class="ml-5" type="primary" @click="handleSearch">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>

    <!--
          需要后端返回一个封装到com.oo.controller.R.Result的对象
          对象的类名随意，属性名请与下述表单一一对应
          el-table-column prog="xxx" 其中xxx是属性名
    -->
    <el-table v-bind:data="tableData" border: stripe :header-cell-class-name="headerBg" row-key="id">
      <el-table-column prop="paperId" label="考试id" width="250"></el-table-column>
      <el-table-column prop="paperName" label="考试名" width="250"></el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleSelect(scope.row.paperId)">挑选<i class="el-icon-edit"></i>
          </el-button>
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
  name: "StudentPaperSearch",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      studentNow: {},
      paperName: null,
      paperId: null,
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
      const resp = await this.axios.get("studentPapers/pageLike?pageNum=" + this.pageNum
          + "&pageSize=" + this.pageSize + "studentId=" + this.studentNow.id);
      // console.log(resp);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
    },
    async sendLikeReq() {
      // this.pageNum = 1;
      const resp = await this.axios.get(`/studentPapers/pageLike?pageNum=${this.pageNum}
      &pageSize=${this.pageSize}&studentPaperId=${this.studentPaperId}&studentPaperName=${this.studentPaperName}
      &studentId=${this.studentNow.id}`);
      // console.log(resp);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
    },
    reload() {
      this.pageNum = 1;
      this.pageSize = 5;
      this.studentPaperId = null;
      this.studentPaperName = null;
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
    },
    handleSelect(id) {
      this.$router.push("student/studentExamView/" + id);
    },
  },
  mounted: function () {
    this.studentNow = JSON.parse(localStorage.getItem("user"))
    console.log(this.studentNow)
    this.sendLikeReq();
  }
}
</script>

<style>
.headerBg {
  background-color: #ccc !important;
}
</style>