package slugapp.com.sluglife.models;

import java.util.Calendar;

import slugapp.com.sluglife.enums.MonthEnum;

/**
 * Created by isaiah on 7/18/2015
 * <p/>
 * This file contains a date object.
 */
public class DateObject extends BaseObject {
    private final static MonthEnum[] months = MonthEnum.values();

    public String string;
    public boolean defined;
    public MonthEnum month;
    public int day;
    public int year;
    public int hour;
    public double value;

    /**
     * Constructor
     *
     * @param string Date string
     */
    public DateObject(String string) {
        this.string = string;
        this.defined = false;
        this.month = null;
    }

    /**
     * Constructor
     *
     * @param month Month
     * @param day   Day
     * @param year  Year
     * @param hour  Hour
     */
    public DateObject(int month, int day, int year, int hour) {
        this.defined = false;

        for (MonthEnum currMonth : months) {
            if (currMonth.number != month) continue;
            this.month = currMonth;
            break;
        }

        // TODO: add date limit constants

        if (this.month == null) return;
        if (day > 31) return;
        if (hour < 0 || hour > 23) return;
        if (year > 9999) return;

        this.day = day;
        this.year = year;
        this.hour = hour;

        this.value = year + (month * 0.01) + (day * 0.0001) + (hour * 0.000001);

        this.defined = true;
    }

    /**
     * Constructor
     *
     * @param month Month
     * @param day   Day
     * @param year  Year
     * @param hour  Hour
     */
    public DateObject(String month, String day, String year, String hour) {
        this.defined = false;

        for (MonthEnum currMonth : months) {
            String newMonth = currMonth.name;
            if (month.compareTo(newMonth) == 0) {
                this.month = currMonth;
                break;
            }
        }
        if (this.month == null) return;

        String newDay;
        if (day.length() > 4) return;
        else if (day.length() < 3) newDay = day;
        else newDay = day.substring(0, day.length() - 2);
        if (!this.isInteger(newDay)) return;
        this.day = Integer.parseInt(newDay);

        if (year.length() != 4) return;
        if (!this.isInteger(year)) return;
        this.year = Integer.parseInt(year);

        if (hour.length() < 3 || hour.length() > 4) return;
        String newHour = hour.substring(0, hour.length() - 2);
        if (!this.isInteger(newHour)) return;
        this.hour = Integer.parseInt(newHour);
        this.hour += this.hour % 12 == Calendar.AM ? 0 : 12;

        this.value = this.year + (this.month.number * 0.01) + (this.day * 0.0001);

        this.defined = true;
    }

    /**
     * Gets string of date with month, day, and year
     *
     * @return Date string
     */
    public String getDateString() {
        if (!this.defined) return this.string;
        return String.valueOf(this.month.name) + " " + String.valueOf(this.day) + ", " +
                String.valueOf(this.year);
    }

    /**
     * Gets string of date with time
     *
     * @return Date string
     */
    public String getTimeString() {
        if (!this.defined) return this.string;
        String time = String.valueOf(this.hour % 12 != 0 ? this.hour % 12 : 12);
        return time + (this.hour % 12 == Calendar.AM ? "am" : "pm");
    }

    /**
     * Gets full date string
     *
     * @return Date string
     */
    public String getFullString() {
        if (!this.defined) return this.string;
        return this.getDateString() + " at " + this.getTimeString();
    }

    /**
     * Gets today's date
     *
     * @return Today's date
     */
    public static DateObject getToday() {
        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        return new DateObject(month, day, year, hour);
    }

    /**
     * Compares dates
     *
     * @param lhs Left operand
     * @param rhs Right operand
     * @return Integer showing which order the operands are in
     */
    public static int compareDates(DateObject lhs, DateObject rhs) {
        return lhs.value - rhs.value < 0 ? -1 : lhs.value - rhs.value > 0 ? 1 : 0;
    }
}
