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

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private final static MarkerEnum[] sMarkerEnums = MarkerEnum.values();
    private final static float DEFAULT_ZOOM = 15.0f;
    private final static float MAX_VISIBLE_ZOOM = 14.9f;

    private HashMap<Facility, Marker> mStaticMarkers;
    private HashMap<Loop, Marker> mDynamicMarkers;
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
        this.setDynamicMarkers(map);
        this.setStaticMarkers(map);
        this.setMapListeners(map);
        this.setInitialZoom(map);
        //this.checkIfFromFindOnMap(map);
    }

    protected void setView() {
        this.mStaticMarkers = new HashMap<>();
        this.mDynamicMarkers = new HashMap<>();
    }

    protected void setLayout(String title, int buttonId) {
        this.mTitle = title;
        this.mButtonId = buttonId;
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
        this.setDiningHallMarkers(map);
        this.setLibraryMarkers(map);
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
                                            .title(val.getName() + mContext.getString(
                                                    R.string.dining_nameaddon))
                                            .snippet("Tap to view dining menu")
                                            .position(val.getLatLng())
                                            .icon(mCallback.toBitMap(DiningHall.diningImage))));
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
            double lat = Double.valueOf(this.mCallback.toStr(currEnum.getLat()));
            double lng = Double.valueOf(this.mCallback.toStr(currEnum.getLng()));

            String title = this.mCallback.toStr(currEnum.getTitle());
            String snippet = this.mCallback.toStr(currEnum.getSnippet());
            LatLng latLng = new LatLng(lat, lng);
            if (currEnum.getType() != MarkerTypeEnum.LIBRARY) continue;
            BitmapDescriptor bitmap = this.mCallback.toBitMap(currEnum.getIcon());

            this.mStaticMarkers.put(new Facility(MarkerTypeEnum.LIBRARY),
                    map.addMarker(new MarkerOptions()
                            .title(title)
                            .snippet(snippet)
                            .position(latLng)
                            .icon(bitmap)));
        }
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

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                goToStatic(marker);
            }
        });

        // OnCameraChangeListener
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                for (Marker marker : mStaticMarkers.values()) {
                    marker.setVisible(cameraPosition.zoom > MAX_VISIBLE_ZOOM);
                }
            }
        });
    }

    private void goToStatic(Marker marker) {
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

    /*
    private void checkIfFromFindOnMap(GoogleMap map) {
        Bundle b = this.getArguments();

        if (b != null && b.containsKey(this.mContext.getString(R.string.bundle_name))) {
            String name = b.getString(this.mContext.getString(R.string.bundle_name));
            for (MarkerEnum currEnum : sMarkerEnums) {
                if (this.foundMarker(name, currEnum, MarkerTypeEnum.DININGHALL)) {
                    for (Marker marker : this.mStaticMarkers.values()) {
                        String title = marker.getTitle();
                        if (name != null && title.contains(name)) {
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
        } else if (this.init) this.setInitialZoom(map);
        this.init = false;
    }

    private boolean foundMarker(String name, MarkerEnum currEnum, MarkerTypeEnum type) {
        return currEnum.getType() == type && this.mCallback.toStr(currEnum.getTitle()).contains(name);
    }
    */

    private void setInitialZoom(GoogleMap map) {
        float lat = Float.valueOf(this.mCallback.toStr(R.string.map_init_lat));
        float lng = Float.valueOf(this.mCallback.toStr(R.string.map_init_lng));
        float zoom = DEFAULT_ZOOM;

        LatLng initLatLng = new LatLng(lat, lng);

        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initLatLng, zoom));
        //map.setMyLocationEnabled(true);
    }

    private void removeDynamicMarkers() {
        for (Marker marker : this.mDynamicMarkers.values()) if (marker != null) marker.remove();
        if (this.mCallback.getTimer() != null) this.mCallback.getTimer().cancel();
    }
}