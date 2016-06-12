package slugapp.com.sluglife.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by isayyuhh_s on 9/2/2015
 */
public class DiningHallWrapper extends DiningHall {
    public DiningHallWrapper(String string, String name) throws JSONException {
        super();
        if (string.isEmpty()) return;
        JSONObject obj = new JSONObject(string);
        addCollege(name);
        try {
            addBreakfast(obj.getJSONArray("breakfast"));
        } catch (JSONException je) {
            Log.e("JSON ERROR", "no breakfast");
        }
        try {
            addLunch(obj.getJSONArray("lunch"));
        } catch (JSONException je) {
            Log.e("JSON ERROR", "no lunch");
        }
        try {
            addDinner(obj.getJSONArray("dinner"));
        } catch (JSONException je) {
            Log.e("JSON ERROR", "no dinner");
        }
    }
}
