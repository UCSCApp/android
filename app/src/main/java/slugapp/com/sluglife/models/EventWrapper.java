package slugapp.com.sluglife.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by simba on 7/31/15.
 */
public class EventWrapper extends Event {
    public EventWrapper(JSONObject obj) throws JSONException {
        super();
        try {
            this.setName(obj.getString("name"));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            this.setDate(obj.getString("date"));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            this.setDescription(obj.getString("description"));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            this.setUrl(obj.getString("url"));
        } catch (JSONException je) {
            je.printStackTrace();
        }

        this.checkDefined();
    }
}
