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
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.http.LoopHttpRequest;
import slugapp.com.sluglife.models.Facility;
import slugapp.com.sluglife.utils.LatLngInterpolator;
import slugapp.com.sluglife.models.Loop;

/**
 * Created by isayyuhh on 2/19/16
 */
public class LoopRunnable implements Runnable {
    private static final Interpolator INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final float DURATION_IN_MS = 1000;

    private Context mContext;
    private GoogleMap mMap;
    private HashMap<Loop, Marker> mLoopList;

    public LoopRunnable(Context context, GoogleMap map, HashMap<Loop, Marker> loopList) {
        this.mContext = context;
        this.mMap = map;
        this.mLoopList = loopList;
    }

    @Override
    public void run() {
        new LoopHttpRequest(this.mContext).execute(new HttpCallback<List<Loop>>() {
            @Override
            public void onSuccess(List<Loop> val) {
                for (Loop loop : val) {
                    if (mLoopList.containsKey(loop))
                        animateMarker(mLoopList.get(loop), new LatLng(loop.getLat(), loop.getLng()),
                                new LatLngInterpolator.Linear());
                    else {
                        mLoopList.put(loop, mMap.addMarker(new MarkerOptions()
                                .title(loop.getType())
                                .snippet(String.valueOf(loop.getId()))
                                .position(new LatLng(loop.getLat(), loop.getLng()))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.loop_bus))));
                    }
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void animateMarker(final Marker marker, final LatLng finalPosition,
                               final LatLngInterpolator latLngInterpolator) {
        final LatLng startPosition = marker.getPosition();
        final long startTime = SystemClock.uptimeMillis();
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            long elapsed;
            float t;
            float v;

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
