package slugapp.com.sluglife.http;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import slugapp.com.sluglife.interfaces.HttpCallback;

/**
 * Created by simba on 7/31/15
 * Edited by isaiah on 7/12/16
 * <p/>
 * This file contains a base http request class.
 */
public abstract class BaseHttpRequest extends BaseRequest {
    private static final String QUESTION_MARK = "?";
    private static final String EQUALS = "=";
    private static final String SPACE = " ";
    private static final String SPACE_URL = "%20";
    private static final String AMPERSAND = "&";
    private static final String AMPERSAND_URL = "%26";

    protected static final String EMPTY_STRING = "";

    protected String mUrl;
    protected String mHeader;

    private int mVolleyMethod;

    /**
     * Constructor
     *
     * @param method Http request method
     */
    public BaseHttpRequest(Method method) {
        this.mVolleyMethod = method.method;
        this.mHeader = EMPTY_STRING;
    }

    /**
     * Creates url by parts
     *
     * @param protocol Protocol
     * @param api      Api
     * @param port     Port
     * @param path     Path
     * @param params   Parameters
     */
    protected void createUrl(String protocol, String api, String port, String path,
                             HashMap<String, String> params) {
        String fields = EMPTY_STRING;
        if (params != null) {
            fields += QUESTION_MARK;
            Set<String> set = params.keySet();
            boolean first = true;
            for (String param : set) {
                if (!first) fields += AMPERSAND;
                fields += param + EQUALS + params.get(param);
                first = false;
            }
        }

        this.mUrl = protocol + api + port + path + fields.replace(SPACE, SPACE_URL).replace(
                AMPERSAND, AMPERSAND_URL);
    }

    /**
     * Executes http request
     *
     * @param callback Http callback
     */
    protected void rawExecute(final HttpCallback<String> callback) {
        StringRequest stringRequest = new StringRequest(
                this.mVolleyMethod,
                this.mUrl,
                new Response.Listener<String>() {

                    /**
                     * On response
                     *
                     * @param response String response
                     */
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {

                    /**
                     * On error
                     *
                     * @param error Error response
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<>();
                if (!mHeader.isEmpty()) {
                    map.put("X-Triton-App", mHeader);
                    return map;
                }

                return Collections.emptyMap();
            }
        };
        queue().add(stringRequest);
    }

    /**
     * Enum containing http request methods
     */
    public enum Method {
        POST(com.android.volley.Request.Method.POST),
        GET(com.android.volley.Request.Method.GET);

        private int method;

        /**
         * Constructor
         *
         * @param method Http request method
         */
        Method(int method) {
            this.method = method;
        }
    }
}
