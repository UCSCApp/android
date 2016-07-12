package slugapp.com.sluglife.fragments;

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
 * Created by isaiah on 2/21/16
 * <p/>
 * This file contains a base fragment class.
 */
public abstract class BaseFragment extends Fragment {
    protected ActivityCallback mCallback;
    protected Context mContext;

    private String mTitle;

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

        this.setArgumentFields(this.getArguments());
    }

    /**
     * Fragment's onStart method
     */
    @Override
    public void onStart() {
        super.onStart();

        this.mCallback.setToolbarTitle(this.mTitle);
    }

    /**
     * Fragment's onDestroy method
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        this.mCallback.setUpEnabled(false);
    }

    /**
     * Sets fragment
     *
     * @param title Name of fragment
     */
    protected void setFragment(int title) {
        this.setFields();
        this.setLayout(title);
    }

    /**
     * Sets fragment
     *
     * @param title Name of fragment
     */
    protected void setFragment(String title) {
        this.setFields();
        this.setLayout(title);
    }

    /**
     * Sets fragment layout
     *
     * @param title Name of fragment
     */
    protected void setLayout(int title) {
        this.mTitle = this.mContext.getString(title);
    }

    /**
     * Sets fragment layout
     *
     * @param title Name of fragment
     */
    protected void setLayout(String title) {
        this.mTitle = title;
    }

    /**
     * Sets child fragment given a resource id and fragment
     *
     * @param containerId Resource id of container
     * @param fragment    Fragment to set
     */
    protected void setChildFragment(int containerId, Fragment fragment) {
        FragmentManager fragmentManager = this.getChildFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(containerId, fragment);
        ft.commit();
    }

    /**
     * Sets dialog fragment
     *
     * @param fragment Dialog fragment to set
     */
    protected void setDialogFragment(DialogFragment fragment) {
        FragmentTransaction ft = this.getFragmentManager().beginTransaction();
        Fragment prev = this.getFragmentManager().findFragmentByTag(this.mContext.getString(R.string.bundle_dialog));

        if (prev != null) ft.remove(prev);
        ft.addToBackStack(null);

        fragment.setTargetFragment(this, 0);
        fragment.show(ft, this.mContext.getString(R.string.bundle_dialog));
    }

    /**
     * Get integer from shared preferences
     *
     * @param key          Shared preferences key
     * @param defaultValue Default value if key not found
     * @return Integer from shared preferences
     */
    protected int getSharedPrefInt(String key, int defaultValue) {
        SharedPreferences sharedPref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getInt(key, defaultValue);
    }

    /**
     * Put integer into shared preferences
     *
     * @param key   Shared preferences key
     * @param value Value of integer to put into shared preferences
     */
    protected void putSharedPrefInt(String key, int value) {
        SharedPreferences sharedPref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Allows user to set fields from fragment arguments
     *
     * @param b Bundle from fragment arguments
     */
    protected abstract void setArgumentFields(Bundle b);

    /**
     * Allows user to set fields
     */
    protected abstract void setFields();
}
