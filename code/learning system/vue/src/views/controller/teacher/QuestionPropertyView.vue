<template>

  <div>
    <h2>问题管理页面</h2>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入问题id" suffix-icon="el-icon-search"
                v-model="questionId"></el-input>
      <el-input style="width: 200px" placeholder="请输入问题类型" suffix-icon="el-icon-question" v-model="questionType"
                class="ml-5"></el-input>
      <el-input style="width: 200px" placeholder="请输入问题知识点" suffix-icon="el-icon-message"
                v-model="questionKnowledgePoint"></el-input>
      <el-input style="width: 200px" placeholder="请输入问题章节" suffix-icon="el-icon-message"
                v-model="questionChapter"></el-input>
      <el-button class="ml-5" type="primary" @click="handleSearch">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
    </div>

    <!--
      需要后端返回一个封装到com.oo.controller.R.Result的对象
      对象的类名随意，属性名请与下述表单一一对应
      el-table-column prog="xxx" 其中xxx是属性名
-->
    <el-table v-bind:data="tableData" border: stripe :header-cell-class-name="headerBg" row-key="id">
      <el-table-column prop="questionId" label="ID" width="50"></el-table-column>
      <el-table-column prop="questionContent" label="内容" width="300"></el-table-column>
      <el-table-column prop="questionType" label="类型" width="100"></el-table-column>
      <el-table-column prop="questionAnswer" label="答案" width="300"></el-table-column>
      <el-table-column prop="questionKnowledgePoint" label="知识点" width="100"></el-table-column>
      <el-table-column prop="questionChapter" label="章节" width="100"></el-table-column>
      <el-table-column prop="questionErrorRate" label="错误率" width="100"></el-table-column>
      <el-table-column prop="questionErrorPoint" label="易错点" width="100"></el-table-column>
      <el-table-column prop="questionDifficulty" label="难度" width="50"></el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="handleDelete(scope.row.questionId)"
          >
            <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i>
            </el-button>
          </el-popconfirm>
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

    <el-dialog title="题目信息" :visible.sync="saveDialogFormVisible" width="30%">
      <el-form :model="saveform" label-width="80px" size="small">
        <el-form-item label="内容">
          <el-input v-model="saveform.questionContent" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-input v-model="saveform.questionType" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="答案">
          <el-input v-model="saveform.questionCorrectAnswer" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="知识点">
          <el-input v-model="saveform.questionKnowledgePoint" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="章节">
          <el-input v-model="saveform.questionChapter" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="错误率">
          <el-input v-model="saveform.questionErrorRate" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="易错点">
          <el-input v-model="saveform.questionErrorPoint" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="难度">
          <el-input v-model="saveform.questionDifficulty" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveForm">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="题目信息" :visible.sync="editDialogFormVisible" width="30%">
      <el-form :model="editform" label-width="80px" size="small">
        <el-form-item label="内容">
          <el-input v-model="saveform.questionContent" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-input v-model="saveform.questionType" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="答案">
          <el-input v-model="saveform.questionCorrectAnswer" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="知识点">
          <el-input v-model="saveform.questionKnowledgePoint" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="章节">
          <el-input v-model="saveform.questionChapter" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="错误率">
          <el-input v-model="saveform.questionErrorRate" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="易错点">
          <el-input v-model="saveform.questionErrorPoint" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="难度">
          <el-input v-model="saveform.questionDifficulty" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="editForm">确 定</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>

export default {
  name: "QuestionPropertyView",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      questionId: null,
      questionContent: null,
      questionCorrectAnswer: null,
      questionErrorRate: null,
      questionErrorPoint: null,
      questionDifficulty: null,
      questionType: null,
      questionKnowledgePoint: null,
      questionChapter: null,
      saveDialogFormVisible: false,
      editDialogFormVisible: false,
      saveform: {},
      editform: {},
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
      const resp = await this.axios.get("questions/pageLike?pageNum=" + this.pageNum
          + "&pageSize=" + this.pageSize + "&questionId=null&questionType=null&questionKnowledgePoint=null" +
          "&questionChapter=null");
      console.log(resp);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
    },
    async sendLikeReq() {
      // this.pageNum = 1;
      const resp = await this.axios.get(`/questions/pageLike?pageNum=${this.pageNum}
      &pageSize=${this.pageSize}&questionId=${this.questionId}&questionType=${this.questionType}
      &questionKnowledgePoint=${this.questionKnowledgePoint}&questionChapter=${this.questionChapter}`);
      console.log(resp);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
    },
    reload() {
      this.pageNum = 1;
      this.pageSize = 5;
      this.questionId = null;
      this.questionType = null;
      this.questionKnowledgePoint = null;
      this.questionChapter = null;
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
    handleAdd() {
      this.saveDialogFormVisible = true;
      this.saveform = {};
    },
    handleEdit(row) {
      this.editform = row;
      this.editDialogFormVisible = true;
    },
    handleDelete(id) {
      this.axios.delete(`/questions/${id}`).then(res => {
        if (res.data) {
          this.$message.success("删除成功");
          this.sendReq();
        } else {
          this.$message.error(`删除失败，原因：${res.msg}`);
        }
      })
    },
    saveForm() {
      this.axios.post("/questions", this.saveform).then(res => {
        // console.log(res.data);
        if (res.data) {
          this.$message.success("保存成功");
          this.saveDialogFormVisible = false;
          this.sendReq();
        } else {
          this.$message.error(`保存失败，原因：${res.msg}`);
        }
      })
      this.sendReq();
    },
    editForm() {
      this.axios.put("/questions", this.editform).then(res => {
        console.log(res.data);
        if (res.data) {
          this.$message.success("编辑成功");
          this.editDialogFormVisible = false;
          this.sendReq();
        } else {
          this.$message.error(`编辑失败，原因：${res.msg}`);
        }
      })
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