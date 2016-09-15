package slugapp.com.sluglife.objects;

import android.content.Context;

import java.util.Calendar;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.MonthEnum;

/**
 * Created by isaiah on 7/18/2015
 * <p/>
 * This file contains a date object.
 */
public class DateObject extends BaseObject {
    private static final int DAY_LOWER_LIMIT = 1;
    private static final int DAY_UPPER_LIMIT = 31;

    private static final int HOUR_LOWER_LIMIT = 0;
    private static final int HOUR_UPPER_LIMIT = 23;

    private static final int YEAR_LOWER_LIMIT = 0;
    private static final int YEAR_UPPER_LIMIT = 9999;

    private static final int LESS_THAN = -1;
    private static final int GREATER_THAN = 1;
    private static final int EQUAL = 1;

    private static final int STANDARD_TIME_MODULUS = 12;

    private static final int NOON_OR_MIDNIGHT_MILITARY = 0;
    private static final int NOON_OR_MIDNIGHT_STANDARD = 12;

    private static final String SPACE = " ";
    private static final String COMMA = ", ";

    private final static MonthEnum[] months = MonthEnum.values();

    public String string;
    public boolean defined;
    public MonthEnum month;
    public int day;
    public int year;
    public int hour;
    public double value;

    private Context context;

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
     * @param context Activity context
     * @param month   Month
     * @param day     Day
     * @param year    Year
     * @param hour    Hour
     */
    public DateObject(Context context, int month, int day, int year, int hour) {
        this.context = context;
        this.defined = false;

        for (MonthEnum currMonth : months) {
            if (currMonth.number != month) continue;
            this.month = currMonth;
            break;
        }

        if (this.month == null) return;
        if (day < DAY_LOWER_LIMIT || day > DAY_UPPER_LIMIT) return;
        if (hour < HOUR_LOWER_LIMIT || hour > HOUR_UPPER_LIMIT) return;
        if (year < YEAR_LOWER_LIMIT || year > YEAR_UPPER_LIMIT) return;

        this.day = day;
        this.year = year;
        this.hour = hour;

        this.value = year + (month * 0.01) + (day * 0.0001) + (hour * 0.000001);

        this.defined = true;
    }

    /**
     * Constructor
     *
     * @param context Activity context
     * @param month   Month
     * @param day     Day
     * @param year    Year
     * @param hour    Hour
     */
    public DateObject(Context context, String month, String day, String year, String hour) {
        this.context = context;
        this.defined = false;

        // TODO: CONSTANTS

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
        if (!isInteger(newDay)) return;
        this.day = Integer.parseInt(newDay);

        if (year.length() != 4) return;
        if (!isInteger(year)) return;
        this.year = Integer.parseInt(year);

        if (hour.length() < 3 || hour.length() > 4) return;
        String newHour = hour.substring(0, hour.length() - 2);
        if (!isInteger(newHour)) return;
        this.hour = Integer.parseInt(newHour);
        this.hour += this.hour % STANDARD_TIME_MODULUS == Calendar.AM ? 0 : 12;

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
        return String.valueOf(this.month.name) + SPACE + String.valueOf(this.day) + COMMA +
                String.valueOf(this.year);
    }

    /**
     * Gets string of date with time
     *
     * @return Date string
     */
    public String getTimeString() {
        if (!this.defined) return this.string;
        return this.getStandardTimeString() + this.getStandardTimeOfDayString();
    }

    /**
     * Gets full date string
     *
     * @return Date string
     */
    public String getFullString() {
        if (!this.defined) return this.string;
        return this.getDateString() + this.context.getString(R.string.detail_event_date) + this.getTimeString();
    }

    /**
     * Gets today's date
     *
     * @return Today's date
     */
    public static DateObject getToday(Context context) {
        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        return new DateObject(context, month, day, year, hour);
    }

    /**
     * Compares dates
     *
     * @param lhs Left operand
     * @param rhs Right operand
     * @return Integer showing which order the operands are in
     */
    public static int compareDates(DateObject lhs, DateObject rhs) {
        return lhs.value < rhs.value ? LESS_THAN : lhs.value > rhs.value ? GREATER_THAN : EQUAL;
    }

    /**
     * Gets time of day in standard time format
     *
     * @return Time of day in standard format
     */
    private String getStandardTimeOfDayString() {
        return this.hour % STANDARD_TIME_MODULUS == Calendar.AM ? this.context.getString(R.string.date_am) : this.context.getString(R.string.date_pm);
    }

    /**
     * Gets time in standard format
     *
     * @return Time in standard format
     */
    private String getStandardTimeString() {
        return String.valueOf(this.hour % STANDARD_TIME_MODULUS != NOON_OR_MIDNIGHT_MILITARY
                ? this.hour % STANDARD_TIME_MODULUS : NOON_OR_MIDNIGHT_STANDARD);
    }
}
