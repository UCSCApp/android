package slugapp.com.sluglife.wrappers;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.objects.LoopObject;

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
                Double.parseDouble(obj.getString(context.getString(R.string.json_map_lat))),
                Double.parseDouble(obj.getString(context.getString(R.string.json_map_lng))),
                obj.getString(context.getString(R.string.json_map_type)));
    }
}
