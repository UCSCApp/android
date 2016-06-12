package slugapp.com.sluglife.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.adapters.EventListAdapter;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.http.TestEventListHttpRequest;
import slugapp.com.sluglife.models.BaseListItem;
import slugapp.com.sluglife.models.Event;

/**
 * Created by isaiah on 6/23/2015.
 */
public class EventListFragment extends BaseSwipeListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_event, container, false);
        //this.setLayout("Event Center", R.id.events_button);
        this.setView(view, new EventListAdapter(getActivity()));
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
        mCallback.hideKeyboard();
        EventSearchListFragment fragment = new EventSearchListFragment();
        Bundle b = new Bundle();
        b.putString("query", query);
        fragment.setArguments(b);
        mCallback.setFragment(fragment);
    }

    @Override
    protected int doSort(BaseListItem lhs, BaseListItem rhs) {
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
                List<BaseListItem> events = new ArrayList<>();
                for (BaseListItem val : vals) events.add(val);
                mAdapter.setData(events);
                mSwipeLayout.setRefreshing(false);
                refreshing = false;
            }

            @Override
            public void onError(Exception e) {
                mSwipeLayout.setRefreshing(false);
            }
        });
    }
}

