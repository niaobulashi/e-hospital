package com.ruoyi.escort.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.apifan.common.random.RandomSource;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.EscortEnums;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.RandInfoUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.escort.domain.*;
import com.ruoyi.escort.mapper.EscortOrderMapper;
import com.ruoyi.escort.service.*;
import com.ruoyi.system.service.ISysConfigService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private IEscortHospitalService escortHospitalService;

    @Autowired
    private IEscortManageService escortManageService;

    @Autowired
    private IEscortPaymentStatementService escortPaymentStatementService;

    @Autowired
    private IEscortMemberService escortMemberService;

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
        int createCount = RandomUtil.randomInt(count - 3, count + 3);
        String startDateStr = DateUtils.getDate() + " " + startTime;
        String endDateStr = DateUtils.getDate() + " " + endTime;
        Set<Long> dates = DateUtils.randomDateLong(startDateStr, endDateStr, createCount);
        log.info("每天根据设置的时间区间{}-{}，获取{}个订单生成时间集合-end", startDateStr, endDateStr, createCount);
        log.info("时间集合为：{}", dates.stream().map(aLong -> DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,
                new Date(aLong))).collect(Collectors.joining(",")));
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
                } catch (Exception e) {
                    log.error("生成订单时间：{}，出现异常：{}", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()), e);
                }
                // 执行此次定时任务 再定时还未执行的时间任务 递归的方式
                scheduledCreateOrderTask(settlementTimes);
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
        Long memberId = getUserId();
        // 随机获取医院
        List<EscortHospital> hospitalList = escortHospitalService.selectEscortHospitalList(new EscortHospital());
        Long hospitalId = RandomUtil.randomEle(hospitalList).getHospitalId();
        // 随机获取项目
        List<EscortProject> projectList = escortProjectService.selectEscortProjectList(new EscortProject());
        EscortProject escortProject = RandomUtil.randomEle(projectList);
        Long projectId = escortProject.getProjectId();
        BigDecimal projectAmount = escortProject.getProjectAmount();
        // 随机生成预约时间
        Date appointmentTime = getAppointmentTimeByHospital(new Date(settlementTime));
        // 随机生成计划完成时间
        Date planFinishTime = getPlanFinishTime(appointmentTime);
        // 随机获取陪诊员
        List<EscortManage> manageList = escortManageService.selectEscortManageList(new EscortManage());
        Long escortId = RandomUtil.randomEle(manageList).getEscortId();
        // 状态
        String status = EscortEnums.OrderStatus.PAID.getCode();
        // 订单号
        String orderNo = "O" + IdUtils.getUUIDBySnow();
        EscortOrder order = new EscortOrder(orderNo, memberId, appointmentTime, hospitalId, projectId, escortId, status, planFinishTime);
        order.setCreateBy(Constants.SYS_USER_NAME);
        order.setCreateTime(DateUtils.getNowDate());
        escortOrderMapper.insertEscortOrder(order);
        // 生成支付单流水
        insertPayment(order.getOrderNo(), projectAmount);
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
        String minutes = RandomUtil.randomEle(new String[]{"10", "20", "30", "40", "50"});
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

    /**
     * 生成支付单流水
     *
     * @param orderNo
     * @param amount
     */
    private void insertPayment(String orderNo, BigDecimal amount) {
        log.info("生成支付单流水-start,参数为：订单号-{}，金额-{}", orderNo, amount);
        EscortPaymentStatement escortPaymentStatement = new EscortPaymentStatement();
        escortPaymentStatement.setPaymentNo("P" + IdUtils.getUUIDBySnow());
        escortPaymentStatement.setPaymentAmount(amount);
        escortPaymentStatement.setPaymentTime(new Date());
        escortPaymentStatement.setOrderNo(orderNo);
        escortPaymentStatement.setCreateBy(Constants.SYS_USER_NAME);
        escortPaymentStatementService.insertEscortPaymentStatement(escortPaymentStatement);
        log.info("生成支付单流水-end");
    }

    /**
     * 生成会员信息并保存
     *
     * @return
     */
    private Long getUserId() {
        log.info("生成会员信息并保存-start");
        EscortMember escortMember = new EscortMember();
        escortMember.setMemberBusinessName(EscortEnums.MemberBusiness.bus_01.getName());
        escortMember.setMemberCorpName(EscortEnums.MemberBusiness.bus_01.getName());
        String[] nameAndSex = RandInfoUtils.getFamilyNameAndSex();
        escortMember.setMemberName(nameAndSex[0]);
        escortMember.setMemberPhone(RandomSource.personInfoSource().randomChineseMobile());
        escortMemberService.insertEscortMember(escortMember);
        log.info("生成会员信息并保存-end");

        return escortMember.getMemberId();
    }

    /**
     * 根据预约日期改变订单状态为服务中
     */
    @Override
    public void changeOrderStatusService() {
        // 获取预约日期为当天的订单
        EscortOrder order = new EscortOrder();
        order.setAppointmentTime(DateUtils.getNowDate());
        List<EscortOrder> list = escortOrderMapper.selectEscortOrderListToday(order);
        // 并转为set<预约日期>集合
        Set<Long> appointmentTimes = list.stream().map(escortOrder -> escortOrder.getAppointmentTime().getTime()).collect(Collectors.toSet());
        // 执行改变订单状态为服务中任务
        String status = EscortEnums.OrderStatus.SERVICE.getCode();
        scheduledChangeOrderStatusTask(appointmentTimes, status);
    }

    /**
     * 根据计划完成时间日期改变订单状态为已完成
     */
    @Override
    public void changeOrderStatusFinish() {
        // 获取计划完成时间为当天的订单
        EscortOrder order = new EscortOrder();
        order.setPlanFinishTime(DateUtils.getNowDate());
        List<EscortOrder> list = escortOrderMapper.selectEscortOrderListToday(order);
        // 并转为set<计划完成时间>集合
        Set<Long> planFinishTimes = list.stream().map(escortOrder -> escortOrder.getPlanFinishTime().getTime()).collect(Collectors.toSet());
        // 执行改变订单状态为服务中任务
        String status = EscortEnums.OrderStatus.FINISH.getCode();
        scheduledChangeOrderStatusTask(planFinishTimes, status);
    }

    /**
     * 执行改变订单状态任务
     *
     * @param times
     * @param status
     */
    private void scheduledChangeOrderStatusTask(Set<Long> times, String status) {
        long nowTime = System.currentTimeMillis();
        Long minSettlementTime = DateUtils.minSettlementTime(nowTime, times);
        if (minSettlementTime != null) {
            log.info("修改状态为：{}，生成时间为：{}", status, DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date(minSettlementTime)));
        }
        if (minSettlementTime != null) {
            // schedule 在延迟多少毫秒后 只执行一次
            scheduledExecutorService.schedule(() -> {
                // 这里使用try catch 保证定时任务不中断
                try {
                    // 执行修改订单状态的任务
                    log.info("执行修改订单状态的任务,状态：{}，时间:{}", status, DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()));
                    changeOrderStatus(minSettlementTime, status);
                } catch (Exception e) {
                    log.error("执行修改订单状态的任务,状态：{}，时间:{}，出现异常：{}", status, DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()), e);
                }
                // 执行此次定时任务 再定时还未执行的时间任务 递归的方式
                scheduledChangeOrderStatusTask(times, status);
            }, minSettlementTime - nowTime, TimeUnit.MILLISECONDS);
        } else {
            log.info("待执行时间为空,执行修改订单状态任务结束");
        }
    }

    /**
     * 修改订单状态通过时间参数
     *
     * @param time
     * @param status
     */
    public void changeOrderStatus(Long time, String status) {
        log.info("修改订单列表通过时间参数，状态改为：{}-start", status);
        EscortOrder order = new EscortOrder();
        order.setUpdateBy(Constants.SYS_USER_NAME);
        order.setUpdateTime(DateUtils.getNowDate());
        order.setStatus(status);
        if (EscortEnums.OrderStatus.SERVICE.getCode().equals(status)) {
            order.setAppointmentTime(new Date(time));
        } else {
            order.setFinishTime(DateUtils.getNowDate());
            order.setPlanFinishTime(new Date(time));
        }
        escortOrderMapper.updateEscortOrderByParam(order);
        log.info("修改订单列表通过时间参数-end");
    }

    /**
     * 查询订单列表列表-带附加信息
     *
     * @param escortOrder 订单列表
     * @return 订单列表
     */
    @Override
    public List<EscortOrder> selectEscortOrderInfoList(EscortOrder escortOrder) {
        return escortOrderMapper.selectEscortOrderInfoList(escortOrder);
    }
}
