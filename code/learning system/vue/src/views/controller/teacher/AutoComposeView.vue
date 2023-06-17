<template>

  <!--
  前端需要的JSON格式如下
  {
    "msg":"信息1”,
    "data":[
      "data":[
        {
          "content":"题干1",
          "score":"分值1"
        },
        {
          "content":"题干2",
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
    <h2>自动组卷页面</h2>
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
      <el-form-item label="试卷难度" prop="difficulty">
        <el-radio-group v-model="examData.difficulty">
          <el-radio label="1">1</el-radio>
          <el-radio label="2">2</el-radio>
          <el-radio label="3">3</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="选择题数量" prop="multipleChoiceCount">
        <el-input-number v-model="examData.multipleChoiceCount" :max="8 - examData.fillInTheBlankCount - examData.subjectiveCount"
                         :min="0"></el-input-number>
      </el-form-item>
      <el-form-item label="填空题数量" prop="fillInTheBlankCount">
        <el-input-number v-model="examData.fillInTheBlankCount" :max="8 - examData.multipleChoiceCount - examData.subjectiveCount"
                         :min="0"></el-input-number>
      </el-form-item>
      <el-form-item label="主观题数量" prop="subjectiveCount">
        <el-input-number v-model="examData.subjectiveCount" :max="8 - examData.multipleChoiceCount - examData.fillInTheBlankCount"
                         :min="0"></el-input-number>
      </el-form-item>
      <el-form-item>
        <el-button class="ml-5" type="primary" @click="submitForm">提交</el-button>
      </el-form-item>
    </el-form>

    <br>
    <el-descriptions :column="1" :size="size" border class="margin-top" title="试卷信息">

      <el-descriptions-item label="试卷名称">{{ examPaper.name }}</el-descriptions-item>
      <el-descriptions-item label="试卷总分">{{ examPaper.totalScore }}</el-descriptions-item>
      <el-descriptions-item label="试卷ID">{{ examPaper.id }}</el-descriptions-item>

    </el-descriptions>
    <br>

    <el-table :header-cell-class-name="headerBg" border: row-key="id" stripe v-bind:data="tableData">
      <el-table-column label="题干" prop="content" width="800"></el-table-column>
      <el-table-column label="分值" prop="score" width="250"></el-table-column>
    </el-table>

  </div>
</template>

<script>

export default {
  data() {
    return {
      examData: {
        name: '',
        totalScore: null,
        multipleChoiceCount: null,
        fillInTheBlankCount: null,
        subjectiveCount: null,
        difficulty:null
      },
      formRules: {
        name: [
          {required: true, message: '请输入试卷名称', trigger: 'blur'}
        ],
        totalScore: [
          {required: true, message: '请选择试卷总分', trigger: 'change'},
        ],
        multipleChoiceCount: [
          {required: true, message: '请输入选择题数量', trigger: 'blur'},
          {type: 'number', message: '请输入有效的数字', trigger: 'blur'},
          {validator: this.validateQuestionCount, trigger: 'change'}
        ],
        fillInTheBlankCount: [
          {required: true, message: '请输入填空题数量', trigger: 'blur'},
          {type: 'number', message: '请输入有效的数字', trigger: 'blur'},
          {validator: this.validateQuestionCount, trigger: 'change'}
        ],
        subjectiveCount: [
          {required: true, message: '请输入主观题数量', trigger: 'blur'},
          {type: 'number', message: '请输入有效的数字', trigger: 'blur'},
          {validator: this.validateQuestionCount, trigger: 'change'}
        ],
        difficulty: [
          { required: true, message: '请选择试卷难度', trigger: 'change' }
        ]
      },
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      examPaper: {
        name: '',
        totalScore: '',
        id: '',
        difficulty:''
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
    validateQuestionCount(rule, value, callback) {
      const remainingCount = 8 - this.examData.multipleChoiceCount - this.examData.fillInTheBlankCount - this.examData.subjectiveCount;
      if (remainingCount < 0) {
        callback(new Error('题目数量总和不得超过8'));
      } else {
        callback();
      }
    },

    submitForm() {
      this.$refs.examForm.validate(async (valid) => {
        if (valid) {
          // 表单校验通过，进行题目数量总和的验证
          const {multipleChoiceCount, fillInTheBlankCount, subjectiveCount} = this.examData;
          const totalQuestionCount = multipleChoiceCount + fillInTheBlankCount + subjectiveCount;
          if (totalQuestionCount === 8) {
            // 题目数量总和正确，执行提交操作
            const formData = {
              name: name,
              totalScore: this.totalScore,
              multipleChoiceCount: this.multipleChoiceCount,
              fillInTheBlankCount: this.fillInTheBlankCount,
              subjectiveCount: this.subjectiveCount
            };

            this.$message.success("提交成功");

            // 发送POST请求，将表单数据传递给后端,要求后端返回一个JSON格式的RESPOND
            const resp = await this.axios.post('/autoCompose/submitForm', formData);
            // console.log(resp);
            this.tableData = resp.data.data;
            this.total = resp.data.total;
            this.examPaper = resp.data.examPaper;
          } else {
            // 题目数量总和不正确，显示警告消息
            this.$message.error("题目数量总和应为15！");
          }
        } else {
          // 表单校验不通过，显示警告消息
          this.$message.error("请填写完整的表单信息！");
        }
      });
    }

  }
};
</script>

<style>
.headerBg {
  background-color: #ccc !important;
}
</style>