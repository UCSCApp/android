package slugapp.com.ucscstudentapp.http;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isayyuhh_s on 9/1/2015.
 */
public class DiningListHttpRequest extends HttpRequest {
    private static final String url =
            "http://ec2-54-183-90-100.us-west-1.compute.amazonaws.com:8080/dining";
    public DiningListHttpRequest() {
        super(url, Method.GET);
    }

    public void execute(final HttpCallback<List<String>> callback) {
        rawExecute(new HttpCallback<String>() {
            @Override
            public void onSuccess(String val) {
                try {
                    JSONArray arr = new JSONArray(val);
                    List<String> diningHalls = new ArrayList<>(arr.length());
                    for (int i = 0; i < arr.length(); i++) {
                        diningHalls.add(arr.getString(i));
                    }
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
