package slugapp.com.sluglife.http;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.objects.EventObject;
import slugapp.com.sluglife.wrappers.EventListWrapper;

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

        this.mContext = context;

        this.mHeader = context.getString(R.string.api_triton_header);

        String protocol = context.getString(R.string.api_protocol_http);
        String api = context.getString(R.string.api_triton);
        String port = context.getString(R.string.api_port_8081);;
        String path = context.getString(R.string.api_triton_get_events);

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
             * @param value Retrieved value
             */
            @Override
            public void onSuccess(String value) {
                try {
                    JSONArray jsonArray = new JSONArray(value);
                    //callback.onSuccess(new EventListWrapper(mContext, jsonArray));
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
