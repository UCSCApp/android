package slugapp.com.ucscstudentapp.http;

import android.util.Log;

import org.json.JSONException;

import java.util.HashMap;

import slugapp.com.ucscstudentapp.interfaces.HttpCallback;
import slugapp.com.ucscstudentapp.models.DiningHall;
import slugapp.com.ucscstudentapp.models.DiningHallWrapper;

/**
 * Created by isayyuhh_s on 9/1/2015.
 */
public class DiningHallHttpRequest extends BaseHttpRequest {
    private static final String url =
            "http://ec2-54-183-90-100.us-west-1.compute.amazonaws.com:8080/dining/menu";
    private String name;

    public DiningHallHttpRequest() {
        super(url, BaseHttpRequest.Method.POST);
    }

    public DiningHallHttpRequest(String name) {
        super(url, BaseHttpRequest.Method.POST);
        this.name = name;
    }

    public void execute(final HttpCallback<DiningHall> callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        rawExecute(params, new HttpCallback<String>() {
            @Override
            public void onSuccess(String val) {
                try {
                    callback.onSuccess(new DiningHallWrapper(val, name));
                } catch (JSONException je) {
                    Log.e("ERROR", "json error");
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
