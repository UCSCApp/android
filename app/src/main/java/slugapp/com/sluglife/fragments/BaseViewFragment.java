package slugapp.com.sluglife.fragments;

import android.view.View;

import slugapp.com.sluglife.enums.FragmentEnum;

/**
 * Created by isayyuhh on 2/21/16.
 */
public abstract class BaseViewFragment extends BaseFragment {
    protected String mName;

    protected void setDetailFragment(View view, FragmentEnum fragmentEnum) {
        this.setFragment(view, fragmentEnum);
        this.setView(view);
    }

    protected void setDetailFragment(View view, FragmentEnum fragmentEnum, String name) {
        this.setFragment(view, fragmentEnum, name);
        this.setView(view);
    }

    protected abstract void setView(View view);
}
