package slugapp.com.sluglife.wrappers;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.objects.FacilityObject;

/**
 * Created by isaiah on 9/14/16
 * <p/>
 * This file contains a facility object wrapper.
 */
public class FacilityWrapper extends FacilityObject {

    /**
     * Constructor
     *
     * @param context Activity context
     * @param obj     Json object
     * @throws JSONException
     */
    public FacilityWrapper(Context context, JSONObject obj) throws JSONException {
        super(context);
        try {
            this.name = obj.getString(context.getString(R.string.json_map_facility_name));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            this.description = obj.getString(context.getString(R.string.json_map_facility_description));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            FacilityObject.setType(
                    this, obj.getString(context.getString(R.string.json_map_facility_type)));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            FacilityObject.setLatitude(
                    this, obj.getString(context.getString(R.string.json_map_facility_latitude)));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            FacilityObject.setLongitude(
                    this, obj.getString(context.getString(R.string.json_map_facility_longitude)));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            this.id = obj.getInt(context.getString(R.string.json_map_facility_id));
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }
}
