package slugapp.com.sluglife.objects;

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

    public long id;
    public String name;
    public DateObject date;
    public String summary;
    public String image;

    /**
     * Constructor
     *
     * @param id Event id
     * @param name Event name
     * @param date Event date
     * @param summary Event summary
     * @param image Event image
     */
    public EventObject(long id, String name, long date, String summary, String image) {
        this.id = id;
        this.name = name;
        this.date = new DateObject(date);
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
        int check = DateObject.compareDates(lhs.date, rhs.date);
        if (check == 0) check = lhs.name.compareTo(rhs.name);

        return check;
    }
}
