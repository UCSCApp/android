package slugapp.com.sluglife.models;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.AttributeEnum;
import slugapp.com.sluglife.enums.MarkerTypeEnum;

/**
 * Created by isaiah on 9/2/2015
 * <p/>
 * This file contains a dining hall object.
 */
public class DiningHallObject extends FacilityObject {
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
        super(MarkerTypeEnum.DININGHALL);
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
     * @param array Json array of menu
     * @throws JSONException
     */
    protected void addBreakfast(JSONArray array) throws JSONException {
        this.breakfast = this.addMenu(array);
    }

    /**
     * Adds dining hall lunch menu
     *
     * @param array Json array of menu
     * @throws JSONException
     */
    protected void addLunch(JSONArray array) throws JSONException {
        this.lunch = this.addMenu(array);
    }

    /**
     * Adds dining hall dinner menu
     *
     * @param array Json array of menu
     * @throws JSONException
     */
    protected void addDinner(JSONArray array) throws JSONException {
        this.dinner = this.addMenu(array);
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

    // TODO: strings to constants

    /**
     * Parses Json array
     *
     * @param array Json array
     * @return Parsed food menu
     * @throws JSONException
     */
    private FoodMenuObject addMenu(JSONArray array) throws JSONException {
        FoodMenuObject menu = new FoodMenuObject();
        for (int k = 0; k < array.length(); k++) {
            List<AttributeEnum> attributes = new ArrayList<>();
            JSONObject item = array.getJSONObject(k);
            JSONArray attr = item.getJSONArray("attribs");
            String name = item.getString("name");
            for (int l = 0; l < attr.length(); l++) {
                attributes.add(AttributeEnum.valueOf(attr.getString(l).toUpperCase()));
            }
            menu.add(new FoodObject(name, attributes));
        }
        return menu;
    }
}