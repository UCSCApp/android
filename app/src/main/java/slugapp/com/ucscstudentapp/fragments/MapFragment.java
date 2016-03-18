package slugapp.com.ucscstudentapp.fragments;

//
//  MapFragment.java
//  SlugRoute
//
//  Created by Karol Josef Bustamante. <karoljosefb@gmail.com>
//  Copyright (c) 2015 UCSC Android. All rights reserved.
//

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.interfaces.ActivityCallback;
import slugapp.com.ucscstudentapp.runnables.LoopRunnable;
import slugapp.com.ucscstudentapp.enums.MarkerEnum;
import slugapp.com.ucscstudentapp.enums.MarkerTypeEnum;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private final static MarkerEnum[] markerEnums = MarkerEnum.values();

    private List<Marker> staticMarkers;
    private List<Marker> dynamicMarkers;
    protected ActivityCallback ac;
    protected String title;
    protected int buttonId;
    private boolean init = true;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.ac = (ActivityCallback) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);
        this.setLayout("Map", R.id.map_button);
        this.setView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.ac.setTitle(title);
        this.ac.setButtons(buttonId);
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
        this.staticMarkers = new ArrayList<>();
        this.dynamicMarkers = new ArrayList<>();
    }

    protected void setLayout(String title, int id) {
        this.title = title;
        this.buttonId = id;
    }

    public void setStaticMarkers(GoogleMap map) {
        for (MarkerEnum currEnum : this.markerEnums) {
            float lat = Float.valueOf(this.ac.toStr(currEnum.getLat()));
            float lng = Float.valueOf(this.ac.toStr(currEnum.getLng()));

            String title = this.ac.toStr(currEnum.getTitle());
            String snippet = this.ac.toStr(currEnum.getSnippet());
            LatLng latLng = new LatLng(lat, lng);
            BitmapDescriptor bitmap = this.ac.toBitMap(currEnum.getIcon());

            this.staticMarkers.add(map.addMarker(new MarkerOptions()
                    .title(title)
                    .snippet(snippet)
                    .position(latLng)
                    .icon(bitmap)));
        }
    }

    private void setDynamicMarkers(GoogleMap map) {
        this.dynamicMarkers = new ArrayList<>();

        final Handler handler = new Handler();
        final LoopRunnable runnable = new LoopRunnable(map, dynamicMarkers);

        this.ac.initTimer();
        this.ac.getTimer().scheduleAtFixedRate(new TimerTask() {
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
                for (Marker marker : staticMarkers) {
                    marker.setVisible(cameraPosition.zoom > 14.9);
                }
            }
        });
    }

    private void ifFromFindOnMap(GoogleMap map) {
        Bundle b = getArguments();

        if (b != null && b.containsKey("name")) {
            String name = b.getString("name");
            for (MarkerEnum currEnum : this.markerEnums) {
                if (this.foundMarker(name, currEnum, MarkerTypeEnum.DININGHALL)) {
                    for (Marker marker : this.staticMarkers) {
                        String title = marker.getTitle();
                        if (title.contains(name)) {
                            Log.e("Test", "Here");
                            float lat = Float.valueOf(this.ac.toStr(currEnum.getLat()));
                            float lng = Float.valueOf(this.ac.toStr(currEnum.getLng()));
                            LatLng latLng = new LatLng(lat, lng);
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.5f));
                            marker.showInfoWindow();
                        }
                    }
                    break;
                }
            }
        } else if (init) this.initialZoom(map);
        this.init = false;
    }

    private boolean foundMarker(String name, MarkerEnum currEnum, MarkerTypeEnum type) {
        return currEnum.getType() == type && this.ac.toStr(currEnum.getTitle()).contains(name);
    }

    private void initialZoom(GoogleMap map) {
        float lat = Float.valueOf(this.ac.toStr(R.string.map_init_lat));
        float lng = Float.valueOf(this.ac.toStr(R.string.map_init_lng));
        float zoom = Float.valueOf(this.ac.toStr(R.string.map_init_zoom));
        LatLng initLatLng = new LatLng(lat, lng);

        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initLatLng, zoom));
        map.setMyLocationEnabled(true);
    }

    private void removeDynamicMarkers() {
        for (Marker marker : this.dynamicMarkers) if (marker != null) marker.remove();
        if (this.ac.getTimer() != null) this.ac.getTimer().cancel();
    }
}