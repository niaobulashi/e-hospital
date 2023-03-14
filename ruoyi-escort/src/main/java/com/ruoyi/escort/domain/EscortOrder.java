package com.ruoyi.escort.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单列表对象 escort_order
 *
 * @author ruoyi
 * @date 2023-03-11
 */
@NoArgsConstructor
@Data
@ToString
public class EscortOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private String orderNo;

    /**
     * 会员ID
     */
    @Excel(name = "会员ID")
    private Long memberId;

    /**
     * 预约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date appointmentTime;

    /**
     * 医院ID
     */
    @Excel(name = "医院ID")
    private Long hospitalId;

    /**
     * 项目ID
     */
    @Excel(name = "项目ID")
    private Long projectId;

    /**
     * 陪诊员ID
     */
    @Excel(name = "陪诊员ID")
    private Long escortId;

    /**
     * 订单状态（0已付款 1服务中 2已完成）
     */
    @Excel(name = "订单状态", readConverterExp = "0=已付款,1=服务中,2=已完成")
    private String status;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 计划完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "计划完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date planFinishTime;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 陪诊员姓名
     */
    private String escortName;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目金额
     */
    private BigDecimal projectAmount;

    /**
     * 会员名称
     */
    private String memberName;

    /**
     * 会员手机
     */
    private String memberPhone;


    public EscortOrder(String orderNo, Long memberId, Date appointmentTime, Long hospitalId, Long projectId,
                       Long escortId, String status, Date planFinishTime) {
        this.orderNo = orderNo;
        this.memberId = memberId;
        this.appointmentTime = appointmentTime;
        this.hospitalId = hospitalId;
        this.projectId = projectId;
        this.escortId = escortId;
        this.status = status;
        this.planFinishTime = planFinishTime;
    }
}
