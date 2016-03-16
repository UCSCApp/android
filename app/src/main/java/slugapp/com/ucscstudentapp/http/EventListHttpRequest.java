package slugapp.com.ucscstudentapp.http;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.ucscstudentapp.interfaces.HttpCallback;
import slugapp.com.ucscstudentapp.models.Event;
import slugapp.com.ucscstudentapp.models.EventWrapper;

/**
 * Created by simba on 7/31/15.
 */
public class EventListHttpRequest extends BaseHttpRequest {
    private static final String url =
            "http://ec2-52-8-25-141.us-west-1.compute.amazonaws.com/events/get/v1";

    public EventListHttpRequest() {
        super(url, Method.GET);
    }

    public void execute(final HttpCallback<List<Event>> callback) {
        rawExecute(new HttpCallback<String>() {
            @Override
            public void onSuccess(String val) {
                try {
                    JSONArray arr = new JSONArray(val);
                    List<Event> events = new ArrayList<>(arr.length());
                    for (int i = 0; i < arr.length(); ++i) {
                        events.add(new EventWrapper(arr.getJSONObject(i)));
                    }
                    callback.onSuccess(events);
                } catch (JSONException je) {
                    callback.onError(je);
                }
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }
}
