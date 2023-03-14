package com.ruoyi.escort.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.escort.mapper.EscortMemberMapper;
import com.ruoyi.escort.domain.EscortMember;
import com.ruoyi.escort.service.IEscortMemberService;

/**
 * 会员信息Service业务层处理
 * 
 * @author Hulang
 * @date 2023-03-14
 */
@Service
public class EscortMemberServiceImpl implements IEscortMemberService 
{
    @Autowired
    private EscortMemberMapper escortMemberMapper;

    /**
     * 查询会员信息
     * 
     * @param memberId 会员信息主键
     * @return 会员信息
     */
    @Override
    public EscortMember selectEscortMemberByMemberId(Long memberId)
    {
        return escortMemberMapper.selectEscortMemberByMemberId(memberId);
    }

    /**
     * 查询会员信息列表
     * 
     * @param escortMember 会员信息
     * @return 会员信息
     */
    @Override
    public List<EscortMember> selectEscortMemberList(EscortMember escortMember)
    {
        return escortMemberMapper.selectEscortMemberList(escortMember);
    }

    /**
     * 新增会员信息
     * 
     * @param escortMember 会员信息
     * @return 结果
     */
    @Override
    public int insertEscortMember(EscortMember escortMember)
    {
        escortMember.setCreateTime(DateUtils.getNowDate());
        return escortMemberMapper.insertEscortMember(escortMember);
    }

    /**
     * 修改会员信息
     * 
     * @param escortMember 会员信息
     * @return 结果
     */
    @Override
    public int updateEscortMember(EscortMember escortMember)
    {
        escortMember.setUpdateTime(DateUtils.getNowDate());
        return escortMemberMapper.updateEscortMember(escortMember);
    }

    /**
     * 批量删除会员信息
     * 
     * @param memberIds 需要删除的会员信息主键
     * @return 结果
     */
    @Override
    public int deleteEscortMemberByMemberIds(Long[] memberIds)
    {
        return escortMemberMapper.deleteEscortMemberByMemberIds(memberIds);
    }

    /**
     * 删除会员信息信息
     * 
     * @param memberId 会员信息主键
     * @return 结果
     */
    @Override
    public int deleteEscortMemberByMemberId(Long memberId)
    {
        return escortMemberMapper.deleteEscortMemberByMemberId(memberId);
    }
}
