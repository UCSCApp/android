package slugapp.com.sluglife.http;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.EventObject;
import slugapp.com.sluglife.models.EventListWrapper;

/**
 * Created by simba on 7/31/15
 * Edited by isaiah on 9/1/2015
 * <p/>
 * This file contains an http request that gathers event information.
 */
public class EventListHttpRequest extends BaseHttpRequest {
    protected Context mContext;

    /**
     * Constructor
     *
     * @param context Activity context
     */
    public EventListHttpRequest(Context context) {
        super(Method.GET);

        String protocol = context.getString(R.string.http);
        String api = context.getString(R.string.sluglife_api);
        String port = context.getString(R.string.port8080);
        String path = context.getString(R.string.api_event_list);

        this.mContext = context;
        this.createUrl(protocol, api, port, path, null);
    }

    /**
     * Executes http request
     *
     * @param callback Http callback
     */
    public void execute(final HttpCallback<List<EventObject>> callback) {
        rawExecute(new HttpCallback<String>() {

            /**
             * On http request success
             *
             * @param val Retrieved value
             */
            @Override
            public void onSuccess(String val) {
                try {
                    JSONArray arr = new JSONArray(val);
                    callback.onSuccess(new EventListWrapper(mContext, arr));
                } catch (JSONException je) {
                    callback.onError(je);
                }
            }

            /**
             * On http request error
             *
             * @param e Exception
             */
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }
}
