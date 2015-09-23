package slugapp.com.ucscstudentapp.map;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.dining.DiningHallDetail;
import slugapp.com.ucscstudentapp.main.ActivityReference;

/**
 * Created by isayyuhh_s on 8/18/2015.
 *
 * Edits the map
 */
public class MapEditor {
    public final static String MARKER_TITLE = "marker_title_";
    public final static String MARKER_LNG = "marker_lng_";
    public final static String MARKER_LAT = "marker_lat_";
    public final static String MARKER_NUMBER = "marker_number";

    private GoogleMap map;
    private ActivityReference mCallBack;
    private Map fragment;
    private Marker currentMarker = null;
    private SharedPreferences customMarkers;
    private SharedPreferences.Editor markerEditor;
    private int markerNumber;

    public MapEditor (Context context, Map fragment, ActivityReference callback) {
        this.fragment = fragment;
        this.mCallBack = callback;

        // Set SharedPref
        customMarkers = context.getSharedPreferences("CUSTOM_MARKERS", Context.MODE_PRIVATE);
        markerEditor = customMarkers.edit();
        if ((markerNumber = customMarkers.getInt(MARKER_NUMBER, 0)) == 0) {
            markerEditor.putInt(MARKER_NUMBER, 0);
            markerEditor.commit();
        }
    }

