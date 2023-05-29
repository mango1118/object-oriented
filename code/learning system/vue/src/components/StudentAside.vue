<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 935px; overflow-x: hidden"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
           @select="handleSelect"
  >
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="@/assets/logo.png" alt="" style="width: 20px; position:relative; top:5px; margin-right: 5px; ">
      <!--这里不能用logoTextShow 原因未知-->
      <b style="color: white" v-show="!isCollapse">在线学习系统 学生</b>
    </div>
    <el-submenu index="1-1">
      <template slot="title">
        <i class="el-icon-more-outline"></i>
        <span slot="title">个人功能</span>
      </template>
      <el-menu-item index="/student/home">
        <i class="el-icon-house"></i>
        <span slot="title">回到主页</span>
      </el-menu-item>
      <el-menu-item index="/student/self">
        <i class="el-icon-edit"></i>
        <span slot="title">个人信息</span>
      </el-menu-item>
      <el-menu-item index="/login">
        <i class="el-icon-switch-button"></i>
        <span slot="title">退出登录</span>
      </el-menu-item>
    </el-submenu>

    <el-submenu index="1-2">
      <template slot="title">
        <i class="el-icon-search"></i>
        <span slot="title">系统功能</span>
      </template>
      <el-menu-item index="/teacher/search">
        <i class="el-icon-document"></i>
        <span slot="title">试卷查询</span>
      </el-menu-item>
      <el-menu-item index="/student/my-score">
        <i class="el-icon-s-data"></i>
        <span slot="title">我的成绩</span>
      </el-menu-item>
      <el-menu-item index="/student/my-exam">
        <i class="el-icon-notebook-2"></i>
        <span slot="title">我的考试</span>
      </el-menu-item>
    </el-submenu>

  </el-menu>
</template>

<script>

export default {
  name: "StudentAside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  },
  methods: {
    handleSelect(index) {
      console.log(this.$router.options.routes)
    }
  }
}

</script>

<style scoped>

</style>