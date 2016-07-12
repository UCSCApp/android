package slugapp.com.sluglife.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.ActivityCallback;

// TODO: implement settings
// TODO: catch up to other fragments

/**
 * Created by isaiah on 7/26/2015
 * <p/>
 * This file contains a settings fragment.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    private ActivityCallback mCallback;
    private Context mContext;

    /**
     * Fragment's onAttach method
     *
     * @param context Activity context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mCallback = (ActivityCallback) context;
        this.mContext = context;
    }

    /**
     * Fragment's onCreate method
     *
     * @param savedInstanceState Saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        this.setRetainInstance(true);

        this.addPreferencesFromResource(R.xml.preferences);
    }

    /**
     * Fragment's onCreatePreferences method
     *
     * @param bundle Bundle containing preferences
     * @param s      String
     */
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
    }

    /**
     * Fragment's onCreateView method
     *
     * @param inflater           Layout inflater
     * @param container          Container of fragment
     * @param savedInstanceState Saved instance state
     * @return Inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_settings, container, false);
        this.mCallback.setToolbarTitle("SettingsFragment");
        return view;
    }

    /**
     * Fragment's onStart method
     */
    @Override
    public void onStart() {
        super.onStart();
    }
}
