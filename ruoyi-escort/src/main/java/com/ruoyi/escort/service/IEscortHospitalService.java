package com.ruoyi.escort.service;

import java.util.List;

import com.ruoyi.escort.domain.EscortHospital;

/**
 * 医院管理Service接口
 *
 * @author HuLang
 * @date 2023-03-09
 */
public interface IEscortHospitalService {
	/**
	 * 查询医院管理
	 *
	 * @param hospitalId 医院管理主键
	 * @return 医院管理
	 */
	public EscortHospital selectEscortHospitalByHospitalId(Long hospitalId);
	
	/**
	 * 查询医院管理列表
	 *
	 * @param escortHospital 医院管理
	 * @return 医院管理集合
	 */
	public List<EscortHospital> selectEscortHospitalList(EscortHospital escortHospital);
	
	/**
	 * 新增医院管理
	 *
	 * @param escortHospital 医院管理
	 * @return 结果
	 */
	public int insertEscortHospital(EscortHospital escortHospital);
	
	/**
	 * 修改医院管理
	 *
	 * @param escortHospital 医院管理
	 * @return 结果
	 */
	public int updateEscortHospital(EscortHospital escortHospital);
	
	/**
	 * 批量删除医院管理
	 *
	 * @param hospitalIds 需要删除的医院管理主键集合
	 * @return 结果
	 */
	public int deleteEscortHospitalByHospitalIds(Long[] hospitalIds);
	
	/**
	 * 删除医院管理信息
	 *
	 * @param hospitalId 医院管理主键
	 * @return 结果
	 */
	public int deleteEscortHospitalByHospitalId(Long hospitalId);
}
