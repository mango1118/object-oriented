<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入教师账户" suffix-icon="el-icon-search"
                v-model="account"></el-input>
      <el-input style="width: 200px" placeholder="请输入教师姓名" suffix-icon="el-icon-search" v-model="name"
                class="ml-5"></el-input>
      <el-button class="ml-5" type="primary" @click="handleSearch">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reload">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
    </div>

    <el-table v-bind:data="tableData" border stripe :header-cell-class-name="headerBg" row-key="id">
      <el-table-column prop="id" label="ID" width="50"></el-table-column>
      <el-table-column prop="account" label="账户" width="300"></el-table-column>
      <el-table-column prop="name" label="姓名" width="200"></el-table-column>
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
              @confirm="handleDelete(scope.row.id)"
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

    <el-dialog title="教师信息" :visible.sync="saveDialogFormVisible" width="30%">
      <el-form ref="saveForm" :model="saveform" :rules="formRules" label-width="80px" size="small">
        <el-form-item label="账户" prop="teacherAccount">
          <el-input v-model="saveform.teacherAccount" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="teacherPassword">
          <el-input v-model="saveform.teacherPassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="teacherName">
          <el-input v-model="saveform.teacherName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="saveDialogFormVisible = false">取 消</el-button>
        <el-button class="ml-5" type="primary" @click="saveForm">提交</el-button>
      </div>
    </el-dialog>

    <el-dialog title="教师信息" :visible.sync="editDialogFormVisible" width="30%">
      <el-form ref="editForm" :model="editform" :rules="formRules" label-width="80px" size="small">
        <el-form-item label="账户" prop="teacherAccount">
          <el-input v-model="editform.account" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="teacherPassword">
          <el-input v-model="editform.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="teacherName">
          <el-input v-model="editform.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogFormVisible = false">取 消</el-button>
        <el-button class="ml-5" type="primary" @click="editForm">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      account: '',
      name: '',
      pageNum: 1,
      pageSize: 5,
      total: 0,
      tableData: [],
      saveform: {
        teacherAccount: '',
        teacherPassword: '',
        teacherName: ''
      },
      editform: {
        teacherId: '',
        teacherAccount: '',
        teacherPassword: '',
        teacherName: ''
      },
      formRules: {
        account: [
          { required: true, message: '请输入账户', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ]
      },
      saveDialogFormVisible: false,
      editDialogFormVisible: false
    };
  },
  mounted() {
    this.sendReq();
  },
  methods: {
    headerBg() {
      return 'header-bg';
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.sendReq();
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.sendReq();
    },
    handleSearch() {
      this.pageNum = 1;
      this.sendReq();
    },
    reload() {
      this.account = '';
      this.name = '';
      this.pageNum = 1;
      this.sendReq();
    },
    handleAdd() {
      this.saveDialogFormVisible = true;
    },
    handleEdit(row) {
      this.editform = Object.assign({}, row);
      this.editDialogFormVisible = true;
    },
    handleDelete(id) {
      this.axios.delete(`/admin/teacherControl/${id}`).then(res => {
        if (res.data) {
          this.$message.success("删除成功");
          this.sendReq();
        } else {
          this.$message.error(`删除失败，原因：${res.msg}`);
        }
      })
    },
    saveForm() {
      this.$refs.saveForm.validate((valid) => {
        if (valid) {
          this.axios.post("/admin/teacherControl", this.saveform).then(res => {
            if (res.data) {
              this.$message.success("保存成功");
              this.saveDialogFormVisible = false;
              this.sendReq();
            } else {
              this.$message.error(`保存失败，原因：${res.msg}`);
            }
          })
        }
      });
    },
    editForm() {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          this.axios.put(`/admin/teacherControl/${this.editform.teacherId}`, this.editform).then(res => {
            if (res.data) {
              this.$message.success("更新成功");
              this.editDialogFormVisible = false;
              this.sendReq();
            } else {
              this.$message.error(`更新失败，原因：${res.msg}`);
            }
          })
        }
      });
    },
    sendReq() {
      const params = {
        //id: this.id,
        account: this.account,
        name: this.name,
        pageNum: this.pageNum,
        pageSize: this.pageSize
      };
      this.axios.get('/admin/teacherControl/pageLike', { params }).then(res => {
        if (res.data.data) {
          this.tableData = res.data.data;
          this.total = res.data.total;
        }
      });
    }
  }
}
</script>

<style scoped>
.header-bg {
  background-color: #f0f2f5 !important;
}
.ml-5 {
  margin-left: 5px;
}
</style>
