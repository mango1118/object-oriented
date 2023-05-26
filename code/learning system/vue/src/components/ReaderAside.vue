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
      <b style="color: white" v-show="!isCollapse">图书管理系统</b>
    </div>
    <el-submenu index="1-1">
      <template slot="title">
        <i class="el-icon-more-outline"></i>
        <span slot="title">个人功能</span>
      </template>
      <el-menu-item index="/reader/home">
        <i class="el-icon-house"></i>
        <span slot="title">回到主页</span>
      </el-menu-item>
      <el-menu-item index="/reader/mybook">
        <i class="el-icon-notebook-2"></i>
        <span slot="title">我的借阅</span>
      </el-menu-item>
      <el-menu-item index="/reader/self">
        <i class="el-icon-edit"></i>
        <span slot="title">个人信息</span>
      </el-menu-item>
      <el-menu-item index="/login">
        <i class="el-icon-switch-button"></i>
        <span slot="title">退出登录</span>
      </el-menu-item>
    </el-submenu>
    <el-submenu index="1-3">
      <template slot="title">
        <i class="el-icon-search"></i>
        <span slot="title">查询功能</span>
      </template>
<!--      <el-menu-item index="/reader/bookSearch">-->
<!--        <i class="el-icon-notebook-1"></i>-->
<!--        <span slot="title">图书查询</span>-->
<!--      </el-menu-item>-->
      <el-menu-item index="/reader/bookSearch">
        <i class="el-icon-notebook-1"></i>
        <span slot="title">全部图书</span>
      </el-menu-item>
      <el-menu-item index="/reader/bookStackSearch">
        <i class="el-icon-notebook-1"></i>
        <span slot="title">图书查询</span>
      </el-menu-item>
      <el-menu-item index="/reader/borrowSearch">
        <i class="el-icon-s-check"></i>
        <span slot="title">借阅查询</span>
      </el-menu-item>
      <el-menu-item index="/reader/putSearch">
        <i class="el-icon-folder-opened"></i>
        <span slot="title">存放查询</span>
      </el-menu-item>
      <el-menu-item index="/reader/stackSearch">
        <i class="el-icon-files"></i>
        <span slot="title">书库查询</span>
      </el-menu-item>
    </el-submenu>
  </el-menu>
</template>
<script>

export default {
  name: "ReaderAside",
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