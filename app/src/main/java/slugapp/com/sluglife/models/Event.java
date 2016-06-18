package slugapp.com.sluglife.models;

/**
 * Created by simba on 5/31/15.
 */
/*
 * This file contains the Event Object that holds all the data for each event
 */
public class Event extends BaseObject {
    private String mName;
    private Date mDate;
    private String mDescription;
    private String mUrl;

    /**
     * Constructor
     */
    public Event() {}

    protected void setName(String name) {
        this.mName = name;
    }

    protected void setDate(String date) {
        String[] dateParts = date.split("\\s+");
        if (dateParts.length != 4) this.mDate = new Date(date);
        else this.mDate = new Date(dateParts[0], dateParts[1], dateParts[2], dateParts[3]);
    }

    protected void setDescription(String description) {
        this.mDescription = description;
    }

    protected void setUrl(String url) {
        this.mUrl = url;
    }

    /**
     * Getters
     */
    public String getDescription() {
        return mDescription;
    }

    public String getShortDescription() {
        return this.mDescription.length() > 150 ? this.mDescription.substring(0, 150) +
                "..." : this.mDescription;
    }

    public Date getDate() {
        return mDate;
    }

    public String getName() {
        return mName;
    }

    public String getUrl() {
        return mUrl;
    }
}
