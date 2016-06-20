package slugapp.com.sluglife.application;

import android.app.Application;

import slugapp.com.sluglife.http.BaseRequest;

/**
 * Created by simba on 7/31/15.
 */
public class SlugLifeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BaseRequest.init(this);
    }
}
