package slugapp.com.ucscstudentapp.http;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.ucscstudentapp.interfaces.HttpCallback;
import slugapp.com.ucscstudentapp.models.Loop;
import slugapp.com.ucscstudentapp.models.LoopWrapper;

/**
 * Created by simba on 7/31/15.
 */
public class LoopHttpRequest extends BaseHttpRequest {
    private static final String url = "http://bts.ucsc.edu:8081/location/get";

    public LoopHttpRequest() {
        super(url, Method.GET);
    }

    public void execute(final HttpCallback<List<Loop>> callback) {
        rawExecute(new HttpCallback<String>() {
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
