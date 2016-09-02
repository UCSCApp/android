package slugapp.com.sluglife.fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.interfaces.ActivityCallback;

/**
 * Created by isaiah on 6/26/16
 * <p/>
 * This file contains a base google map fragment class.
 */
public abstract class BaseMapFragment extends SupportMapFragment implements OnMapReadyCallback {
    protected ActivityCallback mCallback;
    protected Context mContext;

    private String title;

    /**
     * Fragment's onAttach method
     *
     * @param context Activity context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.mCallback = (ActivityCallback) context;
        this.mContext = context;
    }

    /**
     * Fragment's onCreate method
     *
     * @param savedInstanceState Saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getMapAsync(this);

        this.setHasOptionsMenu(true);
    }

    /**
     * Fragment's onStart method
     */
    @Override
    public void onStart() {
        super.onStart();

        this.mCallback.setToolbarTitle(this.title);
    }

    /**
     * Fragment's onStop method
     */
    @Override
    public void onStop() {
        super.onStop();

        this.clearMapData();
    }

    /**
     * Sets google map when ready
     *
     * @param googleMap Google map
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.setMarkers(googleMap);
        this.setMapListeners(googleMap);
        this.setInitialZoom(googleMap);
    }

    /**
     * Sets google map fragment
     *
     * @param fragmentEnum Fragment enum containing fragment information
     */
    protected void setMapFragment(FragmentEnum fragmentEnum) {
        this.setFields();
        this.setLayout(fragmentEnum.name);
    }

    /**
     * Sets fragment layout
     *
     * @param title Name of fragment
     */
    protected void setLayout(int title) {
        this.title = this.mContext.getString(title);
    }

    /**
     * Get integer from shared preferences
     *
     * @param key          Shared preferences key
     * @param defaultValue Default value if key not found
     * @return Integer from shared preferences
     */
    protected int getSharedPrefInt(String key, int defaultValue) {
        SharedPreferences sharedPref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getInt(key, defaultValue);
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
     *
     * @return Boolean if location permissions were accepted
     */
    protected void requestLocationPermissions() {
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{
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

    /**
     * Allows user to set fields
     */
    protected abstract void setFields();

    /**
     * Allows user to set markers
     *
     * @param googleMap Google map
     */
    protected abstract void setMarkers(GoogleMap googleMap);

    /**
     * Allows user to set google map listeners
     *
     * @param googleMap Google map
     */
    protected abstract void setMapListeners(GoogleMap googleMap);

    /**
     * Allows user to set initial google map zoom
     *
     * @param googleMap Google map
     */
    protected abstract void setInitialZoom(GoogleMap googleMap);

    /**
     * Allows user to clear map data
     */
    protected abstract void clearMapData();
}
