package slugapp.com.sluglife.models;

/**
 * Created by isayyuhh on 2/19/16.
 */
public class Loop {
    private int mId;
    private float mLatitude, mLongitude;
    private String mType;

    public Loop(int id, float lat, float lng, String type) {
        this.mId = id;
        this.mLatitude = lat;
        this.mLongitude = lng;
        this.mType = type;
    }

    public int getId() {
        return this.mId;
    }

    public float getLat() {
        return this.mLatitude;
    }

    public float getLng() {
        return this.mLongitude;
    }

    public String getType() { return this.mType; }
}
