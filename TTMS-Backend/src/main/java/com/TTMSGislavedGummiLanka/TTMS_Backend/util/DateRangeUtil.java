package com.TTMSGislavedGummiLanka.TTMS_Backend.util;

import com.TTMSGislavedGummiLanka.TTMS_Backend.enums.TimeRange;
import java.util.Calendar;
import java.util.Date;

public class DateRangeUtil {

    public static Date[] calculateDateRange(TimeRange timeRange, Date startDate, Date endDate) {
        if (startDate != null && endDate != null) {
            return new Date[]{startDate, endDate};
        }

        Calendar cal = Calendar.getInstance();
        Date end = cal.getTime();

        switch (timeRange) {
            case WEEKLY:
                cal.add(Calendar.WEEK_OF_YEAR, -1);
                break;
            case MONTHLY:
                cal.add(Calendar.MONTH, -1);
                break;
            case YEARLY:
                cal.add(Calendar.YEAR, -1);
                break;
            default:
                cal.add(Calendar.YEAR, -1); // Default to yearly
        }

        Date start = cal.getTime();
        return new Date[]{start, end};
    }
}