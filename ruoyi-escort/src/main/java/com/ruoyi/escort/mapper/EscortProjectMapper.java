package com.ruoyi.escort.mapper;

import java.util.List;
import com.ruoyi.escort.domain.EscortProject;

/**
 * 项目管理Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public interface EscortProjectMapper 
{
    /**
     * 查询项目管理
     * 
     * @param projectId 项目管理主键
     * @return 项目管理
     */
    public EscortProject selectEscortProjectByProjectId(Long projectId);

    /**
     * 查询项目管理列表
     * 
     * @param escortProject 项目管理
     * @return 项目管理集合
     */
    public List<EscortProject> selectEscortProjectList(EscortProject escortProject);

    /**
     * 新增项目管理
     * 
     * @param escortProject 项目管理
     * @return 结果
     */
    public int insertEscortProject(EscortProject escortProject);

    /**
     * 修改项目管理
     * 
     * @param escortProject 项目管理
     * @return 结果
     */
    public int updateEscortProject(EscortProject escortProject);

    /**
     * 删除项目管理
     * 
     * @param projectId 项目管理主键
     * @return 结果
     */
    public int deleteEscortProjectByProjectId(Long projectId);

    /**
     * 批量删除项目管理
     * 
     * @param projectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEscortProjectByProjectIds(Long[] projectIds);
}
