package slugapp.com.sluglife.models;

import java.util.Calendar;

import slugapp.com.sluglife.enums.MonthEnum;

/**
 * Created by isayyuhh_s on 7/18/2015
 */

public class Date extends BaseObject {
    private final static MonthEnum[] months = MonthEnum.values();

    public String string;
    public boolean defined;
    public MonthEnum month;
    public int day;
    public int year;
    public int hour;
    public double value;

    /**
     * Constructors
     */
    public Date(String string) {
        this.string = string;
        this.defined = false;
        this.month = null;
    }

    public Date(int month, int day, int year, int hour) {
        this.defined = false;

        for (MonthEnum currMonth : months) {
            if (currMonth.number != month) continue;
            this.month = currMonth;
            break;
        }

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

    public Date(String newMonth, String newDay, String newYear, String newHour) {
        this.defined = false;

        /** Month */
        for (MonthEnum currMonth : months) {
            String month = currMonth.name;
            if (month.compareTo(newMonth) == 0) {
                this.month = currMonth;
                break;
            }
        }
        if (this.month == null) return;

        /** Day */
        String day;
        if (newDay.length() > 4) return;
        else if (newDay.length() < 3) day = newDay;
        else day = newDay.substring(0, newDay.length() - 2);
        if (!this.isInteger(day)) return;
        this.day = Integer.parseInt(day);

        /** Year */
        if (newYear.length() != 4) return;
        if (!this.isInteger(newYear)) return;
        this.year = Integer.parseInt(newYear);

        /** Time */
        if (newHour.length() < 3 || newHour.length() > 4) return;
        String hour = newHour.substring(0, newHour.length() - 2);
        if (!this.isInteger(hour)) return;
        this.hour = Integer.parseInt(hour);
        this.hour += this.hour % 12 == Calendar.AM ? 0 : 12;

        this.value = year + (this.month.number * 0.01) + (this.day * 0.0001);

        this.defined = true;
    }

    public String getDateString() {
        if (!this.defined) return this.string;
        return String.valueOf(this.month.name) + " " + String.valueOf(this.day) + ", " +
                String.valueOf(this.year);
    }

    public String getTimeString() {
        if (!this.defined) return this.string;
        String time = String.valueOf(this.hour % 12 != 0 ? this.hour % 12 : 12);
        return time + (this.hour % 12 == Calendar.AM ? "am" : "pm");
    }

    public String getFullString() {
        if (!this.defined) return this.string;
        return this.getDateString() + " at " + this.getTimeString();
    }

    /**
     * Static Methods
     */
    public static Date getToday() {
        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        return new Date(month, day, year, hour);
    }

    public static int compareDates(Date lhs, Date rhs) {
        return lhs.value - rhs.value < 0 ? -1 : lhs.value - rhs.value > 0 ? 1 : 0;
    }
}
