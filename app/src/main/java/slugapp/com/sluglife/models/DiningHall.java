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
 * Created by isayyuhh_s on 9/2/2015.
 */
public class DiningHall extends Facility {
    public static final int diningImage = R.drawable.dining_hall;

    private String mName;
    private FoodMenu mBreakfast;
    private FoodMenu mLunch;
    private FoodMenu mDinner;
    private LatLng latLng;

    public DiningHall() {
        super(MarkerTypeEnum.DININGHALL);
        this.mBreakfast = new FoodMenu();
        this.mLunch = new FoodMenu();
        this.mDinner = new FoodMenu();
    }

    protected void addName(String name) {
        this.mName = name;
    }

    protected void addCoordinates(String lat, String lng) throws JSONException {
        this.latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
    }

    protected void addBreakfast(JSONArray array) throws JSONException {
        this.mBreakfast = this.addMenu(array);
    }

    protected void addLunch(JSONArray array) throws JSONException {
        this.mLunch = this.addMenu(array);
    }

    protected void addDinner(JSONArray array) throws JSONException {
        this.mDinner = this.addMenu(array);
    }

    private FoodMenu addMenu(JSONArray array) throws JSONException {
        FoodMenu menu = new FoodMenu();
        for (int k = 0; k < array.length(); k++) {
            List<AttributeEnum> attributes = new ArrayList<>();
            JSONObject item = array.getJSONObject(k);
            JSONArray attr = item.getJSONArray("attribs");
            String name = item.getString("name");
            for (int l = 0; l < attr.length(); l++) {
                attributes.add(AttributeEnum.valueOf(attr.getString(l).toUpperCase()));
            }
            menu.add(new Food(name, attributes));
        }
        return menu;
    }

    public String getName() {
        return this.mName;
    }

    public LatLng getLatLng() {
        return this.latLng;
    }

    public FoodMenu getBreakfast() {
        return this.mBreakfast;
    }

    public FoodMenu getLunch() {
        return this.mLunch;
    }

    public FoodMenu getDinner() {
        return this.mDinner;
    }
}
