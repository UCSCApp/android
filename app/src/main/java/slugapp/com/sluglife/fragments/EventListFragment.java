package slugapp.com.sluglife.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.adapters.BaseListAdapter;
import slugapp.com.sluglife.adapters.EventListAdapter;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.http.TestEventListHttpRequest;
import slugapp.com.sluglife.models.BaseObject;
import slugapp.com.sluglife.models.Event;

/**
 * Created by isaiah on 6/23/2015.
 */
public class EventListFragment extends BaseSwipeListFragment {
    private View mView;
    private FragmentEnum fragmentEnum = FragmentEnum.EVENT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_event, container, false);
        this.mView = view;
        this.setLayout(fragmentEnum.getName(), fragmentEnum.getButtonId());
        this.setView(view, new EventListAdapter(this.mContext));
        this.onRefresh();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_toolbar, menu);

        // SearchView
        this.setSearchView(menu);
    }

    @Override
    protected void doSearch(String query) {
        this.mCallback.hideKeyboard();
        EventSearchListFragment fragment = new EventSearchListFragment();
        Bundle b = new Bundle();
        b.putString("query", query);
        fragment.setArguments(b);
        this.mCallback.setFragment(fragment);
    }

    @Override
    protected int doSort(BaseObject lhs, BaseObject rhs) {
        return mCallback.getToday().compareEvents((Event) lhs, (Event) rhs);
    }

    private boolean refreshing = false;

    @Override
    protected void onClick(AdapterView<?> parent, View view, int position, long id) {
        if (refreshing) return;
        Event e = (Event) parent.getItemAtPosition(position);
        String json = this.mCallback.getGson().toJson(e);

        Bundle b = new Bundle();
        b.putString("json", json);

        EventDetailFragment fragment = new EventDetailFragment();
        fragment.setArguments(b);
        this.mCallback.setFragment(fragment);
    }

    @Override
    public void onRefresh() {
        refreshing = true;
        new TestEventListHttpRequest(getActivity()).execute(new HttpCallback<List<Event>>() {
            @Override
            public void onSuccess(List<Event> vals) {
                Collections.sort(vals, new ListSort());
                List<BaseObject> events = new ArrayList<>();
                for (BaseObject val : vals) events.add(val);
                if (events.isEmpty()) {
                    SwipeRefreshLayout list = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_container);
                    list.setVisibility(View.GONE);
                    TextView failed = (TextView) mView.findViewById(R.id.failed);
                    failed.setVisibility(View.VISIBLE);
                } else {
                    SwipeRefreshLayout list = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_container);
                    list.setVisibility(View.VISIBLE);
                    TextView failed = (TextView) mView.findViewById(R.id.failed);
                    failed.setVisibility(View.GONE);
                }
                ((BaseListAdapter) mAdapter).setData(events);
                mSwipeLayout.setRefreshing(false);
                refreshing = false;
            }

            @Override
            public void onError(Exception e) {
                SwipeRefreshLayout list = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_container);
                list.setVisibility(View.GONE);
                TextView failed = (TextView) mView.findViewById(R.id.failed);
                failed.setVisibility(View.VISIBLE);
                mSwipeLayout.setRefreshing(false);
                refreshing = false;
                e.printStackTrace();
            }
        });
    }
}

