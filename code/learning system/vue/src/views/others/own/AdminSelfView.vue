<template>
  <div>
    <!--    <el-dialog title="读者信息" :visible.sync="saveDialogFormVisible" width="30%">-->
    <!--    <el-dialog title="读者信息" :visible.sync=true width="30%">-->
    <div style="box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04); width: 750px">
      <h1 style="height: 40px; margin-left: 20px;">修改个人信息</h1>
      <el-form :model="saveform" label-width="80px" size="small" style="width: 600px">
        <el-form-item label="账号" prop="account">
          <el-input v-model="saveform.studentAccount" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="saveform.studentPassword" autocomplete="off" show-password></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="height: 40px; text-align: right; margin-right: 15px">
        <el-button type="warning" @click="resetForm">重 置</el-button>
        <el-button type="primary" @click="saveForm">确 定</el-button>
      </div>
    </div>
    <!--    </el-dialog>-->
  </div>
</template>

<script>

export default {
  name: "AdminSelfView",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      studentNow: {},
      studentAccount: null,
      studentPassword: null,
      saveform: {},
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
    sendReq() {
      this.axios.get(`/students/${this.studentNow.id}`).then(res => {
        console.log(res)
        localStorage.setItem("user", JSON.stringify(res.data));
        this.saveform = res.data
        this.studentNow = res.data
      })
    },
    saveForm() {
      this.saveform.level = this.studentNow.level
      this.axios.put("/students", this.saveform).then(res => {
        // console.log(res.data);
        if (res.data) {
          this.$message.success("保存成功");
          this.sendReq();
        } else {
          this.$message.error(`保存失败，原因：${res.msg}`);
        }
      })
    },
    resetForm() {
      this.sendReq()
      this.saveForm = this.studentNow
    },
  },
  mounted: function () {
    // this.sendLikeReq();
    this.studentNow = JSON.parse(localStorage.getItem("user"))
    console.log(this.studentNow)
    this.saveform = this.studentNow
  }
}
</script>

<style scoped>

</style>