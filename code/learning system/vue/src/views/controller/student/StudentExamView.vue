<template>
  <!--
  前端需要的JSON格式如下
  {
    "multipleChoiceQuestions": [
      {
        "id": 1,
        "question": "选择题1",
        "options": ["选项A", "选项B", "选项C", "选项D"]
      },
      {
        "id": 2,
        "question": "选择题2",
        "options": ["选项A", "选项B", "选项C", "选项D"]
      }
    ],
    "fillInTheBlankQuestions": [
      {
        "id": 1,
        "question": "填空题1"
      },
      {
        "id": 2,
        "question": "填空题2"
      }
    ],
    "subjectiveQuestions": [
      {
        "id": 1,
        "questionImage": "/api/images/question1.jpg",
        "answerImage": "/api/images/answer1.jpg"
      },
      {
        "id": 2,
        "questionImage": "/api/images/question2.jpg",
        "answerImage": "/api/images/answer2.jpg"
      }
    ]
  }
  -->
  <div>
    <h2>学生考试页面</h2>

    <!-- 选择题 -->
    <h3>选择题</h3>
    <el-card v-for="(question, index) in multipleChoiceQuestions" :key="index">
      <p>{{ question.question }}</p>
      <el-select v-model="selectedAnswers[index]">
        <el-option
            v-for="(option, optionIndex) in question.options"
            :key="optionIndex"
            :label="option"
            :value="option"
        ></el-option>
      </el-select>
    </el-card>

    <!-- 填空题 -->
    <h3>填空题</h3>
    <el-card v-for="(question, index) in fillInTheBlankQuestions" :key="index">
      <p>{{ question.question }}</p>
      <el-input v-model="fillInTheBlankAnswers[index]" type="text"></el-input>
    </el-card>

    <!-- 主观题 --> <h3>主观题</h3>
    <el-card v-for="(question, index) in subjectiveQuestions" :key="index">
      <p>{{ question.question }}</p>
      <el-upload
          :action="uploadUrl"
          :on-success="handleUploadSuccess"
          :show-file-list="false"
          class="upload-demo"
      >
        <el-button slot="trigger" size="small" type="primary">点击上传</el-button>
      </el-upload>
      <div v-if="uploadedImages[index]">
        <img :src="uploadedImages[index]" alt="回答图片"/>
      </div>
    </el-card>

    <!-- 提交按钮 -->
    <el-button :disabled="!isFormComplete" type="success" @click="submitForm">点击提交
    </el-button>

  </div>
</template>

<script>

export default {
  name: "studentExamView",
  data() {
    return {
      multipleChoiceQuestions: [],
      fillInTheBlankQuestions: [],
      subjectiveQuestions: [],
      selectedAnswers: [],
      fillInTheBlankAnswers: [],
      uploadedImages: [], // 保存上传的图片路径

      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      studentNow: {},
      studentPaperName: null,
      studentPaperId: null,
      headerBg: 'headerBg'
    };
  },
  computed: {
    isFormComplete() {
      return (
          this.selectedAnswers.length === this.multipleChoiceQuestions.length &&
          this.fillInTheBlankAnswers.length === this.fillInTheBlankQuestions.length &&
          this.uploadedImages.length === this.subjectiveQuestions.length
      );
    },
    uploadUrl() {
      // 设置图片上传的后端接口地址
      return '/api/upload';
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
    async fetchQuestions() {
      try {
        const response = await fetch('/api/questions'); // 替换为实际的后端接口地址
        const data = await response.json();

        this.multipleChoiceQuestions = data.multipleChoiceQuestions;
        this.fillInTheBlankQuestions = data.fillInTheBlankQuestions;
        this.subjectiveQuestions = data.subjectiveQuestions;
      } catch (error) {
        console.error('获取题目失败:', error);
      }
    },
    submitForm() {
      if (this.isFormComplete) {
        // 在这里编写提交表单的逻辑，可以将选择题、填空题和主观题的答案一起发送到后端
        console.log("表单已提交");
      } else {
        console.log("请完成所有题目后再提交表单");
      }
    },
    handleUploadSuccess(response, file) {
      // 上传成功后，将图片路径保存到uploadedImages数组中
      this.uploadedImages.push(response.filePath);
    },
  },
  mounted() {
    this.fetchQuestions(); // 页面加载时获取题目数据
  }
};
</script>

<style>
.headerBg {
  background-color: #ccc !important;
}
</style>