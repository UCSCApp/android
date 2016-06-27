package slugapp.com.sluglife.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.adapters.BaseListAdapter;
import slugapp.com.sluglife.adapters.FacilityListAdapter;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.models.BaseObject;

/**
 * Created by isayyuhh_s on 9/1/2015
 */
public class FacilityListFragment extends BaseSwipeListFragment {
    private static final FragmentEnum fragmentEnum = FragmentEnum.MAP;

    private View mView;
    private String mQuery;
    private List<BaseObject> mFacilities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_facility, container, false);

        this.setSwipeListFragment(view, fragmentEnum, new FacilityListAdapter(this.mContext));

        return view;
    }

    @Override
    protected void setArgumentFields(Bundle b) {
        this.mQuery = b.getString(this.mContext.getString(R.string.bundle_query));
    }

    @Override
    protected void setSwipeListFields(View view) {
        this.mView = view;
        this.mFacilities = new ArrayList<>();
    }

    @Override
    protected void setView(final View view, final BaseAdapter adapter) {
        super.setView(view, adapter);
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
        SwipeRefreshLayout list = (SwipeRefreshLayout) mView.findViewById(
                R.id.swipe_container);
        list.setVisibility(View.GONE);
        TextView failed = (TextView) mView.findViewById(R.id.failed);
        failed.setVisibility(View.VISIBLE);

        stopRefreshing();
    }
}
