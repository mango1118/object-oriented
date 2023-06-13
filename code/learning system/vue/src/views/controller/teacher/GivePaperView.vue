<template>
<!--
 JSON格式如下
  {
    "msg":"msg1",
    "code":"123".
    "data":[
      "classes":[
        {"id":1, "name":"班级A"}
        {"id":2, "name":"班级B"}
        {"id":3, "name":"班级C"}
      ],
      "papers":[
         {"id":1, "name":"试卷1","sent":true},
         {"id":2, "name":"试卷2","sent":false},   sent属性：该班级是否已经被发送过这张试卷
         {"id":3, "name":"试卷3","sent":true},
      ]
    ]
  }
-->

  <div>
    <h2>选择班级:</h2>
    <el-select v-model="selectedClass" placeholder="请选择班级" @change="fetchPapers">
      <el-option v-for="classItem in classes" :key="classItem.id" :label="classItem.name" :value="classItem.id"></el-option>
    </el-select>
<!--    <el-button type="primary" @click="confirmClassSelection" :disabled="selectedClass === null">确定选择班级</el-button>-->
    <h2>已发试卷:</h2>
    <el-card v-if="sentPapers.length > 0">
      <ul>
        <li v-for="paper in sentPapers" :key="paper.id">{{ paper.name }}</li>
      </ul>
    </el-card>
    <el-empty v-else>暂无已发试卷</el-empty>
    <h2>未发试卷:</h2>
    <el-card v-if="unsentPapers.length > 0">
      <ul>
        <li v-for="paper in unsentPapers" :key="paper.id">
          {{ paper.name }}
          <el-button type="primary" @click="sendPaper(paper.id)">确定</el-button>
        </li>
      </ul>
    </el-card>
    <el-empty v-else>暂无未发试卷</el-empty>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      selectedClass: null,
      classes: [],
      papers: []
    };
  },
  computed: {
    sentPapers() {
      return this.papers.filter(paper => paper.sent);
    },
    unsentPapers() {
      return this.papers.filter(paper => !paper.sent);
    }
  },
  methods: {
    fetchClasses() {
      // 向后端发起异步请求，获取所有班级的数据
      axios.get('/api/classes')
          .then(response => {
            // 将返回的班级数据存储在classes中
            this.classes = response.data.data.classes;
          })
          .catch(error => {
            console.error('获取班级数据失败:', error);
          });
    },
    fetchPapers() {
      // 根据选择的班级从后端获取相应的试卷数据
      if (this.selectedClass !== null) {
        axios.get(`/api/papers?classId=${this.selectedClass}`)
            .then(response => {
              // 将返回的试卷数据存储在papers中
              this.papers = response.data.data.papers;
            })
            .catch(error => {
              console.error('获取试卷数据失败:', error);
            });
      } else {
        this.papers = [];
      }
    },
    sendPaper(paperId) {
      // 向选定的班级发送试卷
      // 你可以在这里发送异步请求，将试卷发送给选定的班级
      // 同时更新试卷的状态为已发送
      axios.post(`/api/send-paper`, { paperId, classId: this.selectedClass })
          .then(response => {
            // 发送成功后，更新试卷的状态为已发送
            const updatedPapers = this.papers.map(paper => {
              if (paper.id === paperId) {
                paper.sent = true;
              }
              return paper;
            });
            this.papers = updatedPapers;
          })
          .catch(error => {
            console.error('发送试卷失败:', error);
          });
    },
    // confirmClassSelection() {
    //   if (this.selectedClass !== null) {
    //     // 执行你的确认班级选择的逻辑
    //     // 可以在这里调用其他方法，或者执行其他操作
    //     console.log('已选择班级:', this.selectedClass);
    //   }
    // }
  },
  mounted() {
    this.fetchClasses(); // 在组件挂载后调用fetchClasses方法，获取班级数据
  }
};
</script>


<style scoped>

</style>