package slugapp.com.sluglife.http;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.HttpCallback;

/**
 * Created by isaiah on 9/1/2015
 * <p/>
 * This file contains an http request that gathers dining hall menu information.
 */
public class DiningListHttpRequest extends BaseHttpRequest {

    /**
     * Constructor
     *
     * @param context Activity context
     */
    public DiningListHttpRequest(Context context) {
        super(Method.GET);

        String protocol = context.getString(R.string.http);
        String api = context.getString(R.string.sluglife_api);
        String port = context.getString(R.string.port8080);
        String path = context.getString(R.string.api_dining_list);

        this.createUrl(protocol, api, port, path, null);
    }

    /**
     * Executes http request
     *
     * @param callback Http callback
     */
    public void execute(final HttpCallback<List<String>> callback) {
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
                    List<String> diningList = new ArrayList<>(arr.length());
                    for (int i = 0; i < arr.length(); i++) {
                        diningList.add(arr.getString(i));
                    }
                    callback.onSuccess(diningList);
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
