package slugapp.com.sluglife.models;

import android.content.Context;

import com.google.android.gms.maps.model.Marker;

import slugapp.com.sluglife.enums.MarkerTypeEnum;

/**
 * Created by isaiah on 6/17/16
 * <p/>
 * This file contains a facility object.
 */
public class FacilityObject extends BaseMarkerObject {
    public MarkerTypeEnum type;
    public Context context;
    public String name;
    public String description;
    public String lat;
    public String lng;
    public int id;

    /**
     * Constructor
     *
     * @param context Activity context
     */
    public FacilityObject(Context context) {
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
     * @param type Facility type
     */
    public void setType(String type) {
    }

    /**
     * Checks if type given
     *
     * @param type Facility type
     * @return Boolean if facility is of facility type
     */
    public boolean isType(MarkerTypeEnum type) {
        return this.type == type;
    }
}
