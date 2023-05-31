package com.example.ssuny;

public class Time {

    private int reday, hour, minute, month, day, year;
    private String name, am_pm, memo;

    public String getName() { return name; }
    public void setName(String name) { this.name = name;}

    public int getReday() { return reday; }
    public void setReday(int reday) { this.reday = reday; }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getAm_pm() {
        return am_pm;
    }

    public void setAm_pm(String am_pm) {
        this.am_pm = am_pm;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) { this.memo = memo;}

    @Override public String toString() {
        final StringBuffer sb = new StringBuffer("Time{");
        sb.append("hour=").append(hour);
        sb.append(", minute=").append(minute);
        sb.append('}');
        return sb.toString();
    }
}
