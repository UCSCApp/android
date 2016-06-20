package slugapp.com.sluglife.models;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by simba on 8/1/15.
 */
public class EventListWrapper extends ArrayList<Event> {
    public EventListWrapper(Context context, JSONArray arr) throws JSONException {
        super(arr.length());
        for (int i = 0; i < arr.length(); ++i) {
            try {
                this.add(new EventWrapper(context, arr.getJSONObject(i)));
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }
    }
}
