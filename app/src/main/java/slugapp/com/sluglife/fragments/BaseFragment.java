package slugapp.com.sluglife.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.ActivityCallback;

/**
 * Created by isayyuhh on 2/21/16
 */
public abstract class BaseFragment extends Fragment {
    protected ActivityCallback mCallback;
    protected Context mContext;

    private String mTitle;

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

        this.setArgumentFields(this.getArguments());
    }

    @Override
    public void onStart() {
        super.onStart();

        this.mCallback.setTitle(this.mTitle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        
        this.mCallback.setUpEnabled(false);
    }

    protected void setFragment(String name) {
        this.setFields();
        this.setLayout(name);
    }

    protected void setLayout(String title) {
        this.mTitle = title;
    }

    protected void setChildFragment(int containerId, Fragment fragment) {
        FragmentManager fragmentManager = this.getChildFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(containerId, fragment);
        ft.commit();
    }

    protected void setDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = this.getFragmentManager().beginTransaction();
        Fragment prev = this.getFragmentManager().findFragmentByTag(this.mContext.getString(R.string.bundle_dialog));

        if (prev != null) ft.remove(prev);
        ft.addToBackStack(null);

        dialogFragment.setTargetFragment(this, 0);
        dialogFragment.show(ft, this.mContext.getString(R.string.bundle_dialog));
    }

    protected int getSharedPrefInt(String key, int defaultValue) {
        SharedPreferences sharedPref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getInt(key, defaultValue);
    }

    protected void putSharedPrefInt(String key, int value) {
        SharedPreferences sharedPref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    protected abstract void setArgumentFields(Bundle b);

    protected abstract void setFields();
}
