package slugapp.com.sluglife.http;

import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;

import slugapp.com.sluglife.R;

/**
 * Created by simba on 8/1/15
 */
public class ImageHttpRequest extends BaseRequest {
    private String mUrl;

    public ImageHttpRequest(String url) {
        this.mUrl = url;
    }

    public void execute(ImageView view) {
        if (view != null) {
            this.getImageLoader().get(this.mUrl,
                    ImageLoader.getImageListener(view, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        }
    }
}