    public void initializeMap (GoogleMap map) {
        if (this.map == null) {
            this.map = map;

            // Set initial state
            map.getUiSettings().setZoomControlsEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(36.991499, -122.058835), 14));
            map.setMyLocationEnabled(true);
        }
    }

    public void moveTo (LatLng latLng, String name) {
        map.addMarker(new MarkerOptions()
                .title(name)
                .position(latLng)).showInfoWindow();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    /**
     * Sets Defaults Markers
     */
    public void setMarkers() {
        // Libraries
        map.addMarker(new MarkerOptions()
                .title("McHenry Library")
                .snippet("tap here to see more")
                .position(new LatLng(36.99578157522153, -122.058908423001))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.library)));
        map.addMarker(new MarkerOptions()
                .title("S&E Library")
                .snippet("tap here to see more")
                .position(new LatLng(36.99904411574191, -122.06070818525006))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.library)));

        // Dining Halls
        map.addMarker(new MarkerOptions()
                .title("College Eight / Oakes Dining Hall")
                .snippet("tap here to see more")
                .position(new LatLng(36.991565, -122.065267))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dining_hall)));
        map.addMarker(new MarkerOptions()
                .title("Porter / Kresge Dining Hall")
                .snippet("tap here to see more")
                .position(new LatLng(36.994344, -122.065800))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dining_hall)));
        map.addMarker(new MarkerOptions()
                .title("College Nine / College Ten Dining Hall")
                .snippet("tap here to see more")
                .position(new LatLng(37.001096, -122.058031))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dining_hall)));
        map.addMarker(new MarkerOptions()
                .title("Crown / Merrill Dining Hall")
                .snippet("tap here to see more")
                .position(new LatLng(36.999971, -122.054448))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dining_hall)));
        map.addMarker(new MarkerOptions()
                .title("Cowell / Stevenson Dining Hall")
                .snippet("tap here to see more")
                .position(new LatLng(36.997157, -122.053150))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dining_hall)));
    }

    /**
     * Sets Custom Markers
     */
    public void setCustomMarkers() {
        String title;
        float lat, lng;
        fixCustomMarkers();
        for (int i = 0; i <= markerNumber; i++) {
            title = customMarkers.getString(MARKER_TITLE + Integer.toString(i), "");
            lat = customMarkers.getFloat(MARKER_LAT + Integer.toString(i), 0);
            lng = customMarkers.getFloat(MARKER_LNG + Integer.toString(i), 0);
            map.addMarker(new MarkerOptions()
                    .snippet("tap to delete")
                    .title(title)
                    .draggable(true)
                    .anchor(0.5f, 0.5f)
                    .position(new LatLng(lat, lng)));
        }
    }

    /**
     * Sets Marker Listeners
     */
    public void setListeners() {
        // OnMarkerClickListener
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                currentMarker = marker;
                map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                return false;
            }
        });

        // OnInfoWindowClickListener
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.isDraggable()) {
                    currentMarker = null;
                    marker.remove();
                    int current = findCustomMarker(marker.getTitle(),
                            (float) marker.getPosition().latitude,
                            (float) marker.getPosition().longitude);
                    markerEditor.remove(MARKER_TITLE + Integer.toString(current));
                    markerEditor.remove(MARKER_LAT + Integer.toString(current));
                    markerEditor.remove(MARKER_LNG + Integer.toString(current));
                    markerEditor.commit();
                    return;
                }
                Bundle b = new Bundle();
                b.putString("name", marker.getTitle());
                FragmentTransaction ft = mCallBack.fm().beginTransaction();
                DiningHallDetail llf = new DiningHallDetail();
                llf.setArguments(b);
                ft.replace(R.id.listFragment, llf);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        // OnMapClickListener
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (currentMarker != null) {
                    currentMarker.hideInfoWindow();
                    currentMarker = null;
                    return;
                }
                Bundle b = new Bundle();
                b.putFloat("LAT", (float) latLng.latitude);
                b.putFloat("LNG", (float) latLng.longitude);
                FragmentTransaction ft = mCallBack.fm().beginTransaction();
                NewMarkerDialog newMarkerDialog = new NewMarkerDialog();
                newMarkerDialog.setTargetFragment(fragment, 0);
                newMarkerDialog.setArguments(b);
                ft.addToBackStack(null);
                newMarkerDialog.show(ft, "dialog");
            }
        });
    }

    /**
     * Creates new Marker
     */
    public void createMarker(String title, float lat, float lng) {
        // Adding Marker to Map
        MarkerOptions options = new MarkerOptions()
                .snippet("tap to delete")
                .title(title)
                .draggable(true)
                .anchor(0.5f, 0.5f)
                .position(new LatLng(lat, lng));
        Marker currentMarker = map.addMarker(options);
        currentMarker.showInfoWindow();
        this.currentMarker = currentMarker;

        // Move camera to new Marker
        map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));

        // Add Marker to SharedPref
        int current = findCustomMarker(title, lat, lng);
        markerEditor.putString(MARKER_TITLE + Integer.toString(current), title);
        markerEditor.putFloat(MARKER_LAT + Integer.toString(current), lat);
        markerEditor.putFloat(MARKER_LNG + Integer.toString(current), lng);
        markerEditor.commit();
    }

    /**
     * Finds Marker in SharedPref
     */
    private int findCustomMarker (String title, float lat, float lng) {
        ++markerNumber;
        markerEditor.putInt(MARKER_NUMBER, markerNumber);
        for (int i = 0; i < markerNumber; i++) {
            if (customMarkers.getString(MARKER_TITLE + Integer.toString(i), "").equals(title) &&
                    customMarkers.getFloat(MARKER_LAT + Integer.toString(i), 0) == lat &&
                    customMarkers.getFloat(MARKER_LNG + Integer.toString(i), 0) == lng) {
                return i;
            }
        }
        return markerNumber;
    }

    /**
     * Reorders Markers in SharedPref
     */
    private void fixCustomMarkers () {
        int current = 0;
        boolean missing = false;
        String title;
        float lat, lng;
        for (int i = 0; i <= markerNumber; i++) {
            title = customMarkers.getString(MARKER_TITLE + Integer.toString(i), null);
            lat = customMarkers.getFloat(MARKER_LAT + Integer.toString(i), 0);
            lng = customMarkers.getFloat(MARKER_LNG + Integer.toString(i), 0);
            if (! missing && title == null && lat == 0 && lng == 0) {
                current = new Integer(i);
                missing = true;
            } else if (missing && (title != null || lat != 0 || lng != 0)) {
                markerEditor.putString(MARKER_TITLE + Integer.toString(current), title);
                markerEditor.putFloat(MARKER_LAT + Integer.toString(current), lat);
                markerEditor.putFloat(MARKER_LNG + Integer.toString(current), lng);
                markerEditor.remove(MARKER_TITLE + Integer.toString(i));
                markerEditor.remove(MARKER_LAT + Integer.toString(i));
                markerEditor.remove(MARKER_LNG + Integer.toString(i));
                missing = false;
            }
            if (! missing) current++;
        }
        markerNumber = current;
        markerEditor.putInt(MARKER_NUMBER, markerNumber);
        markerEditor.commit();
    }

    public void hideInfoWindows () {
        if (currentMarker != null) currentMarker.hideInfoWindow();
    }
}
