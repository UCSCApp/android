package slugapp.com.sluglife.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.adapters.BaseListAdapter;
import slugapp.com.sluglife.adapters.EventListAdapter;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.http.EventListHttpRequest;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.BaseObject;
import slugapp.com.sluglife.models.Date;
import slugapp.com.sluglife.models.Event;

/**
 * Created by isaiah on 6/23/2015
 */
public class EventListFragment extends BaseSwipeListFragment {
    private static final FragmentEnum fragmentEnum = FragmentEnum.EVENT;

    private View mView;
    private View mSearchBar;
    private String mQuery;

    private boolean refreshing;
    private boolean searchShowing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_event, container, false);

        this.setListFragment(view, fragmentEnum, new EventListAdapter(this.mContext));
        this.onRefresh();

        return view;
    }

    @Override
    protected void setFields(View view) {
        this.mView = view;
        this.mSearchBar = view.findViewById(R.id.search);

        this.searchShowing = false;
        this.refreshing = false;
    }

    @Override
    protected void setView(View view, BaseAdapter adapter) {
        super.setView(view, adapter);
        final EditText searchEditText = (EditText) view.findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mQuery = s.toString();
                onRefresh();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_event_search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                if (!searchShowing) {
                    this.mSearchBar.setVisibility(View.VISIBLE);
                    this.searchShowing = true;
                } else {
                    this.mSearchBar.setVisibility(View.GONE);
                    this.searchShowing = false;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // TODO: implement better search algorithm

    private void doSearch(List<Event> events) {
        for (Event event : events) {
            if (event.name.toLowerCase().contains(this.mQuery.toLowerCase())) continue;
            events.remove(event);
        }
    }

    @Override
    protected int doSort(BaseObject lhs, BaseObject rhs) {
        return Date.compareEvents((Event) lhs, (Event) rhs);
    }

    // TODO: move refreshing variable to BaseSwipeListFragment

    @Override
    protected void onClick(AdapterView<?> parent, View view, int position, long id) {
        if (this.refreshing) return;

        Event e = (Event) parent.getItemAtPosition(position);
        String json = this.mCallback.getGson().toJson(e);

        Bundle b = new Bundle();
        b.putString(this.mContext.getString(R.string.bundle_json), json);

        EventViewFragment fragment = new EventViewFragment();
        fragment.setArguments(b);
        this.mCallback.setFragment(fragment);
    }

    @Override
    public void onRefresh() {
        this.refreshing = true;
        new EventListHttpRequest(getActivity()).execute(new HttpCallback<List<Event>>() {
            @Override
            public void onSuccess(List<Event> vals) {
                if (mQuery != null) doSearch(vals);
                Collections.sort(vals, new ListSort());
                List<BaseObject> events = new ArrayList<>();
                for (BaseObject val : vals) events.add(val);
                if (events.isEmpty()) {
                    SwipeRefreshLayout list = (SwipeRefreshLayout) mView.findViewById(
                            R.id.swipe_container);
                    list.setVisibility(View.GONE);
                    TextView failed = (TextView) mView.findViewById(R.id.failed);
                    failed.setVisibility(View.VISIBLE);
                } else {
                    SwipeRefreshLayout list = (SwipeRefreshLayout) mView.findViewById(
                            R.id.swipe_container);
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
                SwipeRefreshLayout list = (SwipeRefreshLayout) mView.findViewById(
                        R.id.swipe_container);
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

