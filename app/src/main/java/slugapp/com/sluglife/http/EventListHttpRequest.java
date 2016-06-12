package slugapp.com.sluglife.http;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.Event;
import slugapp.com.sluglife.models.EventWrapper;

/**
 * Created by simba on 7/31/15.
 */
public class EventListHttpRequest extends BaseHttpRequest {

    public EventListHttpRequest(Context context) {
        super(Method.GET);

        String protocol = context.getString(R.string.http);
        String api = context.getString(R.string.slugapp_api);
        String port = context.getString(R.string.port8080);
        String path = context.getString(R.string.api_event_list);

        this.createUrl(protocol, api, port, path, null);
    }

    public void execute(final HttpCallback<List<Event>> callback) {
        rawExecute(new HttpCallback<String>() {
            @Override
            public void onSuccess(String val) {
                try {
                    JSONArray arr = new JSONArray(val);
                    List<Event> eventList = new ArrayList<>(arr.length());
                    for (int i = 0; i < arr.length(); ++i) {
                        eventList.add(new EventWrapper(arr.getJSONObject(i)));
                    }
                    callback.onSuccess(eventList);
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
