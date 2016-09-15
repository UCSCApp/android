package slugapp.com.sluglife.http;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.objects.LoopObject;
import slugapp.com.sluglife.wrappers.LoopWrapper;

/**
 * Created by simba on 7/31/15
 * Edited by isaiah on 9/1/2015
 * <p/>
 * This file contains an http request that gathers loop bus information.
 */
public class LoopHttpRequest extends BaseHttpRequest {
    private Context mContext;

    /**
     * Constructor
     *
     * @param context Activity context
     */
    public LoopHttpRequest(Context context) {
        super(Method.GET);

        this.mContext = context;

        String protocol = context.getString(R.string.api_protocol_http);
        String api = context.getString(R.string.api_loops);
        String port = context.getString(R.string.api_port_8081);
        String path = context.getString(R.string.api_loops_location);

        this.createUrl(protocol, api, port, path, null);
    }

    /**
     * Executes http request
     *
     * @param callback Http callback
     */
    public void execute(final HttpCallback<List<LoopObject>> callback) {
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
                    List<LoopObject> loopList = new ArrayList<>(jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        loopList.add(new LoopWrapper(mContext, jsonArray.getJSONObject(i)));
                    }
                    callback.onSuccess(loopList);
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
