package slugapp.com.ucscstudentapp.event;

/**
 * Created by simba on 5/31/15.
 */
public class Event {
    private String name;
    private String date;
    private String desc;
    private String url;
    public Event(String name, String date, String desc) {
        this.name = name;
        this.date = date;
        this.desc = desc;
    }
    public Event(String name, String date, String desc, String url) {
        this.name = name;
        this.date = date;
        this.desc = desc;
        this.url = url;
    }

    public String desc() { return desc; }

    public String date() {
        return date;
    }

    public String name() { return name; }

    public String url() { return url; }
}
