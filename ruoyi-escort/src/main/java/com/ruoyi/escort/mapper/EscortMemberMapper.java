package com.ruoyi.escort.mapper;

import java.util.List;
import com.ruoyi.escort.domain.EscortMember;

/**
 * 会员信息Mapper接口
 * 
 * @author Hulang
 * @date 2023-03-14
 */
public interface EscortMemberMapper 
{
    /**
     * 查询会员信息
     * 
     * @param memberId 会员信息主键
     * @return 会员信息
     */
    public EscortMember selectEscortMemberByMemberId(Long memberId);

    /**
     * 查询会员信息列表
     * 
     * @param escortMember 会员信息
     * @return 会员信息集合
     */
    public List<EscortMember> selectEscortMemberList(EscortMember escortMember);

    /**
     * 新增会员信息
     * 
     * @param escortMember 会员信息
     * @return 结果
     */
    public int insertEscortMember(EscortMember escortMember);

    /**
     * 修改会员信息
     * 
     * @param escortMember 会员信息
     * @return 结果
     */
    public int updateEscortMember(EscortMember escortMember);

    /**
     * 删除会员信息
     * 
     * @param memberId 会员信息主键
     * @return 结果
     */
    public int deleteEscortMemberByMemberId(Long memberId);

    /**
     * 批量删除会员信息
     * 
     * @param memberIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEscortMemberByMemberIds(Long[] memberIds);
}
