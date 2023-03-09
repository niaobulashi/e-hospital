package com.ruoyi.escort.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.escort.mapper.EscortHospitalMapper;
import com.ruoyi.escort.domain.EscortHospital;
import com.ruoyi.escort.service.IEscortHospitalService;

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
		return escortHospitalMapper.selectEscortHospitalByHospitalId(hospitalId);
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
}
