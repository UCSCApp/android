package slugapp.com.sluglife.models;

/**
 * Created by isayyuhh on 2/19/16.
 */
public class Loop {
    public int id;
    public float lat, lng;
    public String type;

    public Loop(int id, float lat, float lng, String type) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.type = type;
    }
}
