package slugapp.com.sluglife.wrappers;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.objects.DiningHallObject;

/**
 * Created by isaiah on 9/2/2015
 * <p/>
 * This file contains a dining hall object wrapper.
 */
public class DiningHallWrapper extends DiningHallObject {

    /**
     * Constructor
     *
     * @param context Activity context
     * @param array   String of dining menu array
     * @param name    Name of dining hall
     * @throws JSONException
     */
    public DiningHallWrapper(Context context, String array, String name) throws JSONException {
        super();
        if (array.isEmpty()) return;
        JSONObject obj = new JSONObject(array);
        this.name = name;
        try {
            this.addBreakfast(context, obj.getJSONArray(context.getString(R.string.json_dining_breakfast)));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            this.addLunch(context, obj.getJSONArray(context.getString(R.string.json_dining_lunch)));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            this.addDinner(context, obj.getJSONArray(context.getString(R.string.json_dining_dinner)));
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }
}
