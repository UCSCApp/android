package slugapp.com.ucscstudentapp.runnables;

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

import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.interfaces.HttpCallback;
import slugapp.com.ucscstudentapp.http.LoopHttpRequest;
import slugapp.com.ucscstudentapp.utils.LatLngInterpolator;
import slugapp.com.ucscstudentapp.models.Loop;

/**
 * Created by isayyuhh on 2/19/16
 */
public class LoopRunnable implements Runnable {
    private Context mContext;
    private GoogleMap mMap;
    private List<Marker> mLoopList;
    private LatLngInterpolator.Linear linear;

    public LoopRunnable(Context context, GoogleMap map, List<Marker> loopList) {
        this.mContext = context;
        this.mMap = map;
        this.mLoopList = loopList;
        this.linear = new LatLngInterpolator.Linear();
    }

    @Override
    public void run() {
        new LoopHttpRequest(mContext).execute(new HttpCallback<List<Loop>>() {
            @Override
            public void onSuccess(List<Loop> val) {
                for (Loop loop : val) {
                    boolean found = false;
                    for (Marker marker : mLoopList) {
                        if (String.valueOf(loop.getId()).compareTo(marker.getSnippet()) == 0) {
                            animateMarker(marker, new LatLng(loop.getLat(), loop.getLng()), linear);
                            //marker.setPosition(new LatLng(loop.getLat(), loop.getLng()));
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        mLoopList.add(mMap.addMarker(new MarkerOptions()
                                .title(loop.getType())
                                .snippet(String.valueOf(loop.getId()))
                                .position(new LatLng(loop.getLat(), loop.getLng()))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.loop_bus))));
                    }
                }
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }

    private void animateMarker(final Marker marker, final LatLng finalPosition,
                               final LatLngInterpolator latLngInterpolator) {
        final LatLng startPosition = marker.getPosition();
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final float durationInMs = 3000;

        handler.post(new Runnable() {
            long elapsed;
            float t;
            float v;

            @Override
            public void run() {
                // Calculate progress using interpolator
                this.elapsed = SystemClock.uptimeMillis() - start;
                this.t = this.elapsed / durationInMs;
                this.v = interpolator.getInterpolation(this.t);

                marker.setPosition(latLngInterpolator.interpolate(this.v, startPosition,
                        finalPosition));

                // Repeat till progress is complete.
                if (this.t < 1) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
    }
}
