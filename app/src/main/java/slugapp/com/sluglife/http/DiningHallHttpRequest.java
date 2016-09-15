package slugapp.com.sluglife.http;

import android.content.Context;

import org.json.JSONException;

import java.util.HashMap;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.objects.DiningHallObject;
import slugapp.com.sluglife.wrappers.DiningHallWrapper;

/**
 * Created by isaiah on 9/1/2015
 * <p/>
 * This file contains an http request that gathers dining hall menu information.
 */
public class DiningHallHttpRequest extends BaseHttpRequest {
    protected Context mContext;
    protected String mName;

    /**
     * Constructor
     *
     * @param context Activity context
     * @param name    Dining hall name
     */
    public DiningHallHttpRequest(Context context, String name) {
        super(BaseHttpRequest.Method.GET);

        this.mContext = context;
        this.mName = name;

        String protocol = context.getString(R.string.api_protocol_http);
        String api = context.getString(R.string.sluglife_api);
        String port = context.getString(R.string.api_port_8080);
        String path = context.getString(R.string.api_dining_menu);

        HashMap<String, String> params = new HashMap<>();
        params.put(context.getString(R.string.bundle_name), name);

        this.createUrl(protocol, api, port, path, params);
    }

    /**
     * Executes http request
     *
     * @param callback Http callback
     */
    public void execute(final HttpCallback<DiningHallObject> callback) {
        this.rawExecute(new HttpCallback<String>() {

            /**
             * On http request success
             *
             * @param value Retrieved value
             */
            @Override
            public void onSuccess(String value) {
                try {
                    callback.onSuccess(new DiningHallWrapper(mContext, value, mName));
                } catch (JSONException je) {
                    je.printStackTrace();
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
