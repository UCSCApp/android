package slugapp.com.ucscstudentapp.map;

//
//  Map.java
//  SlugRoute
//
//  Created by Karol Josef Bustamante. <karoljosefb@gmail.com>
//  Copyright (c) 2015 UCSC Android. All rights reserved.
//

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.main.ActivityCallback;

public class Map extends SupportMapFragment {
    private MapEditor mapEditor;
    private ActivityCallback mCallBack;
    private List<Marker> diningHallList;
    private List<Marker> libraryList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallBack = (ActivityCallback) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        // Map editor
        mapEditor = new MapEditor(mCallBack);
        libraryList = new ArrayList<>();
        diningHallList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);
        mCallBack.setTitle("Map");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mCallBack.setButtons(R.id.map_button);

        // Set map
        mapEditor.initializeMap(getMap());
        mapEditor.setMarkers(diningHallList, libraryList);
        mapEditor.setListeners(diningHallList, libraryList);

        Bundle b = getArguments();
        if (b != null && b.containsKey("name")) {
            switch (b.getString("name")) {
                case "College Eight / Oakes":
                    mapEditor.moveTo(new LatLng(36.991565, -122.065267));
                    break;
                case "Porter / Kresge":
                    mapEditor.moveTo(new LatLng(36.994344, -122.065800));
                    break;
                case "College Nine / College Ten":
                    mapEditor.moveTo(new LatLng(37.001096, -122.058031));
                    break;
                case "Crown / Merrill":
                    mapEditor.moveTo(new LatLng(36.999971, -122.054448));
                    break;
                case "Cowell / Stevenson":
                    mapEditor.moveTo(new LatLng(36.997157, -122.053150));
                    break;
            }
        }
    }
}