package slugapp.com.sluglife.models;

import java.util.Calendar;

import slugapp.com.sluglife.enums.MonthEnum;

/**
 * Created by isayyuhh_s on 7/18/2015
 */

// TODO: implement saving military format hour instead of saving "am" and "pm" strings

public class Date extends BaseObject {
    private final static MonthEnum[] months = MonthEnum.values();

    public String string;
    public boolean defined;
    public MonthEnum month;
    public int day;
    public int year;
    public int hour;
    public int startTime;
    public int endTime;
    public String startTOD;
    public String endTOD;
    public double comparor;

    /**
     * Constructors
     */
    public Date(String string) {
        this.string = string;
        this.month = null;
        this.comparor = 0.0d;
        this.defined = false;
    }

    public Date(int month, int day, int year, int hour) {
        this.defined = false;

        for (MonthEnum currMonth : months) {
            if (currMonth.order != month) continue;
            this.month = currMonth;
            break;
        }

        if (this.month == null) return;
        if (day > 31) return;
        if (hour < 0 || hour > 23) return;

        this.day = day;
        this.year = year;
        this.hour = hour;

        this.startTime = hour % 12;
        this.endTime = hour % 12;
        this.startTOD = hour / 12 == Calendar.AM ? "am" : "pm";
        this.endTOD = hour / 12 == Calendar.AM ? "am" : "pm";

        this.comparor = year + (month * 0.01) + (day * 0.0001) + (hour * 0.000001);

        this.defined = true;
    }

    public Date(String newMonth, String newDay, String newYear, String newStartTime,
                String newEndTime) {
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

        /** Start Time */
        if (newStartTime.length() < 3 || newStartTime.length() > 4) return;
        String startTime = newStartTime.substring(0, newStartTime.length() - 2);
        String startTOD = newStartTime.substring(newStartTime.length() - 2, newStartTime.length());
        if (!this.isInteger(startTime)) return;
        this.startTime = Integer.parseInt(startTime);
        this.startTOD = startTOD;

        /** End Time */
        if (newEndTime.length() < 3 || newEndTime.length() > 4) return;
        String endTime = newEndTime.substring(0, newEndTime.length() - 2);
        String endTOD = newEndTime.substring(newEndTime.length() - 2, newEndTime.length());
        if (!this.isInteger(endTime)) return;
        this.endTime = Integer.parseInt(endTime);
        this.endTOD = endTOD;

        this.comparor = year + (this.month.order * 0.01) + (this.day * 0.0001);

        /** Date String */
        this.string = newMonth + " " + newDay + ", " + newYear + " | " + newStartTime;
        this.defined = true;
    }

    public String getDateString() {
        if (!this.defined) return this.string;
        return String.valueOf(this.month.name) + " " + String.valueOf(this.day) + ", " +
                String.valueOf(this.year);
    }

    public String getFullString() {
        if (!this.defined) return this.string;
        return String.valueOf(this.month.name) + " " + String.valueOf(this.day) + ", " +
                String.valueOf(this.year) + " | " + String.valueOf(this.startTime) +
                (this.hour % 12 == Calendar.AM ? "am" : "pm");
    }

    public static Date getToday() {
        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        return new Date(month, day, year, hour);
    }

    public static int compareEvents(Event lhs, Event rhs) {
        if (!lhs.date.defined) return 1;
        else if (!rhs.date.defined) return -1;
        int check = compareDatesTemp(lhs.date, rhs.date);
        if (check == 0) check = lhs.name.compareTo(rhs.name);
        return check;
    }

    private static int compareDatesTemp(Date lhs, Date rhs) {
        double result = compareDoubles(lhs.comparor, rhs.comparor);
        return result < 0 ? -1 : result > 0 ? 1 : 0;
    }

    private static int compareDates(Date lhs, Date rhs) {
        int check;
        if ((check = compInts(lhs.year, rhs.year)) != 0) return check;
        if ((check = compMonths(lhs.month, rhs.month)) != 0) return check;
        if ((check = compInts(lhs.day, rhs.day)) != 0) return check;
        if ((check = compTODs(lhs.startTOD, rhs.startTOD)) != 0) return check;
        if ((check = compInts(lhs.startTime, rhs.startTime)) != 0) return check;
        if ((check = compTODs(lhs.endTOD, rhs.endTOD)) != 0) return check;
        return compInts(lhs.endTime, rhs.endTime);
    }

    private static int compMonths(MonthEnum lhs, MonthEnum rhs) {
        if (lhs.order < rhs.order) return -1;
        else if (lhs.order > rhs.order) return 1;
        return 0;
    }

    private static int compTODs(String lhs, String rhs) {
        if (lhs.equals("am") && rhs.equals("pm")) return -1;
        else if (lhs.equals("pm") && rhs.equals("am")) return 1;
        return 0;
    }

    private static int compInts(int lhs, int rhs) {
        return lhs - rhs;
    }

    private static double compareDoubles(double lhs, double rhs) {
        return lhs - rhs;
    }
}
