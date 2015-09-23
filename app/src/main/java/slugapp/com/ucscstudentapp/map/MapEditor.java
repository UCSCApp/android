package slugapp.com.ucscstudentapp.map;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.dining.DiningHallDetail;
import slugapp.com.ucscstudentapp.main.ActivityReference;

/**
 * Created by isayyuhh_s on 8/18/2015.
 *
 * Edits the map
 */
public class MapEditor {
    private GoogleMap map;
    private ActivityReference mCallBack;
    private Marker currentMarker = null;
    private List<Marker> diningHallList;
    private List<Marker> libraryList;

    public MapEditor (ActivityReference callback) {
        this.mCallBack = callback;
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

    public void moveTo (LatLng latLng) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.5f));
    }

    /**
     * Sets Defaults Markers
     */
    public void setMarkers() {
        libraryList = new ArrayList<>();
        diningHallList = new ArrayList<>();

        // Libraries
        libraryList.add(map.addMarker(new MarkerOptions()
                .title("McHenry Library")
                .snippet("tap here to see more")
                .position(new LatLng(36.99578157522153, -122.058908423001))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.library))));
        libraryList.add(map.addMarker(new MarkerOptions()
                .title("S&E Library")
                .snippet("tap here to see more")
                .position(new LatLng(36.99904411574191, -122.06070818525006))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.library))));

        // Dining Halls
        diningHallList.add(map.addMarker(new MarkerOptions()
                .title("College Eight / Oakes Dining Hall")
                .snippet("tap here to see more")
                .position(new LatLng(36.991565, -122.065267))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dining_hall))));
        diningHallList.add(map.addMarker(new MarkerOptions()
                .title("Porter / Kresge Dining Hall")
                .snippet("tap here to see more")
                .position(new LatLng(36.994344, -122.065800))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dining_hall))));
        diningHallList.add(map.addMarker(new MarkerOptions()
                .title("College Nine / College Ten Dining Hall")
                .snippet("tap here to see more")
                .position(new LatLng(37.001096, -122.058031))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dining_hall))));
        diningHallList.add(map.addMarker(new MarkerOptions()
                .title("Crown / Merrill Dining Hall")
                .snippet("tap here to see more")
                .position(new LatLng(36.999971, -122.054448))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dining_hall))));
        diningHallList.add(map.addMarker(new MarkerOptions()
                .title("Cowell / Stevenson Dining Hall")
                .snippet("tap here to see more")
                .position(new LatLng(36.997157, -122.053150))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dining_hall))));
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

        // OnCameraChangeListener
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Marker marker;
                for (int i = 0; i < libraryList.size(); i++) {
                    marker = libraryList.get(i);
                    marker.setVisible(cameraPosition.zoom > 15);
                }
                for (int i = 0; i < diningHallList.size(); i++) {
                    marker = diningHallList.get(i);
                    marker.setVisible(cameraPosition.zoom > 15);
                }
            }
        });
    }

    public void hideInfoWindows () {
        if (currentMarker != null) currentMarker.hideInfoWindow();
    }
}
