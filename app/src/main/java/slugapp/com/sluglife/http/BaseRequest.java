package slugapp.com.sluglife.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by simba on 8/1/15
 * Edited by isaiah on 7/12/16
 * <p/>
 * This file contains a base request class.
 */
public abstract class BaseRequest {
    private static RequestQueue sQueue;
    private static ImageLoader sILoader;

    private static boolean hasInit = false;

    /**
     * Initializes volley
     *
     * @param ctx Context
     */
    public static void init(Context ctx) {
        if (!hasInit) {
            sQueue = Volley.newRequestQueue(ctx);
            sILoader = new ImageLoader(sQueue,
                    new ImageLoader.ImageCache() {
                        private final LruCache<String, Bitmap> cache = new LruCache<>(20);

                        /**
                         * Gets bitmap from url
                         *
                         * @param url Url
                         * @return Bitmap from url
                         */
                        @Override
                        public Bitmap getBitmap(String url) {
                            return cache.get(url);
                        }

                        /**
                         * Puts bitmap into url
                         *
                         * @param url Url
                         * @param bitmap Bitmap to put
                         */
                        @Override
                        public void putBitmap(String url, Bitmap bitmap) {
                            cache.put(url, bitmap);
                        }
                    });
            hasInit = true;
        }
    }

    /**
     * Gets request queue
     *
     * @return Request queue
     */
    protected RequestQueue queue() {
        return sQueue;
    }

    /**
     * Gets image loader
     *
     * @return Image loader
     */
    protected ImageLoader getImageLoader() {
        return sILoader;
    }
}
