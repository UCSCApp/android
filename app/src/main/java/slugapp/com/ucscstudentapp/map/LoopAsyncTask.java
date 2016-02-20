package slugapp.com.ucscstudentapp.map;

import android.os.AsyncTask;
import android.os.Handler;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Downloader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.http.Callback;
import slugapp.com.ucscstudentapp.http.LoopHttpRequest;

/**
 * Created by isayyuhh on 2/19/16.
 */
public class LoopAsyncTask extends AsyncTask<Void, Integer, Long> {
    private GoogleMap map;
    private List<Marker> loops;

    public LoopAsyncTask(GoogleMap map) {
        this.map = map;
        this.loops = new ArrayList<>();
    }

    @Override
    protected Long doInBackground(Void... params) {
        while (!isCancelled()) {
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
        return null;
    }
}

