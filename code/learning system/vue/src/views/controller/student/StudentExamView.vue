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

    <h3>主观题</h3>
    <el-card v-for="(question, index) in subjectiveQuestions" :key="index">
      <p>{{ question.question }}</p>
      <div v-if="question.questionImage">
        <img :src="question.questionImage" alt="题目内容"/>
      </div>
      <el-upload
          ref="upload"
          action=""
          :auto-upload="false"
          :show-file-list="true"
          :file-list="fileList"
          :on-change="handleUploadImg"
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
      paperId: null,
      studentPaperName: null,
      studentPaperId: null,
      headerBg: 'headerBg',
      fileList: []
    };
  },
  computed: {
/*    isFormComplete() {
      return (
          this.selectedAnswers.length === this.multipleChoiceQuestions.length &&
          this.fillInTheBlankAnswers.length === this.fillInTheBlankQuestions.length &&
          this.uploadedImages.length === this.subjectiveQuestions.length

      );
    },*/
    isFormComplete() {
      // 检查选择题和填空题的答案是否都已填写
      const isChoiceComplete =
          this.selectedAnswers.length === this.multipleChoiceQuestions.length;
      const isFillBlankComplete =
          this.fillInTheBlankAnswers.length === this.fillInTheBlankQuestions.length;
      // 检查主观题的图片是否已上传
      const isSubjectiveComplete =
          this.uploadedImages.length === this.subjectiveQuestions.length;

      // 返回表单是否完整的布尔值
      return isChoiceComplete && isFillBlankComplete && isSubjectiveComplete;
    },
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
        this.paperId = localStorage.getItem("paperId")
        this.studentNow = JSON.parse(localStorage.getItem("user"))
        // console.log(this.studentNow)

        const resp = await this.axios.get(`/paperQuestions/{this.paperId}?paperId=${this.paperId}`)


        console.log(resp.data)
        this.multipleChoiceQuestions = resp.data.multipleChoiceQuestions;
        this.fillInTheBlankQuestions = resp.data.fillInTheBlankQuestions;
        this.subjectiveQuestions = resp.data.subjectiveQuestions;

      } catch (error) {
        console.error('获取题目失败:', error);
      }
    },
    submitForm() {
      if (this.isFormComplete) {
        // 构造需要提交的数据
        const formData = {
          studentId: this.studentNow.id,
          paperId: this.paperId,
          multipleChoiceAnswers: this.selectedAnswers,
          fillInTheBlankAnswers: this.fillInTheBlankAnswers,
          subjectiveAnswers: this.uploadedImages
        };

        // 发送表单数据到后端
        this.axios.post('/studentPapers/submitForm', formData)
            .then(response => {
              console.log('表单提交成功:', response);
              // 处理提交成功的逻辑
            })
            .catch(error => {
              console.error('表单提交失败:', error);
              // 处理提交失败的逻辑
            });
      } else {
        console.log("请完成所有题目后再提交表单");
      }
    },
    handleUploadImg(file) {
      console.log('handleUploadImg');
      console.log(file);
      let formData = new FormData();
      formData.append('objQImgFile', file.raw);
      console.log(formData.get('objQImgFile'));
      this.axios
          .post('/questions/upload', formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            },
            transformRequest: [(data) => data]
          })
          .then(response => {
            // 处理请求成功的逻辑
            console.log(response);
            this.$message({
              message: '图片上传成功！',
              type: 'success'
            });

            // 更新题目的图片链接
            const questionIndex = this.subjectiveQuestions.findIndex(
                question => question.id === file.id
            );
            if (questionIndex !== -1) {
              this.$set(
                  this.subjectiveQuestions[questionIndex],
                  'questionImage',
                  response.data.url
              );
            }
          })
          .catch(error => {
            // 处理请求失败的逻辑
            console.log(error);
            this.$message.error('图片上传失败！');
          });
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