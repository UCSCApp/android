package slugapp.com.sluglife.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.adapters.BaseListAdapter;
import slugapp.com.sluglife.adapters.FacilityListAdapter;
import slugapp.com.sluglife.databinding.ListMapFacilityBinding;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.models.BaseObject;

/**
 * Created by isayyuhh_s on 9/1/2015
 */
public class MapFacilityListFragment extends BaseSwipeListFragment {
    private static final FragmentEnum FRAGMENT = FragmentEnum.MAP;

    private ListMapFacilityBinding mBinding;
    private String mQuery;
    private List<BaseObject> mFacilities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mBinding = DataBindingUtil.inflate(this.getActivity().getLayoutInflater(),
                R.layout.list_map_facility, container, false);

        this.setSwipeListFragment(FRAGMENT, this.mBinding.list,
                new FacilityListAdapter(this.mContext));

        return this.mBinding.getRoot();
    }

    @Override
    protected void setArgumentFields(Bundle b) {
        this.mQuery = b.getString(this.mContext.getString(R.string.bundle_query));
    }

    @Override
    protected void setSwipeListFields() {
        this.mSwipeLayout = this.mBinding.swipeContainer;

        this.mFacilities = new ArrayList<>();
    }

    @Override
    protected void setView(ListView listView, final BaseAdapter adapter) {
        super.setView(listView, adapter);
    }

    @Override
    protected int doSort(BaseObject lhs, BaseObject rhs) {
        return 0;
    }

    @Override
    protected void onSwipeListItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onSwipeListRefresh() {
        ((BaseListAdapter) mAdapter).setData(mFacilities);
        this.mBinding.swipeContainer.setVisibility(View.GONE);
        this.mBinding.failed.setVisibility(View.VISIBLE);

        stopRefreshing();
    }
}
