package slugapp.com.sluglife.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.interfaces.ActivityCallback;

/**
 * Created by isayyuhh on 6/26/16
 */
public abstract class BaseMapFragment extends SupportMapFragment implements OnMapReadyCallback {
    protected ActivityCallback mCallback;
    protected Context mContext;

    private String mTitle;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.mCallback = (ActivityCallback) context;
        this.mContext = context;
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
        this.setLayout(fragmentEnum.name);
    }

    protected void setLayout(int title) {
        this.mTitle = this.mContext.getString(title);
    }

    protected int getSharedPrefInt(String key, int defaultValue) {
        SharedPreferences sharedPref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getInt(key, defaultValue);
    }

    protected abstract void setFields(View view);

    protected abstract void setMarkers(GoogleMap googleMap);

    protected abstract void setMapListeners(GoogleMap googleMap);

    protected abstract void setInitialZoom(GoogleMap googleMap);

    protected abstract void clearData();
}
