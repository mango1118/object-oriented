<template>

  <div>
    <h2>问题管理页面</h2>
    <div style="margin: 10px 0">
      <el-input v-model="questionId" placeholder="请输入问题id" style="width: 200px"
                suffix-icon="el-icon-search"></el-input>
      <el-input v-model="type" class="ml-5" placeholder="请输入问题类型" style="width: 200px"
                suffix-icon="el-icon-question"></el-input>
      <el-input v-model="knowledgePoint" placeholder="请输入问题知识点" style="width: 200px"
                suffix-icon="el-icon-message"></el-input>
      <el-input v-model="chapter" placeholder="请输入问题章节" style="width: 200px"
                suffix-icon="el-icon-message"></el-input>
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
    <!--    <el-table :header-cell-class-name="headerBg" border: row-key="id" stripe v-bind:data="tableData">
          <el-table-column label="ID" prop="questionId" width="50"></el-table-column>
          <el-table-column label="内容" prop="content" width="300"></el-table-column>
          <el-table-column label="类型" prop="type" width="100"></el-table-column>
          <el-table-column label="答案" prop="answer" width="300"></el-table-column>
          <el-table-column label="知识点" prop="knowledgePoint" width="100"></el-table-column>
          <el-table-column label="章节" prop="chapter" width="100"></el-table-column>
          <el-table-column label="错误率" prop="errorRate" width="100"></el-table-column>
          <el-table-column label="易错点" prop="errorPoint" width="100"></el-table-column>
          <el-table-column label="难度" prop="difficulty" width="50"></el-table-column>
          <el-table-column align="center" label="操作" width="200">
            <template slot-scope="scope">
              <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
              <el-popconfirm
                  cancel-button-text='取消'
                  class="ml-5"
                  confirm-button-text='确定'
                  icon="el-icon-info"
                  icon-color="red"
                  title="您确定删除吗？"
                  @confirm="handleDelete(scope.row.questionId)"
              >
                <el-button slot="reference" type="danger">删除<i class="el-icon-remove-outline"></i>
                </el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>-->

    <el-table :header-cell-class-name="headerBg" border row-key="id" stripe v-bind:data="tableData">
      <el-table-column label="ID" prop="questionId" width="50"></el-table-column>
      <el-table-column label="内容" prop="content" width="300">
        <template slot-scope="scope">
          <div v-if="scope.row.type === '主观题'">
            <img :src="scope.row.content" style="max-width: 100%; max-height: 200px;">
          </div>
          <div v-else>
            {{ scope.row.content }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="类型" prop="type" width="100"></el-table-column>
      <el-table-column label="答案" prop="answer" width="300"></el-table-column>
      <el-table-column label="知识点" prop="knowledgePoint" width="100"></el-table-column>
      <el-table-column label="章节" prop="chapter" width="100"></el-table-column>
      <el-table-column label="错误率" prop="errorRate" width="100"></el-table-column>
      <el-table-column label="易错点" prop="errorPoint" width="100"></el-table-column>
      <el-table-column label="难度" prop="difficulty" width="50"></el-table-column>
      <el-table-column align="center" label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              cancel-button-text='取消'
              class="ml-5"
              confirm-button-text='确定'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="handleDelete(scope.row.questionId)"
          >
            <el-button slot="reference" type="danger">删除<i class="el-icon-remove-outline"></i>
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          :current-page="pageNum"
          :page-size="pageSize"
          :page-sizes="[5, 10, 20]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange">
        >
      </el-pagination>
    </div>

    <!--    <el-dialog :visible.sync="saveDialogFormVisible" title="题目信息" width="30%">
          <el-form :model="saveform" label-width="80px" size="small">
            <el-form-item label="内容">
              <el-input v-model="saveform.content" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="类型">
              <el-input v-model="saveform.type" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="saveDialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveForm">确 定</el-button>
          </div>
        </el-dialog>-->

<!--    <el-dialog :visible.sync="saveDialogFormVisible" title="题目信息" width="30%">
      <el-form :model="saveform" label-width="80px" size="small">
        <el-form-item label="类型">
          <el-input v-model="saveform.type" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <template v-if="saveform.type === '主观题'">
            &lt;!&ndash; 替换为实际的图片上传接口 &ndash;&gt;
            <el-upload
                ref="upload"
                class="upload-demo"
                action="/your-upload-api"
                :on-success="handleSaveUploadSuccess"
                :file-list="saveform.imageList"
                :show-file-list="false"
                :auto-upload="false"
                :before-upload="beforeUpload"
            >
              <el-button slot="trigger" size="small" type="primary">上传图片</el-button>
            </el-upload>
          </template>
          <template v-else>
            <el-input v-model="saveform.content" autocomplete="off"></el-input>
          </template>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveForm">确 定</el-button>
      </div>
    </el-dialog>-->

    <el-dialog :visible.sync="saveDialogFormVisible" title="题目信息" width="30%">
      <el-form :model="saveform" label-width="80px" size="small">
        <el-form-item label="类型">
          <el-input v-model="saveform.type" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <template v-if="saveform.type === '主观题'">
            <!-- 根据实际的上传接口进行设置 -->
            <el-upload
                ref="upload"
                action=""
                :auto-upload="false"
                :show-file-list="true"
                :file-list="fileList"
                :on-change="handleUploadImg"
            >
            <el-button size="small" type="primary">选择图片</el-button>
            </el-upload>
          </template>
          <template v-else>
            <el-input v-model="saveform.content" autocomplete="off"></el-input>
          </template>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveForm">确 定</el-button>
      </div>
    </el-dialog>


    <!--    <el-dialog :visible.sync="editDialogFormVisible" title="题目信息" width="30%">
          <el-form :model="editform" label-width="80px" size="small">
            <el-form-item label="内容">
              <el-input v-model="editform.content" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="类型">
              <el-input v-model="editform.type" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="答案">
              <el-input v-model="editform.answer" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="知识点">
              <el-input v-model="editform.knowledgePoint" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="章节">
              <el-input v-model="editform.chapter" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="错误率">
              <el-input v-model="editform.errorRate" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="易错点">
              <el-input v-model="editform.errorPoint" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="难度">
              <el-input v-model="editform.difficulty" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="editDialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="editForm">确 定</el-button>
          </div>
        </el-dialog>-->

    <el-dialog :visible.sync="editDialogFormVisible" title="题目信息" width="30%">
      <el-form :model="editform" label-width="80px" size="small">
        <el-form-item label="内容">
          <template v-if="editform.type === '主观题'">
            <img v-if="editform.content" :src="editform.content" style="max-width: 100%; max-height: 200px;">
            <!-- 设置上传图片的接口地址 -->
            <el-upload
                ref="upload"
                action=""
                :auto-upload="false"
                :show-file-list="true"
                :file-list="fileList"
                :on-change="handleUploadImg2"
            >
              <el-button size="small" type="primary">选择图片</el-button>
            </el-upload>
<!--            <el-upload-->
<!--                        action="/questionProperties/upload"-->
<!--                       :on-success="handleEditUploadSuccess" :show-file-list="false" :before-upload="beforeUpload">-->
<!--              <el-button size="small" type="primary">上传图片</el-button>-->
<!--            </el-upload>-->
          </template>
          <template v-else>
            <el-input v-model="editform.content" autocomplete="off"></el-input>
          </template>
        </el-form-item>
        <el-form-item label="类型">
          <el-input v-model="editform.type" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="答案">
          <el-input v-model="editform.answer" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="知识点">
          <el-input v-model="editform.knowledgePoint" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="章节">
          <el-input v-model="editform.chapter" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="错误率">
          <el-input v-model="editform.errorRate" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="易错点">
          <el-input v-model="editform.errorPoint" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="难度">
          <el-input v-model="editform.difficulty" autocomplete="off"></el-input>
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
  name: "QuestionControlView",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      questionId: null,
      content: null,
      answer: null,
      errorRate: null,
      errorPoint: null,
      difficulty: null,
      type: null,
      knowledgePoint: null,
      chapter: null,
      editDialogFormVisible: false,
      editform: {},
      headerBg: 'headerBg',
      isUploading: false,
      saveDialogFormVisible: false,
      saveform: {
        content: "",
        type: ""
      },
      fileList: []
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
      // const resp = await this.axios.get("/questionProperties/pageLike?pageNum=" + this.pageNum
      //     + "&pageSize=" + this.pageSize);
      const resp = await this.axios.get("/questionProperties/search?pageNum="+this.pageNum+
          "&pageSize="+this.pageSize+"&questionId=null&type=null&knowledgePoint=null&chapter=null");
      console.log(resp);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
    },
    async sendLikeReq() {
      // this.pageNum = 1;
      const resp = await this.axios.get(`/questionProperties/search?pageNum=${this.pageNum}
      &pageSize=${this.pageSize}&questionId=${this.questionId}&type=${this.type}
      &knowledgePoint=${this.knowledgePoint}&chapter=${this.chapter}`);
      console.log(resp.data);
      this.tableData = resp.data;
      this.total = resp.data.total;
    },
    reload() {
      this.pageNum = 1;
      this.pageSize = 5;
      this.questionId = null;
      this.type = null;
      this.knowledgePoint = null;
      this.chapter = null;
      // this.sendReq();
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
/*    saveForm() {
      // debugger
      if (!this.saveform.type) {
        this.$message.error('请完整填写表单');
        return; // Prevent form submission
      }

      if (this.saveform.type === '主观题' && this.saveform.content === '') {
        // 选择了主观题且没有填写内容，等待图片上传成功后再提交表单
        this.isUploading = true;
        return; // Prevent form submission
      }

      // 继续处理表单提交逻辑
      this.submitForm();
    },*/

    handleUploadImg(file){
      console.log('handleUploadImg')
      console.log(file)
      let formData = new FormData()
      formData.append('objQImgFile',file.raw)
      console.log(formData.get('objQImgFile'))
      this.axios
          .post("/questions/upload", formData, {
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
            this.saveform.content = response.data.url
          })
    },
    handleUploadImg2(file){
      console.log('handleUploadImg')
      console.log(file)
      let formData = new FormData()
      formData.append('objQImgFile',file.raw)
      console.log(formData.get('objQImgFile'))
      this.axios
          .post("/questions/upload", formData, {
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
            this.editform.content = response.data.url
          })
    },
    saveForm() {
      if (!this.saveform.type) {
        this.$message.error("请完整填写表单");
        return; // Prevent form submission
      }
      if(!this.saveform.content){
        if(this.saveform.type === '主观题'){
          this.$message.error("请等待图片上传成功！");
        } else this.$message.error("请完整填写表单");
        return;
      }

      let requestBody = {
        content: this.saveform.content,
        type: this.saveform.type
      };

      // if (this.saveform.type === "主观题") {
      //   // 获取上传图片的 URL
      //   const uploadComponent = this.$refs.upload;
      //   console.log(uploadComponent)
      //   const imageUrl = uploadComponent.uploadFiles[0].url;
      //   //调/question/upload,传uploadComponent.uploadFiles[0].raw，拿到返回的url再调submit-form
      //   requestBody = {
      //     content: imageUrl,
      //     type: this.saveform.type
      //   };
      // } else {
      //   requestBody =
      // }

      // 发送请求到后端，将 requestBody 作为请求体
      this.axios
          .post("/questions/submit-form", requestBody)
          .then(response => {
            // 处理请求成功的逻辑
            console.log("上传成功");
            this.saveDialogFormVisible = false;
          })
          .catch(error => {
            // 处理请求失败的逻辑
            console.error("上传失败，原因：", error);
          });
    },
    editForm() {
      // Check if any required fields are empty
      if (
          !this.editform.content ||
          !this.editform.type ||
          !this.editform.answer ||
          !this.editform.knowledgePoint ||
          !this.editform.chapter ||
          !this.editform.errorRate ||
          !this.editform.errorPoint ||
          !this.editform.difficulty
      ) {
        this.$message.error('请完整填写表单');
        return; // Prevent form submission
      }
      this.axios.put("/questionProperties", this.editform).then(res => {
        console.log(res.data);
        if (res.data) {
          this.$message.success("编辑成功");
          this.editDialogFormVisible = false;
          this.sendLikeReq();
        } else {
          this.$message.error(`编辑失败，原因：${res.msg}`);
        }
      })
    },
    submitForm(){
      this.axios.put("/api", this.saveform).then(res => {
        if (res.data) {
          this.$message.success("编辑成功");
          this.editDialogFormVisible = false;
          this.sendLikeReq();
        } else {
          this.$message.error(`编辑失败，原因：${res.msg}`);
        }
      })
    },
    handleEditUploadSuccess(response, file, fileList) {
      // 上传成功的回调函数
      // 在这里更新 editform.content 的值为新上传图片的 URL
      this.editform.content = response.url;
    },
    handleSaveUploadSuccess(response, file, fileList) {
      // 处理图片上传成功后的逻辑
      // 可以获取上传的图片地址等信息，并将其保存到 saveform.imageList 中
      // 例如：
      this.saveform.content = response.imageUrl;
      this.isUploading = false;
      this.saveForm(); // 继续提交表单
    },
    beforeUpload(file) {
      console.log(file)
      // 限制只能上传图片类型的文件
      const isImage = file.type.startsWith("image/");
      if (!isImage) {
        this.$message.error("只能上传图片文件");
      }
      // return isImage;
    },
  },
  mounted: function () {
    this.sendReq();
  }
}
</script>

<style>
.headerBg {
  background-color: #ccc !important;
}
</style>