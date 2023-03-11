package com.ruoyi.escort.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单列表对象 escort_order
 *
 * @author ruoyi
 * @date 2023-03-11
 */
@NoArgsConstructor
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

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setEscortId(Long escortId) {
        this.escortId = escortId;
    }

    public Long getEscortId() {
        return escortId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setPlanFinishTime(Date planFinishTime) {
        this.planFinishTime = planFinishTime;
    }

    public Date getPlanFinishTime() {
        return planFinishTime;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("orderId", getOrderId())
                .append("orderNo", getOrderNo())
                .append("memberId", getMemberId())
                .append("appointmentTime", getAppointmentTime())
                .append("hospitalId", getHospitalId())
                .append("projectId", getProjectId())
                .append("escortId", getEscortId())
                .append("status", getStatus())
                .append("finishTime", getFinishTime())
                .append("planFinishTime", getPlanFinishTime())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }

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
