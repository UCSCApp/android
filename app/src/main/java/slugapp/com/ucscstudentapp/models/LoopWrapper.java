package slugapp.com.ucscstudentapp.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by isayyuhh on 2/19/16.
 */
public class LoopWrapper extends Loop {
    public LoopWrapper(JSONObject obj) throws JSONException {
        super(Integer.parseInt(obj.getString("id")),
                Float.parseFloat(obj.getString("lat")),
                Float.parseFloat(obj.getString("lon")),
                obj.getString("type"));
    }
}
