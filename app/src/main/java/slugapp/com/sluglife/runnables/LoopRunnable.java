package slugapp.com.sluglife.runnables;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;

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
    private static final float DURATION_IN_MS = 2000;

    private Context mContext;
    private GoogleMap mMap;
    private HashMap<Loop, Marker> mLoopList;
    private boolean noLoops;

    public LoopRunnable(Context context, GoogleMap map, HashMap<Loop, Marker> loopList) {
        this.mContext = context;
        this.mMap = map;
        this.mLoopList = loopList;

        this.noLoops = false;
    }

    @Override
    public void run() {
        new LoopHttpRequest(this.mContext).execute(new HttpCallback<List<Loop>>() {
            @Override
            public void onSuccess(List<Loop> val) {
                if (val.isEmpty() && !noLoops) {
                    // TODO: add to strings.xml
                    Toast.makeText(mContext, "No loops available at the moment.",
                            Toast.LENGTH_SHORT).show();
                    noLoops = true;
                    return;
                }
                for (Loop loop : val) {
                    boolean found = false;
                    for (Marker marker : mLoopList.values()) {
                        if (String.valueOf(loop.id).compareTo(marker.getSnippet()) == 0) {
                            animateMarker(marker, new LatLng(loop.lat, loop.lng),
                                    new LatLngInterpolator.Linear());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        mLoopList.put(loop, mMap.addMarker(new MarkerOptions()
                                .title(loop.type)
                                .snippet(String.valueOf(loop.id))
                                .position(new LatLng(loop.lat, loop.lng))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.loop_bus))));
                    }
                    noLoops = false;
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
