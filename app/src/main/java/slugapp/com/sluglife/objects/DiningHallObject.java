package slugapp.com.sluglife.objects;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.AttributeEnum;

/**
 * Created by isaiah on 9/2/2015
 * <p/>
 * This file contains a dining hall object.
 */
public class DiningHallObject extends BaseMarkerObject {
    public static final int diningImage = R.drawable.dining_hall;

    public String name;
    public FoodMenuObject breakfast;
    public FoodMenuObject lunch;
    public FoodMenuObject dinner;
    public LatLng latLng;

    /**
     * Constructor
     */
    public DiningHallObject() {
        //super(MarkerTypeEnum.DINING_HALL);
        this.breakfast = new FoodMenuObject();
        this.lunch = new FoodMenuObject();
        this.dinner = new FoodMenuObject();
    }

    /**
     * Adds name to dining hall
     *
     * @param name Name of dining hall
     */
    protected void addName(String name) {
        this.name = name;
    }

    /**
     * Adds dining hall breakfast menu
     *
     * @param context Activity context
     * @param array Json array of menu
     * @throws JSONException
     */
    protected void addBreakfast(Context context, JSONArray array) throws JSONException {
        this.breakfast = this.addMenu(context, array);
    }

    /**
     * Adds dining hall lunch menu
     *
     * @param context Activity context
     * @param array Json array of menu
     * @throws JSONException
     */
    protected void addLunch(Context context, JSONArray array) throws JSONException {
        this.lunch = this.addMenu(context, array);
    }

    /**
     * Adds dining hall dinner menu
     *
     * @param context Activity context
     * @param array Json array of menu
     * @throws JSONException
     */
    protected void addDinner(Context context, JSONArray array) throws JSONException {
        this.dinner = this.addMenu(context, array);
    }

    /**
     * Adds dining hall coordinates
     *
     * @param lat Dining hall latitude
     * @param lng Dining hall longitude
     * @throws JSONException
     */
    protected void addCoordinates(String lat, String lng) throws JSONException {
        this.latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
    }

    /**
     * Parses Json array
     *
     * @param context Activity context
     * @param array Json array
     * @return Parsed food menu
     * @throws JSONException
     */
    private FoodMenuObject addMenu(Context context, JSONArray array) throws JSONException {
        FoodMenuObject menu = new FoodMenuObject();
        for (int k = 0; k < array.length(); k++) {
            List<AttributeEnum> attributes = new ArrayList<>();
            JSONObject item = array.getJSONObject(k);
            String name = item.getString(context.getString(R.string.json_dining_food_name));
            JSONArray attr = item.getJSONArray(context.getString(R.string.json_dining_food_attributes));
            for (int l = 0; l < attr.length(); l++) {
                attributes.add(AttributeEnum.valueOf(attr.getString(l).toUpperCase()));
            }
            menu.add(new FoodObject(name, attributes));
        }
        return menu;
    }
}
