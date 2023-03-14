package com.ruoyi.escort.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 陪诊师管理对象 escort_manage
 *
 * @author Hulang
 * @date 2023-03-14
 */
@Data
public class EscortManage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 陪诊师编号
     */
    private Long escortId;

    /**
     * 所属商家ID
     */
    private Long escortBusinessId;

    /**
     * 所属商家
     */
    @Excel(name = "所属商家")
    private String escortBusinessName;

    /**
     * 所属机构ID
     */
    private Long escortCorpId;

    /**
     * 所属机构
     */
    @Excel(name = "所属机构")
    private String escortCorpName;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String escortName;

    /**
     * 手机
     */
    @Excel(name = "手机")
    private String escortPhone;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private String escortSex;

    /**
     * 年龄
     */
    @Excel(name = "年龄")
    private String escortAge;

    /**
     * 护龄
     */
    @Excel(name = "护龄")
    private String escortNursingAge;

    /**
     * 籍贯
     */
    @Excel(name = "籍贯")
    private String escortNativePlace;
    
    /**
     * 籍贯List
     */
    private List<String> escortNativePlaceList;

    /**
     * 住址
     */
    @Excel(name = "住址")
    private String escortAddress;

    /**
     * 个人简介
     */
    @Excel(name = "个人简介")
    private String escortIntroduce;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    private String escortIdentityCard;

    /**
     * 身份证正面
     */
    @Excel(name = "身份证正面")
    private String escortIdentityCardFront;

    /**
     * 身份证反面
     */
    @Excel(name = "身份证反面")
    private String escortIdentityCardBack;

    /**
     * 体检报告
     */
    @Excel(name = "体检报告")
    private String escortReport;

    /**
     * 审核状态
     */
    @Excel(name = "审核状态")
    private String escortAutitStatus;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("escortId", getEscortId())
                .append("escortBusinessId", getEscortBusinessId())
                .append("escortBusinessName", getEscortBusinessName())
                .append("escortCorpId", getEscortCorpId())
                .append("escortCorpName", getEscortCorpName())
                .append("escortName", getEscortName())
                .append("escortPhone", getEscortPhone())
                .append("escortSex", getEscortSex())
                .append("escortAge", getEscortAge())
                .append("escortNursingAge", getEscortNursingAge())
                .append("escortNativePlace", getEscortNativePlace())
                .append("escortAddress", getEscortAddress())
                .append("escortIntroduce", getEscortIntroduce())
                .append("escortIdentityCard", getEscortIdentityCard())
                .append("escortIdentityCardFront", getEscortIdentityCardFront())
                .append("escortIdentityCardBack", getEscortIdentityCardBack())
                .append("escortReport", getEscortReport())
                .append("escortAutitStatus", getEscortAutitStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
