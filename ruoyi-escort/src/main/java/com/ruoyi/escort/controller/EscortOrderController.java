package com.ruoyi.escort.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.escort.domain.EscortOrder;
import com.ruoyi.escort.domain.OrderProjectInfoVo;
import com.ruoyi.escort.service.IEscortOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单列表Controller
 *
 * @author ruoyi
 * @date 2023-03-11
 */
@RestController
@RequestMapping("/escort/order")
public class EscortOrderController extends BaseController {
    @Autowired
    private IEscortOrderService escortOrderService;

    /**
     * 查询订单列表列表
     */
    @PreAuthorize("@ss.hasPermi('escort:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(EscortOrder escortOrder) {
        startPage();
        List<EscortOrder> list = escortOrderService.selectEscortOrderList(escortOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单列表列表
     */
    @PreAuthorize("@ss.hasPermi('escort:order:export')")
    @Log(title = "订单列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EscortOrder escortOrder) {
        List<EscortOrder> list = escortOrderService.selectEscortOrderList(escortOrder);
        ExcelUtil<EscortOrder> util = new ExcelUtil<EscortOrder>(EscortOrder.class);
        util.exportExcel(response, list, "订单列表数据");
    }

    /**
     * 获取订单列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('escort:order:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId) {
        return success(escortOrderService.selectEscortOrderByOrderId(orderId));
    }

    /**
     * 新增订单列表
     */
    @PreAuthorize("@ss.hasPermi('escort:order:add')")
    @Log(title = "订单列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EscortOrder escortOrder) {
        return toAjax(escortOrderService.insertEscortOrder(escortOrder));
    }

    /**
     * 修改订单列表
     */
    @PreAuthorize("@ss.hasPermi('escort:order:edit')")
    @Log(title = "订单列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EscortOrder escortOrder) {
        return toAjax(escortOrderService.updateEscortOrder(escortOrder));
    }

    /**
     * 删除订单列表
     */
    @PreAuthorize("@ss.hasPermi('escort:order:remove')")
    @Log(title = "订单列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds) {
        return toAjax(escortOrderService.deleteEscortOrderByOrderIds(orderIds));
    }

    /**
     * 查询订单列表列表-带附加信息
     */
    @PreAuthorize("@ss.hasPermi('escort:order:list')")
    @GetMapping("/infoList")
    public TableDataInfo infoList(EscortOrder escortOrder) {
        startPage();
        List<EscortOrder> list = escortOrderService.selectEscortOrderInfoList(escortOrder);
        return getDataTable(list);
    }

    /**
     * 查询当月订单的项目类型及其数量
     */
    @GetMapping("/projectTypeInfo")
    public AjaxResult projectTypeInfo() {
        List<OrderProjectInfoVo> list = escortOrderService.projectTypeInfo();
        List<String> projectNameList = list.stream().map(escortOrder -> escortOrder.getName()).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("projectName", projectNameList);
        map.put("projectTypeInfo", list);
        return AjaxResult.success(map);
    }
}
