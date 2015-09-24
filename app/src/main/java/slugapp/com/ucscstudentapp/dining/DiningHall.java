package slugapp.com.ucscstudentapp.dining;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.ucscstudentapp.R;

/**
 * Created by isayyuhh_s on 9/2/2015.
 */
public class DiningHall {
    private String college;
    private FoodMenu breakfast;
    private FoodMenu lunch;
    private FoodMenu dinner;

    public DiningHall () {}

    private FoodMenu addMenu (JSONArray array) throws JSONException {
        FoodMenu menu = new FoodMenu();
        for (int k = 0; k < array.length(); k++) {
            List<AttributeEnum> attributes = new ArrayList<>();
            JSONObject item = array.getJSONObject(k);
            JSONArray attr = item.getJSONArray("attribs");
            String name = item.getString("name");
            for (int l = 0; l < attr.length(); l++) {
                attributes.add(AttributeEnum.valueOf(attr.getString(l).toUpperCase()));
            }
            menu.add(new FoodItem(name, attributes));
        }
        return menu;
    }
    public void addCollege (String college) {
        this.college = college;
    }
    public void addBreakfast (JSONArray array) throws JSONException {
        this.breakfast = addMenu(array);
    }
    public void addLunch (JSONArray array) throws JSONException {
        this.lunch = addMenu(array);
    }
    public void addDinner (JSONArray array) throws JSONException {
        this.dinner = addMenu(array);
    }

    FoodMenu getBreakfast() {
        return this.breakfast;
    }
    FoodMenu getLunch() {
        return this.lunch;
    }
    FoodMenu getDinner() {
        return this.dinner;
    }

}
