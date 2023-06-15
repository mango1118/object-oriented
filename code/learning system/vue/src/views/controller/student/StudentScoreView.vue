<template>
  <div>
    <h2>学生成绩查询页面</h2>
    <div style="margin: 10px 0">
      <el-input v-model="studentPaperId" placeholder="请输入考试id" style="width: 200px"
                suffix-icon="el-icon-search"></el-input>
      <el-input v-model="studentPaperName" class="ml-5" placeholder="请输入考试名"
                style="width: 200px"
                suffix-icon="el-icon-studentPaper"></el-input>
      <el-button class="ml-5" type="primary" @click="handleSearch">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>

    <!--
          需要后端返回一个封装到com.oo.controller.R.Result的对象
          对象的类名随意，属性名请与下述表单一一对应
          el-table-column prog="xxx" 其中xxx是属性名
    -->
    <el-table :header-cell-class-name="headerBg" border: row-key="id" stripe v-bind:data="tableData">
      <el-table-column label="学生id" prop="studentId" width="250"></el-table-column>
      <el-table-column label="学生姓名" prop="studentName" width="250"></el-table-column>
      <el-table-column label="考试id" prop="paperId" width="200"></el-table-column>
      <el-table-column label="考试名" prop="paperName" width="200"></el-table-column>
      <el-table-column label="考试得分" prop="totalScore" width="200"></el-table-column>
    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          :current-page="pageNum"
          :page-size="pageSize"
          :page-sizes="[5, 10, 20]"
          :total=total
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange">
        >
      </el-pagination>
    </div>
  </div>

</template>

<script>

export default {
  name: "StudentScoreView",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      studentNow: {},
      studentPaperName: null,
      studentPaperId: null,
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
      const resp = await this.axios.get("/studentPapers/pageLike?pageNum=" + this.pageNum
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