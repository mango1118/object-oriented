<template>
  <div>
    <h2>手动组卷页面</h2>
    <br>

    <el-form ref="examForm" :model="examData" :rules="formRules" label-width="120px" label-position="left">
      <el-form-item label="试卷名称" prop="name">
        <el-input v-model="examData.name"></el-input>
      </el-form-item>
      <el-form-item label="试卷总分" prop="totalScore">
        <el-radio-group v-model="examData.totalScore">
          <el-radio label="100">100</el-radio>
          <el-radio label="150">150</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button class="ml-5" type="primary" @click="submitForm1">提交</el-button>
      </el-form-item>
    </el-form>

    <br>
    <el-descriptions class="margin-top" title="试卷信息" :column="1" :size="size" border>

      <el-descriptions-item label="试卷ID">{{ examPaper.id }}</el-descriptions-item>
      <el-descriptions-item label="试卷名称">{{ examPaper.name }}</el-descriptions-item>
      <el-descriptions-item label="试卷总分">{{ examPaper.totalScore }}</el-descriptions-item>

    </el-descriptions>
    <br>
    <h2>挑选题目</h2>

    <h3>题目列表</h3>
    <div v-for="question in questions" :key="question.id">
      <el-checkbox v-model="question.selected" @change="updateSelectedCount(question)">{{
          question.title
        }}
      </el-checkbox>
      <el-input v-model="question.score" type="number" :disabled="!question.selected" placeholder="分值"></el-input>
    </div>
    <h3>已选题目：{{ selectedCount }}/15</h3>
    <el-button type="primary" @click="submitForm2">提交</el-button>

  </div>
</template>

<script>

export default {
  data() {
    return {
      questions: [],
      selectedCount: 0,

      examData: {
        name: '',
        totalScore: null,
        multipleChoiceCount: null,
        fillInTheBlankCount: null,
        subjectiveCount: null
      },
      formRules: {
        name: [
          {required: true, message: '请输入试卷名称', trigger: 'blur'}
        ],
        totalScore: [
          {required: true, message: '请选择试卷总分', trigger: 'change'},
        ],
      },
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
    };
  },
  computed: {
    isFormValid() {
      return this.$refs.examForm.validate();
    }
  },
  methods: {
    updateSelectedCount(question) {
      if (question.selected) {
        this.selectedCount++;
      } else {
        this.selectedCount--;
      }
    },
    submitForm1() {
      this.$refs.examForm.validate(async (valid) => {
            if (valid) {
              const formData = {
                name: name,
                totalScore: this.totalScore,
                multipleChoiceCount: this.multipleChoiceCount,
                fillInTheBlankCount: this.fillInTheBlankCount,
                subjectiveCount: this.subjectiveCount
              };
              this.$message.success("提交成功");

              // 发送POST请求，将表单数据传递给后端,要求后端返回一个JSON格式的RESPOND
              const resp = await this.axios.post('/manualCompose/submitForm', formData);
              // console.log(resp);
              this.questions = resp.data.questions;
              this.total = resp.data.total;
              this.examPaper = resp.data.examPaper;
            } else {
              // 表单校验不通过，显示警告消息
              this.$message.error("请填写完整的表单信息！");
            }
          }
      )
      ;
    },
    async submitForm2() {
      // 检查是否选中了15道题目
      if (this.selectedCount === 15) {
        // 构建提交的数据
        const selectedQuestions = this.questions.filter(question => question.selected);
        const formData = {
          selectedQuestions: selectedQuestions.map(question => {
            return {
              id: question.id,
              score: question.score
            };
          })
        };
        const resp = await this.axios.post('/manualCompose/submitForm', formData);
        this.$message.success("提交成功");
      } else {
        this.$message.error("请选择15道题目！");
      }
    }
  }
}
;
</script>

<style scoped>

</style>