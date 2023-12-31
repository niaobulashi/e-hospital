package com.ruoyi.escort.service;

import com.ruoyi.escort.domain.EscortOrder;
import com.ruoyi.escort.domain.OrderProjectInfoVo;

import java.util.List;


/**
 * 订单列表Service接口
 *
 * @author ruoyi
 * @date 2023-03-11
 */
public interface IEscortOrderService {
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
     * 批量删除订单列表
     *
     * @param orderIds 需要删除的订单列表主键集合
     * @return 结果
     */
    public int deleteEscortOrderByOrderIds(Long[] orderIds);

    /**
     * 删除订单列表信息
     *
     * @param orderId 订单列表主键
     * @return 结果
     */
    public int deleteEscortOrderByOrderId(Long orderId);

    /**
     * 每天根据设置的时间区间，随机生成N条订单
     */
    public void createRandomOrder();

    /**
     * 根据预约日期改变订单状态为服务中
     */
    public void changeOrderStatusService();

    /**
     * 根据计划完成时间日期改变订单状态为已完成
     */
    public void changeOrderStatusFinish();

    /**
     * 查询订单列表列表-带附加信息
     *
     * @param escortOrder 订单列表
     * @return 订单列表集合
     */
    public List<EscortOrder> selectEscortOrderInfoList(EscortOrder escortOrder);

    /**
     * 查询当月订单的项目类型及其数量
     *
     * @return
     */
    public List<OrderProjectInfoVo> projectTypeInfo();
}
