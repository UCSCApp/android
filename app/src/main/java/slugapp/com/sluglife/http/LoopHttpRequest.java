package slugapp.com.sluglife.http;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.Loop;
import slugapp.com.sluglife.models.WrapperLoop;

/**
 * Created by simba on 7/31/15.
 */
public class LoopHttpRequest extends BaseHttpRequest {

    public LoopHttpRequest(Context context) {
        super(Method.GET);
        String protocol = context.getString(R.string.http);
        String api = context.getString(R.string.bus_api);
        String port = context.getString(R.string.port8081);
        String path = context.getString(R.string.api_map_loops);
        this.createUrl(protocol, api, port, path, null);
    }

    public void execute(final HttpCallback<List<Loop>> callback) {
        rawExecute(new HttpCallback<String>() {
            @Override
            public void onSuccess(String val) {
                try {
                    JSONArray arr = new JSONArray(val);
                    List<Loop> loopList = new ArrayList<>(arr.length());
                    for (int i = 0; i < arr.length(); ++i) {
                        loopList.add(new WrapperLoop(arr.getJSONObject(i)));
                    }
                    callback.onSuccess(loopList);
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
