package com.ruoyi.escort.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.escort.mapper.EscortProjectMapper;
import com.ruoyi.escort.domain.EscortProject;
import com.ruoyi.escort.service.IEscortProjectService;

/**
 * 项目管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
@Service
public class EscortProjectServiceImpl implements IEscortProjectService 
{
    @Autowired
    private EscortProjectMapper escortProjectMapper;

    /**
     * 查询项目管理
     * 
     * @param projectId 项目管理主键
     * @return 项目管理
     */
    @Override
    public EscortProject selectEscortProjectByProjectId(Long projectId)
    {
        return escortProjectMapper.selectEscortProjectByProjectId(projectId);
    }

    /**
     * 查询项目管理列表
     * 
     * @param escortProject 项目管理
     * @return 项目管理
     */
    @Override
    public List<EscortProject> selectEscortProjectList(EscortProject escortProject)
    {
        return escortProjectMapper.selectEscortProjectList(escortProject);
    }

    /**
     * 新增项目管理
     * 
     * @param escortProject 项目管理
     * @return 结果
     */
    @Override
    public int insertEscortProject(EscortProject escortProject)
    {
        escortProject.setCreateTime(DateUtils.getNowDate());
        return escortProjectMapper.insertEscortProject(escortProject);
    }

    /**
     * 修改项目管理
     * 
     * @param escortProject 项目管理
     * @return 结果
     */
    @Override
    public int updateEscortProject(EscortProject escortProject)
    {
        escortProject.setUpdateTime(DateUtils.getNowDate());
        return escortProjectMapper.updateEscortProject(escortProject);
    }

    /**
     * 批量删除项目管理
     * 
     * @param projectIds 需要删除的项目管理主键
     * @return 结果
     */
    @Override
    public int deleteEscortProjectByProjectIds(Long[] projectIds)
    {
        return escortProjectMapper.deleteEscortProjectByProjectIds(projectIds);
    }

    /**
     * 删除项目管理信息
     * 
     * @param projectId 项目管理主键
     * @return 结果
     */
    @Override
    public int deleteEscortProjectByProjectId(Long projectId)
    {
        return escortProjectMapper.deleteEscortProjectByProjectId(projectId);
    }
}
