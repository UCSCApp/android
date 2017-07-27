package slugapp.com.sluglife.objects;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.MonthEnum;

/**
 * Created by isaiah on 7/18/2015
 * <p/>
 * This file contains a date object.
 */
public class DateObject extends BaseObject {
    private static final int LESS_THAN = -1;
    private static final int GREATER_THAN = 1;
    private static final int EQUAL = 1;

    private static final String DATE_FORMAT = "MMMM dd, yyyy";
    private static final String HOUR_FORMAT = "haa";

    public long value;

    public String day;
    public String hour;
    public String string;

    public DateObject(long value) {
        Date date = new Date(value);

        DateFormat dateFormat = new SimpleDateFormat(DateObject.DATE_FORMAT, Locale.US);
        DateFormat hourFormat = new SimpleDateFormat(DateObject.HOUR_FORMAT, Locale.US);

        this.value = value;

        this.day = dateFormat.format(date);
        this.hour = hourFormat.format(date);

        this.string = this.day + " at " + this.hour;
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
}
