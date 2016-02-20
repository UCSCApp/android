package slugapp.com.ucscstudentapp.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.http.Callback;
import slugapp.com.ucscstudentapp.http.LoopHttpRequest;

/**
 * Created by isayyuhh on 2/19/16.
 */
public class LoopRunnable implements Runnable {
    private GoogleMap map;
    private List<Marker> loops;

    public LoopRunnable(GoogleMap map) {
        this.map = map;
        this.loops = new ArrayList<>();
    }

    /*
    @Override
    public Void call() throws Exception {
        while (true) {
            System.gc();
            System.runFinalization();
            System.gc();
            new LoopHttpRequest().execute(new Callback<List<Loop>>() {
                @Override
                public void onSuccess(List<Loop> val) {
                    if (loops.size() == 0) {
                        for (Loop loop : val) {
                            loops.add(map.addMarker(new MarkerOptions()
                                    .title("Loop")
                                    .snippet(String.valueOf(loop.getId()))
                                    .position(new LatLng(loop.getLat(), loop.getLng()))
                                    .icon(BitmapDescriptorFactory.fromResource(
                                            R.drawable.loop_bus))));
                        }
                        return;
                    }
                    for (Marker marker : loops) {
                        Loop found = null;
                        for (Loop loop : val) {
                            if (String.valueOf(loop.getId()).compareTo(
                                    marker.getSnippet()) == 0) {
                                found = loop;
                                break;
                            }
                        }
                        if (found == null) return;
                        marker.setPosition(
                                new LatLng(found.getLat(), found.getLng()));
                    }
                }

                @Override
                public void onError(Exception e) {
                }
            });
        }
    }
    */

    @Override
    public void run() {
        new LoopHttpRequest().execute(new Callback<List<Loop>>() {
            @Override
            public void onSuccess(List<Loop> val) {
                if (loops.size() == 0) {
                    for (Loop loop : val) {
                        loops.add(map.addMarker(new MarkerOptions()
                                .title("Loop")
                                .snippet(String.valueOf(loop.getId()))
                                .position(new LatLng(loop.getLat(), loop.getLng()))
                                .icon(BitmapDescriptorFactory.fromResource(
                                        R.drawable.loop_bus))));
                    }
                    return;
                }
                for (Marker marker : loops) {
                    Loop found = null;
                    for (Loop loop : val) {
                        if (String.valueOf(loop.getId()).compareTo(
                                marker.getSnippet()) == 0) {
                            found = loop;
                            break;
                        }
                    }
                    if (found == null) return;
                    marker.setPosition(
                            new LatLng(found.getLat(), found.getLng()));
                }
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }
}
