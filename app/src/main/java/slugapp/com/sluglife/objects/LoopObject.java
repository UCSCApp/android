package slugapp.com.sluglife.objects;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.sluglife.http.LoopTimeHttpRequest;
import slugapp.com.sluglife.interfaces.HttpCallback;

/**
 * Created by isaiah on 2/19/16
 * <p/>
 * This file contains a loop object.
 */
public class LoopObject extends BaseMarkerObject {
    private static final double LAT_UNIT = 68.9;
    private static final double LNG_UNIT = 54.6;

    public int id;
    public String type;
    public List<Double> mphs;
    public String eta;

    /**
     * Constructor
     *
     * @param id   Loop id
     * @param lat  Loop latitude
     * @param lng  Loop longitude
     * @param type Loop type
     */
    public LoopObject(int id, double lat, double lng, String type) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.type = type;
        this.mphs = new ArrayList<>();
        this.eta = "Loading...";
    }

    /**
     * Updates marker coordinates and adds new mph
     *
     * @param lat New latitude
     * @param lng New longitude
     */
    public void updateCoordinates(double lat, double lng) {
        double distance = Math.sqrt(Math.pow(Math.abs((lat - this.lat) * LAT_UNIT), 2)
                + Math.pow((Math.abs((lng - this.lng) * LNG_UNIT)), 2));

        double mph = (distance / 2) * 3600;
        if (this.mphs.size() > 3) this.mphs.remove(0);
        this.mphs.add(mph);

        this.lat = lat;
        this.lng = lng;
    }

    /**
     * Gets time bus will take to get to your position
     *
     * @return Time bus will take to get to your position
     */
    public int getTime() {
        double total = 0;
        for (Double mph : this.mphs) {
            total += mph;
        }
        double average = total / this.mphs.size();
        return (int) average;
    }

    /**
     * Gets the estimated time of arrival
     *
     * @param context Activity's context
     */
    @SuppressWarnings({"MissingPermission"})
    public void getEta(Context context) {
        if (this.isGPSEnabled(context)) {
            LocationManager locationManager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);

            Location location = locationManager.getLastKnownLocation(locationManager
                    .getBestProvider(new Criteria(), false));

            if (location == null) return;

            new LoopTimeHttpRequest(context, this.lat, this.lng, location.getLatitude(),
                    location.getLongitude()).execute(new HttpCallback<JSONObject>() {
                @Override
                public void onSuccess(JSONObject val) {
                    try {
                        JSONArray rows = val.getJSONArray("rows");
                        JSONObject obj = (JSONObject) rows.get(0);
                        JSONArray elements = obj.getJSONArray("elements");
                        obj = (JSONObject) elements.get(0);
                        JSONObject duration = obj.getJSONObject("duration");
                        eta = duration.getString("text");
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }
                }

                @Override
                public void onError(Exception e) {
                }
            });
        }
    }
}
