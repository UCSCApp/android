package slugapp.com.ucscstudentapp.http;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.ucscstudentapp.event.Event;
import slugapp.com.ucscstudentapp.event.EventWrapper;
import slugapp.com.ucscstudentapp.map.Loop;
import slugapp.com.ucscstudentapp.map.LoopWrapper;

/**
 * Created by simba on 7/31/15.
 */
public class LoopHttpRequest extends HttpRequest {
    private static final String url = "http://bts.ucsc.edu:8081/location/get";

    public LoopHttpRequest() {
        super(url, Method.GET);
    }

    public void execute(final Callback<List<Loop>> callback) {
        rawExecute(new Callback<String>() {
            @Override
            public void onSuccess(String val) {
                try {
                    JSONArray arr = new JSONArray(val);
                    List<Loop> loops = new ArrayList<>(arr.length());
                    for (int i = 0; i < arr.length(); ++i) {
                        loops.add(new LoopWrapper(arr.getJSONObject(i)));
                    }
                    callback.onSuccess(loops);
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
