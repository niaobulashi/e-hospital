package com.ruoyi.escort.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 支付单流水对象 escort_payment_statement
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public class EscortPaymentStatement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 支付流水ID */
    private Long paymentId;

    /** 支付流水号 */
    @Excel(name = "支付流水号")
    private String paymentNo;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNo;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;

    /** 支付金额 */
    @Excel(name = "支付金额")
    private BigDecimal paymentAmount;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setPaymentId(Long paymentId) 
    {
        this.paymentId = paymentId;
    }

    public Long getPaymentId() 
    {
        return paymentId;
    }
    public void setPaymentNo(String paymentNo) 
    {
        this.paymentNo = paymentNo;
    }

    public String getPaymentNo() 
    {
        return paymentNo;
    }
    public void setOrderNo(String orderNo) 
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo() 
    {
        return orderNo;
    }
    public void setPaymentTime(Date paymentTime) 
    {
        this.paymentTime = paymentTime;
    }

    public Date getPaymentTime() 
    {
        return paymentTime;
    }
    public void setPaymentAmount(BigDecimal paymentAmount) 
    {
        this.paymentAmount = paymentAmount;
    }

    public BigDecimal getPaymentAmount() 
    {
        return paymentAmount;
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
            .append("paymentId", getPaymentId())
            .append("paymentNo", getPaymentNo())
            .append("orderNo", getOrderNo())
            .append("paymentTime", getPaymentTime())
            .append("paymentAmount", getPaymentAmount())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
