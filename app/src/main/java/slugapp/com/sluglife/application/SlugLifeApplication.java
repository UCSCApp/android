package slugapp.com.sluglife.application;

import android.app.Application;

import slugapp.com.sluglife.http.BaseRequest;

/**
 * Created by simba on 7/31/15
 * Edited by isaiah on 7/11/16
 *
 * This file contains the application class. This class initializes what needs to be initialized
 * before the activity starts.
 */
public class SlugLifeApplication extends Application {

    /**
     * Application's onCreate method
     */
    @Override
    public void onCreate() {
        super.onCreate();
        BaseRequest.init(this);
    }
}
