<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-radio-group v-model="queryParams.escortAutitStatus" size="small" @change="handleQuery">
        <el-radio-button label="">全部</el-radio-button>
        <el-radio-button label="1">已通过</el-radio-button>
        <el-radio-button label="0">待审核</el-radio-button>
        <el-radio-button label="2">未通过</el-radio-button>
      </el-radio-group>
      <div style="flex: 1" />
      <br>
      <el-form-item label="所属商家" prop="escortBusinessName">
        <el-input
          v-model="queryParams.escortBusinessName"
          placeholder="请输入所属商家"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属机构" prop="escortCorpName">
        <el-input
          v-model="queryParams.escortCorpName"
          placeholder="请输入所属机构"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="escortName">
        <el-input
          v-model="queryParams.escortName"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机" prop="escortPhone">
        <el-input
          v-model="queryParams.escortPhone"
          placeholder="请输入手机"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="escortSex">
        <el-select v-model="queryParams.escortSex" placeholder="请选择性别" clearable>
          <el-option
            v-for="dict in dict.type.sys_user_sex"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="住址" prop="escortAddress">
        <el-input
          v-model="queryParams.escortAddress"
          placeholder="请输入住址"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['escort:manage:add']"
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
          v-hasPermi="['escort:manage:edit']"
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
          v-hasPermi="['escort:manage:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['escort:manage:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="manageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="陪诊师编号" align="center" prop="escortId" />
      <el-table-column label="所属商家" align="center" prop="escortBusinessName" />
      <el-table-column label="所属机构" align="center" prop="escortCorpName" />
      <el-table-column label="姓名" align="center" prop="escortName" />
      <el-table-column label="手机" align="center" prop="escortPhone" />
      <el-table-column label="性别" align="center" prop="escortSex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.escortSex"/>
        </template>
      </el-table-column>
      <el-table-column label="年龄" align="center" prop="escortAge" />
      <el-table-column label="护龄" align="center" prop="escortNursingAge" />
      <el-table-column label="籍贯" align="center" prop="escortNativePlace" />
      <el-table-column label="住址" align="center" prop="escortAddress" />
      <el-table-column label="个人简介" align="center" prop="escortIntroduce" min-width="120" :show-overflow-tooltip="true"/>
      <el-table-column label="身份证号" align="center" prop="escortIdentityCard" />
      <el-table-column label="身份证正面" align="center" prop="escortIdentityCardFront" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.escortIdentityCardFront" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="身份证反面" align="center" prop="escortIdentityCardBack" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.escortIdentityCardBack" :width="50" :height="50"/>
        </template>
      </el-table-column>

      <el-table-column label="体检报告" align="center" prop="escortReport" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.escortReport" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="escortAutitStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.escort_audit_status" :value="scope.row.escortAutitStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['escort:manage:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['escort:manage:remove']"
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

    <!-- 添加或修改陪诊师管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属商家" prop="escortBusinessName">
          <el-input v-model="form.escortBusinessName" placeholder="请输入所属商家" />
        </el-form-item>
        <el-form-item label="所属机构" prop="escortCorpName">
          <el-input v-model="form.escortCorpName" placeholder="请输入所属机构" />
        </el-form-item>
        <el-form-item label="姓名" prop="escortName">
          <el-input v-model="form.escortName" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="手机" prop="escortPhone">
          <el-input v-model="form.escortPhone" placeholder="请输入手机" />
        </el-form-item>
        <el-form-item label="性别" prop="escortSex">
          <el-select v-model="form.escortSex" placeholder="请选择性别">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="escortAge">
          <el-input v-model="form.escortAge" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="护龄" prop="escortNursingAge">
          <el-input v-model="form.escortNursingAge" placeholder="请输入护龄" />
        </el-form-item>
        <el-form-item label="籍贯" prop="escortNativePlaceList">
          <div>
            <ProvincesCascader v-model="form.escortNativePlaceList" placeholder="请输入籍贯"/>
          </div>
        </el-form-item>
        <el-form-item label="住址" prop="escortAddress">
          <el-input v-model="form.escortAddress" placeholder="请输入住址" />
        </el-form-item>
        <el-form-item label="个人简介" prop="escortIntroduce">
          <el-input v-model="form.escortIntroduce" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="身份证号" prop="escortIdentityCard">
          <el-input v-model="form.escortIdentityCard" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="身份证正面" prop="escortIdentityCardFront">
          <image-upload v-model="form.escortIdentityCardFront"/>
        </el-form-item>
        <el-form-item label="身份证反面" prop="escortIdentityCardBack">
          <image-upload v-model="form.escortIdentityCardBack"/>
        </el-form-item>
        <el-form-item label="体检报告" prop="escortReport">
          <image-upload v-model="form.escortReport"/>
        </el-form-item>
        <el-form-item label="审核状态" prop="escortAutitStatus">
          <el-radio-group v-model="form.escortAutitStatus">
            <el-radio
              v-for="dict in dict.type.escort_audit_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
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
import { listManage, getManage, delManage, addManage, updateManage } from "@/api/escort/manage";
import ProvincesCascader from '@/components/ProvincesCascader/ProvincesCascader';

export default {
  name: "Manage",
  dicts: ['sys_user_sex', 'escort_audit_status'],
  components: {
    ProvincesCascader
  },
  data() {
    return {
      showBugSelect: [],
      provincesCascaderSelect: [],
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
      // 陪诊师管理表格数据
      manageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        escortBusinessName: null,
        escortCorpName: null,
        escortName: null,
        escortPhone: null,
        escortSex: null,
        escortAddress: null,
        escortIntroduce: null,
        escortAutitStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        escortBusinessName: [
          { required: true, message: "所属商家不能为空", trigger: "blur" }
        ],
        escortCorpName: [
          { required: true, message: "所属机构不能为空", trigger: "blur" }
        ],
        escortName: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        escortPhone: [
          { required: true, message: "手机不能为空", trigger: "blur" }
        ],
        escortSex: [
          { required: true, message: "性别不能为空", trigger: "change" }
        ],
        escortAge: [
          { required: true, message: "年龄不能为空", trigger: "blur" }
        ],
        escortNativePlaceList: [
          { required: true, message: "籍贯不能为空", trigger: "blur" }
        ],
        escortAutitStatus: [
          { required: true, message: "审核状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询陪诊师管理列表 */
    getList() {
      this.loading = true;
      listManage(this.queryParams).then(response => {
        this.manageList = response.rows;
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
        escortId: null,
        escortBusinessId: null,
        escortBusinessName: null,
        escortCorpId: null,
        escortCorpName: null,
        escortName: null,
        escortPhone: null,
        escortSex: null,
        escortAge: null,
        escortNursingAge: null,
        escortNativePlace: null,
        escortNativePlaceList: null,
        escortAddress: null,
        escortIntroduce: null,
        escortIdentityCard: null,
        escortIdentityCardFront: null,
        escortIdentityCardBack: null,
        escortReport: null,
        escortAutitStatus: null
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
      this.ids = selection.map(item => item.escortId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加陪诊师管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const escortId = row.escortId || this.ids
      getManage(escortId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改陪诊师管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.escortId != null) {
            updateManage(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addManage(this.form).then(response => {
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
      const escortIds = row.escortId || this.ids;
      this.$modal.confirm('是否确认删除陪诊师管理编号为"' + escortIds + '"的数据项？').then(function() {
        return delManage(escortIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('escort/manage/export', {
        ...this.queryParams
      }, `manage_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
