package slugapp.com.sluglife.objects;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by isaiah on 9/2/16
 */
public class BaseMarkerObject extends BaseObject {
    public double lat, lng;
    public Marker marker;

    /**
     * Checks if location permissions were accepted
     *
     * @return Boolean if location permissions were accepted
     */
    protected boolean isLocationPermitted(Context context) {
        return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Checks if gps is enabled
     *
     * @return If gps is enabled
     */
    protected boolean isGPSEnabled(Context context) {
        int locationMode;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(),
                        Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
}
