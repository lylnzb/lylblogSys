package com.lylblog.common.util;

import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateUtil {
    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE = "yyyy-MM-dd";

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    public DateUtil() {
    }

    public static String datetimeToStr(Date date, String format) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }
    }

    public static String dateTimeToStr(Date date) {
        return datetimeToStr(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateToStr(Date date) {
        return datetimeToStr(date, "yyyy-MM-dd");
    }

    public static String dateToStr(Date date, String format) {
        return datetimeToStr(date, format);
    }

    public static String getCurrentDate() {
        return (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    }

    public static String getCurrentDate(String format) {
        return (new SimpleDateFormat(format)).format(new Date());
    }

    public static String getCurrentDatetime() {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    }

    public static String getCurrentDatetime(String format) {
        return (new SimpleDateFormat(format)).format(new Date());
    }

    public static int getCurrentTimeHashCode() {
        return String.valueOf(System.currentTimeMillis()).hashCode();
    }

    public static Date getDateBegin(Date date) {
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (date != null) {
            try {
                return DateFormat.getDateInstance(2, Locale.CHINA).parse(ymdFormat.format(date));
            } catch (ParseException var3) {
                System.err.println("DataFromat error");
            }
        }

        return null;
    }

    public static Date getDateEnd(Date date) {
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (date != null) {
            try {
                Date endDate = strToDate(ymdFormat.format(new Date(date.getTime() + 86400000L)));
                endDate = new Date(endDate.getTime() - 1000L);
                return endDate;
            } catch (Exception var3) {
                System.err.println("DataFromat error");
            }
        }

        return null;
    }

    public static long getNow() {
        return System.currentTimeMillis();
    }

    public static String getTime() {
        Date d = new Date();
        String re = datetimeToStr(d, "yyyyMMddHHmmssSSS");
        return re;
    }

    public static String getTime(String format) {
        Date d = new Date();
        String re = datetimeToStr(d, format);
        return re;
    }

    public static Date strToFormatDate(String date, String format) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date, new ParsePosition(0));
        }
    }

    public static Date strToDate(String date) {
        return strToFormatDate(date, "yyyy-MM-dd");
    }

    public static final Date strToDate(String dateStr, String format) {
        return strToFormatDate(dateStr, format);
    }

    public static Date strToDateTime(String date) {
        return strToFormatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date strToDateTime(String date, String format) {
        return strToFormatDate(date, format);
    }

    public static Timestamp strToTimestamp(String str) throws Exception {
        if (StringUtils.isEmpty(str)) {
            throw new Exception("转换错误");
        } else {
            return str.trim().length() > 10 ? new Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(str).getTime()) : new Timestamp((new SimpleDateFormat("yyyy-MM-dd")).parse(str).getTime());
        }
    }

    public static Timestamp strToTimestamp(String sDate, String sFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
        Date t = sdf.parse(sDate);
        return new Timestamp(t.getTime());
    }

    public static boolean validateExpireDate(long timeMillis, long expireTimeMillis) {
        return getNow() - timeMillis > expireTimeMillis;
    }

    public static String getHHmmssSSS() {
        Date d = new Date();
        return getHHmmssSSS(d);
    }

    public static String getHHmmssSSS(Date d) {
        int hh = Integer.valueOf(datetimeToStr(d, "HH"));
        int mm = Integer.valueOf(datetimeToStr(d, "mm"));
        int ss = Integer.valueOf(datetimeToStr(d, "ss"));
        int sss = Integer.valueOf(datetimeToStr(d, "SSS"));
        int time = 0;
        if (hh != 0) {
            time += hh * 60 * 60 * 1000;
        }

        if (mm != 0) {
            time += mm * 60 * 1000;
        }

        if (ss != 0) {
            time += ss * 1000;
        }

        if (sss != 0) {
            time += sss;
        }

        String str;
        for(str = String.valueOf(time); str.length() < 8; str = "0" + str) {
            ;
        }

        return str;
    }

    public static Date caculateDate(Date date, Integer count, int status) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(status, calendar.get(status) + count);
        return calendar.getTime();
    }

    public static int caculateDaysNumber(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, month - 1);
        return cal.getActualMaximum(5);
    }

    public static Date caculateDateByNextBaseMonth(Date now, Integer month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        /*cal.set(2, cal.get(2) + month + NumberUtils.INTEGER_ONE);
        cal.set(5, NumberUtils.INTEGER_ZERO);*/
        return cal.getTime();
    }

    public static Date caculateFinallyDatebyDateType(Date date, int dateType, int dateNumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch(dateType) {
            case 1:
//                calendar.add(dateType, dateNumber + 1);
//                calendar.set(2, 0);
//                calendar.set(5, NumberUtils.INTEGER_ZERO);
                break;
            case 2:
//                calendar.add(2, dateNumber + 1);
//                calendar.set(5, NumberUtils.INTEGER_ZERO);
                break;
            case 3:
            case 4:
            default:
                throw new RuntimeException("dateType value error");
            case 5:
                calendar.add(5, dateNumber);
        }

        return calendar.getTime();
    }

    public static int compareTime(String dateTime1, String dateTime2) {
        if (!StringUtils.isEmpty(dateTime1) && !StringUtils.isEmpty(dateTime1)) {
            DateFormat df = null;
            if (dateTime1.length() == 10 && dateTime2.length() == 10) {
                df = new SimpleDateFormat("yyyy-MM-dd");
            } else {
                if (dateTime1.length() != 19 || dateTime2.length() != 19) {
                    return 2;
                }

                df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();

            try {
                c1.setTime(df.parse(dateTime1));
                c2.setTime(df.parse(dateTime2));
            } catch (ParseException var6) {
                System.err.println("格式不正确");
            }

            int result = c1.compareTo(c2);
            return result;
        } else {
            return 2;
        }
    }

    public static String format(String dateTxt) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(dateTxt);
        long delta = new Date().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    public static String format(Date date) {
        long delta = new Date().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

    public static void main(String[] args) {
        Date now = new Date();
        String nowStr = dateTimeToStr(now);
        System.out.println(nowStr);
        Date fDate = caculateFinallyDatebyDateType(now, 10, 3);
        System.out.println(dateTimeToStr(fDate));
    }
}
