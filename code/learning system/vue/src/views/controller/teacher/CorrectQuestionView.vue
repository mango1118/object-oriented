<template>
  <!--
    json
    {
      "msg":"msg1",
      "code":"123",
      "data":[
        "data":[
          {
            "id":"1",
            "content":"题目1内容",
            "answerImage":"url1",
            "score":10,
            "teacherScore":0,
            "submitted":false
          },
          {
            "id":"2",
            "content":"题目1内容",
            "answerImage":"url2",
            "score":15,
            "teacherScore":0,
            "submitted":true
          },
        ]
      ]
    }
  -->


  <div>
    <h2>批改题目</h2>
<!--    <el-card v-for="(question, index) in questions" :key="index" class="question-card">
      <div class="question-content">{{ question.content }}</div>
      <div class="student-answer">
        <img :src="question.answerImage" alt="学生作答">
      </div>
      <div class="score-row">
        <div class="question-score">分值: {{ question.score }}</div>
        <div class="teacher-score">
          <el-form ref="scoreForm" :model="question" label-width="60px">
            <el-form-item label="教师给分" label-width="20">
              <el-input-number v-model="question.teacherScore" :disabled="question.submitted" :max="question.score"
                               :min="0"></el-input-number>
            </el-form-item>
          </el-form>
        </div>
        <el-button :disabled="question.submitted" class="confirm-button" type="primary" @click="confirmScore(index)">
          确认
        </el-button>
      </div>
    </el-card>-->
    <el-card v-for="(question, index) in questions" :key="index" class="question-card">
      <div class="question-content">
        <img v-if="question.content" :src="question.content" alt="题目内容">
      </div>
      <div class="student-answer">
        <img :src="question.answerImage" alt="学生作答">
      </div>
      <div class="score-row">
        <div class="question-score">分值: {{ question.score }}</div>
        <div class="teacher-score">
          <el-form ref="scoreForm" :model="question" label-width="60px">
            <el-form-item label="教师给分" label-width="20">
              <el-input-number v-model="question.teacherScore" :disabled="question.submitted" :max="question.score" :min="0"></el-input-number>
            </el-form-item>
          </el-form>
        </div>
        <el-button :disabled="question.submitted" class="confirm-button" type="primary" @click="confirmScore(index)">
          确认
        </el-button>
      </div>
    </el-card>

  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      questions: []
    };
  },
  mounted() {
    // 从后端获取题目数据的逻辑
    this.fetchQuestions();
  },
  methods: {
    fetchQuestions() {
      axios
          .get('/api/questions') // 通过合适的URL获取题目数据
          .then(response => {
            this.questions = response.data;
          })
          .catch(error => {
            console.error('Failed to fetch questions:', error);
          });
    },
    confirmScore(index) {
      const question = this.questions[index];
      if (question.teacherScore > question.score) {
        // 教师给分超过题目分值，可以给出提示或采取其他处理方式
        this.$message.error('教师给分不能超过题目分值');
        return;
      }

      question.submitted = true;

      // 构建需要发送的表单数据
      const formData = {
        questionId: question.id,
        teacherScore: question.teacherScore
      };

      // 使用axios或其他HTTP客户端库发送表单数据到后端
      axios
          .post('/api/submit-score', formData) // 根据实际情况修改URL和发送方法
          .then(response => {
            // 处理成功发送后的逻辑
            console.log('Score submitted successfully!');
            this.questions.splice(index, 1);
          })
          .catch(error => {
            // 处理发送失败的逻辑
            console.error('Failed to submit score:', error);
          });
    }
  }
};
</script>

<style scoped>
.question-card {
  margin-bottom: 20px;
  padding: 20px;
}

.question-content {
  font-weight: bold;
}

.student-answer img {
  max-width: 200px;
  max-height: 200px;
  margin-top: 10px;
}

/*.score-row {*/
/*  margin-top: 10px;*/
/*  display: flex;*/
/*  align-items: center;*/
/*}*/

.question-score {
  margin-top: 10px;
}

.teacher-score {
  margin-top: 10px;
}

.confirm-button {
  margin-top: 20px;
  margin-left: 300px;
}
</style>
