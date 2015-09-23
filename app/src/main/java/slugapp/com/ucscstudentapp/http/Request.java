package slugapp.com.ucscstudentapp.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by simba on 8/1/15.
 */
public abstract class Request {
    private static RequestQueue queue;
    private static ImageLoader iLoader;
    private static boolean hasInit = false;

    public static void init(Context ctx) {
        if(!hasInit) {
            queue = Volley.newRequestQueue(ctx);
            iLoader = new ImageLoader(queue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
            hasInit = true;
        }
    }

    protected RequestQueue queue() {
        return queue;
    }

    protected ImageLoader getImageLoader() {
        return iLoader;
    }
}
