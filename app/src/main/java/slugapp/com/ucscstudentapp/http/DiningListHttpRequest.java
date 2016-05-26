package slugapp.com.ucscstudentapp.http;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.interfaces.HttpCallback;

/**
 * Created by isayyuhh_s on 9/1/2015.
 */
public class DiningListHttpRequest extends BaseHttpRequest {

    public DiningListHttpRequest(Context context) {
        super(Method.GET);
        String api = context.getString(R.string.slugapp_api);
        String localhost = context.getString(R.string.localhost);
        String dining = context.getString(R.string.api_dining_list);
        String url = api + localhost + dining;
        this.url = url.replace(" ", "%20").replace("&", "%26");
    }

    public void execute(final HttpCallback<List<String>> callback) {
        rawExecute(new HttpCallback<String>() {
            @Override
            public void onSuccess(String val) {
                try {
                    JSONArray arr = new JSONArray(val);
                    List<String> diningHalls = new ArrayList<>(arr.length());
                    for (int i = 0; i < arr.length(); i++) diningHalls.add(arr.getString(i));
                    callback.onSuccess(diningHalls);
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
