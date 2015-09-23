package slugapp.com.ucscstudentapp.event;

/**
 * Created by simba on 5/31/15.
 */
/*
 * This file contains the Event Object that holds all the data for each event
 */
public class Event {
    /** Private Fields */
    private String name;
    private Date date;
    private String desc;
    private String url;

    /** Getters */
    public String desc() { return desc; }
    public Date date() { return date; }
    public String name() { return name; }
    public String url() { return url; }

    /** Constructor */
    public Event(String name, String date, String desc, String url) {
        this.name = name;
        this.date = new Date(date);
        this.desc = desc;
        this.url = url;
    }
}
