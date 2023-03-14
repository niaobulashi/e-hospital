package com.ruoyi.escort.service.impl;

import java.util.Arrays;
import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.escort.mapper.EscortManageMapper;
import com.ruoyi.escort.domain.EscortManage;
import com.ruoyi.escort.service.IEscortManageService;

import static com.ruoyi.common.utils.SecurityUtils.getUsername;

/**
 * 陪诊师管理Service业务层处理
 *
 * @author Hulang
 * @date 2023-03-14
 */
@Service
public class EscortManageServiceImpl implements IEscortManageService {
	@Autowired
	private EscortManageMapper escortManageMapper;
	
	/**
	 * 查询陪诊师管理
	 *
	 * @param escortId 陪诊师管理主键
	 * @return 陪诊师管理
	 */
	@Override
	public EscortManage selectEscortManageByEscortId(Long escortId) {
		EscortManage escortManage = escortManageMapper.selectEscortManageByEscortId(escortId);
		List<String> escortNativePlaceList = Arrays.asList(escortManage.getEscortNativePlace().split("/"));
		escortManage.setEscortNativePlaceList(escortNativePlaceList);
		return escortManage;
	}
	
	/**
	 * 查询陪诊师管理列表
	 *
	 * @param escortManage 陪诊师管理
	 * @return 陪诊师管理
	 */
	@Override
	public List<EscortManage> selectEscortManageList(EscortManage escortManage) {
		return escortManageMapper.selectEscortManageList(escortManage);
	}
	
	/**
	 * 新增陪诊师管理
	 *
	 * @param escortManage 陪诊师管理
	 * @return 结果
	 */
	@Override
	public int insertEscortManage(EscortManage escortManage) {
		escortManage.setCreateTime(DateUtils.getNowDate());
		escortManage.setCreateBy(getUsername());
		escortManage.setEscortNativePlace(StringUtils.listToString(escortManage.getEscortNativePlaceList(), '/'));
		return escortManageMapper.insertEscortManage(escortManage);
	}
	
	/**
	 * 修改陪诊师管理
	 *
	 * @param escortManage 陪诊师管理
	 * @return 结果
	 */
	@Override
	public int updateEscortManage(EscortManage escortManage) {
		escortManage.setUpdateTime(DateUtils.getNowDate());
		escortManage.setUpdateBy(getUsername());
		escortManage.setEscortNativePlace(StringUtils.listToString(escortManage.getEscortNativePlaceList(), '/'));
		return escortManageMapper.updateEscortManage(escortManage);
	}
	
	/**
	 * 批量删除陪诊师管理
	 *
	 * @param escortIds 需要删除的陪诊师管理主键
	 * @return 结果
	 */
	@Override
	public int deleteEscortManageByEscortIds(Long[] escortIds) {
		return escortManageMapper.deleteEscortManageByEscortIds(escortIds);
	}
	
	/**
	 * 删除陪诊师管理信息
	 *
	 * @param escortId 陪诊师管理主键
	 * @return 结果
	 */
	@Override
	public int deleteEscortManageByEscortId(Long escortId) {
		return escortManageMapper.deleteEscortManageByEscortId(escortId);
	}
}
