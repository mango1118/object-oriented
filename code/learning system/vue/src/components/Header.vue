<template>
  <div style="font-size: 12px; line-height: 60px; display: flex">
    <div style="flex: 1">
      <span :class="collapseBtnClass" style="cursor: pointer;  font-size: 18px" @click="collapse"></span>
      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <!--        <el-breadcrumb-item>菜单收缩</el-breadcrumb-item>-->
      </el-breadcrumb>
    </div>
    <!--    <el-dropdown style="width: 70px; cursor: pointer">-->
    <!--      <i class="el-icon-arrow-down" style="margin-left: 5px"></i>-->
    <!--      <el-dropdown-menu slot="dropdown">-->
    <!--        <el-dropdown-item style="font-size: 14px; padding: 5px 0">个人信息</el-dropdown-item>-->
    <!--        <el-dropdown-item style="font-size: 14px; padding: 5px 0">-->
    <!--          <router-link to="/login" style="text-decoration: none">退出</router-link>-->
    <!--        </el-dropdown-item>-->
    <!--      </el-dropdown-menu>-->
    <!--    </el-dropdown>-->
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    collapse: Function,
  },
  data() {
    return {
      paths: []
    }
  },
  created() {
    console.log(this.$router)
  }
}
</script>

<style scoped>

</style>