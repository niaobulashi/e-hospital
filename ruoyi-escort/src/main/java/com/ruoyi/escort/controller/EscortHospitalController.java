package com.ruoyi.escort.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.utils.DateUtils;
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
import com.ruoyi.escort.domain.EscortHospital;
import com.ruoyi.escort.service.IEscortHospitalService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 医院管理Controller
 *
 * @author HuLang
 * @date 2023-03-09
 */
@RestController
@RequestMapping("/escort/hospital")
public class EscortHospitalController extends BaseController {
	@Autowired
	private IEscortHospitalService escortHospitalService;
	
	/**
	 * 查询医院管理列表
	 */
	@PreAuthorize("@ss.hasPermi('escort:hospital:list')")
	@GetMapping("/list")
	public TableDataInfo list(EscortHospital escortHospital) {
		startPage();
		List<EscortHospital> list = escortHospitalService.selectEscortHospitalList(escortHospital);
		return getDataTable(list);
	}
	
	/**
	 * 导出医院管理列表
	 */
	@PreAuthorize("@ss.hasPermi('escort:hospital:export')")
	@Log(title = "医院管理", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, EscortHospital escortHospital) {
		List<EscortHospital> list = escortHospitalService.selectEscortHospitalList(escortHospital);
		ExcelUtil<EscortHospital> util = new ExcelUtil<EscortHospital>(EscortHospital.class);
		util.exportExcel(response, list, "医院管理数据");
	}
	
	/**
	 * 获取医院管理详细信息
	 */
	@PreAuthorize("@ss.hasPermi('escort:hospital:query')")
	@GetMapping(value = "/{hospitalId}")
	public AjaxResult getInfo(@PathVariable("hospitalId") Long hospitalId) {
		return success(escortHospitalService.selectEscortHospitalByHospitalId(hospitalId));
	}
	
	/**
	 * 新增医院管理
	 */
	@PreAuthorize("@ss.hasPermi('escort:hospital:add')")
	@Log(title = "医院管理", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody EscortHospital escortHospital) {
		return toAjax(escortHospitalService.insertEscortHospital(escortHospital));
	}
	
	/**
	 * 修改医院管理
	 */
	@PreAuthorize("@ss.hasPermi('escort:hospital:edit')")
	@Log(title = "医院管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody EscortHospital escortHospital) {
		return toAjax(escortHospitalService.updateEscortHospital(escortHospital));
	}
	
	/**
	 * 删除医院管理
	 */
	@PreAuthorize("@ss.hasPermi('escort:hospital:remove')")
	@Log(title = "医院管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{hospitalIds}")
	public AjaxResult remove(@PathVariable Long[] hospitalIds) {
		return toAjax(escortHospitalService.deleteEscortHospitalByHospitalIds(hospitalIds));
	}
	
	
	/**
	 * 状态修改
	 */
	@PreAuthorize("@ss.hasPermi('escort:hospital:edit')")
	@Log(title = "医院管理", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	public AjaxResult changeStatus(@RequestBody EscortHospital escortHospital)
	{
		escortHospital.setUpdateBy(getUsername());
		escortHospital.setUpdateTime(DateUtils.getNowDate());
		return toAjax(escortHospitalService.updateHospitalStatus(escortHospital));
	}
}
