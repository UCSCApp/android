package slugapp.com.sluglife.fragments;

import android.view.View;
import android.view.ViewGroup;

import slugapp.com.sluglife.enums.FragmentEnum;

/**
 * Created by isayyuhh on 2/21/16.
 */
public abstract class BaseViewFragment extends BaseFragment {
    protected String mName;

    protected void setViewFragment(View view, ViewGroup container, FragmentEnum fragmentEnum) {
        this.setFragment(view, container, fragmentEnum);
        this.setView(view);
    }

    protected void setViewFragment(View view, ViewGroup container, FragmentEnum fragmentEnum,
                                   String name) {
        this.setFragment(view, container, fragmentEnum, name);
        this.setView(view);
    }

    protected abstract void setView(View view);
}
