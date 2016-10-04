package slugapp.com.sluglife.fragments;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.http.FacilityListHttpRequest;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.objects.BaseMarkerObject;
import slugapp.com.sluglife.objects.FacilityObject;
import slugapp.com.sluglife.objects.LoopObject;
import slugapp.com.sluglife.runnables.LoopRunnable;

/**
 * Created by Karol Josef Bustamante. <karoljosefb@gmail.com>
 * Edited by isaiah on 8/8/2015
 * <p/>
 * This file contains a google map fragment that displays a google map using the google map api.
 */
public class MapFragment extends BaseMapFragment {
    // INSERT INTO locations VALUES(25, 'Page Smith Library', 'description', 'library', '36.99687611', '-122.0535511');

    private static final FragmentEnum FRAGMENT = FragmentEnum.MAP;

    private static final long MAP_DELAY = 0;
    private static final int MAP_PERIOD = 2000;
    private static final float DEFAULT_ZOOM = 14.2f;
    private static final float LOCATION_ZOOM = 14.5f;
    private static final float SCHOOL_RADIUS = 1672.2233f;

    private static final double INIT_LAT = 36.991339;
    private static final double INIT_LNG = -122.058972;

    private static final int LOOP_MASK = 0b000001;
    public static final int DEFAULT_MASK = 0b001001;

    private LoopRunnable loopRunnable;
    private List<BaseMarkerObject> staticMarkers;
    private List<LoopObject> dynamicMarkers;

    /**
     * Gets a new instance of fragment
     *
     * @return New instance of fragment
     */
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    /**
     * Gets a new instance of fragment
     *
     * @param context  Activity context
     * @param facility Name of facility to search
     * @return New instance of fragment
     */
    public static MapFragment newInstance(Context context, FacilityObject facility) {
        MapFragment fragment = new MapFragment();

        Bundle b = new Bundle();
        b.putSerializable(context.getString(R.string.bundle_name), facility);
        fragment.setArguments(b);

        return fragment;
    }

    /**
     * Fragment's onCreateView method
     *
     * @param inflater           Layout inflater
     * @param container          Container of fragment
     * @param savedInstanceState Saved instance state
     * @return Inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);

        this.setMapFragment(FRAGMENT);

        return view;
    }

    /**
     * Fragment's onResume method
     */
    @Override
    public void onResume() {
        super.onResume();
        if (this.loopRunnable != null) {
            this.mCallback.scheduleTimer(this.loopRunnable, MAP_DELAY, MAP_PERIOD);
            this.loopRunnable.start();
        }
    }

    /**
     * Sets fields
     */
    @Override
    protected void setFields() {
        this.staticMarkers = new ArrayList<>();
        this.dynamicMarkers = new ArrayList<>();
    }

    /**
     * Sets google map markers
     *
     * @param googleMap Google map
     */
    @Override
    protected void setMarkers(GoogleMap googleMap) {
        int bin = this.getSharedPrefInt(this.mContext.getString(R.string.bundle_markers), DEFAULT_MASK);

        this.setLoopBusMarkers(googleMap, bin);
        this.setStaticMarkers(googleMap, bin);
    }

