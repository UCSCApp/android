package slugapp.com.sluglife.models;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import slugapp.com.sluglife.R;

/**
 * Created by simba on 7/31/15.
 */
public class EventWrapper extends Event {
    public EventWrapper(Context context, JSONObject obj) throws JSONException {
        super();
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

        this.checkDefined();
    }
}
