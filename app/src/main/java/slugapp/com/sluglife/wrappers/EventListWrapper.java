package slugapp.com.sluglife.wrappers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import slugapp.com.sluglife.objects.EventObject;

/**
 * Created by simba on 8/1/15
 * Edited by isaiah on 9/2/2015
 * <p/>
 * This file contains an event object wrapper.
 */
public class EventListWrapper extends ArrayList<EventObject> {

    /**
     * Constructor
     *
     * @param context Activity context
     * @param array   Json array
     * @throws JSONException
     */
    public EventListWrapper(Context context, JSONArray array) throws JSONException {
        super(array.length());
        for (int i = 0; i < array.length(); ++i) {
            /*
            try {
                this.add(new EventWrapper(context, array.getJSONObject(i)));
            } catch (JSONException je) {
                je.printStackTrace();
            }
            */
        }
    }
}
