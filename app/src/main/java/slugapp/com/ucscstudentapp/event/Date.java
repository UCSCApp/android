package slugapp.com.ucscstudentapp.event;

import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by isayyuhh_s on 7/18/2015.
 */
public class Date {
    /** Private Fields */
    private String string;
    private boolean defined = true;
    private String month;
    private int day;
    private int startTime;
    private int endTime;
    private String startTimeTOD;
    private String endTimeTOD;


    /** Public Fields */
    public static final String[] MONTHS = new String[] {"january", "february", "march", "april",
            "may", "june", "july", "august",
            "september", "october", "november",
            "december"};

    /** Getters */
    public String string() { return this.string; }
    public String month() { return this.month; }
    public int day() { return day; }
    public int startTime() { return this.startTime; }
    public int endTime() { return this.endTime; }
    public String startTimeTOD() { return this.startTimeTOD; }
    public String endTimeTOD() { return this.endTimeTOD; }
    public boolean isDefined() { return defined; }

    /** Constructor */
    public Date (String string) {
        this.string = string;
        StringTokenizer stringTokenizer = new StringTokenizer(string);
        String current;

        // Month
        if (stringTokenizer.hasMoreTokens() && defined) {
            current = stringTokenizer.nextToken();
            // If Correct Format
            if (Arrays.asList(MONTHS).contains(current.toLowerCase())) {
                this.month = current;
            } else { defined = false; }
        } else { defined = false; }

        // Day
        if (stringTokenizer.hasMoreTokens() && defined) {
            current = stringTokenizer.nextToken();
            // If Correct Format
            if (isInteger(current.substring(0, current.length()))) {
                this.day = Integer.parseInt(current);
            } else if (isInteger(current.substring(0, current.length() - 2))) {
                this.day = Integer.parseInt(current.substring(0, current.length() - 2));
            } else { defined = false; }
        } else { this.day = 0; }

        // Start Time
        if (stringTokenizer.hasMoreTokens() && defined) {
            current = stringTokenizer.nextToken();
            // If Correct Format
            if (current.length() > 2 && isInteger(current.substring(0, current.length() - 2)) &&
                    (current.toLowerCase().substring(current.length() - 2,
                            current.length()).equals("am") ||
                            current.toLowerCase().substring(current.length() - 2,
                                    current.length()).equals("pm"))){
                this.startTime = Integer.parseInt(current.substring(0, current.length() - 2));
                this.startTimeTOD = current.toLowerCase()
                        .substring(current.length() - 2, current.length());
            } else { defined = false; }
        } else { this.startTime = 0; this.startTimeTOD = ""; }

        // End Time
        if (stringTokenizer.hasMoreTokens() && defined) {
            current = stringTokenizer.nextToken();
            // If Correct Format
            if (current.length() > 2 && isInteger(current.substring(0, current.length() - 2)) &&
                    (current.toLowerCase().substring(current.length() - 2,
                            current.length()).equals("am") ||
                            current.toLowerCase().substring(current.length() - 2,
                                    current.length()).equals("pm"))){
                this.endTime = Integer.parseInt(current.substring(0, current.length() - 2));
                this.endTimeTOD = current.toLowerCase()
                        .substring(current.length() - 2, current.length());
            } else { defined = false; }
        } else { this.endTime = 0; this.endTimeTOD = ""; }

        // Clean String If Valid Date
        if (defined) {
            int digit = 0;
            for (int i = 0; i < day; i++) digit++;
            while (digit > 9) digit %= 10;
            this.string = month + " " + Integer.toString(day) +
                    (digit == 1 ? "st" : digit == 2 ? "nd" : digit == 3 ? "rd" : "th") + " - " +
                    Integer.toString(startTime) + startTimeTOD + " to " + Integer.toString(endTime)
                    + endTimeTOD;
        }
    }

    /** Helper Functions */
    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /** Date Compare Functions */
    public int compareEvents (Event lhs, Event rhs) {
        // Check If Defined
        if (! lhs.date().isDefined()) return 1;
        else if (! rhs.date().isDefined()) return -1;
        return compareDates(lhs.date(), rhs.date());
    }

    public int compareDates (Date lhs, Date rhs) {
        // Compare
        if (compareMonths(lhs.month().toLowerCase(), rhs.month().toLowerCase()) == 0) {
            if (compareInts(lhs.day(), rhs.day()) == 0) {
                if (compareTODs(lhs.startTimeTOD(), rhs.startTimeTOD()) == 0) {
                    if (compareInts(lhs.startTime(), rhs.startTime()) == 0)
                        return compareInts(lhs.endTime(), rhs.endTime());
                    else
                        return compareInts(lhs.startTime(), rhs.startTime());
                } else return compareTODs(lhs.startTimeTOD(), rhs.startTimeTOD());
            } else return compareInts(lhs.day(), rhs.day());
        } else return compareMonths(lhs.month().toLowerCase(), rhs.month().toLowerCase());
    }

    public int compareMonths(String lhs, String rhs) {
        if (Arrays.asList(MONTHS).indexOf(lhs) < Arrays.asList(MONTHS).indexOf(rhs)) {
            return -1;
        } else if (Arrays.asList(MONTHS).indexOf(lhs) > Arrays.asList(MONTHS).indexOf(rhs)) {
            return 1;
        }
        return 0;
    }

    public int compareTODs(String lhs, String rhs) {
        if (lhs == "am" && rhs == "pm") {
            return -1;
        } else if (lhs == "pm" && rhs == "am") {
            return 1;
        }
        return 0;
    }

    public int compareInts(int lhs, int rhs) {
        return lhs - rhs;
    }
}
