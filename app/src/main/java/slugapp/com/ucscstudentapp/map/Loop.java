package slugapp.com.ucscstudentapp.map;

/**
 * Created by isayyuhh on 2/19/16.
 */
public class Loop {
    private int id;
    private float lat, lng;
    private LoopType type;

    public Loop (int id, float lat, float lng, String type) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;

        switch (type) {
            case "LOOP":
                this.type = LoopType.LOOP;
                break;
            case "OUT OF SERVICE/SORRY": default:
                this.type = LoopType.OUTOFSERVICE;
                break;
            case "UPPER CAMPUS":
                this.type = LoopType.UPPERCAMPUS;
                break;
        }
    }

    public int getId () {
        return this.id;
    }

    public float getLat () {
        return this.lat;
    }

    public float getLng () {
        return this.lng;
    }

    public LoopType getType () {
        return this.type;
    }
}
