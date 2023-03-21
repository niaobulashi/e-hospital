<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="85px">
      <el-form-item label="支付流水号" prop="paymentNo">
        <el-input
          v-model="queryParams.paymentNo"
          placeholder="请输入支付流水号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="pickerOptions"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="支付金额" prop="paymentAmount">
        <el-input
          v-model="queryParams.paymentAmount"
          placeholder="请输入支付金额"
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
          v-hasPermi="['escort:payment:add']"
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
          v-hasPermi="['escort:payment:edit']"
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
          v-hasPermi="['escort:payment:remove']"
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
          v-hasPermi="['escort:payment:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5" style="color: rgb(206 120 69);">
        <td class="el-table__cell is-leaf">
          <div class="cell" v-show="sumShowFlag"><strong>流水总数：{{ pagmentSum.paymentCount |money(0) }} 笔{{"\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0"}}总金额：{{ pagmentSum.paymentAmountSum | money }} 元</strong>
          </div>
        </td>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="paymentList" @selection-change="handleSelectionChange">
      <!--      <el-table-column type="selection" width="55" align="center" />-->
      <el-table-column label="支付流水ID" align="center" width="150" prop="paymentId"/>
      <el-table-column label="支付流水号" align="center" width="260" prop="paymentNo"/>
      <el-table-column label="订单号" align="center" width="260" prop="orderNo"/>
      <el-table-column label="会员姓名" align="center" width="240" prop="memberName"/>
      <el-table-column label="支付时间" align="center" width="260" prop="paymentTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.paymentTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="支付金额（元）" width="260" align="center" prop="paymentAmount"/>
      <!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['escort:payment:edit']"
                >修改</el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['escort:payment:remove']"
                >删除</el-button>
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

    <!-- 添加或修改支付单流水对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="支付流水号" prop="paymentNo">
          <el-input v-model="form.paymentNo" placeholder="请输入支付流水号"/>
        </el-form-item>
        <el-form-item label="订单号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单号"/>
        </el-form-item>
        <el-form-item label="支付时间" prop="paymentTime">
          <el-date-picker clearable
                          v-model="form.paymentTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择支付时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="支付金额" prop="paymentAmount">
          <el-input v-model="form.paymentAmount" placeholder="请输入支付金额"/>
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
import {
  listPayment,
  getPayment,
  delPayment,
  addPayment,
  updatePayment,
  queryEscortPaymentSumAmount
} from "@/api/escort/payment";

export default {
  name: "Payment",
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
      // 统计是否展示
      sumShowFlag: false,
      // 支付单流水表格数据
      paymentList: [],
      // 日期范围
      dateRange: [],
      // cache信息
      pagmentSum: {},
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        paymentNo: null,
        orderNo: null,
        paymentTime: null,
        paymentAmount: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        paymentNo: [
          {required: true, message: "支付流水号不能为空", trigger: "blur"}
        ],
        orderNo: [
          {required: true, message: "订单号不能为空", trigger: "blur"}
        ],
        paymentAmount: [
          {required: true, message: "支付金额不能为空", trigger: "blur"}
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
    this.getList();
    this.queryEscortPaymentSumAmount();
  },
  methods: {
    /** 查询支付单流水列表 */
    getList() {
      this.loading = true;
      listPayment(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.paymentList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 支付流水列表查询，统计订单数量、支付金额总数 */
    queryEscortPaymentSumAmount() {
      queryEscortPaymentSumAmount(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.pagmentSum = response;
        if (this.dateRange.length > 0) {
          this.sumShowFlag = true;
        } else {
          this.sumShowFlag = false;
        }
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
        paymentId: null,
        paymentNo: null,
        orderNo: null,
        paymentTime: null,
        paymentAmount: null,
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
      this.queryEscortPaymentSumAmount();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.paymentId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加支付单流水";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const paymentId = row.paymentId || this.ids
      getPayment(paymentId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改支付单流水";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.paymentId != null) {
            updatePayment(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.queryEscortPaymentSumAmount();
            });
          } else {
            addPayment(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.queryEscortPaymentSumAmount();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const paymentIds = row.paymentId || this.ids;
      this.$modal.confirm('是否确认删除支付单流水编号为"' + paymentIds + '"的数据项？').then(function () {
        return delPayment(paymentIds);
      }).then(() => {
        this.getList();
        this.queryEscortPaymentSumAmount();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('escort/payment/export', {
        ...this.queryParams
      }, `payment_${new Date().getTime()}.xlsx`)
    },
    // 是我用来获取当月的第一天的
    handleTimeOld(time, split) {
      let date = new Date(time);
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
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
