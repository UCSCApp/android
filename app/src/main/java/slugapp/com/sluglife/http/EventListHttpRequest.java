package slugapp.com.sluglife.http;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.Event;
import slugapp.com.sluglife.models.WrapperEventList;

/**
 * Created by simba on 7/31/15
 */
public class EventListHttpRequest extends BaseHttpRequest {
    protected Context mContext;

    public EventListHttpRequest(Context context) {
        super(Method.GET);

        String protocol = context.getString(R.string.http);
        String api = context.getString(R.string.sluglife_api);
        String port = context.getString(R.string.port8080);
        String path = context.getString(R.string.api_event_list);

        this.mContext = context;
        this.createUrl(protocol, api, port, path, null);
    }

    public void execute(final HttpCallback<List<Event>> callback) {
        rawExecute(new HttpCallback<String>() {
            @Override
            public void onSuccess(String val) {
                try {
                    JSONArray arr = new JSONArray(val);
                    callback.onSuccess(new WrapperEventList(mContext, arr));
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
