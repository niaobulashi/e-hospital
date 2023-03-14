package com.ruoyi.escort.mapper;

import java.util.List;

import com.ruoyi.escort.domain.EscortManage;

/**
 * 陪诊师管理Mapper接口
 *
 * @author Hulang
 * @date 2023-03-14
 */
public interface EscortManageMapper {
    /**
     * 查询陪诊师管理
     *
     * @param escortId 陪诊师管理主键
     * @return 陪诊师管理
     */
    public EscortManage selectEscortManageByEscortId(Long escortId);

    /**
     * 查询陪诊师管理列表
     *
     * @param escortManage 陪诊师管理
     * @return 陪诊师管理集合
     */
    public List<EscortManage> selectEscortManageList(EscortManage escortManage);

    /**
     * 新增陪诊师管理
     *
     * @param escortManage 陪诊师管理
     * @return 结果
     */
    public int insertEscortManage(EscortManage escortManage);

    /**
     * 修改陪诊师管理
     *
     * @param escortManage 陪诊师管理
     * @return 结果
     */
    public int updateEscortManage(EscortManage escortManage);

    /**
     * 删除陪诊师管理
     *
     * @param escortId 陪诊师管理主键
     * @return 结果
     */
    public int deleteEscortManageByEscortId(Long escortId);

    /**
     * 批量删除陪诊师管理
     *
     * @param escortIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEscortManageByEscortIds(Long[] escortIds);
}
