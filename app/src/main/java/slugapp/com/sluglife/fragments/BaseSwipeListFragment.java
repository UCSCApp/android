package slugapp.com.sluglife.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.BaseAdapter;

import slugapp.com.sluglife.R;

/**
 * Created by isayyuhh on 2/21/16.
 */
public abstract class BaseSwipeListFragment extends BaseListFragment
        implements SwipeRefreshLayout.OnRefreshListener {
    protected SwipeRefreshLayout mSwipeLayout;

    @Override
    protected void setView(View view, BaseAdapter adapter) {
        super.setView(view, adapter);
        this.mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        this.mSwipeLayout.setOnRefreshListener(this);
        this.mSwipeLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
}
