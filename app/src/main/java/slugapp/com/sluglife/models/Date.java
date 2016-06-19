package slugapp.com.sluglife.models;

import slugapp.com.sluglife.enums.MonthEnum;

/**
 * Created by isayyuhh_s on 7/18/2015
 */

// TODO: move date helper functions

public class Date {
    private final static MonthEnum[] sMonths = MonthEnum.values();

    private String mString;
    private boolean mDefined;
    private MonthEnum mMonth;
    private int mDay;
    private int mYear;
    private int mStartTime;
    private int mEndTime;
    private String mStartTOD;
    private String mEndTOD;

    /**
     * Getters
     */
    public String getString() {
        return this.mString;
    }

    public MonthEnum getMonth() {
        return this.mMonth;
    }

    public int getDay() {
        return this.mDay;
    }

    public int getYear() {
        return this.mYear;
    }

    public int getStartTime() {
        return this.mStartTime;
    }

    public int getEndTime() {
        return this.mEndTime;
    }

    public String getStartTOD() {
        return this.mStartTOD;
    }

    public String getEndTOD() {
        return this.mEndTOD;
    }

    public boolean isDefined() {
        return this.mDefined;
    }

    /**
     * Constructor
     */
    public Date(String string) {
        this.mString = string;
        this.mMonth = null;
        this.mDefined = false;
    }

    public Date(String newMonth, String newDay, String newYear, String newStartTime,
                String newEndTime) {
        this.mDefined = false;

        /** Month */
        for (MonthEnum currMonth : sMonths) {
            String month = currMonth.getVal();
            if (month.compareTo(newMonth) == 0) {
                this.mMonth = currMonth;
                break;
            }
        }
        if (this.mMonth == null) return;

        /** Day */
        String day;
        if (newDay.length() > 4) return;
        else if (newDay.length() < 3) day = newDay;
        else day = newDay.substring(0, newDay.length() - 2);
        if (!this.isInteger(day)) return;
        this.mDay = Integer.parseInt(day);

        /** Year */
        if (newYear.length() != 4) return;
        if (!this.isInteger(newYear)) return;
        this.mYear = Integer.parseInt(newYear);

        /** Start Time */
        if (newStartTime.length() < 3 || newStartTime.length() > 4) return;
        String startTime = newStartTime.substring(0, newStartTime.length() - 2);
        String startTOD = newStartTime.substring(newStartTime.length() - 2, newStartTime.length());
        if (!this.isInteger(startTime)) return;
        this.mStartTime = Integer.parseInt(startTime);
        this.mStartTOD = startTOD;

        /** End Time */
        if (newEndTime.length() < 3 || newEndTime.length() > 4) return;
        String endTime = newEndTime.substring(0, newEndTime.length() - 2);
        String endTOD = newEndTime.substring(newEndTime.length() - 2, newEndTime.length());
        if (!this.isInteger(endTime)) return;
        this.mEndTime = Integer.parseInt(endTime);
        this.mEndTOD = endTOD;

        /** Date String */
        this.mString = newMonth + " " + newDay +  " " + newYear + " | " + newStartTime + " - "
                + newEndTime;
        this.mDefined = true;
    }

    /**
     * Helper Functions
     */
    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Date Compare Functions
     */
    public int compareEvents(Event lhs, Event rhs) {
        if (!lhs.getDate().isDefined()) return 1;
        else if (!rhs.getDate().isDefined()) return -1;
        int check = compareDates(lhs.getDate(), rhs.getDate());
        if (check == 0) check = lhs.getName().compareTo(rhs.getName());
        return check;
    }

    private int compareDates(Date lhs, Date rhs) {
        int check;
        if ((check = compInts(lhs.getYear(), rhs.getYear())) != 0) return check;
        if ((check = compMonths(lhs.getMonth(), rhs.getMonth())) != 0) return check;
        if ((check = compInts(lhs.getDay(), rhs.getDay())) != 0) return check;
        if ((check = compTODs(lhs.getStartTOD(), rhs.getStartTOD())) != 0) return check;
        if ((check = compInts(lhs.getStartTime(), rhs.getStartTime())) != 0) return check;
        if ((check = compTODs(lhs.getEndTOD(), rhs.getEndTOD())) != 0) return check;
        return compInts(lhs.getEndTime(), rhs.getEndTime());
    }

    private int compMonths(MonthEnum lhs, MonthEnum rhs) {
        if (lhs.getOrder() < rhs.getOrder()) return -1;
        else if (lhs.getOrder() > rhs.getOrder()) return 1;
        return 0;
    }

    private int compTODs(String lhs, String rhs) {
        if (lhs.equals("am") && rhs.equals("pm")) return -1;
        else if (lhs.equals("pm") && rhs.equals("am")) return 1;
        return 0;
    }

    private int compInts(int lhs, int rhs) {
        return lhs - rhs;
    }
}
