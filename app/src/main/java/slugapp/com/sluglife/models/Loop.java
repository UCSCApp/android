package slugapp.com.sluglife.models;

/**
 * Created by isayyuhh on 2/19/16.
 */
public class Loop {
    private int id;
    private float lat, lng;
    private String type;

    public Loop(int id, float lat, float lng, String type) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public float getLat() {
        return this.lat;
    }

    public float getLng() {
        return this.lng;
    }

    public String getType() { return this.type; }
}
