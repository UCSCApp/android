package slugapp.com.sluglife.fragments;

//
//  MapFragment.java
//  SlugRoute
//
//  Created by Karol Josef Bustamante. <karoljosefb@gmail.com>
//  Copyright (c) 2015 UCSC Android. All rights reserved.
//

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.interfaces.ActivityCallback;
import slugapp.com.sluglife.runnables.LoopRunnable;
import slugapp.com.sluglife.enums.MarkerEnum;
import slugapp.com.sluglife.enums.MarkerTypeEnum;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private final static MarkerEnum[] sMarkerEnums = MarkerEnum.values();
    private final static float DEFAULT_ZOOM = 15.5f;
    private final static float MAX_VISIBLE_ZOOM = 14.9f;

    private List<Marker> mStaticMarkers;
    private List<Marker> mDynamicMarkers;
    private ActivityCallback mCallback;
    private Context mContext;
    private String mTitle;
    private int mButtonId;
    private FragmentEnum fragmentEnum = FragmentEnum.MAP;

    private boolean init = true;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mCallback = (ActivityCallback) activity;
        this.mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);

        this.setLayout(this.fragmentEnum.getName(), this.fragmentEnum.getButtonId());
        this.setView();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        this.mCallback.setTitle(this.mTitle);
        this.mCallback.setButtons(this.mButtonId);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.getMapAsync(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        this.removeDynamicMarkers();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.setStaticMarkers(map);
        this.setDynamicMarkers(map);
        this.setMapListeners(map);
        this.ifFromFindOnMap(map);
    }

    protected void setView() {
        this.mStaticMarkers = new ArrayList<>();
        this.mDynamicMarkers = new ArrayList<>();
    }

    protected void setLayout(String title, int buttonId) {
        this.mTitle = title;
        this.mButtonId = buttonId;
    }

    public void setStaticMarkers(GoogleMap map) {
        for (MarkerEnum currEnum : sMarkerEnums) {
            float lat = Float.valueOf(this.mCallback.toStr(currEnum.getLat()));
            float lng = Float.valueOf(this.mCallback.toStr(currEnum.getLng()));

            String title = this.mCallback.toStr(currEnum.getTitle());
            String snippet = this.mCallback.toStr(currEnum.getSnippet());
            LatLng latLng = new LatLng(lat, lng);
            BitmapDescriptor bitmap = this.mCallback.toBitMap(currEnum.getIcon());

            this.mStaticMarkers.add(map.addMarker(new MarkerOptions()
                    .title(title)
                    .snippet(snippet)
                    .position(latLng)
                    .icon(bitmap)));
        }
    }

    private void setDynamicMarkers(GoogleMap map) {
        this.mDynamicMarkers = new ArrayList<>();

        final Handler handler = new Handler();
        final LoopRunnable runnable = new LoopRunnable(getActivity(), map, this.mDynamicMarkers);

        this.mCallback.initTimer();
        this.mCallback.getTimer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 0, 2000);
    }

    public void setMapListeners(final GoogleMap map) {
        // OnMarkerClickListener
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                return false;
            }
        });

        // OnCameraChangeListener
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                for (Marker marker : mStaticMarkers) {
                    marker.setVisible(cameraPosition.zoom > MAX_VISIBLE_ZOOM);
                }
            }
        });
    }

    private void ifFromFindOnMap(GoogleMap map) {
        Bundle b = this.getArguments();

        if (b != null && b.containsKey(this.mContext.getString(R.string.name))) {
            String name = b.getString(this.mContext.getString(R.string.name));
            for (MarkerEnum currEnum : sMarkerEnums) {
                if (this.foundMarker(name, currEnum, MarkerTypeEnum.DININGHALL)) {
                    for (Marker marker : this.mStaticMarkers) {
                        String title = marker.getTitle();
                        if (title.contains(name)) {
                            float lat = Float.valueOf(this.mCallback.toStr(currEnum.getLat()));
                            float lng = Float.valueOf(this.mCallback.toStr(currEnum.getLng()));
                            LatLng latLng = new LatLng(lat, lng);
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                            marker.showInfoWindow();
                        }
                    }
                    break;
                }
            }
        } else if (this.init) this.initialZoom(map);
        this.init = false;
    }

    private boolean foundMarker(String name, MarkerEnum currEnum, MarkerTypeEnum type) {
        return currEnum.getType() == type && this.mCallback.toStr(currEnum.getTitle()).contains(name);
    }

    private void initialZoom(GoogleMap map) {
        float lat = Float.valueOf(this.mCallback.toStr(R.string.map_init_lat));
        float lng = Float.valueOf(this.mCallback.toStr(R.string.map_init_lng));
        float zoom = Float.valueOf(this.mCallback.toStr(R.string.map_init_zoom));

        LatLng initLatLng = new LatLng(lat, lng);

        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initLatLng, zoom));
        map.setMyLocationEnabled(true);
    }

    private void removeDynamicMarkers() {
        for (Marker marker : this.mDynamicMarkers) if (marker != null) marker.remove();
        if (this.mCallback.getTimer() != null) this.mCallback.getTimer().cancel();
    }
}