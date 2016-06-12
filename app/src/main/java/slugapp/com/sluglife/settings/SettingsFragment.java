package slugapp.com.sluglife.settings;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.machinarius.preferencefragment.PreferenceFragment;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.ActivityCallback;

/**
 * Created by isayyuhh_s on 7/26/2015.
 */
public class SettingsFragment extends PreferenceFragment {
    private ActivityCallback mCallback;
    private Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (ActivityCallback) activity;
        context = activity;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_settings, container, false);
        //TextView tv = (TextView) view.findViewById(R.id.googleMaps_license);
        //tv.setText(GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(context));
        mCallback.setTitle("SettingsFragment");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //mCallback.setButtons(R.id.settings_button);
    }
}
