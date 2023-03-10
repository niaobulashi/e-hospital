package com.ruoyi.escort.mapper;

import java.util.List;
import com.ruoyi.escort.domain.EscortOrder;

/**
 * 订单列表Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public interface EscortOrderMapper 
{
    /**
     * 查询订单列表
     * 
     * @param orderId 订单列表主键
     * @return 订单列表
     */
    public EscortOrder selectEscortOrderByOrderId(Long orderId);

    /**
     * 查询订单列表列表
     * 
     * @param escortOrder 订单列表
     * @return 订单列表集合
     */
    public List<EscortOrder> selectEscortOrderList(EscortOrder escortOrder);

    /**
     * 新增订单列表
     * 
     * @param escortOrder 订单列表
     * @return 结果
     */
    public int insertEscortOrder(EscortOrder escortOrder);

    /**
     * 修改订单列表
     * 
     * @param escortOrder 订单列表
     * @return 结果
     */
    public int updateEscortOrder(EscortOrder escortOrder);

    /**
     * 删除订单列表
     * 
     * @param orderId 订单列表主键
     * @return 结果
     */
    public int deleteEscortOrderByOrderId(Long orderId);

    /**
     * 批量删除订单列表
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEscortOrderByOrderIds(Long[] orderIds);
}
