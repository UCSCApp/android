package slugapp.com.sluglife.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by isaiah on 2/19/16
 * <p/>
 * This file contains an loop object wrapper.
 */
public class LoopWrapper extends LoopObject {

    // TODO: strings to constants

    /**
     * Constructor
     *
     * @param obj Json object
     * @throws JSONException
     */
    public LoopWrapper(JSONObject obj) throws JSONException {
        super(Integer.parseInt(obj.getString("id")),
                Float.parseFloat(obj.getString("lat")),
                Float.parseFloat(obj.getString("lon")),
                obj.getString("type"));
    }
}
