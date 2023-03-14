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
import com.ruoyi.escort.domain.EscortMember;
import com.ruoyi.escort.service.IEscortMemberService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 会员信息Controller
 * 
 * @author Hulang
 * @date 2023-03-14
 */
@RestController
@RequestMapping("/escort/member")
public class EscortMemberController extends BaseController
{
    @Autowired
    private IEscortMemberService escortMemberService;

    /**
     * 查询会员信息列表
     */
    @PreAuthorize("@ss.hasPermi('escort:member:list')")
    @GetMapping("/list")
    public TableDataInfo list(EscortMember escortMember)
    {
        startPage();
        List<EscortMember> list = escortMemberService.selectEscortMemberList(escortMember);
        return getDataTable(list);
    }

    /**
     * 导出会员信息列表
     */
    @PreAuthorize("@ss.hasPermi('escort:member:export')")
    @Log(title = "会员信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EscortMember escortMember)
    {
        List<EscortMember> list = escortMemberService.selectEscortMemberList(escortMember);
        ExcelUtil<EscortMember> util = new ExcelUtil<EscortMember>(EscortMember.class);
        util.exportExcel(response, list, "会员信息数据");
    }

    /**
     * 获取会员信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('escort:member:query')")
    @GetMapping(value = "/{memberId}")
    public AjaxResult getInfo(@PathVariable("memberId") Long memberId)
    {
        return success(escortMemberService.selectEscortMemberByMemberId(memberId));
    }

    /**
     * 新增会员信息
     */
    @PreAuthorize("@ss.hasPermi('escort:member:add')")
    @Log(title = "会员信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EscortMember escortMember)
    {
        return toAjax(escortMemberService.insertEscortMember(escortMember));
    }

    /**
     * 修改会员信息
     */
    @PreAuthorize("@ss.hasPermi('escort:member:edit')")
    @Log(title = "会员信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EscortMember escortMember)
    {
        return toAjax(escortMemberService.updateEscortMember(escortMember));
    }

    /**
     * 删除会员信息
     */
    @PreAuthorize("@ss.hasPermi('escort:member:remove')")
    @Log(title = "会员信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{memberIds}")
    public AjaxResult remove(@PathVariable Long[] memberIds)
    {
        return toAjax(escortMemberService.deleteEscortMemberByMemberIds(memberIds));
    }
}
