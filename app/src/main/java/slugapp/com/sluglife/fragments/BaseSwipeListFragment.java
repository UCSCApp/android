package slugapp.com.sluglife.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import slugapp.com.sluglife.enums.FragmentEnum;

/**
 * Created by isayyuhh on 2/21/16
 */
public abstract class BaseSwipeListFragment extends BaseListFragment
        implements SwipeRefreshLayout.OnRefreshListener {
    protected SwipeRefreshLayout mSwipeLayout;
    protected boolean refreshing;

    @Override
    protected void setFields() {
        this.refreshing = false;

        this.setSwipeListFields();
    }

    @Override
    protected void onClick(AdapterView<?> parent, View view, int position, long id) {
        if (this.refreshing) return;

        this.onSwipeListItemClick(parent, view, position, id);
    }

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

    @Override
    public void onRefresh() {
        this.refreshing = true;

        this.onSwipeListRefresh();
    }

    protected void setSwipeListFragment(FragmentEnum fragmentEnum, ListView listView,
                                        BaseAdapter adapter) {
        this.setListFragment(fragmentEnum, listView, adapter);
        this.onRefresh();
    }

    protected void stopRefreshing() {
        this.mSwipeLayout.setRefreshing(false);
        this.refreshing = false;
    }

    protected abstract void setSwipeListFields();

    protected abstract void onSwipeListItemClick(AdapterView<?> parent, View view, int position,
                                                 long id);

    protected abstract void onSwipeListRefresh();
}
