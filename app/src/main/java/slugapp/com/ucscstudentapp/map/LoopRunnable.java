package slugapp.com.ucscstudentapp.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.http.HttpCallback;
import slugapp.com.ucscstudentapp.http.LoopHttpRequest;

/**
 * Created by isayyuhh on 2/19/16.
 */
public class LoopRunnable implements Runnable {
    private GoogleMap map;
    private List<Marker> loops;

    public LoopRunnable(GoogleMap map, List<Marker> loops) {
        this.map = map;
        this.loops = loops;
    }

    @Override
    public void run() {
        new LoopHttpRequest().execute(new HttpCallback<List<Loop>>() {
            @Override
            public void onSuccess(List<Loop> val) {
                for (Loop loop: val) {
                    boolean found = false;
                    for (Marker marker: loops) {
                        if (String.valueOf(loop.getId()).compareTo(marker.getSnippet()) == 0) {
                            marker.setPosition(new LatLng(loop.getLat(), loop.getLng()));
                            found = true;
                            break;
                        }
                    }
                    if (found == false) {
                        loops.add(map.addMarker(new MarkerOptions()
                                .title("Loop")
                                .snippet(String.valueOf(loop.getId()))
                                .position(new LatLng(loop.getLat(), loop.getLng()))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.loop_bus))));
                    }
                }
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }
}
