package slugapp.com.sluglife.runnables;

import android.content.Context;
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
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.ActivityCallback;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.http.LoopHttpRequest;
import slugapp.com.sluglife.utils.LatLngInterpolator;
import slugapp.com.sluglife.models.Loop;

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
    private GoogleMap mMap;
    private HashMap<Loop, Marker> mLoopMap;
    private boolean noLoops;

    /**
     * Constructor
     *
     * @param context Activity context
     * @param map     Google map
     * @param loopMap Map containing loop information
     */
    public LoopRunnable(Context context, GoogleMap map, HashMap<Loop, Marker> loopMap) {
        this.mContext = context;
        this.mCallback = (ActivityCallback) context;
        this.mMap = map;
        this.mLoopMap = loopMap;

        this.noLoops = false;
    }

    /**
     * Runs runnable
     */
    @Override
    public void run() {
        new LoopHttpRequest(this.mContext).execute(new HttpCallback<List<Loop>>() {

            // TODO: compare map to val to remove nonoperating loops

            /**
             * On request success
             *
             * @param val Loop object from request
             */
            @Override
            public void onSuccess(List<Loop> val) {
                if (val.isEmpty() && !noLoops) {
                    mCallback.showSnackBar(mContext.getString(R.string.no_map_loop));
                    noLoops = true;
                    return;
                }
                for (Loop loop : val) {
                    boolean found = false;
                    for (Marker marker : mLoopMap.values()) {
                        if (String.valueOf(loop.id).compareTo(marker.getSnippet()) == 0) {
                            animateMarker(marker, new LatLng(loop.lat, loop.lng),
                                    new LatLngInterpolator.Linear());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        mLoopMap.put(loop, mMap.addMarker(new MarkerOptions()
                                .title(loop.type)
                                .snippet(String.valueOf(loop.id))
                                .position(new LatLng(loop.lat, loop.lng))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.loop_bus))));
                    }
                    noLoops = false;
                }
            }

            /**
             * On request error
             *
             * @param e Exception
             */
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
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
            long elapsed;
            float t;
            float v;

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
