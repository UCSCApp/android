package slugapp.com.sluglife.http;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.objects.LoopObject;
import slugapp.com.sluglife.wrappers.LoopWrapper;

/**
 * Created by isaiah on 10/3/16
 * <p/>
 * This file contains an http request that gathers loop bus time information.
 */
public class LoopTimeHttpRequest extends BaseHttpRequest {
    private Context mContext;

    /**
     * Constructor
     *
     * @param context Activity context
     */
    public LoopTimeHttpRequest(Context context, double startLat, double startLng, double endLat,
                               double endLng) {
        super(Method.GET);

        this.mContext = context;

        String protocol = context.getString(R.string.api_protocol_https);
        String api = context.getString(R.string.map_maps_api);
        String port = EMPTY_STRING;
        String path = context.getString(R.string.api_map_distance_matrix);

        // TODO: move strings to strings.xml

        path += "?units=imperial&origins=" + String.valueOf(startLat) + "," +
                String.valueOf(startLng) + "&destinations=" + String.valueOf(endLat) + "," +
                String.valueOf(endLng) + "&key=AIzaSyA8SvvXTE8kj2BFNAMiEhVKcgb4az-CvZM";

        /*
        HashMap<String, String> params = new HashMap<>();
        params.put("units", "imperial");
        params.put("origins", String.valueOf(startLat) + "," + String.valueOf(startLng));
        params.put("destinations", String.valueOf(endLat) + "," + String.valueOf(endLng));
        params.put("key", "AIzaSyA8SvvXTE8kj2BFNAMiEhVKcgb4az-CvZM");
        */

        this.createUrl(protocol, api, port, path, null);
        Log.e("URL", this.mUrl);
    }

    /**
     * Executes http request
     *
     * @param callback Http callback
     */
    public void execute(final HttpCallback<JSONObject> callback) {
        rawExecute(new HttpCallback<String>() {

            /**
             * On http request success
             *
             * @param value Retrieved value
             */
            @Override
            public void onSuccess(String value) {
                try {
                    callback.onSuccess(new JSONObject(value));
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
