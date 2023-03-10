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
import com.ruoyi.escort.domain.EscortProject;
import com.ruoyi.escort.service.IEscortProjectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 项目管理Controller
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
@RestController
@RequestMapping("/escort/project")
public class EscortProjectController extends BaseController
{
    @Autowired
    private IEscortProjectService escortProjectService;

    /**
     * 查询项目管理列表
     */
    @PreAuthorize("@ss.hasPermi('escort:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(EscortProject escortProject)
    {
        startPage();
        List<EscortProject> list = escortProjectService.selectEscortProjectList(escortProject);
        return getDataTable(list);
    }

    /**
     * 导出项目管理列表
     */
    @PreAuthorize("@ss.hasPermi('escort:project:export')")
    @Log(title = "项目管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EscortProject escortProject)
    {
        List<EscortProject> list = escortProjectService.selectEscortProjectList(escortProject);
        ExcelUtil<EscortProject> util = new ExcelUtil<EscortProject>(EscortProject.class);
        util.exportExcel(response, list, "项目管理数据");
    }

    /**
     * 获取项目管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('escort:project:query')")
    @GetMapping(value = "/{projectId}")
    public AjaxResult getInfo(@PathVariable("projectId") Long projectId)
    {
        return success(escortProjectService.selectEscortProjectByProjectId(projectId));
    }

    /**
     * 新增项目管理
     */
    @PreAuthorize("@ss.hasPermi('escort:project:add')")
    @Log(title = "项目管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EscortProject escortProject)
    {
        return toAjax(escortProjectService.insertEscortProject(escortProject));
    }

    /**
     * 修改项目管理
     */
    @PreAuthorize("@ss.hasPermi('escort:project:edit')")
    @Log(title = "项目管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EscortProject escortProject)
    {
        return toAjax(escortProjectService.updateEscortProject(escortProject));
    }

    /**
     * 删除项目管理
     */
    @PreAuthorize("@ss.hasPermi('escort:project:remove')")
    @Log(title = "项目管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{projectIds}")
    public AjaxResult remove(@PathVariable Long[] projectIds)
    {
        return toAjax(escortProjectService.deleteEscortProjectByProjectIds(projectIds));
    }
}
