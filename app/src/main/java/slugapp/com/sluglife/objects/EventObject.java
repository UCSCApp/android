package slugapp.com.sluglife.objects;

import android.content.Context;

/**
 * Created by simba on 5/31/15
 * Edited by isaiah on 3/16/16
 * <p/>
 * This file contains an event object.
 */
public class EventObject extends BaseObject {
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String SUMMARY = "summary";
    public static final String IMAGE = "image";

    public String name;
    public DateObject date;
    public String summary;
    public String image;

    private Context context;

    /**
     * Constructor
     *
     * @param context Activity context
     */
    public EventObject(Context context) {
        this.context = context;
        this.name = "";
        this.date = new DateObject("");
        this.summary = "";
        this.image = "";
    }

    /**
     * Constructor
     *
     * @param context Activity context
     * @param name    String name of event
     * @param date    String date of event
     * @param summary String summary of event
     * @param image   String image of event
     */
    public EventObject(Context context, String name, String date, String summary, String image) {
        this.context = context;
        this.name = name;
        this.setDate(date);
        this.summary = summary;
        this.image = image;
    }

    /**
     * Gets short version of event name
     *
     * @return Short version of event name
     */
    public String getShortName() {
        return this.name.length() > 100 ? this.name.substring(0, 100) + "..." : this.name;
    }

    /**
     * Gets toolbar title version of event name
     *
     * @return Toolbar title version of event name
     */
    public String getTitleName() {
        return this.name.length() > 25 ? this.name.substring(0, 25) + "..." : this.name;
    }

    /**
     * Gets full date string
     *
     * @return Full date string
     */
    public String getFullDate() {
        return this.date.getFullString();
    }

    /**
     * Sets date from string
     *
     * @param date Date string
     */
    public void setDate(String date) {
        String[] dateParts = date.split("\\s+");
        if (dateParts.length != 4 && dateParts.length != 5) this.date = new DateObject(date);
        else this.date = new DateObject(this.context, dateParts[0], dateParts[1], dateParts[2],
                dateParts[3]);
    }

    /**
     * Gets short version of event summary
     *
     * @return Short version of event summary
     */
    public String getShortSummary() {
        return this.summary.length() > 150 ? this.summary.substring(0, 150) + "..." : this.summary;
    }

    /**
     * Compares events by date
     *
     * @param lhs Left operand
     * @param rhs Right operand
     * @return Integer showing which order the operands are in
     */
    public static int compareEvents(EventObject lhs, EventObject rhs) {
        if (!lhs.date.defined) return 1;
        if (!rhs.date.defined) return -1;

        int check = DateObject.compareDates(lhs.date, rhs.date);
        if (check == 0) check = lhs.name.compareTo(rhs.name);

        return check;
    }
}
