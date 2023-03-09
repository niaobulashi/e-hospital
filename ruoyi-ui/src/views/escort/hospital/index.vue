<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="医院名称" prop="hospitalName">
        <el-input
          v-model="queryParams.hospitalName"
          placeholder="请输入医院名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="医院类型" prop="hospitalType">
        <el-select v-model="queryParams.hospitalType" placeholder="请选择医院类型" clearable>
          <el-option
            v-for="dict in dict.type.escort_hospital_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="服务时间" prop="hospitalServerTime">
        <el-input
          v-model="queryParams.hospitalServerTime"
          placeholder="请输入服务时间"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地区" prop="hospitalArea">
        <el-input
          v-model="queryParams.hospitalArea"
          placeholder="请输入地区"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地址" prop="hospitalAddress">
        <el-input
          v-model="queryParams.hospitalAddress"
          placeholder="请输入地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="简介" prop="hospitalIntroduce">
        <el-input
          v-model="queryParams.hospitalIntroduce"
          placeholder="请输入简介"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="hospitalStatus">
        <el-select v-model="queryParams.hospitalStatus" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['escort:hospital:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['escort:hospital:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['escort:hospital:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['escort:hospital:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="hospitalList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="医院编号" align="center" prop="hospitalId" />
      <el-table-column label="医院名称" align="center" prop="hospitalName" />
      <el-table-column label="医院类型" align="center" prop="hospitalType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.escort_hospital_type" :value="scope.row.hospitalType"/>
        </template>
      </el-table-column>
      <el-table-column label="服务时间" align="center" prop="hospitalServerTime" />
      <el-table-column label="地区" align="center" prop="hospitalArea" />
      <el-table-column label="地址" align="center" prop="hospitalAddress" />
      <el-table-column label="简介" align="center" prop="hospitalIntroduce" />
      <el-table-column label="状态" align="center" prop="hospitalStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.hospitalStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['escort:hospital:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['escort:hospital:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改医院管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="医院名称" prop="hospitalName">
          <el-input v-model="form.hospitalName" placeholder="请输入医院名称" />
        </el-form-item>
        <el-form-item label="医院类型" prop="hospitalType">
          <el-select v-model="form.hospitalType" placeholder="请选择医院类型">
            <el-option
              v-for="dict in dict.type.escort_hospital_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="服务时间" prop="hospitalServerTime">
          <el-input v-model="form.hospitalServerTime" placeholder="请输入服务时间" />
        </el-form-item>
        <el-form-item label="地区" prop="hospitalArea">
          <el-input v-model="form.hospitalArea" placeholder="请输入地区" />
        </el-form-item>
        <el-form-item label="地址" prop="hospitalAddress">
          <el-input v-model="form.hospitalAddress" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="简介" prop="hospitalIntroduce">
          <el-input v-model="form.hospitalIntroduce" placeholder="请输入简介" />
        </el-form-item>
        <el-form-item label="状态" prop="hospitalStatus">
          <el-radio-group v-model="form.hospitalStatus">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listHospital, getHospital, delHospital, addHospital, updateHospital } from "@/api/escort/hospital";

export default {
  name: "Hospital",
  dicts: ['sys_normal_disable', 'escort_hospital_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 医院管理表格数据
      hospitalList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        hospitalName: null,
        hospitalType: null,
        hospitalServerTime: null,
        hospitalArea: null,
        hospitalAddress: null,
        hospitalIntroduce: null,
        hospitalStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询医院管理列表 */
    getList() {
      this.loading = true;
      listHospital(this.queryParams).then(response => {
        this.hospitalList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        hospitalId: null,
        hospitalName: null,
        hospitalType: null,
        hospitalServerTime: null,
        hospitalArea: null,
        hospitalAddress: null,
        hospitalIntroduce: null,
        hospitalStatus: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.hospitalId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加医院管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const hospitalId = row.hospitalId || this.ids
      getHospital(hospitalId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改医院管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.hospitalId != null) {
            updateHospital(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHospital(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const hospitalIds = row.hospitalId || this.ids;
      this.$modal.confirm('是否确认删除医院管理编号为"' + hospitalIds + '"的数据项？').then(function() {
        return delHospital(hospitalIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('escort/hospital/export', {
        ...this.queryParams
      }, `hospital_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
