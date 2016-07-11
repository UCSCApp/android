package slugapp.com.sluglife.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by isayyuhh on 2/19/16.
 */
public class WrapperLoop extends Loop {
    public WrapperLoop(JSONObject obj) throws JSONException {
        super(Integer.parseInt(obj.getString("id")),
                Float.parseFloat(obj.getString("lat")),
                Float.parseFloat(obj.getString("lon")),
                obj.getString("type"));
    }
}
