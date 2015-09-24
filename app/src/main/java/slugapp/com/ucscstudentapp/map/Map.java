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

        // if started from "find on map" button on dining hall
        Bundle b = getArguments();
        if (b != null && b.containsKey("name")) {
            for (int i = 0; i < MarkerEnum.DiningHall.values().length; i++) {

                // if correct dining hall
                if (b.getString("name").equals(MarkerEnum.DiningHall.values()[i].getName())) {
                    for (int j = 0; j < diningHallList.size(); j++) {

                        // if found marker from marker list
                        if (diningHallList.get(j).getTitle().replace(" Dining Hall", "")
                                .equals(b.getString("name"))) {
                            mapEditor.moveTo(new LatLng(MarkerEnum.DiningHall.values()[i].getLat(),
                                    MarkerEnum.DiningHall.values()[i].getLng()), diningHallList.get(j));
                        }
                    }
                    break;
                }
            }
        }
    }
}