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

    public String name;
    public FoodMenu breakfast;
    public FoodMenu lunch;
    public FoodMenu dinner;
    public LatLng latLng;

    public DiningHall() {
        super(MarkerTypeEnum.DININGHALL);
        this.breakfast = new FoodMenu();
        this.lunch = new FoodMenu();
        this.dinner = new FoodMenu();
    }

    protected void addName(String name) {
        this.name = name;
    }

    protected void addCoordinates(String lat, String lng) throws JSONException {
        this.latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
    }

    protected void addBreakfast(JSONArray array) throws JSONException {
        this.breakfast = this.addMenu(array);
    }

    protected void addLunch(JSONArray array) throws JSONException {
        this.lunch = this.addMenu(array);
    }

    protected void addDinner(JSONArray array) throws JSONException {
        this.dinner = this.addMenu(array);
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
}
