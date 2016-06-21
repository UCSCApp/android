package slugapp.com.sluglife.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import slugapp.com.sluglife.adapters.EventListAdapter;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.interfaces.ActivityCallback;
import slugapp.com.sluglife.models.Date;

/**
 * Created by isayyuhh on 2/21/16
 */
public abstract class BaseFragment extends Fragment {
    private String mTitle;
    private int mButtonId;

    protected ActivityCallback mCallback;
    protected Context mContext;
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
}
