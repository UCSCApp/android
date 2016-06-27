package slugapp.com.sluglife.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.interfaces.ActivityCallback;

/**
 * Created by isayyuhh on 6/26/16.
 */
public abstract class BaseMapFragment extends SupportMapFragment implements OnMapReadyCallback {
    protected ActivityCallback mCallback;
    protected Context mContext;
    private String mTitle;
    private int mButtonId;

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
        this.clearData();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.setMarkers(googleMap);
        this.setMapListeners(googleMap);
        this.setInitialZoom(googleMap);
    }

    protected void setMapFragment(View view, FragmentEnum fragmentEnum) {
        this.setFields(view);
        this.setLayout(fragmentEnum.getName(), fragmentEnum.getButtonId());
    }

    protected void setLayout(String title, int buttonId) {
        this.mTitle = title;
        this.mButtonId = buttonId;
    }

    protected abstract void setFields(View view);

    protected abstract void setMarkers(GoogleMap googleMap);

    protected abstract void setMapListeners(GoogleMap googleMap);

    protected abstract void setInitialZoom(GoogleMap googleMap);

    protected abstract void clearData();
}
