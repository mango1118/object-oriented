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
      <b style="color: white" v-show="!isCollapse">在线学习系统 教师</b>
    </div>
    <el-submenu index="1-1">
      <template slot="title">
        <i class="el-icon-more-outline"></i>
        <span slot="title">个人功能</span>
      </template>
      <el-menu-item index="/teacher/home">
        <i class="el-icon-house"></i>
        <span slot="title">回到主页</span>
      </el-menu-item>
      <el-menu-item index="/teacher/self">
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
      <el-menu-item index="/teacher/question-control">
        <i class="el-icon-folder-opened"></i>
        <span slot="title">题目管理</span>
      </el-menu-item>
      <el-menu-item index="/teacher/question-property">
        <i class="el-icon-edit-outline"></i>
        <span slot="title">题目性质</span>
      </el-menu-item>
      <el-menu-item index="/teacher/auto-compose">
        <i class="el-icon-magic-stick"></i>
        <span slot="title">自动组卷</span>
      </el-menu-item>
      <el-menu-item index="/teacher/manual-compose">
        <i class="el-icon-view"></i>
        <span slot="title">手动组卷</span>
      </el-menu-item>
    </el-submenu>

  </el-menu>
</template>


<script>

export default {
  name: "TeacherAside",
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