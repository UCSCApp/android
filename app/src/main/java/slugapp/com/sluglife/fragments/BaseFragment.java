package slugapp.com.sluglife.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.interfaces.ActivityCallback;

/**
 * Created by isayyuhh on 2/21/16
 */
public abstract class BaseFragment extends Fragment {
    protected ActivityCallback mCallback;
    protected Context mContext;

    private String mTitle;
    private int mButtonId;

    // TODO: create abstract bundle function
    // TODO: create SharedPreferences functions

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
        this.mCallback.setTitle(mTitle);
        this.mCallback.setButtons(mButtonId);
    }

    protected void setFragment(View view, FragmentEnum fragmentEnum) {
        this.setFields(view);
        this.setLayout(fragmentEnum.getName(), fragmentEnum.getButtonId());
    }

    protected void setFragment(View view, FragmentEnum fragmentEnum, String name) {
        this.setFields(view);
        this.setLayout(name, fragmentEnum.getButtonId());
    }

    protected abstract void setFields(View view);

    protected void setLayout(String title, int buttonId) {
        this.mTitle = title;
        this.mButtonId = buttonId;
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
}
