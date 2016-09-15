package slugapp.com.sluglife.objects;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.Marker;

import slugapp.com.sluglife.enums.MarkerTypeEnum;

/**
 * Created by isaiah on 6/17/16
 * <p/>
 * This file contains a facility object.
 */
public class FacilityObject extends BaseMarkerObject {
    public Context context;
    public String name;
    public MarkerTypeEnum type;
    public String description;
    public double lat;
    public double lng;
    public int id;

    /**
     * Constructor
     *
     * @param context Activity context
     */
    public FacilityObject(Context context) {
        this.name = "";
        this.description = "";
        this.lat = 0.0;
        this.lng = 0.0;
        this.id = -1;
        this.type = MarkerTypeEnum.DEFAULT;
        this.context = context;
    }

    /**
     * Constructor
     *
     * @param type Facility type
     */
    public FacilityObject(MarkerTypeEnum type, Marker marker) {
        this.type = type;
        this.marker = marker;
    }

    /**
     * Sets type
     *
     * @param facility Facility object
     * @param type Facility type
     */
    public static void setType(FacilityObject facility, String type) {
        for (MarkerTypeEnum markerTypeEnum : MarkerTypeEnum.values()) {
            if (type.equals(markerTypeEnum.type)) {
                facility.type = markerTypeEnum;
            }
        }
    }

    /**
     * Sets latitude
     *
     * @param facility Facility object
     * @param lat Latitude
     */
    public static void setLatitude(FacilityObject facility, String lat) {
        if (isDouble(lat)) facility.lat = Double.parseDouble(lat);
    }

    /**
     * Sets longitude
     *
     * @param facility Facility object
     * @param lng Longitude
     */
    public static void setLongitude(FacilityObject facility, String lng) {
        if (isDouble(lng)) facility.lng = Double.parseDouble(lng);
    }
}
