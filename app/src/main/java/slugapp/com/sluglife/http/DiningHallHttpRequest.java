package slugapp.com.sluglife.http;

import android.content.Context;

import org.json.JSONException;

import java.util.HashMap;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.DiningHall;
import slugapp.com.sluglife.models.WrapperDiningHall;

/**
 * Created by isayyuhh_s on 9/1/2015.
 */
public class DiningHallHttpRequest extends BaseHttpRequest {
    protected Context mContext;

    protected String mName;

    public DiningHallHttpRequest(Context context, String name) {
        super(BaseHttpRequest.Method.GET);

        String protocol = context.getString(R.string.http);
        String api = context.getString(R.string.sluglife_api);
        String port = context.getString(R.string.port8080);
        String path = context.getString(R.string.api_dining_menu);

        HashMap<String, String> params = new HashMap<>();
        params.put(context.getString(R.string.bundle_name), name);

        this.createUrl(protocol, api, port, path, params);
        this.mContext = context;
        this.mName = name;
    }

    public void execute(final HttpCallback<DiningHall> callback) {
        this.rawExecute(new HttpCallback<String>() {
            @Override
            public void onSuccess(String val) {
                try {
                    callback.onSuccess(new WrapperDiningHall(mContext, val, mName));
                } catch (JSONException je) {
                    je.printStackTrace();
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
