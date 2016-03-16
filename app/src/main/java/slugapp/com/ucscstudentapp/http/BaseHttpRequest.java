package slugapp.com.ucscstudentapp.http;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import slugapp.com.ucscstudentapp.interfaces.HttpCallback;

/**
 * Created by simba on 7/31/15.
 */
public abstract class BaseHttpRequest extends BaseRequest {
    private String url;
    private int volleyMethod;

    public BaseHttpRequest(String url, Method method) {
        this.url = url;
        volleyMethod = method.method;
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
