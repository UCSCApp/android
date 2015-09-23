package slugapp.com.ucscstudentapp.settings;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.machinarius.preferencefragment.PreferenceFragment;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.main.ActivityReference;

/**
 * Created by isayyuhh_s on 7/26/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Settings extends PreferenceFragment {
    private ActivityReference mCallback;
    private Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (ActivityReference) activity;
        context = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_settings, container, false);
        //TextView tv = (TextView) view.findViewById(R.id.googleMaps_license);
        //tv.setText(GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(context));
        mCallback.setTitle("Settings");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //mCallback.setButtons(R.id.settings_button);
    }
}
