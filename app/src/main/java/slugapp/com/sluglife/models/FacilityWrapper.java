package slugapp.com.sluglife.models;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import slugapp.com.sluglife.R;

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
            this.setType(obj.getString(context.getString(R.string.json_map_facility_type)));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            this.lat = obj.getString(context.getString(R.string.json_map_facility_latitude));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            this.lng = obj.getString(context.getString(R.string.json_map_facility_longitude));
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
