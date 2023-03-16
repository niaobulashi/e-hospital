<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="85px">
      <el-radio-group v-model="queryParams.status" size="small" @change="handleQuery">
        <el-radio-button label="">全部</el-radio-button>
        <el-radio-button label="0">已付款</el-radio-button>
        <el-radio-button label="1">服务中</el-radio-button>
        <el-radio-button label="2">已完成</el-radio-button>
      </el-radio-group>
      <div style="flex: 1" />
      <br>
      <el-form-item label="订单号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="会员姓名" prop="memberName">
        <el-input
          v-model="queryParams.memberName"
          placeholder="请输入会员姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="医院名称" prop="hospitalName">
        <el-input
          v-model="queryParams.hospitalName"
          placeholder="请输入医院名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目名称" prop="projectName">
        <el-input
          v-model="queryParams.projectName"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="陪诊员姓名" prop="escortName">
        <el-input
          v-model="queryParams.escortName"
          placeholder="请输入陪诊员姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预约时间">
        <el-date-picker
          v-model="appointmentDateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="pickerOptions"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="完成时间">
        <el-date-picker
          v-model="finishDateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="pickerOptions"
        ></el-date-picker>
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
          v-hasPermi="['escort:order:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['escort:order:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['escort:order:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['escort:order:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
<!--      <el-table-column type="selection" width="55" align="center"/>-->
      <el-table-column label="订单ID" align="center" prop="orderId"/>
      <el-table-column label="订单号" width="200" align="center" prop="orderNo"/>
      <el-table-column label="会员ID" align="center" prop="memberId"/>
      <el-table-column label="会员姓名" align="center" prop="memberName"/>
      <el-table-column label="会员手机" width="150" align="center" prop="memberPhone">
        <template slot-scope="scope">
          {{scope.row.memberPhone? scope.row.memberPhone.replace(/^(.{3})(?:\w+)(.{4})$/,
          "\$1****\$2"):""}}
        </template>
      </el-table-column>
      <el-table-column label="医院名称" width="200" min-width="200" :show-overflow-tooltip="true" align="center" prop="hospitalName"/>
      <el-table-column label="项目名称" width="100" min-width="100" :show-overflow-tooltip="true" align="center" prop="projectName"/>
      <el-table-column label="项目金额（元）" width="150" align="center" prop="projectAmount"/>
      <el-table-column label="陪诊员ID" align="center" prop="escortId"/>
      <el-table-column label="陪诊员姓名" width="150" min-width="150" :show-overflow-tooltip="true" align="center" prop="escortName"/>
      <el-table-column label="预约时间" align="center" prop="appointmentTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.appointmentTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订单状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.escort_order_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="下单时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="完成时间" align="center" prop="finishTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.finishTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="计划完成时间" align="center" prop="planFinishTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.planFinishTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['escort:order:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['escort:order:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改订单列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单号"/>
        </el-form-item>
        <el-form-item label="会员ID" prop="memberId">
          <el-input v-model="form.memberId" placeholder="请输入会员ID"/>
        </el-form-item>
        <el-form-item label="预约时间" prop="appointmentTime">
          <el-date-picker clearable
                          v-model="form.appointmentTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择预约时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="医院ID" prop="hospitalId">
          <el-input v-model="form.hospitalId" placeholder="请输入医院ID"/>
        </el-form-item>
        <el-form-item label="项目ID" prop="projectId">
          <el-input v-model="form.projectId" placeholder="请输入项目ID"/>
        </el-form-item>
        <el-form-item label="陪诊员ID" prop="escortId">
          <el-input v-model="form.escortId" placeholder="请输入陪诊员ID"/>
        </el-form-item>
        <el-form-item label="完成时间" prop="finishTime">
          <el-date-picker clearable
                          v-model="form.finishTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择完成时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="计划完成时间" prop="planFinishTime">
          <el-date-picker clearable
                          v-model="form.planFinishTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择计划完成时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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
import {listOrder, getOrder, delOrder, addOrder, updateOrder} from "@/api/escort/order";

export default {
  name: "Order",
  dicts: ['escort_order_status'],
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
      // 订单列表表格数据
      orderList: [],
      // 预约日期范围
      appointmentDateRange: [],
      finishDateRange: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: null,
        memberName: null,
        appointmentTime: null,
        hospitalName: null,
        projectName: null,
        escortName: null,
        status: "",
        finishTime: null,
        planFinishTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderNo: [
          {required: true, message: "订单号不能为空", trigger: "blur"}
        ],
        memberId: [
          {required: true, message: "会员ID不能为空", trigger: "blur"}
        ],
        hospitalId: [
          {required: true, message: "医院ID不能为空", trigger: "blur"}
        ],
        projectId: [
          {required: true, message: "项目ID不能为空", trigger: "blur"}
        ],
        escortId: [
          {required: true, message: "陪诊员ID不能为空", trigger: "blur"}
        ],
      },
      // 日期选择器
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            const end = new Date();
            end.setHours(23, 59, 59);
            const start = new Date(end);
            start.setTime(end.getTime() - 3600 * 1000 * 24 + 1000);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            end.setHours(23, 59, 59);
            start.setTime(end.getTime() - 3600 * 1000 * 24 * 7 + 1000);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            end.setHours(23, 59, 59);
            start.setTime(end.getTime() - 3600 * 1000 * 24 * 30 + 1000);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            end.setHours(23, 59, 59);
            start.setTime(end.getTime() - 3600 * 1000 * 24 * 90 + 1000);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一年',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            end.setHours(23, 59, 59);
            start.setTime(end.getTime() - 3600 * 1000 * 24 * 365 + 1000);
            picker.$emit('pick', [start, end]);
          }
        }]
      }
    };
  },
  created() {
    //this.appointmentDateRange.push(this.handleTimeOld(new Date())); // handleTimeOld是我用来获取当月的第一天的
    //this.appointmentDateRange.push(this.handleTimeNew(new Date())); // handleTimeNew是获取当月最后一天
    //this.finishDateRange.push(this.handleTimeOld(new Date())); // handleTimeOld是我用来获取当月的第一天的
    //this.finishDateRange.push(this.handleTimeNew(new Date())); // handleTimeNew是获取当月最后一天
    this.getList();
  },
  methods: {
    /** 查询订单列表列表 */
    getList() {
      this.loading = true;
      listOrder(this.addDateRangeTwo(this.queryParams, this.appointmentDateRange, this.finishDateRange)).then(response => {
        this.orderList = response.rows;
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
        orderId: null,
        orderNo: null,
        memberId: null,
        appointmentTime: null,
        hospitalId: null,
        projectId: null,
        escortId: null,
        status: null,
        finishTime: null,
        planFinishTime: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
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
      this.appointmentDateRange = [];
      this.finishDateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单列表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const orderId = row.orderId || this.ids
      getOrder(orderId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单列表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orderId != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
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
      const orderIds = row.orderId || this.ids;
      this.$modal.confirm('是否确认删除订单列表编号为"' + orderIds + '"的数据项？').then(function () {
        return delOrder(orderIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('escort/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    },
    // 是我用来获取当月的第一天的
    handleTimeOld(time, split) {
      let date = new Date(time);
      let month = date.getMonth() + 1;
      let year = date.getFullYear();
      split = '-';
      return [year, month, 1].map(num => this.formatNumber(num)).join(split);
    },
    // handleTimeNew是获取当月最后一日
    handleTimeNew(time) {
      let year = new Date().getFullYear(); //获取年份
      let month = new Date().getMonth() + 1; //获取月份
      let day = new Date(year, month, 0).getDate(); //获取当月最后一日
      return `${year}-${month}-${day}`;
    },
    formatNumber(number) {
      return String(number)[1] ? String(number) : `0${number}`;
    }
  }
};
</script>
