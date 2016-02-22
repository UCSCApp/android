package slugapp.com.ucscstudentapp.event;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import slugapp.com.ucscstudentapp.main.ActivityCallback;

/**
 * Created by simba on 8/1/15.
 */
public class EventListWrapper extends ArrayList<Event> {
    public EventListWrapper(JSONArray arr) throws JSONException {
        super(arr.length());
        for(int i = 0; i < arr.length(); ++i) {
            add(new EventWrapper(arr.getJSONObject(i)));
        }
    }
}
