package slugapp.com.sluglife.fragments;

//
//  MapFragment.java
//  SlugRoute
//
//  Created by Karol Josef Bustamante. <karoljosefb@gmail.com>
//  Copyright (c) 2015 UCSC Android. All rights reserved.
//

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
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
import slugapp.com.sluglife.http.DiningHallHttpRequest;
import slugapp.com.sluglife.http.DiningListHttpRequest;
import slugapp.com.sluglife.interfaces.ActivityCallback;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.DiningHall;
import slugapp.com.sluglife.models.Facility;
import slugapp.com.sluglife.models.Loop;
import slugapp.com.sluglife.runnables.LoopRunnable;
import slugapp.com.sluglife.enums.MarkerEnum;
import slugapp.com.sluglife.enums.MarkerTypeEnum;

public class MapFragment extends BaseMapFragment {
    private static final FragmentEnum fragmentEnum = FragmentEnum.MAP;

    private static final MarkerEnum[] sMarkerEnums = MarkerEnum.values();
    private static final float DEFAULT_ZOOM = 15.0f;

    private HashMap<Facility, Marker> mStaticMarkers;
    private HashMap<Loop, Marker> mDynamicMarkers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);

        this.setMapFragment(view, fragmentEnum);

        return view;
    }

    @Override
    protected void setFields(View view) {
        this.mStaticMarkers = new HashMap<>();
        this.mDynamicMarkers = new HashMap<>();
    }

    @Override
    protected void setMarkers(GoogleMap googleMap) {
        this.setDynamicMarkers(googleMap);
        this.setStaticMarkers(googleMap);
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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
    }

    @Override
    protected void clearData() {
        for (Marker marker : this.mDynamicMarkers.values()) if (marker != null) marker.remove();
        if (this.mCallback.getTimer() != null) this.mCallback.getTimer().cancel();
    }

    private void setDynamicMarkers(GoogleMap map) {
        this.mDynamicMarkers = new HashMap<>();

        final Handler handler = new Handler();
        final LoopRunnable runnable = new LoopRunnable(mContext, map, this.mDynamicMarkers);

        this.mCallback.initTimer();
        this.mCallback.getTimer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 0, 2000);
    }

    public void setStaticMarkers(final GoogleMap map) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int bin = sharedPref.getInt(this.mContext.getString(R.string.bundle_markers), 0b00);

        if ((bin & 0b01) != 0b00) this.setDiningHallMarkers(map);
        if ((bin & 0b10) != 0b00) this.setLibraryMarkers(map);
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
                                                    R.string.dining_nameaddon))
                                            .snippet("Tap to view dining menu")
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
            double lat = Double.valueOf(this.mContext.getString(currEnum.getLat()));
            double lng = Double.valueOf(this.mContext.getString(currEnum.getLng()));

            String title = this.mContext.getString(currEnum.getTitle());
            String snippet = this.mContext.getString(currEnum.getSnippet());
            LatLng latLng = new LatLng(lat, lng);
            if (currEnum.getType() != MarkerTypeEnum.LIBRARY) continue;
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(currEnum.getIcon());

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
                DiningHallViewPagerFragment fragment = new DiningHallViewPagerFragment();

                Bundle b = new Bundle();
                b.putString(mContext.getString(R.string.bundle_name), marker.getTitle().replace(mContext.getString(R.string.dining_nameaddon), ""));

                fragment.setArguments(b);
                this.mCallback.setFragment(fragment);
            }
        }
    }
}