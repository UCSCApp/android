package slugapp.com.sluglife.runnables;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Iterator;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.ActivityCallback;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.http.LoopHttpRequest;
import slugapp.com.sluglife.objects.LoopObject;
import slugapp.com.sluglife.utils.LatLngInterpolator;

/**
 * Created by isaiah on 2/19/16
 * <p/>
 * This file contains a runnable that periodically gathers loop data.
 */
public class LoopRunnable implements Runnable {
    private static final Interpolator INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final float DURATION_IN_MS = 2000;
    private static final float MAX_DURATION = 1;
    private static final long DELAY = 16;

    private Context mContext;
    private ActivityCallback mCallback;

    private GoogleMap googleMap;
    private List<LoopObject> loops;
    private boolean noInternet;
    private boolean running;

    /**
     * Constructor
     *
     * @param context Activity context
     * @param map     Google map
     * @param loops   Map containing loop information
     */
    public LoopRunnable(Context context, GoogleMap map, List<LoopObject> loops) {
        this.mContext = context;
        this.mCallback = (ActivityCallback) context;
        this.googleMap = map;
        this.loops = loops;

        this.noInternet = false;
        this.running = true;
    }

    /**
     * Runs runnable
     */
    @Override
    @SuppressWarnings({"MissingPermission"})
    public void run() {
        if (!this.running) return;

        new LoopHttpRequest(this.mContext).execute(new HttpCallback<List<LoopObject>>() {

            /**
             * On request success
             *
             * @param values Loop list from request
             */
            @Override
            public void onSuccess(List<LoopObject> values) {
                noInternet = false;

                if (isGPSEnabled() && isLocationPermitted()) {
                    googleMap.setMyLocationEnabled(true);
                } else if (isGPSEnabled()) {
                    requestLocationPermissions();
                } else {
                    googleMap.setMyLocationEnabled(false);
                }

                // Removes markers on map that disappear
                Iterator<LoopObject> iterator = loops.iterator();
                while (iterator.hasNext()) {
                    boolean found = false;
                    LoopObject loopObject = iterator.next();
                    for (LoopObject loop : values) {
                        if (loop.id == loopObject.id) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) iterator.remove();
                }

                // Adds new markers that are not currently shown
                for (LoopObject loop : values) {
                    boolean found = false;

                    // If found, animate
                    for (LoopObject loopObject : loops) {
                        if (loop.id == loopObject.id) {
                            loopObject.lat = loop.lat;
                            loopObject.lng = loop.lng;
                            loopObject.getEta(mContext);
                            loopObject.marker.setSnippet(loopObject.eta);
                            animateMarker(loopObject.marker, new LatLng(loop.lat, loop.lng),
                                    new LatLngInterpolator.Linear());
                            found = true;
                            break;
                        }
                    }

                    // If not found, add to map
                    if (!found) {
                        if (loop.marker != null) continue;
                        loop.getEta(mContext);
                        loop.marker = googleMap.addMarker(new MarkerOptions()
                                .title(loop.type)
                                .snippet(loop.eta)
                                .position(new LatLng(loop.lat, loop.lng))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.loop_bus)));
                        loops.add(loop);
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
                    noInternet = true;
                    return;
                }

                Iterator<LoopObject> iterator = loops.iterator();
                while (iterator.hasNext()) {
                    LoopObject loopObject = iterator.next();
                    loopObject.marker.remove();
                    iterator.remove();
                }

                e.printStackTrace();
            }
        });
    }

    /**
     * Starts runnable
     */
    public void start() {
        this.running = true;
    }

    /**
     * Stops runnable
     */
    public void stop() {
        this.loops.clear();
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

                if (this.t < MAX_DURATION) handler.postDelayed(this, DELAY);
            }
        });
    }

    /**
     * Checks if location permissions were accepted
     *
     * @return Boolean if location permissions were accepted
     */
    protected boolean isLocationPermitted() {
        return ActivityCompat.checkSelfPermission(this.mContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Requests location permissions
     */
    protected void requestLocationPermissions() {
        ActivityCompat.requestPermissions((Activity) this.mContext, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, 0);
    }

    /**
     * Checks if gps is enabled
     *
     * @return If gps is enabled
     */
    protected boolean isGPSEnabled() {
        int locationMode;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(this.mContext.getContentResolver(),
                        Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(this.mContext.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
}
