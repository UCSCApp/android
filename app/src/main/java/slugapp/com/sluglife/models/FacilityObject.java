package slugapp.com.sluglife.models;

import com.google.android.gms.maps.model.Marker;

import slugapp.com.sluglife.enums.MarkerTypeEnum;

//TODO: create abstract map object

/**
 * Created by isaiah on 6/17/16
 * <p/>
 * This file contains a facility object.
 */
public class FacilityObject extends BaseObject {
    public MarkerTypeEnum type;
    public Marker marker;

    /**
     * Constructor
     *
     * @param type Facility type
     */
    public FacilityObject(MarkerTypeEnum type) {
        this.type = type;
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
     * Checks if type given
     *
     * @param type Facility type
     * @return Boolean if facility is of facility type
     */
    public boolean isType(MarkerTypeEnum type) {
        return this.type == type;
    }
}
