package com.ruoyi.escort.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.EscortEnums;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.escort.domain.EscortOrder;
import com.ruoyi.escort.domain.EscortProject;
import com.ruoyi.escort.mapper.EscortOrderMapper;
import com.ruoyi.escort.service.IEscortOrderService;
import com.ruoyi.escort.service.IEscortProjectService;
import com.ruoyi.system.service.ISysConfigService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 订单列表Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-11
 */
@Service
public class EscortOrderServiceImpl implements IEscortOrderService {

    public static final Logger log = LoggerFactory.getLogger(EscortOrderServiceImpl.class);

    @Autowired
    private EscortOrderMapper escortOrderMapper;

    @Autowired
    private IEscortProjectService escortProjectService;

    @Autowired
    private ISysConfigService iSysConfigService;


    static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(3,
            new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

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
     * 每天根据设置的时间区间，随机生成N条订单
     */
    @Override
    public void createRandomOrder() {
        // 获取随机生成时间集合
        Set<Long> dates = getOrderCreateDates();
        // 执行生成订单任务
        scheduledCreateOrderTask(dates);
    }

    /**
     * 每天根据设置的时间区间，获取N个订单生成时间集合
     */
    private Set<Long> getOrderCreateDates() {
        log.info("每天根据设置的时间区间，获取N个订单生成时间集合-start");
        String startTime = iSysConfigService.selectConfigByKey("create.order.time.start");
        String endTime = iSysConfigService.selectConfigByKey("create.order.time.end");
        int count = Integer.parseInt(iSysConfigService.selectConfigByKey("create.order.count"));
        String startDateStr = DateUtils.getDate() + " " + startTime;
        String endDateStr = DateUtils.getDate() + " " + endTime;
        Set<Long> dates = DateUtils.randomDateLong(startDateStr, endDateStr, count);
        log.info("每天根据设置的时间区间{}-{}，获取{}个订单生成时间集合-end", startDateStr, endDateStr, count);
        return dates;
    }

    /**
     * 根据时间集合定时执行生成订单任务
     *
     * @param settlementTimes
     */
    private void scheduledCreateOrderTask(Set<Long> settlementTimes) {
        long nowTime = System.currentTimeMillis();
        Long minSettlementTime = DateUtils.minSettlementTime(nowTime, settlementTimes);
        if (minSettlementTime != null) {
            log.info("生成订单时间为：" + DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date(minSettlementTime)));
        }
        if (minSettlementTime != null) {
            // schedule 在延迟多少毫秒后 只执行一次
            scheduledExecutorService.schedule(() -> {
                // 这里使用try catch 保证定时任务不中断
                try {
                    // 执行生成订单的任务
                    log.info("执行生成订单任务时间" + DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
                    randomAssemblyOrder(minSettlementTime);
                    // 执行此次定时任务 再定时还未执行的时间任务 递归的方式
                    scheduledCreateOrderTask(settlementTimes);
                } catch (Exception e) {
                    log.error("生成订单时间：{}，出现异常：{}", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()), e);
                }
            }, minSettlementTime - nowTime, TimeUnit.MILLISECONDS);
        } else {
            log.info("待执行时间为空,执行生成订单任务结束");
        }
    }

    /**
     * 随机拼装订单信息并保存
     *
     * @param settlementTime
     */
    public void randomAssemblyOrder(Long settlementTime) {
        log.info("随机拼装订单信息并保存-start");
        // 随机生成会员并保存
        Long memberId = 0L;
        // 随机获取医院
        Long hospitalId = 0L;
        // 随机获取项目
        List<EscortProject> projectList = escortProjectService.selectEscortProjectList(new EscortProject());
        Long projectId = RandomUtil.randomEle(projectList).getProjectId();
        // 随机生成预约时间
        Date appointmentTime = getAppointmentTimeByHospital(new Date(settlementTime));
        // 随机生成计划完成时间
        Date planFinishTime = getPlanFinishTime(appointmentTime);
        // 随机获取陪诊员
        Long escortId = 0L;
        // 状态
        String status = EscortEnums.OrderStatus.PAID.getCode();
        // 订单号
        String orderNo = "O" + IdUtils.fastSimpleUUID();
        EscortOrder order = new EscortOrder(orderNo, memberId, appointmentTime, hospitalId, projectId, escortId, status, planFinishTime);
        order.setCreateBy(Constants.SYS_USER_NAME);
        order.setCreateTime(DateUtils.getNowDate());
        escortOrderMapper.insertEscortOrder(order);
        log.info("随机拼装订单信息并保存-end");
    }

    /**
     * 获取医院营业时间中的随机预约时间
     * 某个日期1-3天内的8-17点之间的10分整数点
     */
    private Date getAppointmentTimeByHospital(Date baseDate) {
        // RandomUtils.nextInt 左闭右开
        String date = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, DateUtils.getdateAddDay(baseDate, RandomUtils.nextInt(1, 4)));
        String hour = String.valueOf(RandomUtils.nextInt(8, 16));
        String minutes = RandomUtil.randomEle(new String[]{"10", "20", "30", "40", "50", "60"});
        String appointmentTimeStr = date + " " + hour + ":" + minutes + ":00";
        return DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, appointmentTimeStr);
    }

    /**
     * 获取某个时间4-7小时内的随机时间 计划完成时间，默认5小时
     */
    private Date getPlanFinishTime(Date baseDate) {
        // 默认5小时
        Date planFinishTime = DateUtil.offsetHour(baseDate, 5).toJdkDate();
        String dateStart = DateUtil.offsetHour(baseDate, 4).toString(DateUtils.YYYY_MM_DD_HH_MM_SS);
        String dateEnd = DateUtil.offsetHour(baseDate, 7).toString(DateUtils.YYYY_MM_DD_HH_MM_SS);
        Set<Long> longSet = DateUtils.randomDateLong(dateStart, dateEnd, 1);
        if (longSet != null && longSet.size() > 0) {
            planFinishTime = DateUtil.date((Long) longSet.toArray()[0]);
        }
        return planFinishTime;
    }
}
