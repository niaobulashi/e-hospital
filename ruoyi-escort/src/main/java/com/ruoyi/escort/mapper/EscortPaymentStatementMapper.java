package com.ruoyi.escort.mapper;

import java.util.List;
import com.ruoyi.escort.domain.EscortPaymentStatement;

/**
 * 支付单流水Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public interface EscortPaymentStatementMapper 
{
    /**
     * 查询支付单流水
     * 
     * @param paymentId 支付单流水主键
     * @return 支付单流水
     */
    public EscortPaymentStatement selectEscortPaymentStatementByPaymentId(Long paymentId);

    /**
     * 查询支付单流水列表
     * 
     * @param escortPaymentStatement 支付单流水
     * @return 支付单流水集合
     */
    public List<EscortPaymentStatement> selectEscortPaymentStatementList(EscortPaymentStatement escortPaymentStatement);

    /**
     * 新增支付单流水
     * 
     * @param escortPaymentStatement 支付单流水
     * @return 结果
     */
    public int insertEscortPaymentStatement(EscortPaymentStatement escortPaymentStatement);

    /**
     * 修改支付单流水
     * 
     * @param escortPaymentStatement 支付单流水
     * @return 结果
     */
    public int updateEscortPaymentStatement(EscortPaymentStatement escortPaymentStatement);

    /**
     * 删除支付单流水
     * 
     * @param paymentId 支付单流水主键
     * @return 结果
     */
    public int deleteEscortPaymentStatementByPaymentId(Long paymentId);

    /**
     * 批量删除支付单流水
     * 
     * @param paymentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEscortPaymentStatementByPaymentIds(Long[] paymentIds);

    /**
     * 查询支付单流水列表-带会员
     *
     * @param escortPaymentStatement 支付单流水
     * @return 支付单流水集合
     */
    public List<EscortPaymentStatement> selectEscortPaymentAndMemList(EscortPaymentStatement escortPaymentStatement);
    
    /**
     * 首页查询一周内的总营业额
     *
     * @param escortPaymentStatement 支付单流水
     * @return 支付单流水集合
     */
    public List<EscortPaymentStatement> selectEscortPaymentSumAmountList(EscortPaymentStatement escortPaymentStatement);
    
}
