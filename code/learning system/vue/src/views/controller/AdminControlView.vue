<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入管理员账户" suffix-icon="el-icon-search"
                v-model="adminAccount"></el-input>
      <el-input style="width: 200px" placeholder="请输入管理员姓名" suffix-icon="el-icon-admin" v-model="adminName"
                class="ml-5"></el-input>
      <el-input style="width: 200px" placeholder="请输入管理员邮箱" suffix-icon="el-icon-message"
                v-model="adminEmail"
                class="ml-5"></el-input>
      <el-button class="ml-5" type="primary" @click="handleSearch">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
    </div>

    <el-table v-bind:data="tableData" border: stripe :header-cell-class-name="headerBg" row-key="id">
      <el-table-column prop="adminId" label="ID" width="50"></el-table-column>
      <el-table-column prop="adminAccount" label="账户" width="150"></el-table-column>
      <el-table-column prop="adminName" label="姓名" width="100"></el-table-column>
      <el-table-column prop="adminEmail" label="邮箱"></el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="handleDelete(scope.row.adminId)"
          >
            <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i>
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total=total>
        >
      </el-pagination>
    </div>

    <el-dialog title="管理员信息" :visible.sync="saveDialogFormVisible" width="30%">
      <el-form :model="saveform" label-width="80px" size="small">
        <el-form-item label="账户">
          <el-input v-model="saveform.adminAccount" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="saveform.adminPassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="saveform.adminName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="saveform.adminEmail" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveForm">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="管理员信息" :visible.sync="editDialogFormVisible" width="30%">
      <el-form :model="editform" label-width="80px" size="small">
        <el-form-item label="账户">
          <el-input v-model="editform.adminAccount" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="editform.adminPassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="editform.adminName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editform.adminEmail" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="editForm">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "AdminControlView",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      adminAccount: null,
      adminName: null,
      adminEmail: null,
      saveDialogFormVisible: false,
      editDialogFormVisible: false,
      saveform: {},
      editform: {},
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
    async sendReq() {
      const resp = await this.axios.get("admins/pageLike?pageNum=" + this.pageNum
          + "&pageSize=" + this.pageSize + "&account=null&name=null&email=null");
      // console.log(resp);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
    },
    async sendLikeReq() {
      const resp = await this.axios.get(`/admins/pageLike?pageNum=${this.pageNum}
      &pageSize=${this.pageSize}&account=${this.adminAccount}&name=${this.adminName}&email=${this.adminEmail}`);
      // console.log(resp);
      this.tableData = resp.data.data;
      this.total = resp.data.total;
    },
    reload() {
      this.adminName = null;
      this.adminAccount = null;
      this.adminEmail = null;
      this.sendReq();
    },
    handleSearch() {
      this.pageNum = 1;
      this.sendLikeReq();
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.sendLikeReq();
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.sendLikeReq();
    },
    handleAdd() {
      this.saveDialogFormVisible = true;
      this.saveform = {};
    },
    handleEdit(row) {
      this.editform = row;
      this.editDialogFormVisible = true;
    },
    handleDelete(id) {
      this.axios.delete(`/admins/${id}`).then(res => {
        if (res.data) {
          this.$message.success("删除成功");
          this.sendReq();
        } else {
          this.$message.error(`删除失败，原因：${res.msg}`);
        }
      })
    },
    saveForm() {
      this.axios.post("/admins", this.saveform).then(res => {
        // console.log(res.data);
        if (res.data) {
          this.$message.success("保存成功");
          this.saveDialogFormVisible = false;
          this.sendReq();
        } else {
          this.$message.error(`保存失败，原因：${res.msg}`);
        }
      })
      this.sendReq();
    },
    editForm() {
      this.axios.put("/admins", this.editform).then(res => {
        // console.log(res.data);
        if (res.data) {
          this.$message.success("编辑成功");
          this.editDialogFormVisible = false;
          this.sendReq();
        } else {
          this.$message.error(`编辑失败，原因：${res.msg}`);
        }
      })
    }
  },
  mounted: function () {
    this.sendLikeReq();
  }
}
</script>

<style>
.headerBg {
  background-color: #ccc !important;
}
</style>