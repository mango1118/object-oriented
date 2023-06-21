<template>
  <!--
    前端需要的JSON格式如下
    {
      "msg":"信息1”,
      "data":[
        "data":[
          {
            "content":"题干1",
            "type":"选择题"
            "score":"分值1"
          },
          {
            "content":"题干2",
            "type":"主观题"
            "score":"分值2"
          }
        ],
        "examPaper":{
          "id":"试卷id",
          "name":"试卷名",
          "totalScore":"总分"
        }
      ],
      "code":"xxxxx"
    }

  -->

  <div>
    <h2>教师试卷查询</h2>
    <div style="margin: 10px 0">
      <el-input v-model="paperId" placeholder="请输入考试id" style="width: 200px"
                suffix-icon="el-icon-search"></el-input>
      <el-button class="ml-5" type="primary" @click="sendReqId">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>

    <br>
    <el-descriptions :column="1"  border class="margin-top" title="试卷信息">

      <el-descriptions-item label="试卷名称">{{ examPaper.name }}</el-descriptions-item>
      <el-descriptions-item label="试卷总分">{{ examPaper.totalScore }}</el-descriptions-item>
      <el-descriptions-item label="试卷ID">{{ examPaper.id }}</el-descriptions-item>

    </el-descriptions>
    <br>

    <!--
          需要后端返回一个封装到com.oo.controller.R.Result的对象
          对象的类名随意，属性名请与下述表单一一对应
          el-table-column prog="xxx" 其中xxx是属性名
    -->
<!--    <el-table :header-cell-class-name="headerBg" border: class="paper_info" row-key="id" stripe v-bind:data="tableData">
      <el-table-column label="题干" prop="content" width="800"></el-table-column>
      <el-table-column label="类型" prop="type" width="300"></el-table-column>
      <el-table-column label="分值" prop="score" width="250"></el-table-column>
    </el-table>-->

    <el-table :header-cell-class-name="headerBg" border class="paper_info" row-key="id" stripe :data="tableData">
      <el-table-column label="题干" width="800">
        <template slot-scope="scope">
          <template v-if="scope.row.type === '主观题'">
            <img v-if="scope.row.content" :src="scope.row.content" style="max-width: 100%; max-height: 200px;">
          </template>
          <template v-else>
            {{ scope.row.content }}
          </template>
        </template>
      </el-table-column>
      <el-table-column label="类型" prop="type" width="300"></el-table-column>
      <el-table-column label="分值" prop="score" width="250"></el-table-column>
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
  name: "TeacherPaperSearch",
  data() {
    return {

      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,

      examPaper: {
        name: '',
        totalScore: '',
        id: ''
      },
      content: null,
      score: null,

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
    async sendReqId() {
      // this.pageNum = 1;
      const resp = await this.axios.get("/papers/pageLike/?pageNum=" + this.pageNum
          + "&pageSize=" + this.pageSize + "&pid=" + this.paperId);
      // console.log(resp);
      // console.log(resp.data.data);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
      this.examPaper = resp.data.examPaper;
    },
    async sendLikeReq() {
      // this.pageNum = 1;
      const resp = await this.axios.get("/papers/pageLike/?pageNum=" + this.pageNum
          + "&pid=" + this.pageSize);
      // console.log(resp);
      this.tableData = resp.data.data.data;
      this.total = resp.data.data.total;
      this.examPaper = resp.data.data.examPaper;
    },
    reload() {
      this.pageNum = 1;
      this.pageSize = 5;
      // this.studentPaperId = null;
      // this.studentPaperName = null;
      this.paperId = null;
      this.sendReqId();
    },
    handleSearch() {
      this.pageNum = 1;
      this.sendReqId();
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.sendReqId();
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.sendReqId();
    },
    handleSelect(id) {
      this.sendReqId(id);
    },
  },
  mounted: function () {

  }
}
</script>

<style scoped>

.margin-top {
  max-width: 1100px;
}


</style>