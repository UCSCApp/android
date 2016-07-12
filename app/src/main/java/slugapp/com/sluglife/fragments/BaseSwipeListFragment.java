package slugapp.com.sluglife.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import slugapp.com.sluglife.enums.FragmentEnum;

/**
 * Created by isaiah on 2/21/16
 * <p/>
 * This file contains a base swipe list fragment class.
 */
public abstract class BaseSwipeListFragment extends BaseListFragment
        implements SwipeRefreshLayout.OnRefreshListener {
    protected SwipeRefreshLayout mSwipeLayout;
    protected boolean refreshing;

    /**
     * Sets list fragment
     */
    @Override
    protected void setFields() {
        this.refreshing = false;

        this.setSwipeListFields();
    }

    /**
     * Does action on list item click
     *
     * @param parent   Parent view of list item
     * @param view     List item view
     * @param position List item position
     * @param id       Id of list item
     */
    @Override
    protected void onClick(AdapterView<?> parent, View view, int position, long id) {
        if (this.refreshing) return;

        this.onSwipeListItemClick(parent, view, position, id);
    }

    /**
     * Sets fragment view
     *
     * @param listView List view
     * @param adapter  List adapter
     */
    @Override
    protected void setView(ListView listView, BaseAdapter adapter) {
        super.setView(listView, adapter);
        this.mSwipeLayout.setOnRefreshListener(this);
        this.mSwipeLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    /**
     * On swipe list refresh
     */
    @Override
    public void onRefresh() {
        this.refreshing = true;

        this.onSwipeListRefresh();
    }

    /**
     * Sets swipe list fragment
     *
     * @param fragmentEnum Fragment enum containing fragment information
     * @param listView     List view
     * @param adapter      List adapter
     */
    protected void setSwipeListFragment(FragmentEnum fragmentEnum, ListView listView,
                                        BaseAdapter adapter) {
        this.setListFragment(fragmentEnum, listView, adapter);
        this.onRefresh();
    }

    /**
     * Stops swipe list refreshing
     */
    protected void stopRefreshing() {
        this.mSwipeLayout.setRefreshing(false);
        this.refreshing = false;
    }

    /**
     * Allows user to set fields for swipe list fragment
     */
    protected abstract void setSwipeListFields();

    /**
     * Allows user to set what happens on swipe list item click
     *
     * @param parent   Parent view of list item
     * @param view     List item view
     * @param position List item position
     * @param id       Id of list item
     */
    protected abstract void onSwipeListItemClick(AdapterView<?> parent, View view, int position,
                                                 long id);

    /**
     * Allows the user to set what happens on swipe list refresh
     */
    protected abstract void onSwipeListRefresh();
}
