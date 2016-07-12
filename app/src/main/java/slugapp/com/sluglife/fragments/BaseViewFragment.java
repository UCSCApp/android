package slugapp.com.sluglife.fragments;

import slugapp.com.sluglife.enums.FragmentEnum;

/**
 * Created by isaiah on 2/21/16
 * <p/>
 * This file contains a base view fragment class.
 */
public abstract class BaseViewFragment extends BaseFragment {
    protected String mName;

    /**
     * Sets view fragment
     *
     * @param fragmentEnum Fragment enum containing fragment information
     */
    protected void setViewFragment(FragmentEnum fragmentEnum) {
        this.setFragment(fragmentEnum.name);
        this.setView();
    }

    /**
     * Sets view fragment
     */
    protected void setViewFragment() {
        this.setFragment(this.mName);
        this.setView();
    }

    /**
     * Allows user to set view
     */
    protected abstract void setView();
}
