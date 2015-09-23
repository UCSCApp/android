package slugapp.com.ucscstudentapp.http;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by simba on 7/31/15.
 */
public abstract class HttpRequest extends Request {
    private String url;
    private int volleyMethod;

    public HttpRequest(String url, Method method) {
        this.url = url;
        volleyMethod = method.method;
    }

    protected void rawExecute(final Callback<String> callback) {
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

    public enum Method {
        POST(com.android.volley.Request.Method.POST),
        GET(com.android.volley.Request.Method.GET);

        private int method;
        Method(int method) {
            this.method = method;
        }
    }
}
