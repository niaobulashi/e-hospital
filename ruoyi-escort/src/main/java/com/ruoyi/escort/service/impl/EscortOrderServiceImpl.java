package com.ruoyi.escort.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.escort.mapper.EscortOrderMapper;
import com.ruoyi.escort.domain.EscortOrder;
import com.ruoyi.escort.service.IEscortOrderService;

/**
 * 订单列表Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
@Service
public class EscortOrderServiceImpl implements IEscortOrderService 
{
    @Autowired
    private EscortOrderMapper escortOrderMapper;

    /**
     * 查询订单列表
     * 
     * @param orderId 订单列表主键
     * @return 订单列表
     */
    @Override
    public EscortOrder selectEscortOrderByOrderId(Long orderId)
    {
        return escortOrderMapper.selectEscortOrderByOrderId(orderId);
    }

    /**
     * 查询订单列表列表
     * 
     * @param escortOrder 订单列表
     * @return 订单列表
     */
    @Override
    public List<EscortOrder> selectEscortOrderList(EscortOrder escortOrder)
    {
        return escortOrderMapper.selectEscortOrderList(escortOrder);
    }

    /**
     * 新增订单列表
     * 
     * @param escortOrder 订单列表
     * @return 结果
     */
    @Override
    public int insertEscortOrder(EscortOrder escortOrder)
    {
        escortOrder.setCreateTime(DateUtils.getNowDate());
        return escortOrderMapper.insertEscortOrder(escortOrder);
    }

    /**
     * 修改订单列表
     * 
     * @param escortOrder 订单列表
     * @return 结果
     */
    @Override
    public int updateEscortOrder(EscortOrder escortOrder)
    {
        escortOrder.setUpdateTime(DateUtils.getNowDate());
        return escortOrderMapper.updateEscortOrder(escortOrder);
    }

    /**
     * 批量删除订单列表
     * 
     * @param orderIds 需要删除的订单列表主键
     * @return 结果
     */
    @Override
    public int deleteEscortOrderByOrderIds(Long[] orderIds)
    {
        return escortOrderMapper.deleteEscortOrderByOrderIds(orderIds);
    }

    /**
     * 删除订单列表信息
     * 
     * @param orderId 订单列表主键
     * @return 结果
     */
    @Override
    public int deleteEscortOrderByOrderId(Long orderId)
    {
        return escortOrderMapper.deleteEscortOrderByOrderId(orderId);
    }
}
