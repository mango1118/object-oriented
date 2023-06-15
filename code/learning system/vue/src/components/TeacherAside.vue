<template>
  <el-menu :collapse="isCollapse" :collapse-transition="false"
           :default-openeds="['1', '3']"
           active-text-color="#ffd04b"
           background-color="rgb(48,65,86)"
           router
           style="min-height: 935px; overflow-x: hidden"
           text-color="#fff"
           @select="handleSelect"
  >
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img alt="" src="@/assets/logo.png" style="width: 20px; position:relative; top:5px; margin-right: 5px; ">
      <!--这里不能用logoTextShow 原因未知-->
      <b v-show="!isCollapse" style="color: white">在线学习系统 教师</b>
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
      <el-menu-item index="/teacher/teacherPaperSearch">
        <i class="el-icon-document"></i>
        <span slot="title">试卷查询</span>
      </el-menu-item>
      <el-menu-item index="/teacher/teacherQuestionControl">
        <i class="el-icon-folder-opened"></i>
        <span slot="title">题目管理</span>
      </el-menu-item>
      <!--      <el-menu-item index="/teacher/teacherQuestionProperty">-->
      <!--        <i class="el-icon-edit-outline"></i>-->
      <!--        <span slot="title">题目性质</span>-->
      <!--      </el-menu-item>-->
      <el-menu-item index="/teacher/teacherAutoCompose">
        <i class="el-icon-magic-stick"></i>
        <span slot="title">自动组卷</span>
      </el-menu-item>
      <el-menu-item index="/teacher/teacherManualCompose">
        <i class="el-icon-view"></i>
        <span slot="title">手动组卷</span>
      </el-menu-item>
      <el-menu-item index="/teacher/teacherCorrectAnswer">
        <i class="el-icon-check"></i>
        <span slot="title">改卷功能</span>
      </el-menu-item>
      <el-menu-item index="/teacher/teacherGivePaper">
        <i class="el-icon-s-promotion"></i>
        <span slot="title">发卷功能</span>
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