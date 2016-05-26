package slugapp.com.ucscstudentapp.http;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.interfaces.HttpCallback;
import slugapp.com.ucscstudentapp.models.Event;
import slugapp.com.ucscstudentapp.models.EventWrapper;

/**
 * Created by simba on 7/31/15.
 */
public class EventListHttpRequest extends BaseHttpRequest {

    public EventListHttpRequest(Context context) {
        super(Method.GET);
        String api = context.getString(R.string.slugapp_api);
        String localhost = context.getString(R.string.localhost);
        String events = context.getString(R.string.api_event_list);
        String url = api + localhost + events;
        this.url = url.replace(" ", "%20").replace("&", "%26");
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
