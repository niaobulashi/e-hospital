package com.ruoyi.escort.service.impl;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.convert.Convert;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.escort.mapper.EscortHospitalMapper;
import com.ruoyi.escort.domain.EscortHospital;
import com.ruoyi.escort.service.IEscortHospitalService;

import static com.ruoyi.common.utils.SecurityUtils.getUsername;

/**
 * 医院管理Service业务层处理
 *
 * @author HuLang
 * @date 2023-03-09
 */
@Service
public class EscortHospitalServiceImpl implements IEscortHospitalService {
	@Autowired
	private EscortHospitalMapper escortHospitalMapper;
	
	/**
	 * 查询医院管理
	 *
	 * @param hospitalId 医院管理主键
	 * @return 医院管理
	 */
	@Override
	public EscortHospital selectEscortHospitalByHospitalId(Long hospitalId) {
		EscortHospital escortHospital = escortHospitalMapper.selectEscortHospitalByHospitalId(hospitalId);
		List<String> hospitalServerTimeList = Arrays.asList(escortHospital.getHospitalServerTime().split("-"));
		escortHospital.setHospitalServerTimeList(hospitalServerTimeList);
		
		List<String> hospitalAreaList = Arrays.asList(escortHospital.getHospitalArea().split("/"));
		escortHospital.setHospitalAreaList(hospitalAreaList);
		return escortHospital;
	}
	
	/**
	 * 查询医院管理列表
	 *
	 * @param escortHospital 医院管理
	 * @return 医院管理
	 */
	@Override
	public List<EscortHospital> selectEscortHospitalList(EscortHospital escortHospital) {
		return escortHospitalMapper.selectEscortHospitalList(escortHospital);
	}
	
	/**
	 * 新增医院管理
	 *
	 * @param escortHospital 医院管理
	 * @return 结果
	 */
	@Override
	public int insertEscortHospital(EscortHospital escortHospital) {
		escortHospital.setCreateTime(DateUtils.getNowDate());
		escortHospital.setCreateBy(getUsername());
		escortHospital.setHospitalServerTime(StringUtils.listToString(escortHospital.getHospitalServerTimeList(), '-'));
		escortHospital.setHospitalArea(StringUtils.listToString(escortHospital.getHospitalAreaList(), '/'));
		return escortHospitalMapper.insertEscortHospital(escortHospital);
	}
	
	
	
	/**
	 * 修改医院管理
	 *
	 * @param escortHospital 医院管理
	 * @return 结果
	 */
	@Override
	public int updateEscortHospital(EscortHospital escortHospital) {
		escortHospital.setUpdateTime(DateUtils.getNowDate());
		escortHospital.setUpdateBy(getUsername());
		escortHospital.setHospitalServerTime(StringUtils.listToString(escortHospital.getHospitalServerTimeList(), '-'));
		escortHospital.setHospitalArea(StringUtils.listToString(escortHospital.getHospitalAreaList(), '/'));
		return escortHospitalMapper.updateEscortHospital(escortHospital);
	}
	
	/**
	 * 批量删除医院管理
	 *
	 * @param hospitalIds 需要删除的医院管理主键
	 * @return 结果
	 */
	@Override
	public int deleteEscortHospitalByHospitalIds(Long[] hospitalIds) {
		return escortHospitalMapper.deleteEscortHospitalByHospitalIds(hospitalIds);
	}
	
	/**
	 * 删除医院管理信息
	 *
	 * @param hospitalId 医院管理主键
	 * @return 结果
	 */
	@Override
	public int deleteEscortHospitalByHospitalId(Long hospitalId) {
		return escortHospitalMapper.deleteEscortHospitalByHospitalId(hospitalId);
	}
	
	/**
	 * 修改医院状态
	 *
	 * @param escortHospital 医院列表
	 * @return 结果
	 */
	@Override
	public int updateHospitalStatus(EscortHospital escortHospital)
	{
		return escortHospitalMapper.updateEscortHospital(escortHospital);
	}
}
