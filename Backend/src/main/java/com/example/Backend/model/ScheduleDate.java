package com.example.Backend.model;

import lombok.Data;

@Data
public class ScheduleDate implements Comparable<ScheduleDate>{

    private String second;

    private String minute;

    private String hour;

    private String dayMonth;

    private String month;

    private String dayWeek;

    @Override
    public int compareTo(ScheduleDate o) {
        if(this.second.equals(o.getSecond()) && this.minute.equals(o.getMinute()) && this.hour.equals(o.getHour())
                && this.dayMonth.equals(o.getDayMonth()) && this.month.equals(o.getMonth()) && this.dayWeek.equals(o.getDayWeek()))
            return 0;

        return 1;
    }
}
