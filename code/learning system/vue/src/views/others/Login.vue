<template>
  <div class="wrapper">
    <div
        style="margin: 200px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
      <el-form ref="userForm" :model="user" :rules="rules">
        <el-form-item prop="account">
          <el-input v-model="user.account" prefix-icon="el-icon-user" size="medium" style="margin: 10px 0"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="user.password" prefix-icon="el-icon-lock" show-password size="medium"
                    style="margin: 10px 0"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button autocomplete="off" size="small" type="primary" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
// import{setRoutes} from "@/router"

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "login",
  data() {
    return {
      user: {},
      rules: {
        account: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      }
    }
  },
  methods: {
    login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {//表单合法
          this.axios.post("http://localhost:9090/login", this.user).then(res => {
            console.log(res);
            if (res.code === 10001 || res.code === 10002 || res.code === 10003) {
              localStorage.setItem("user", JSON.stringify(res.data))
              this.$message.success("登录成功");
              if (res.code === 10001) {//学生
                this.$router.push("/student");
              } else if (res.code === 10002) {//老师
                this.$router.push("/teacher");
              } else if (res.code == 10003){ //管理员
                this.$router.push("/admin");
              }
            } else {
              this.$message.error(res.msg);
            }
          })
        }
      })
    }
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
  overflow: hidden;
}
</style>