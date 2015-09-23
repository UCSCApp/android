package slugapp.com.ucscstudentapp.dining;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by isayyuhh_s on 9/2/2015.
 */
public class DiningHallWrapper extends DiningHall {
    public DiningHallWrapper(JSONArray arr, String name) throws JSONException {
        super();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            String college = obj.getString("name");
            if (name.contains(college)) {
                addCollege(college);
                addBreakfast(obj.getJSONObject("items").getJSONArray("breakfast"));
                addLunch(obj.getJSONObject("items").getJSONArray("lunch"));
                addDinner(obj.getJSONObject("items").getJSONArray("dinner"));
                return;
            }
        }
    }
}
