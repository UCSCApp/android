package slugapp.com.sluglife.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.sluglife.enums.AttributeEnum;

/**
 * Created by isayyuhh_s on 9/2/2015.
 */
public class DiningHall {
    private String mCollege;
    private FoodMenu mBreakfast;
    private FoodMenu mLunch;
    private FoodMenu mDinner;

    public DiningHall() {
        this.mBreakfast = new FoodMenu();
        this.mLunch = new FoodMenu();
        this.mDinner = new FoodMenu();
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

    public void addCollege(String college) {
        this.mCollege = college;
    }

    public void addBreakfast(JSONArray array) throws JSONException {
        this.mBreakfast = addMenu(array);
    }

    public void addLunch(JSONArray array) throws JSONException {
        this.mLunch = addMenu(array);
    }

    public void addDinner(JSONArray array) throws JSONException {
        this.mDinner = addMenu(array);
    }

    public String getCollege() {
        return this.mCollege;
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
