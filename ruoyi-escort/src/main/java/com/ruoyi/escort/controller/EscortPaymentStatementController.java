package com.ruoyi.escort.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.escort.domain.EscortPaymentStatement;
import com.ruoyi.escort.service.IEscortPaymentStatementService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 支付单流水Controller
 *
 * @author ruoyi
 * @date 2023-03-10
 */
@RestController
@RequestMapping("/escort/payment")
public class EscortPaymentStatementController extends BaseController {
    @Autowired
    private IEscortPaymentStatementService escortPaymentStatementService;

    /**
     * 查询支付单流水列表
     */
    @PreAuthorize("@ss.hasPermi('escort:payment:list')")
    @GetMapping("/list")
    public TableDataInfo list(EscortPaymentStatement escortPaymentStatement) {
        startPage();
        List<EscortPaymentStatement> list = escortPaymentStatementService.selectEscortPaymentStatementList(escortPaymentStatement);
        return getDataTable(list);
    }

    /**
     * 查询支付单流水列表-带用户信息
     */
    @PreAuthorize("@ss.hasPermi('escort:payment:list')")
    @GetMapping("/memlist")
    public TableDataInfo memlist(EscortPaymentStatement escortPaymentStatement) {
        startPage();
        List<EscortPaymentStatement> list = escortPaymentStatementService.selectEscortPaymentAndMemList(escortPaymentStatement);
        return getDataTable(list);
    }

    /**
     * 导出支付单流水列表
     */
    @PreAuthorize("@ss.hasPermi('escort:payment:export')")
    @Log(title = "支付单流水", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EscortPaymentStatement escortPaymentStatement) {
        List<EscortPaymentStatement> list = escortPaymentStatementService.selectEscortPaymentStatementList(escortPaymentStatement);
        ExcelUtil<EscortPaymentStatement> util = new ExcelUtil<EscortPaymentStatement>(EscortPaymentStatement.class);
        util.exportExcel(response, list, "支付单流水数据");
    }

    /**
     * 获取支付单流水详细信息
     */
    @PreAuthorize("@ss.hasPermi('escort:payment:query')")
    @GetMapping(value = "/{paymentId}")
    public AjaxResult getInfo(@PathVariable("paymentId") Long paymentId) {
        return success(escortPaymentStatementService.selectEscortPaymentStatementByPaymentId(paymentId));
    }

    /**
     * 新增支付单流水
     */
    @PreAuthorize("@ss.hasPermi('escort:payment:add')")
    @Log(title = "支付单流水", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EscortPaymentStatement escortPaymentStatement) {
        return toAjax(escortPaymentStatementService.insertEscortPaymentStatement(escortPaymentStatement));
    }

    /**
     * 修改支付单流水
     */
    @PreAuthorize("@ss.hasPermi('escort:payment:edit')")
    @Log(title = "支付单流水", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EscortPaymentStatement escortPaymentStatement) {
        return toAjax(escortPaymentStatementService.updateEscortPaymentStatement(escortPaymentStatement));
    }

    /**
     * 删除支付单流水
     */
    @PreAuthorize("@ss.hasPermi('escort:payment:remove')")
    @Log(title = "支付单流水", businessType = BusinessType.DELETE)
    @DeleteMapping("/{paymentIds}")
    public AjaxResult remove(@PathVariable Long[] paymentIds) {
        return toAjax(escortPaymentStatementService.deleteEscortPaymentStatementByPaymentIds(paymentIds));
    }

    /**
     * 查询指定日期的支付流水
     */
    @GetMapping("/queryPaymentByDate")
    public EscortPaymentStatement queryPaymentByDate(EscortPaymentStatement escortPaymentStatement) {
        return escortPaymentStatementService.queryPaymentByDate(escortPaymentStatement);
    }

    /**
     * 首页查询一周内的总营业额
     * @return
     */
    @GetMapping("/paySumAmount")
    public AjaxResult paySumAmount(){
        List<EscortPaymentStatement> list = escortPaymentStatementService.selectEscortPaymentSumAmountList();
        List<String> paymentTimeStrList = new ArrayList<>();
        List<BigDecimal> paymentAmountList = new ArrayList<>();
        
        for (EscortPaymentStatement vo : list) {
            paymentTimeStrList.add(vo.getPaymentTimeStr());
            paymentAmountList.add(vo.getPaymentAmount());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("paymentTimeStr", CollUtil.newArrayList(paymentTimeStrList));
        map.put("paymentAmount", CollUtil.newArrayList(paymentAmountList));
        return AjaxResult.success(map);
    }

    /**
     * 支付流水列表查询，统计订单数量、支付金额总数
     */
    @GetMapping("/queryEscortPaymentSumAmount")
    public EscortPaymentStatement queryEscortPaymentSumAmount(EscortPaymentStatement escortPaymentStatement) {
        return escortPaymentStatementService.selectEscortPaymentSumAmount(escortPaymentStatement);
    }
}
