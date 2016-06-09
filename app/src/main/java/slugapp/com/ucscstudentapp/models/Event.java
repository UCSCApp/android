package slugapp.com.ucscstudentapp.models;

/**
 * Created by simba on 5/31/15.
 */
/*
 * This file contains the Event Object that holds all the data for each event
 */
public class Event extends BaseListItem {
    /**
     * Private Fields
     */
    private String name;
    private Date date;
    private String desc;
    private String url;

    /**
     * Getters
     */
    public String getDesc() {
        return desc;
    }

    public String shortDesc() {
        return this.desc.length() > 150 ? this.desc.substring(0, 150) + "..." : this.desc;
    }

    public Date date() {
        return date;
    }

    public String name() {
        return name;
    }

    public String url() {
        return url;
    }

    /**
     * Constructor
     */
    public Event(String name, String date, String desc, String url) {
        String[] dateParts = date.split("\\s+");

        this.name = name;
        if (dateParts.length != 4) this.date = new Date(date);
        else this.date = new Date(dateParts[0], dateParts[1], dateParts[2], dateParts[3]);
        this.desc = desc;
        this.url = url;
    }
}