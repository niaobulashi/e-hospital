package com.ruoyi.escort.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.escort.mapper.EscortPaymentStatementMapper;
import com.ruoyi.escort.domain.EscortPaymentStatement;
import com.ruoyi.escort.service.IEscortPaymentStatementService;

/**
 * 支付单流水Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
@Service
public class EscortPaymentStatementServiceImpl implements IEscortPaymentStatementService 
{
    @Autowired
    private EscortPaymentStatementMapper escortPaymentStatementMapper;

    /**
     * 查询支付单流水
     * 
     * @param paymentId 支付单流水主键
     * @return 支付单流水
     */
    @Override
    public EscortPaymentStatement selectEscortPaymentStatementByPaymentId(Long paymentId)
    {
        return escortPaymentStatementMapper.selectEscortPaymentStatementByPaymentId(paymentId);
    }

    /**
     * 查询支付单流水列表
     * 
     * @param escortPaymentStatement 支付单流水
     * @return 支付单流水
     */
    @Override
    public List<EscortPaymentStatement> selectEscortPaymentStatementList(EscortPaymentStatement escortPaymentStatement)
    {
        return escortPaymentStatementMapper.selectEscortPaymentStatementList(escortPaymentStatement);
    }

    /**
     * 新增支付单流水
     * 
     * @param escortPaymentStatement 支付单流水
     * @return 结果
     */
    @Override
    public int insertEscortPaymentStatement(EscortPaymentStatement escortPaymentStatement)
    {
        escortPaymentStatement.setCreateTime(DateUtils.getNowDate());
        return escortPaymentStatementMapper.insertEscortPaymentStatement(escortPaymentStatement);
    }

    /**
     * 修改支付单流水
     * 
     * @param escortPaymentStatement 支付单流水
     * @return 结果
     */
    @Override
    public int updateEscortPaymentStatement(EscortPaymentStatement escortPaymentStatement)
    {
        escortPaymentStatement.setUpdateTime(DateUtils.getNowDate());
        return escortPaymentStatementMapper.updateEscortPaymentStatement(escortPaymentStatement);
    }

    /**
     * 批量删除支付单流水
     * 
     * @param paymentIds 需要删除的支付单流水主键
     * @return 结果
     */
    @Override
    public int deleteEscortPaymentStatementByPaymentIds(Long[] paymentIds)
    {
        return escortPaymentStatementMapper.deleteEscortPaymentStatementByPaymentIds(paymentIds);
    }

    /**
     * 删除支付单流水信息
     * 
     * @param paymentId 支付单流水主键
     * @return 结果
     */
    @Override
    public int deleteEscortPaymentStatementByPaymentId(Long paymentId)
    {
        return escortPaymentStatementMapper.deleteEscortPaymentStatementByPaymentId(paymentId);
    }
}
