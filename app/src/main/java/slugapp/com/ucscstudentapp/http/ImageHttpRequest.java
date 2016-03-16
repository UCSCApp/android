package slugapp.com.ucscstudentapp.http;

import android.util.Log;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;

import slugapp.com.ucscstudentapp.R;

/**
 * Created by simba on 8/1/15.
 */
public class ImageHttpRequest extends BaseRequest {
    private String url;

    public ImageHttpRequest(String url) {
        this.url = url;
    }

    public void execute(ImageView view) {
        if (view == null) {
            Log.e("VIEW", "NULL");
        }
        getImageLoader().get(url,
                ImageLoader.getImageListener(view,
                        R.drawable.ic_dining,
                        R.drawable.ic_dining));
    }
}
