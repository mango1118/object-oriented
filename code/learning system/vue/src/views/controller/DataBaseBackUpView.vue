<template>
  <div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleBackUp">数据库备份</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "DataBaseBackUpView",
  data() {
    return {
      print: ""
    }
  },
  methods: {
    handleBackUp() {
      this.axios.get(`/superAdmins/backup`).then(res => {
        if (res.data) {
          this.$message.success(`${res.msg}`);
          // this.sendReq();
        } else {
          this.$message.error(`${res.msg}`);
        }
      })
    }
  }
}
</script>
<style scoped>

</style>