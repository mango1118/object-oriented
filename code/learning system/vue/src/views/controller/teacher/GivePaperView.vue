<template>
  <!--
   JSON格式如下
    {
      "msg":"msg1",
      "code":"123".
      "data":{
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
      }
    }
  -->

  <!--  <div>
      <h2>选择班级:</h2>
      <el-select v-model="selectedClass" placeholder="请选择班级" @change="fetchPapers">
        <el-option v-for="classItem in classes" :key="classItem.id" :label="classItem.name"
                   :value="classItem.id"></el-option>
      </el-select>
      &lt;!&ndash;    <el-button type="primary" @click="confirmClassSelection" :disabled="selectedClass === null">确定选择班级</el-button>&ndash;&gt;
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
    </div>-->

  <div class="paper-management">
    <h2 class="section-title">选择班级:</h2>
    <el-select v-model="selectedClass" placeholder="请选择班级" @change="fetchPapers">
      <el-option v-for="classItem in classes" :key="classItem.id" :label="classItem.name"
                 :value="classItem.id"></el-option>
    </el-select>
    <br><br/>
    <h2 class="section-title">已发试卷:</h2>
    <el-card v-if="sentPapers.length > 0" class="paper-list">
      <ul>
        <li v-for="paper in sentPapers" :key="paper.id">{{ paper.name }}</li>
      </ul>
    </el-card>
    <el-empty v-else>暂无已发试卷</el-empty>
    <br><br/>
    <h2 class="section-title">未发试卷:</h2>
    <el-card v-if="unsentPapers.length > 0" class="paper-list">
      <ul>
        <li v-for="paper in unsentPapers" :key="paper.id" class="paper-item">
          <div class="paper-text">{{ paper.name }}</div>
          <el-button type="primary" @click="sendPaper(paper.id)">发送试卷</el-button>
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
    /*    fetchClasses() {
          // 向后端发起异步请求，获取所有班级的数据
          axios.get('http://localhost:9090/teachers/classes')
              .then(response => {
                // 将返回的班级数据存储在classes中
                console.log(response.data)
                this.classes = response.data;
              })
              .catch(error => {
                console.error('获取班级数据失败:', error);
              });
        },*/
    fetchClasses() {
      axios.get('http://localhost:9090/teachers/classes')
          .then(response => {

            this.classes = response.data.data;
          })
          .catch(error => {
            console.error('获取班级数据失败:', error);
          });
    },

    /*    fetchPapers() {
          // 根据选择的班级从后端获取相应的试卷数据
          if (this.selectedClass !== null) {
            axios.get(`/teachers/papers?classId=${this.selectedClass}`)
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
        },*/
    /*    fetchPapers() {
          // 根据选择的班级从后端获取相应的试卷数据
          if (this.selectedClass !== null) {
            axios.get(`http://localhost:9090/teachers/papers?classId=${this.selectedClass}`)
                .then(response => {
                  // 将返回的试卷数据存储在 papers 中
                  this.papers = response.data.papers;
                })
                .catch(error => {
                  console.error('获取试卷数据失败:', error);
                });
          } else {
            this.papers = [];
          }
        },*/
    fetchPapers() {
      // 根据选择的班级从后端获取相应的试卷数据
      if (this.selectedClass !== null) {
        axios.get(`http://localhost:9090/teachers/papers?classId=${this.selectedClass}`)
            .then(response => {
              this.papers = response.data.data;
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
      axios.post(`http://localhost:9090/teachers/send-paper`, {paperId, classId: this.selectedClass})
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
.paper-management {
  margin: 20px;
}

.section-title {
  font-size: 20px;
  margin-bottom: 10px;
}

.paper-list {
  margin-top: 10px;
}

.paper-list ul {
  list-style-type: none;
  padding: 0;
}

.paper-item {
  margin-bottom: 5px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.paper-text {
  flex: 1;
}

.paper-item .el-button {
  margin-left: 2px;
}
</style>