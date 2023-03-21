package com.ruoyi.escort.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
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
@Data
@ToString
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
    
    private String paymentTimeStr;
    
    /** 支付开始时间 */
    private String beginTime;
    
    /** 支付结束时间 */
    private String endTime;

    /** 支付金额 */
    @Excel(name = "支付金额")
    private BigDecimal paymentAmount;

    /** 支付金额总数 */
    private BigDecimal paymentAmountSum;

    /** 支付单个数总数*/
    private String paymentCount;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 会员名称 */
    private String memberName;

    /** 会员编号 */
    private String memberId;

    /**
     * 目标支付日期（目前用于首页查看当日完成的流水额）
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String targetPaymentTime;

}
