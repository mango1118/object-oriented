<template>

  <div>
    <h2>学生考试选择</h2>
    <div style="margin: 10px 0">
      <el-input v-model="paperId" placeholder="请输入考试id" style="width: 200px"
                suffix-icon="el-icon-search"></el-input>
      <el-input v-model="paperName" class="ml-5" placeholder="请输入考试名"
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
      <el-table-column label="考试id" prop="paperId" width="250"></el-table-column>
      <el-table-column label="考试名" prop="paperName" width="250"></el-table-column>
      <el-table-column align="center" label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="success" @click="handleSelect(scope.row.paperId)">挑选<i class="el-icon-edit"></i>
          </el-button>
        </template>
      </el-table-column>
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
  name: "StudentSelectExamView",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      studentNow: {},
      studentId:null,
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
      const resp = await this.axios.get("/studentPapers/pageLike?pageNum=" + this.pageNum
          + "&pageSize=" + this.pageSize + "studentId=" + this.studentNow.id);
      // console.log(resp);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
    },
    async sendLikeReq() {
      // this.pageNum = 1;
      const resp = await this.axios.get(`/studentPapers/pageLike?pageNum=${this.pageNum}
      &pageSize=${this.pageSize}&paperId=${this.paperId}&paperName=${this.paperName}
      &studentId=${this.studentNow.id}`);
      // console.log(resp);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
    },
    reload() {
      this.pageNum = 1;
      this.pageSize = 5;
      this.paperId = null;
      this.paperName = null;
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
      localStorage.setItem("paperId", JSON.stringify(id))
      // this.$router.push("/student/studentExamView/" + id);
      this.$router.push("/student/studentExam/");
    },
  },
  mounted: function () {
    this.studentNow = JSON.parse(localStorage.getItem("user"))
    console.log(this.studentNow)
    this.sendLikeReq();
  }
}
</script>

<style scoped>

</style>