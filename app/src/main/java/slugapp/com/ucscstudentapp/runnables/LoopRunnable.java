package slugapp.com.ucscstudentapp.runnables;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.interfaces.HttpCallback;
import slugapp.com.ucscstudentapp.http.LoopHttpRequest;
import slugapp.com.ucscstudentapp.models.Loop;

/**
 * Created by isayyuhh on 2/19/16
 */
public class LoopRunnable implements Runnable {
    private Context mContext;
    private GoogleMap mMap;
    private List<Marker> mLoopList;

    public LoopRunnable(Context context, GoogleMap map, List<Marker> loopList) {
        this.mContext = context;
        this.mMap = map;
        this.mLoopList = loopList;
    }

    @Override
    public void run() {
        new LoopHttpRequest(mContext).execute(new HttpCallback<List<Loop>>() {
            @Override
            public void onSuccess(List<Loop> val) {
                for (Loop loop : val) {
                    boolean found = false;
                    for (Marker marker : mLoopList) {
                        if (String.valueOf(loop.getId()).compareTo(marker.getSnippet()) == 0) {
                            marker.setPosition(new LatLng(loop.getLat(), loop.getLng()));
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        mLoopList.add(mMap.addMarker(new MarkerOptions()
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
