package slugapp.com.sluglife.models;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import slugapp.com.sluglife.R;

/**
 * Created by simba on 7/31/15
 * Edited by isaiah on 3/16/16
 * <p/>
 * This file contains an event object wrapper.
 */
public class EventWrapper extends EventObject {

    /**
     * Constructor
     *
     * @param context Activity context
     * @param obj     Json object
     * @throws JSONException
     */
    public EventWrapper(Context context, JSONObject obj) throws JSONException {
        super(context);
        try {
            this.name = obj.getString(context.getString(R.string.json_event_name));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            this.setDate(obj.getString(context.getString(R.string.json_event_date)));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            this.summary = obj.getString(context.getString(R.string.json_event_summary));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            this.image = obj.getString(context.getString(R.string.json_event_image));
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }
}
