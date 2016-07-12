package slugapp.com.sluglife.settings;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.ActivityCallback;

/**
 * Created by isayyuhh_s on 7/26/2015.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    private ActivityCallback mCallback;
    private Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mCallback = (ActivityCallback) activity;
        this.mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_settings, container, false);
        this.mCallback.setToolbarTitle("SettingsFragment");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
