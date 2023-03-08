package com.ruoyi.common.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算相差天数
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
    }

    /**
     * 计算时间差
     * @param endDate
     * @param startTime
     * @return
     */
    public static String timeDistance(Date endDate, Date startTime) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startTime.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 增加 LocalDateTime ==> Date
     */
    public static Date toDate(LocalDateTime temporalAccessor) {
        ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 增加 LocalDate ==> Date
     */
    public static Date toDate(LocalDate temporalAccessor) {
        LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }


    /**
     * date2比date1多的天数:比较是基于年月日做的比较,不计算时分秒
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        // 不同年
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    // 闰年
                    timeDistance += 366;
                } else {
                    // 不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1 + 1);
        } else {
            // 同一年
            return day2 - day1 + 1;
        }
    }

    /**
     * 获取当月第一天
     *
     * @return
     */
    public static String firstDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = null;
        cale = Calendar.getInstance();
        String firstday;
        // 获取当月第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        return firstday;
    }

    /**
     * 获取前月的最后一天
     *
     * @return
     */
    public static String lastDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = null;
        cale = Calendar.getInstance();
        String lastday;
        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = format.format(cale.getTime());
        return lastday;
    }

    /**
     * 生成size数量的随机时间，位于[start,end)范围内 时间正序排列
     *
     * @param start 开始时间
     * @param end   结束时间
     * @param size  生成时间个数
     * @return List<Date>
     * @throws Exception
     */
    public static List<Date> randomDate(String start, String end, int size) throws Exception {

        Date startTime = dateTime(YYYY_MM_DD_HH_MM_SS, start);
        Date endTime = dateTime(YYYY_MM_DD_HH_MM_SS, end);

        Random random = new Random();
        List<Date> dates = random.longs(size, startTime.getTime(), endTime.getTime()).mapToObj(t -> new Date(t)).collect(Collectors.toList());

        dates.sort(Comparator.naturalOrder());

        return dates;
    }

    /**
     * 生成size数量的随机时间，位于[start,end)范围内 时间戳
     *
     * @param start 开始时间
     * @param end   结束时间
     * @param size  生成时间个数
     * @return Set<Long>
     */
    public static Set<Long> randomDateLong(String start, String end, int size) {

        Date startTime = dateTime(YYYY_MM_DD_HH_MM_SS, start);
        Date endTime = dateTime(YYYY_MM_DD_HH_MM_SS, end);

        Random random = new Random();
        Set<Long> dates = random.longs(size, startTime.getTime(), endTime.getTime()).boxed().collect(Collectors.toSet());

        return dates;
    }

    /**
     * @param nowTime         当前时间
     * @param settlementTimes 需要执行任务的时间集合
     * @return 求出比当前时间大的最小时间
     */
    public static Long minSettlementTime(long nowTime, Set<Long> settlementTimes) {
        if (settlementTimes.isEmpty()) {
            return null;
        }
        Long min = Collections.min(settlementTimes);
        if (nowTime > min) {
            settlementTimes.remove(min);
            return minSettlementTime(nowTime, settlementTimes);
        } else {
            return min;
        }
    }

    static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

    /**
     * 根据时间集合定时执行任务
     *
     * @param settlementTimes 需要执行任务的时间集合
     */
    public static void scheduledWorkTask(Set<Long> settlementTimes) {
        long nowTime = System.currentTimeMillis();
        Long minSettlementTime = minSettlementTime(nowTime, settlementTimes);
        if (minSettlementTime != null) {
            logger.info("生成时间为：" + parseDateToStr(YYYY_MM_DD_HH_MM_SS, new Date(minSettlementTime)));
        }
        if (minSettlementTime != null) {
            // schedule 在延迟多少毫秒后 只执行一次
            scheduledExecutorService.schedule(() -> {
                // 这里使用try catch 保证定时任务不中断
                try {
                    // 自己的代码逻辑
                    logger.info("执行了任务" + parseDateToStr(YYYY_MM_DD_HH_MM_SS, new Date()));
                    // 执行此次定时任务 再定时还未执行的时间任务 递归的方式
                    scheduledWorkTask(settlementTimes);
                } catch (Exception e) {
                    logger.info(e.getMessage());
                }
            }, minSettlementTime - nowTime, TimeUnit.MILLISECONDS);
        } else {
            logger.info("待执行时间为空");
        }
    }

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//        List<Date> dates = randomDate("2021-06-21 07:00:00", "2021-06-21 22:00:00", 40);
//        dates.forEach(t -> System.out.println(sdf.format(t)));
//        Set<Long> dates = randomDateLong("2021-06-21 07:00:00", "2021-06-21 22:00:00", 40);
//        dates.forEach(t -> System.out.println(sdf.format(new Date(t))));

        Set<Long> dates = randomDateLong("2023-03-08 11:02:00", "2023-03-08 11:03:00", 10);
        scheduledWorkTask(dates);

    }
}
