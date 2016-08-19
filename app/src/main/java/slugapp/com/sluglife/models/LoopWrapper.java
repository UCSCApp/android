package slugapp.com.sluglife.models;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import slugapp.com.sluglife.R;

/**
 * Created by isaiah on 2/19/16
 * <p/>
 * This file contains an loop object wrapper.
 */
public class LoopWrapper extends LoopObject {

    /**
     * Constructor
     *
     * @param context Activity context
     * @param obj Json object
     * @throws JSONException
     */
    public LoopWrapper(Context context, JSONObject obj) throws JSONException {
        super(Integer.parseInt(obj.getString(context.getString(R.string.json_map_id))),
                Float.parseFloat(obj.getString(context.getString(R.string.json_map_lat))),
                Float.parseFloat(obj.getString(context.getString(R.string.json_map_lng))),
                obj.getString(context.getString(R.string.json_map_type)));
    }
}
