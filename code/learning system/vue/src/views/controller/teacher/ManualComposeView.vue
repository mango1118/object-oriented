<template>

  <div>
    <h2>手动组卷页面</h2>
    <br>

    <el-form ref="examForm" :model="examData" :rules="formRules" label-position="left" label-width="120px">
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
    <el-descriptions :column="1" :size="size" border class="margin-top" title="试卷信息">

      <el-descriptions-item label="试卷ID">{{ examPaper.id }}</el-descriptions-item>
      <el-descriptions-item label="试卷名称">{{ examPaper.name }}</el-descriptions-item>
      <el-descriptions-item label="试卷总分">{{ examPaper.totalScore }}</el-descriptions-item>

    </el-descriptions>
    <br>
    <h2>挑选题目</h2>
    <h3>题目列表</h3>
    <div v-for="question in questions" :key="question.id">
      <el-checkbox v-model="question.selected" @change="updateSelectedCount(question)" border class="question">
        {{ question.content }}
        <span class="question-type">{{ question.type }}</span>
      </el-checkbox>
      <el-input v-model="question.score" :disabled="!question.selected" placeholder="分值" type="number"></el-input>
    </div>
    <h3>已选题目：{{ selectedCount }}/8</h3>
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
        // multipleChoiceCount: null,
        // fillInTheBlankCount: null,
        // subjectiveCount: null
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
                name: this.name,
                totalScore: this.totalScore,
                // multipleChoiceCount: this.multipleChoiceCount,
                // fillInTheBlankCount: this.fillInTheBlankCount,
                // subjectiveCount: this.subjectiveCount
              };
              this.$message.success("提交成功");

              // 发送POST请求，将表单数据传递给后端,要求后端返回一个JSON格式的RESPOND
              const resp = await this.axios.post('/manualCompose/create', this.examData);
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
      // 检查是否选中了8道题目
      if (this.selectedCount === 8) {
        // debugger
        // 检查题目的分值是否有效
        const isValidScore = this.questions.every(question => {
          return question.selected && !isNaN(Number(question.score));
        });

        if (isValidScore) {
          // 计算题目的分值之和
          const totalScore = this.questions
              .filter(question => question.selected)
              .reduce((sum, question) => sum + Number(question.score), 0);
          // console.log(this.examData.totalScore)
          // 检查题目的分值之和是否等于试卷总分
          if (totalScore == this.examData.totalScore) {
            // 构建提交的数据
            const selectedQuestions = this.questions.filter(question => question.selected);
            const formData = {
              selectedQuestions: selectedQuestions.map(question => {
                return {
                  id: question.id,
                  score: question.score
                };
              }),
              paperId:this.examPaper.id
            };
            this.$message.success("提交成功");
            const resp = await this.axios.post('/manualCompose', formData);
          } else {
            this.$message.error("挑选的题目分值之和不等于试卷总分！");
          }
        } else {
          this.$message.error("请为每个题目输入有效的分值！");
        }
      } else {
        this.$message.error("请选择8道题目！");
      }
    }
  }
}
;
</script>

<style scoped>
.question {
  margin-top: 10px;
}

.question-type {
  margin-left: 10px;
  color: #888;
  font-style: italic;
}
</style>
