package slugapp.com.sluglife.runnables;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.ActivityCallback;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.http.LoopHttpRequest;
import slugapp.com.sluglife.models.LoopObject;
import slugapp.com.sluglife.utils.LatLngInterpolator;

/**
 * Created by isaiah on 2/19/16
 * <p/>
 * This file contains a runnable that periodically gathers loop data.
 */
public class LoopRunnable implements Runnable {
    private static final Interpolator INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final float DURATION_IN_MS = 2000;

    private Context mContext;
    private ActivityCallback mCallback;
    private GoogleMap mGoogleMap;
    private List<LoopObject> mLoops;
    private boolean noLoops;
    private boolean noInternet;
    private boolean running;

    /**
     * Constructor
     *
     * @param context Activity context
     * @param map     Google map
     * @param loops Map containing loop information
     */
    public LoopRunnable(Context context, GoogleMap map, List<LoopObject> loops) {
        this.mContext = context;
        this.mCallback = (ActivityCallback) context;
        this.mGoogleMap = map;
        this.mLoops = loops;

        this.noLoops = false;
        this.noInternet = false;
        this.running = true;
    }

    /**
     * Runs runnable
     */
    @Override
    public void run() {
        if (!running) return;

        new LoopHttpRequest(this.mContext).execute(new HttpCallback<List<LoopObject>>() {

            /**
             * On request success
             *
             * @param vals Loop list from request
             */
            @Override
            public void onSuccess(List<LoopObject> vals) {
                noInternet = false;

                if (vals.isEmpty() && !noLoops) {
                    mCallback.showSnackBar(mContext.getString(R.string.snackbar_map_no_loop));
                    noLoops = true;
                    return;
                }

                // Removes markers on map that disappear
                Iterator<LoopObject> iterator = mLoops.iterator();
                while (iterator.hasNext()) {
                    boolean found = false;
                    LoopObject entry = iterator.next();
                    for (LoopObject loop : vals) {
                        if (loop.id == entry.id) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) iterator.remove();
                }

                // Adds new markers that are not currently shown
                for (LoopObject loop : vals) {
                    boolean found = false;
                    for (LoopObject object : mLoops) {
                        if (loop.id == object.id) {
                            animateMarker(object.marker, new LatLng(loop.lat, loop.lng),
                                    new LatLngInterpolator.Linear());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        loop.marker = mGoogleMap.addMarker(new MarkerOptions()
                                .title(loop.type)
                                .position(new LatLng(loop.lat, loop.lng))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.loop_bus)));
                        mLoops.add(loop);
                    }
                }
            }

            /**
             * On request error
             *
             * @param e Exception
             */
            @Override
            public void onError(Exception e) {
                if (!internetIsWorking() && !noInternet) {
                    mCallback.showSnackBar(mContext.getString(R.string.snackbar_map_no_internet));
                    noLoops = true;
                    noInternet = true;
                    return;
                }

                Iterator<LoopObject> iterator = mLoops.iterator();
                while (iterator.hasNext()) {
                    LoopObject entry = iterator.next();
                    entry.marker.remove();
                    iterator.remove();
                }

                e.printStackTrace();
            }
        });
    }

    /**
     * Stops runnable
     */
    public void stop() {
        this.running = false;
    }

    /**
     * Checks if internet is working
     *
     * @return If internet is working
     */
    private boolean internetIsWorking() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Animates marker on google map
     *
     * @param marker             Marker
     * @param finalPosition      Position to move to
     * @param latLngInterpolator Interpolator to move marker
     */
    private void animateMarker(final Marker marker, final LatLng finalPosition,
                               final LatLngInterpolator latLngInterpolator) {
        final LatLng startPosition = marker.getPosition();
        final long startTime = SystemClock.uptimeMillis();
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            private long elapsed;
            private float t;
            private float v;

            /**
             * Runs runnable
             */
            @Override
            public void run() {
                // Calculate progress using INTERPOLATOR
                this.elapsed = SystemClock.uptimeMillis() - startTime;
                this.t = this.elapsed / DURATION_IN_MS;
                this.v = INTERPOLATOR.getInterpolation(this.t);

                marker.setPosition(latLngInterpolator.interpolate(this.v, startPosition,
                        finalPosition));

                if (this.t < 1) handler.postDelayed(this, 16);
            }
        });
    }
}
