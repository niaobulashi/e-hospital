package com.ruoyi.escort.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.ruoyi.escort.domain.EscortManage;
import com.ruoyi.escort.service.IEscortManageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 陪诊师管理Controller
 *
 * @author Hulang
 * @date 2023-03-14
 */
@RestController
@RequestMapping("/escort/manage")
public class EscortManageController extends BaseController {
    @Autowired
    private IEscortManageService escortManageService;

    /**
     * 查询陪诊师管理列表
     */
    @PreAuthorize("@ss.hasPermi('escort:manage:list')")
    @GetMapping("/list")
    public TableDataInfo list(EscortManage escortManage) {
        startPage();
        List<EscortManage> list = escortManageService.selectEscortManageList(escortManage);
        return getDataTable(list);
    }

    /**
     * 导出陪诊师管理列表
     */
    @PreAuthorize("@ss.hasPermi('escort:manage:export')")
    @Log(title = "陪诊师管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EscortManage escortManage) {
        List<EscortManage> list = escortManageService.selectEscortManageList(escortManage);
        ExcelUtil<EscortManage> util = new ExcelUtil<EscortManage>(EscortManage.class);
        util.exportExcel(response, list, "陪诊师管理数据");
    }

    /**
     * 获取陪诊师管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('escort:manage:query')")
    @GetMapping(value = "/{escortId}")
    public AjaxResult getInfo(@PathVariable("escortId") Long escortId) {
        return success(escortManageService.selectEscortManageByEscortId(escortId));
    }

    /**
     * 新增陪诊师管理
     */
    @PreAuthorize("@ss.hasPermi('escort:manage:add')")
    @Log(title = "陪诊师管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EscortManage escortManage) {
        return toAjax(escortManageService.insertEscortManage(escortManage));
    }

    /**
     * 修改陪诊师管理
     */
    @PreAuthorize("@ss.hasPermi('escort:manage:edit')")
    @Log(title = "陪诊师管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EscortManage escortManage) {
        return toAjax(escortManageService.updateEscortManage(escortManage));
    }

    /**
     * 删除陪诊师管理
     */
    @PreAuthorize("@ss.hasPermi('escort:manage:remove')")
    @Log(title = "陪诊师管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{escortIds}")
    public AjaxResult remove(@PathVariable Long[] escortIds) {
        return toAjax(escortManageService.deleteEscortManageByEscortIds(escortIds));
    }
}
