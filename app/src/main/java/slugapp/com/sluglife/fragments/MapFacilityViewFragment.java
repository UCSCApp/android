package slugapp.com.sluglife.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.adapters.FacilityListAdapter;
import slugapp.com.sluglife.databinding.ListMapFacilityBinding;
import slugapp.com.sluglife.databinding.ViewMapFacilityBinding;

/**
 * Created by isayyuhh on 7/11/16.
 */
public class MapFacilityViewFragment extends BaseViewFragment {
    private ViewMapFacilityBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mBinding = DataBindingUtil.inflate(this.getActivity().getLayoutInflater(),
                R.layout.view_map_facility, container, false);

        this.setViewFragment();

        return this.mBinding.getRoot();
    }

    @Override
    protected void setView() {
        this.mBinding.text.setText(this.mName);
    }

    @Override
    protected void setArgumentFields(Bundle b) {
        this.mName = b.getString(this.mContext.getString(R.string.bundle_name));
    }

    @Override
    protected void setFields() {

    }
}
