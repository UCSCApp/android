package slugapp.com.ucscstudentapp.models;

import org.json.JSONException;
import org.json.JSONObject;

import slugapp.com.ucscstudentapp.models.DiningHall;

/**
 * Created by isayyuhh_s on 9/2/2015.
 */
public class DiningHallWrapper extends DiningHall {
    public DiningHallWrapper(String string, String name) throws JSONException {
        super();
        JSONObject obj = new JSONObject(string);
        addCollege(name);
        addBreakfast(obj.getJSONArray("breakfast"));
        addLunch(obj.getJSONArray("lunch"));
        addDinner(obj.getJSONArray("dinner"));
        return;
    }
}
