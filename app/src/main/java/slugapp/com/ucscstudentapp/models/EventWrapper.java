package slugapp.com.ucscstudentapp.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by simba on 7/31/15.
 */
public class EventWrapper extends Event {
    public EventWrapper(JSONObject obj) throws JSONException {
        super(
                obj.getString("name"),
                obj.getString("date"),
                obj.getString("description"),
                obj.getString("url"));
    }
}
