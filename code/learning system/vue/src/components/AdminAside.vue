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
      <b v-show="!isCollapse" style="color: white">在线学习系统 管理</b>
    </div>
    <el-submenu index="1-1">
      <template slot="title">
        <i class="el-icon-more-outline"></i>
        <span slot="title">个人功能</span>
      </template>
      <el-menu-item index="/admin/home">
        <i class="el-icon-house"></i>
        <span slot="title">回到主页</span>
      </el-menu-item>
      <el-menu-item index="/admin/self">
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
      <el-menu-item index="/admin/teacherControl">
        <i class="el-icon-s-data"></i>
        <span slot="title">教师管理</span>
      </el-menu-item>
    </el-submenu>

  </el-menu>
</template>

<script>

export default {
  name: "AdminAside",
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