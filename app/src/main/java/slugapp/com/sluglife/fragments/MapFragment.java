package slugapp.com.sluglife.fragments;

//
//  MapFragment.java
//  SlugRoute
//
//  Created by Karol Josef Bustamante. <karoljosefb@gmail.com>
//  Copyright (c) 2015 UCSC Android. All rights reserved.
//

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.enums.MarkerEnum;
import slugapp.com.sluglife.enums.MarkerTypeEnum;
import slugapp.com.sluglife.http.DiningHallHttpRequest;
import slugapp.com.sluglife.http.DiningListHttpRequest;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.DiningHall;
import slugapp.com.sluglife.models.Facility;
import slugapp.com.sluglife.models.Loop;
import slugapp.com.sluglife.runnables.LoopRunnable;

public class MapFragment extends BaseMapFragment {
    private static final FragmentEnum FRAGMENT = FragmentEnum.MAP;
    private static final MarkerEnum[] sMarkerEnums = MarkerEnum.values();

    private static final long MAP_DELAY = 0;
    private static final int MAP_PERIOD = 2000;
    private static final float SCHOOL_RADIUS = 1672.2233f;
    private static final float DEFAULT_ZOOM = 14.5f;
    private static final float LOCATION_ZOOM = 15.0f;

    private static final int LOOP_MASK = 0b000001;
    private static final int DINING_HALL_MASK = 0b000100;
    private static final int LIBRARY_MASK = 0b001000;

    public static final int DEFAULT_MASK = 0b000000;

    private HashMap<Facility, Marker> mStaticMarkers;
    private HashMap<Loop, Marker> mDynamicMarkers;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);

        this.setMapFragment(view, FRAGMENT);

        return view;
    }

    @Override
    protected void setFields(View view) {
        this.mStaticMarkers = new HashMap<>();
        this.mDynamicMarkers = new HashMap<>();
    }

    @Override
    protected void setMarkers(GoogleMap googleMap) {
        int bin = this.getSharedPrefInt(this.mContext.getString(R.string.bundle_markers),
                DEFAULT_MASK);

        if ((bin & LOOP_MASK) != 0) this.setLoopBusMarkers(googleMap);
        if ((bin & DINING_HALL_MASK) != 0) this.setDiningHallMarkers(googleMap);
        if ((bin & LIBRARY_MASK) != 0) this.setLibraryMarkers(googleMap);
    }

    @Override
    protected void setMapListeners(final GoogleMap map) {
        // OnMarkerClickListener
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                return false;
            }
        });

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                onClickStaticInfoWindow(marker);
            }
        });
    }

    @Override
    protected void setInitialZoom(GoogleMap map) {
        float lat = Float.valueOf(this.mContext.getString(R.string.map_init_lat));
        float lng = Float.valueOf(this.mContext.getString(R.string.map_init_lng));
        float zoom = DEFAULT_ZOOM;

        LatLng initLatLng = new LatLng(lat, lng);

        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initLatLng, zoom));
        if (ActivityCompat.checkSelfPermission(
                this.mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        this.mContext, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(initLatLng, zoom));
        }
        else {
            map.setMyLocationEnabled(true);
            float[] distance = new float[3];
            LocationManager locationManager = (LocationManager)this.getActivity()
                    .getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(locationManager
                    .getBestProvider(new Criteria(), false));
            Location.distanceBetween(initLatLng.latitude, initLatLng.longitude,
                    location.getLatitude(), location.getLongitude(), distance);
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
            if (distance[0] < SCHOOL_RADIUS) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, LOCATION_ZOOM));
            } else {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(initLatLng, DEFAULT_ZOOM));
            }
        }
    }

    @Override
    protected void clearData() {
        for (Marker marker : this.mDynamicMarkers.values()) if (marker != null) marker.remove();
        this.mCallback.cancelTimer();
    }

    private void setLoopBusMarkers(final GoogleMap map) {
        this.mDynamicMarkers = new HashMap<>();

        final Handler handler = new Handler();
        final LoopRunnable runnable = new LoopRunnable(this.mContext, map, this.mDynamicMarkers);

        this.mCallback.initTimer();
        this.mCallback.scheduleTimer(handler, runnable, MAP_DELAY, MAP_PERIOD);
    }

    private void setDiningHallMarkers(final GoogleMap map) {
        new DiningListHttpRequest(this.mContext).execute(new HttpCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> val) {
                for (String diningHallName : val) {
                    new DiningHallHttpRequest(mContext, diningHallName).execute(
                            new HttpCallback<DiningHall>() {
                                @Override
                                public void onSuccess(DiningHall val) {
                                    mStaticMarkers.put(val, map.addMarker(new MarkerOptions()
                                            .title(val.name + mContext.getString(
                                                    R.string.detail_map_dining_ending))
                                            .snippet(mContext.getString(R.string.map_dining_snippet))
                                            .position(val.latLng)
                                            .icon(BitmapDescriptorFactory.fromResource(
                                                    DiningHall.diningImage))));
                                }

                                @Override
                                public void onError(Exception e) {
                                    e.printStackTrace();
                                }
                            });
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setLibraryMarkers(final GoogleMap map) {
        for (MarkerEnum currEnum : sMarkerEnums) {
            double lat = Double.valueOf(this.mContext.getString(currEnum.lat));
            double lng = Double.valueOf(this.mContext.getString(currEnum.lng));

            String title = this.mContext.getString(currEnum.title);
            String snippet = this.mContext.getString(currEnum.snippet);
            LatLng latLng = new LatLng(lat, lng);
            if (currEnum.type != MarkerTypeEnum.LIBRARY) continue;
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(currEnum.icon);

            this.mStaticMarkers.put(new Facility(MarkerTypeEnum.LIBRARY),
                    map.addMarker(new MarkerOptions()
                            .title(title)
                            .snippet(snippet)
                            .position(latLng)
                            .icon(bitmap)));
        }
    }

    private void onClickStaticInfoWindow(Marker marker) {
        Set<Map.Entry<Facility, Marker>> set = mStaticMarkers.entrySet();
        for (Map.Entry entry : set) {
            Facility facility = (Facility) entry.getKey();

            if (!((Marker) entry.getValue()).getTitle().equals(marker.getTitle())) continue;
            if (facility.isType(MarkerTypeEnum.DININGHALL)) {
                this.mCallback.setFragment(DiningHallViewPagerFragment.newInstance(this.mContext,
                        marker.getTitle().replace(this.mContext.getString(R.string.detail_map_dining_ending), "")));
            }
            else if (facility.isType(MarkerTypeEnum.LIBRARY)) {
                this.mCallback.setFragment(MapFacilityViewFragment.newInstance(this.mContext,
                        marker.getTitle()));
            }
        }
    }
}