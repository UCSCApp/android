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
    private boolean mDefined;

    /**
     * Constructor
     */
    public Event() {
        this.mName = "";
        this.mDate = new Date("");
        this.mDescription = "";
        this.mUrl = "";
    }

    protected void setName(String name) {
        this.mName = name;
    }

    protected void setDate(String date) {
        String[] dateParts = date.split("\\s+");
        if (dateParts.length != 5) this.mDate = new Date(date);
        else this.mDate = new Date(dateParts[0], dateParts[1], dateParts[2], dateParts[3],
                dateParts[4]);
    }

    protected void setDescription(String description) {
        this.mDescription = description;
    }

    protected void setUrl(String url) {
        this.mUrl = url;
    }

    protected void checkDefined() {
        this.mDefined = !(mName.equals("") || mDescription.equals(""));
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
