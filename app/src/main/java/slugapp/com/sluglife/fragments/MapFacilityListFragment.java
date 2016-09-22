package slugapp.com.sluglife.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.adapters.BaseListAdapter;
import slugapp.com.sluglife.adapters.FacilityListAdapter;
import slugapp.com.sluglife.databinding.ListMapFacilityBinding;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.http.FacilityListHttpRequest;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.objects.BaseObject;
import slugapp.com.sluglife.objects.EventObject;
import slugapp.com.sluglife.objects.FacilityObject;

/**
 * Created by isaiah on 9/1/2015
 * <p/>
 * This file contains a list fragment that displays a list of facilities.
 */
public class MapFacilityListFragment extends BaseSwipeListFragment {
    private static final FragmentEnum FRAGMENT = FragmentEnum.MAP;

    private ListMapFacilityBinding mBinding;
    private String mQuery;

    /**
     * Gets a new instance of fragment
     *
     * @return New instance of fragment
     */
    public static MapFacilityListFragment newInstance(Context context, String query) {
        MapFacilityListFragment fragment = new MapFacilityListFragment();

        Bundle b = new Bundle();
        b.putString(context.getString(R.string.bundle_query), query);
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
                R.layout.list_map_facility, container, false);

        this.setSwipeListFragment(FRAGMENT, this.mBinding.list,
                new FacilityListAdapter(this.mContext));

        return this.mBinding.getRoot();
    }

    /**
     * Sets fields from fragment arguments
     *
     * @param b Bundle from fragment arguments
     */
    @Override
    protected void setArgumentFields(Bundle b) {
        this.mQuery = b.getString(this.mContext.getString(R.string.bundle_query));
    }

    /**
     * Sets fields for swipe list fragment
     */
    @Override
    protected void setSwipeListFields() {
        this.mSwipeLayout = this.mBinding.swipeContainer;
    }

    /**
     * Sets fragment view
     *
     * @param listView List view
     * @param adapter  List adapter
     */
    @Override
    protected void setView(ListView listView, final BaseAdapter adapter) {
        super.setView(listView, adapter);
    }

    /**
     * Sorts list
     *
     * @param lhs Left operand
     * @param rhs Right operand
     * @return Integer showing which order the operands are in
     */
    @Override
    protected int doSort(BaseObject lhs, BaseObject rhs) {
        return (((FacilityObject) lhs).name).compareTo(((FacilityObject) rhs).name);
    }

    /**
     * Does action on swipe list item click
     *
     * @param parent   Parent view of list item
     * @param view     List item view
     * @param position List item position
     * @param id       Id of list item
     */
    @Override
    protected void onSwipeListItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    /**
     * Does action on swipe list refresh
     */
    @Override
    public void onSwipeListRefresh() {

        new FacilityListHttpRequest(this.mContext).execute(new HttpCallback<List<FacilityObject>>() {

            /**
             * On request success
             *
             * @param values List of values from request
             */
            @Override
            public void onSuccess(List<FacilityObject> values) {
                evaluateQuery(values);
                Collections.sort(values, new ListSort());
                List<BaseObject> facilities = new ArrayList<>();
                for (BaseObject val : values) facilities.add(val);

                if (facilities.isEmpty()) {
                    hideViews(mBinding.swipeContainer);
                    showViews(mBinding.failed);
                } else {
                    hideViews(mBinding.failed);
                    showViews(mBinding.swipeContainer);
                }

                ((BaseListAdapter) mAdapter).setData(facilities);

                stopRefreshing();
            }

            /**
             * On request error
             *
             * @param e Exception
             */
            @Override
            public void onError(Exception e) {
                hideViews(mBinding.swipeContainer);
                showViews(mBinding.failed);

                stopRefreshing();
            }
        });
    }

    /**
     * Updates query
     *
     * @param query Query to update to
     */
    public void updateQuery(String query) {
        this.mQuery = query;
        this.onRefresh();
    }

    /**
     * Adjusts list based on query
     *
     * @param facilities List of events
     */
    private void evaluateQuery(List<FacilityObject> facilities) {
        ListIterator<FacilityObject> iterator = facilities.listIterator();
        while (iterator.hasNext()) {
            FacilityObject facility = iterator.next();
            if (this.mQuery != null &&
                    !facility.name.toLowerCase().contains(this.mQuery.toLowerCase()) &&
                    !facility.description.toLowerCase().contains(this.mQuery.toLowerCase()) &&
                    !facility.type.name().toLowerCase().contains(this.mQuery.toLowerCase()) &&
                    !facility.type.name().toLowerCase().contains(this.mQuery.toLowerCase())) {
                iterator.remove();
            }
        }
    }
}
