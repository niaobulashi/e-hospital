package com.ruoyi.escort.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.ruoyi.common.enums.EscortEnums;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.escort.domain.EscortOrder;
import com.ruoyi.escort.domain.EscortProject;
import com.ruoyi.escort.mapper.EscortOrderMapper;
import com.ruoyi.escort.service.IEscortOrderService;
import com.ruoyi.escort.service.IEscortProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单列表Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-10
 */
@Service
public class EscortOrderServiceImpl implements IEscortOrderService {
    @Autowired
    private EscortOrderMapper escortOrderMapper;

    @Autowired
    private IEscortProjectService escortProjectService;

    /**
     * 查询订单列表
     *
     * @param orderId 订单列表主键
     * @return 订单列表
     */
    @Override
    public EscortOrder selectEscortOrderByOrderId(Long orderId) {
        return escortOrderMapper.selectEscortOrderByOrderId(orderId);
    }

    /**
     * 查询订单列表列表
     *
     * @param escortOrder 订单列表
     * @return 订单列表
     */
    @Override
    public List<EscortOrder> selectEscortOrderList(EscortOrder escortOrder) {
        return escortOrderMapper.selectEscortOrderList(escortOrder);
    }

    /**
     * 新增订单列表
     *
     * @param escortOrder 订单列表
     * @return 结果
     */
    @Override
    public int insertEscortOrder(EscortOrder escortOrder) {
        escortOrder.setCreateBy(SecurityUtils.getUsername());
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
    public int updateEscortOrder(EscortOrder escortOrder) {
        escortOrder.setUpdateBy(SecurityUtils.getUsername());
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
    public int deleteEscortOrderByOrderIds(Long[] orderIds) {
        return escortOrderMapper.deleteEscortOrderByOrderIds(orderIds);
    }

    /**
     * 删除订单列表信息
     *
     * @param orderId 订单列表主键
     * @return 结果
     */
    @Override
    public int deleteEscortOrderByOrderId(Long orderId) {
        return escortOrderMapper.deleteEscortOrderByOrderId(orderId);
    }

    /**
     * 随机拼装订单信息并保存
     */
    public void randomAssemblyOrder() {
        // 随机生成会员并保存
        Long memberId = 0L;
        // 随机获取医院
        Long hospitalId = 0L;
        // 随机获取项目
        List<EscortProject> projectList = escortProjectService.selectEscortProjectList(new EscortProject());
        Long projectId = RandomUtil.randomEle(projectList).getProjectId();
        // 随机生成预约时间
        Date appointmentTime = DateUtils.getAppointmentTimeByHospital();
        // 随机获取陪诊员
        Long escortId = 0L;
        // 状态
        String status = EscortEnums.OrderStatus.PAID.getCode();
        // 订单号
        String orderNo = "O11";
        EscortOrder order = new EscortOrder(orderNo, memberId, appointmentTime, hospitalId, projectId, escortId, status);
        order.setCreateBy(SecurityUtils.getUsername());
        order.setCreateTime(DateUtils.getNowDate());
        escortOrderMapper.insertEscortOrder(order);
    }
}
