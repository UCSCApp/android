package slugapp.com.sluglife.models;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import slugapp.com.sluglife.R;

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

        // TODO: implement coordinates
        // TODO: strings to constants

        /*
        try {
            this.addCoordinates(obj.getString("lat"), obj.getString("lng"));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        */
        switch (name) {
            case "Cowell & Stevenson":
                this.addCoordinates("36.996808", "-122.053036");
                break;
            case "Crown & Merrill":
                this.addCoordinates("37.000112", "-122.054417");
                break;
            case "Porter & Kresge":
                this.addCoordinates("36.994269", "-122.065944");
                break;
            default:
            case "College Eight & Oakes":
                this.addCoordinates("36.991694", "-122.065386");
                break;
            case "College Nine & College Ten":
                this.addCoordinates("37.000729", "-122.057771");
                break;
        }
        this.addName(name);
        try {
            this.addBreakfast(obj.getJSONArray(context.getString(R.string.json_dining_breakfast)));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            this.addLunch(obj.getJSONArray(context.getString(R.string.json_dining_lunch)));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        try {
            this.addDinner(obj.getJSONArray(context.getString(R.string.json_dining_dinner)));
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }
}
