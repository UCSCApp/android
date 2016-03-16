package slugapp.com.ucscstudentapp.models;

import slugapp.com.ucscstudentapp.enums.LoopTypeEnum;

/**
 * Created by isayyuhh on 2/19/16.
 */
public class Loop {
    private int id;
    private float lat, lng;
    private LoopTypeEnum type;

    public Loop (int id, float lat, float lng, String type) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;

        switch (type) {
            case "LOOP":
                this.type = LoopTypeEnum.LOOP;
                break;
            case "OUT OF SERVICE/SORRY": default:
                this.type = LoopTypeEnum.OUTOFSERVICE;
                break;
            case "UPPER CAMPUS":
                this.type = LoopTypeEnum.UPPERCAMPUS;
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

    public LoopTypeEnum getType () {
        return this.type;
    }
}
