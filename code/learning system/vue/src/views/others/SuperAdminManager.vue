<template>
  <el-container style="min-height: 100%;">
    <el-aside :width="sideWidth + 'px'"
              style="background-color: rgb(238, 241, 246); box-shadow: 2px 0 6px rgba(0, 21, 41);">
      <Aside :isCollapse="isCollapse" :localTextShow="logoTextShow"/>
    </el-aside>
    <el-container>
      <el-header style="font-size: 1px; border-bottom: 1px solid #ccc;">
        <Header :collapseBtnClass="collapseBtnClass" :collapse=collapse />
      </el-header>
      <el-main>
<!--        表示当前页面的子路由会在router-view里面展示-->
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
import Aside from "@/components/SuperAdminAside";
import Header from "@/components/Header";
export default {
  name: "SuperAdminManager",
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
    }
  },
  components: {
    Aside,
    Header
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
    }
  },
};
</script>
<style>
.headerBg {
  background-color: #ccc !important;
}
</style>