    /**
     * Sets google map listeners
     *
     * @param googleMap Google map
     */
    @Override
    protected void setMapListeners(final GoogleMap googleMap) {
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                return false;
            }
        });
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = getActivity().getLayoutInflater().inflate(
                        R.layout.info_window_map, null);
                TextView name = (TextView) view.findViewById(R.id.name);
                TextView description = (TextView) view.findViewById(R.id.description);

                name.setText(marker.getTitle());
                description.setText(marker.getSnippet());

                return view;
            }
        });
    }

    /**
     * Sets initial google map zoom
     *
     * @param googleMap Google map
     */
    @Override
    @SuppressWarnings({"MissingPermission"})
    protected void setInitialZoom(final GoogleMap googleMap) {
        LatLng initLatLng = new LatLng(INIT_LAT, INIT_LNG);

        googleMap.getUiSettings().setZoomControlsEnabled(true);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initLatLng, DEFAULT_ZOOM));

        googleMap.setMinZoomPreference(12.5f);
        googleMap.setLatLngBoundsForCameraTarget(new LatLngBounds(
                new LatLng(36.949355, -122.088585), new LatLng(37.025354, -121.929058)));

        if (this.isGPSEnabled() && this.isLocationPermitted()) {
            googleMap.setMyLocationEnabled(true);

            LocationManager locationManager = (LocationManager) this.getActivity()
                    .getSystemService(Context.LOCATION_SERVICE);

            Location location = locationManager.getLastKnownLocation(locationManager
                    .getBestProvider(new Criteria(), false));

            if (location == null) return;

            float[] distance = new float[3];
            Location.distanceBetween(initLatLng.latitude, initLatLng.longitude,
                    location.getLatitude(), location.getLongitude(), distance);
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
            if (distance[0] < SCHOOL_RADIUS) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, LOCATION_ZOOM));
            }
        } else if (this.isGPSEnabled()) this.requestLocationPermissions();

        if (this.getArguments() != null && this.getArguments().containsKey(this.getString(
                R.string.bundle_name))) {
            FacilityObject facility = (FacilityObject) this.getArguments().getSerializable(
                    this.getString(R.string.bundle_name));
            this.createMarker(googleMap, facility);
        }
    }

    /**
     * Clears google map data
     */
    @Override
    protected void clearMapData() {
        for (LoopObject loop : this.dynamicMarkers) {
            if (loop.marker != null) {
                loop.marker.remove();
                loop.marker = null;
            }
        }
        this.mCallback.cancelTimer();
        if (this.loopRunnable != null) this.loopRunnable.stop();
    }

    /**
     * Sets loop bus markers on google map
     *
     * @param googleMap Google map
     * @param bin       Binary number for map filter
     */
    private void setLoopBusMarkers(final GoogleMap googleMap, int bin) {
        if ((bin & LOOP_MASK) == 0) return;

        this.dynamicMarkers = new ArrayList<>();

        this.loopRunnable = new LoopRunnable(this.mContext, googleMap,
                this.dynamicMarkers);

        this.mCallback.scheduleTimer(this.loopRunnable, MAP_DELAY, MAP_PERIOD);
    }

    /**
     * Sets static markers on google map
     *
     * @param googleMap Google map
     * @param bin       Binary number for map filter
     */
    private void setStaticMarkers(final GoogleMap googleMap, final int bin) {
        new FacilityListHttpRequest(this.mContext).execute(
                new HttpCallback<List<FacilityObject>>() {

                    /**
                     * On request success
                     *
                     * @param values List of values from request
                     */
                    @Override
                    public void onSuccess(List<FacilityObject> values) {
                        for (FacilityObject facility : values) {
                            staticMarkers.add(facility);

                            String title = facility.name;
                            String snippet = facility.description;
                            LatLng latLng = new LatLng(facility.lat, facility.lng);

                            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(
                                    facility.type.markerImage);

                            if ((bin & facility.type.mask) == 0) continue;
                            facility.marker = googleMap.addMarker(new MarkerOptions()
                                    .title(title)
                                    .snippet(snippet)
                                    .position(latLng)
                                    .icon(bitmap));
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

    public void createMarker(GoogleMap googleMap, FacilityObject facility) {
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(
                facility.type.markerImage);

        // Adding Marker to Map
        MarkerOptions options = new MarkerOptions()
                .snippet(facility.description)
                .title(facility.name)
                .position(new LatLng(facility.lat, facility.lng))
                .icon(bitmap);
        Marker currentMarker = googleMap.addMarker(options);
        currentMarker.showInfoWindow();

        // Move camera to new Marker
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(facility.lat,
                facility.lng), LOCATION_ZOOM));
    }
}