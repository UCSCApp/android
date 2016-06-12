package slugapp.com.sluglife.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import slugapp.com.sluglife.interfaces.ActivityCallback;
import slugapp.com.sluglife.models.Date;

/**
 * Created by isayyuhh on 2/21/16
 */
public abstract class BaseFragment extends Fragment {
    protected ActivityCallback mCallback;
    protected Context mContext;
    protected String mTitle;
    protected int mButtonId;
    protected Date mToday;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mCallback = (ActivityCallback) activity;
        this.mContext = activity;
        this.mToday = this.mCallback.getToday();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mCallback.setTitle(mTitle);
        this.mCallback.setButtons(mButtonId);
    }

    protected void setLayout(String title, int buttonId) {
        this.mTitle = title;
        this.mButtonId = buttonId;
    }
}
