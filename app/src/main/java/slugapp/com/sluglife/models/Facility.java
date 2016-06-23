package slugapp.com.sluglife.models;

import slugapp.com.sluglife.enums.MarkerTypeEnum;

/**
 * Created by isayyuhh on 6/17/16.
 */
public class Facility extends BaseObject {
    private MarkerTypeEnum mType;

    public Facility(MarkerTypeEnum type) {
        this.mType = type;
    }

    public boolean isType(MarkerTypeEnum type) {
        return this.mType == type;
    }
}
