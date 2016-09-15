package slugapp.com.sluglife.objects;

/**
 * Created by isaiah on 2/19/16
 * <p/>
 * This file contains a loop object.
 */
public class LoopObject extends BaseMarkerObject {
    public int id;
    public String type;

    /**
     * Constructor
     *
     * @param id   Loop id
     * @param lat  Loop latitude
     * @param lng  Loop longitude
     * @param type Loop type
     */
    public LoopObject(int id, float lat, float lng, String type) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.type = type;
    }
}
