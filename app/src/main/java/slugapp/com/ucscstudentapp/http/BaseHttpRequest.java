package slugapp.com.ucscstudentapp.http;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import slugapp.com.ucscstudentapp.interfaces.HttpCallback;

/**
 * Created by simba on 7/31/15.
 */
public abstract class BaseHttpRequest extends BaseRequest {
    protected String url;
    private int volleyMethod;

    public BaseHttpRequest(Method method) {
        volleyMethod = method.method;
    }

    protected void createUrl(String api, String port, String path, HashMap<String, String> params) {
        String fields = "";
        if (params != null) {
            fields += "?";
            Set<String> set = params.keySet();
            boolean first = true;
            for (String param : set) {
                if (! first) fields += "&";
                fields += param + "=" + params.get(param);
                first = false;
            }
        }
        this.url =  api + port + path + fields.replace(" ", "%20").replace("&", "%26");
    }

    protected void rawExecute(final HttpCallback<String> callback) {
        StringRequest stringRequest = new StringRequest(
                volleyMethod,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        );
        queue().add(stringRequest);
    }

    protected void rawExecute(final HashMap<String, String> params, final HttpCallback<String> callback) {
        StringRequest stringRequest = new StringRequest(
                volleyMethod,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        queue().add(stringRequest);
    }

    public enum Method {
        POST(com.android.volley.Request.Method.POST),
        GET(com.android.volley.Request.Method.GET);

        private int method;

        Method(int method) {
            this.method = method;
        }
    }
}
