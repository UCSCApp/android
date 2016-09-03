package slugapp.com.sluglife.http;

import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;

import slugapp.com.sluglife.R;

/**
 * Created by simba on 8/1/15
 * Edited by isaiah on 9/1/2015
 * <p/>
 * This file contains an http request that retrieves an image.
 */
public class ImageHttpRequest extends BaseRequest {
    private String mUrl;

    /**
     * Constructor
     *
     * @param url Url
     */
    public ImageHttpRequest(String url) {
        this.mUrl = url;
    }

    // TODO: change default

    /**
     * Executes http request
     *
     * @param view Image view
     */
    public void execute(ImageView view) {
        if (view != null) {
            this.getImageLoader().get(this.mUrl,
                    ImageLoader.getImageListener(view, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        }
    }
}
