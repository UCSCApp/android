package slugapp.com.sluglife.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.databinding.ViewMapFacilityBinding;

/**
 * Created by isaiah on 7/11/16
 * <p/>
 * This file contains a view fragment that displays facility information.
 */
public class MapFacilityViewFragment extends BaseViewFragment {
    private ViewMapFacilityBinding mBinding;

    /**
     * Gets a new instance of fragment
     *
     * @param context Activity context
     * @param name    Fragment name
     * @return New instance of fragment
     */
    public static MapFacilityViewFragment newInstance(Context context, String name) {
        MapFacilityViewFragment fragment = new MapFacilityViewFragment();

        Bundle b = new Bundle();
        b.putString(context.getString(R.string.bundle_name), name);
        fragment.setArguments(b);

        return fragment;
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
        this.mBinding = DataBindingUtil.inflate(this.getActivity().getLayoutInflater(),
                R.layout.view_map_facility, container, false);

        this.setViewFragment();

        return this.mBinding.getRoot();
    }

    /**
     * Sets fields from fragment arguments
     *
     * @param b Bundle from fragment arguments
     */
    @Override
    protected void setArgumentFields(Bundle b) {
        this.mName = b.getString(this.mContext.getString(R.string.bundle_name));
    }

    /**
     * Sets fields
     */
    @Override
    protected void setFields() {
    }

    /**
     * Sets fragment view
     */
    @Override
    protected void setView() {
        this.mBinding.text.setText(this.mName);
    }
}
