package slugapp.com.ucscstudentapp.models;

import slugapp.com.ucscstudentapp.enums.MonthEnum;

/**
 * Created by isayyuhh_s on 7/18/2015
 */
public class Date {
    private final static MonthEnum[] months = MonthEnum.values();

    private String string;
    private boolean defined;
    private MonthEnum month;
    private int day;
    private int startTime;
    private int endTime;
    private String startTOD;
    private String endTOD;

    /**
     * Getters
     */
    public String getString() {
        return this.string;
    }

    public MonthEnum getMonth() {
        return this.month;
    }

    public int getDay() {
        return day;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public String getStartTOD() {
        return this.startTOD;
    }

    public String getEndTOD() {
        return this.endTOD;
    }

    public boolean isDefined() {
        return defined;
    }

    /**
     * Constructor
     */
    public Date(String string) {
        this.string = string;
        this.month = null;
        this.defined = false;
    }

    public Date(String newMonth, String newDay, String newStartTime, String newEndTime) {
        this.defined = false;

        /** Month */
        for (MonthEnum currMonth : months) {
            String month = currMonth.getVal();
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

        /** Date String */
        this.string = newMonth + " " + newDay + " | " + newStartTime + " - " + newEndTime;
        this.defined = true;
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
        if (!lhs.date().isDefined()) return 1;
        else if (!rhs.date().isDefined()) return -1;
        int check = compareDates(lhs.date(), rhs.date());
        if (check == 0) check = lhs.name().compareTo(rhs.name());
        return check;
    }

    private int compareDates(Date lhs, Date rhs) {
        int check;
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
