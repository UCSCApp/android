package slugapp.com.sluglife.models;

/**
 * Created by simba on 5/31/15.
 */
/*
 * This file contains the Event Object that holds all the data for each event
 */
public class Event extends BaseObject {
    public String name;
    public Date date;
    public String summary;
    public String image;
    public boolean defined;

    /**
     * Constructor
     */
    public Event() {
        this.name = "";
        this.date = new Date("");
        this.summary = "";
        this.image = "";
    }

    protected void setDate(String date) {
        String[] dateParts = date.split("\\s+");
        if (dateParts.length != 5) this.date = new Date(date);
        else this.date = new Date(dateParts[0], dateParts[1], dateParts[2], dateParts[3],
                dateParts[4]);
    }

    protected void checkDefined() {
        this.defined = !(this.name.equals("") || this.summary.equals(""));
    }

    public String getShortSummary() {
        return this.summary.length() > 150 ? this.summary.substring(0, 150) +
                "..." : this.summary;
    }
}
