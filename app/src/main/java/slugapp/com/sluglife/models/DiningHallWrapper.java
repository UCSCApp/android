package slugapp.com.sluglife.models;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import slugapp.com.sluglife.R;

/**
 * Created by isayyuhh_s on 9/2/2015
 */
public class DiningHallWrapper extends DiningHall {
    public DiningHallWrapper(Context context, String string, String name) throws JSONException {
        super();
        if (string.isEmpty()) return;
        JSONObject obj = new JSONObject(string);
        addCollege(name);
        try {
            addBreakfast(obj.getJSONArray(context.getString(R.string.key_breakfast)));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            addLunch(obj.getJSONArray(context.getString(R.string.key_lunch)));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            addDinner(obj.getJSONArray(context.getString(R.string.key_dinner)));
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }
}
