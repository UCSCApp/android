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

import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.dining.DiningHallDetail;
import slugapp.com.ucscstudentapp.main.ActivityCallback;

/**
 * Created by isayyuhh_s on 8/18/2015.
 * <p/>
 * Edits the map
 */
public class MapEditor {
    private GoogleMap map;
    private ActivityCallback mCallBack;

    public MapEditor(ActivityCallback callback) {
        this.mCallBack = callback;
    }

    public void initializeMap(GoogleMap map) {
        if (this.map == null) {
            this.map = map;

            // Set initial state
            map.getUiSettings().setZoomControlsEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(36.991499, -122.058835), 14));
            map.setMyLocationEnabled(true);
        }
    }

    public void moveTo(LatLng latLng, Marker marker) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.5f));
        marker.showInfoWindow();
    }

    /**
     * Sets Defaults Markers
     */
    public void setMarkers(List<Marker> dhList, List<Marker> lList) {
        final List<Marker> diningHallList = dhList;
        final List<Marker> libraryList = lList;

        // Dining Halls
        for (int i = 0; i < MarkerEnum.DiningHall.values().length; i++) {
            diningHallList.add(map.addMarker(new MarkerOptions()
                    .title(MarkerEnum.DiningHall.values()[i].getName() + " Dining Hall")
                    .snippet("tap here to see more")
                    .position(new LatLng(MarkerEnum.DiningHall.values()[i].getLat(),
                            MarkerEnum.DiningHall.values()[i].getLng()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.dining_hall))));
        }

        // Libraries
        for (int i = 0; i < MarkerEnum.Library.values().length; i++) {
            libraryList.add(map.addMarker(new MarkerOptions()
                    .title(MarkerEnum.Library.values()[i].getName())
                    .position(new LatLng(MarkerEnum.Library.values()[i].getLat(),
                            MarkerEnum.Library.values()[i].getLng()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.library))));
        }
    }

    /**
     * Sets Marker Listeners
     */
    public void setListeners(List<Marker> dhList, List<Marker> lList) {
        final List<Marker> diningHallList = dhList;
        final List<Marker> libraryList = lList;

        // OnMarkerClickListener
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                return false;
            }
        });
        // OnInfoWindowClickListener
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                // if not library
                for (int i = 0; i < MarkerEnum.Library.values().length; i++) {
                    if (marker.getTitle().equals(MarkerEnum.Library.values()[i].getName())) return;
                }
                Bundle b = new Bundle();
                b.putString("name", marker.getTitle().replace(" Dining Hall", ""));
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
}
