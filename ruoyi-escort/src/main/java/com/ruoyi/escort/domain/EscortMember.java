package com.ruoyi.escort.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员信息对象 escort_member
 * 
 * @author Hulang
 * @date 2023-03-14
 */
public class EscortMember extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 会员ID */
    private Long memberId;

    /** 所属商家ID */
    private Long memberBusinessId;

    /** 所属商家 */
    @Excel(name = "所属商家")
    private String memberBusinessName;

    /** 所属机构ID */
    private Long memberCorpId;

    /** 所属机构 */
    @Excel(name = "所属机构")
    private String memberCorpName;

    /** 姓名 */
    @Excel(name = "姓名")
    private String memberName;

    /** 手机 */
    @Excel(name = "手机")
    private String memberPhone;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setMemberId(Long memberId) 
    {
        this.memberId = memberId;
    }

    public Long getMemberId() 
    {
        return memberId;
    }
    public void setMemberBusinessId(Long memberBusinessId) 
    {
        this.memberBusinessId = memberBusinessId;
    }

    public Long getMemberBusinessId() 
    {
        return memberBusinessId;
    }
    public void setMemberBusinessName(String memberBusinessName) 
    {
        this.memberBusinessName = memberBusinessName;
    }

    public String getMemberBusinessName() 
    {
        return memberBusinessName;
    }
    public void setMemberCorpId(Long memberCorpId) 
    {
        this.memberCorpId = memberCorpId;
    }

    public Long getMemberCorpId() 
    {
        return memberCorpId;
    }
    public void setMemberCorpName(String memberCorpName) 
    {
        this.memberCorpName = memberCorpName;
    }

    public String getMemberCorpName() 
    {
        return memberCorpName;
    }
    public void setMemberName(String memberName) 
    {
        this.memberName = memberName;
    }

    public String getMemberName() 
    {
        return memberName;
    }
    public void setMemberPhone(String memberPhone) 
    {
        this.memberPhone = memberPhone;
    }

    public String getMemberPhone() 
    {
        return memberPhone;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("memberId", getMemberId())
            .append("memberBusinessId", getMemberBusinessId())
            .append("memberBusinessName", getMemberBusinessName())
            .append("memberCorpId", getMemberCorpId())
            .append("memberCorpName", getMemberCorpName())
            .append("memberName", getMemberName())
            .append("memberPhone", getMemberPhone())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
