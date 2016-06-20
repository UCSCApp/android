package slugapp.com.sluglife.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.adapters.BaseListAdapter;
import slugapp.com.sluglife.adapters.EventListAdapter;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.http.EventListHttpRequest;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.BaseObject;
import slugapp.com.sluglife.models.Event;

/**
 * Created by isaiah on 6/23/2015.
 */
public class EventListFragment extends BaseSwipeListFragment {
    private View mView;
    private FragmentEnum fragmentEnum = FragmentEnum.EVENT;

    private String query;
    private boolean queried;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_event, container, false);
        this.mView = view;

        String name = this.checkIfQueried();
        this.setLayout(name, this.fragmentEnum.getButtonId());
        this.setView(view, new EventListAdapter(this.mContext));
        this.onRefresh();

        return view;
    }

    private String checkIfQueried() {
        Bundle b = this.getArguments();
        if (b != null && b.containsKey(this.mContext.getString(R.string.bundle_query))) {
            this.queried = true;
            this.query = b.getString(this.mContext.getString(R.string.bundle_query));
            return "Search: \"" + this.query + "\"";
        }
        this.queried = false;
        return this.fragmentEnum.getName();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_event_search, menu);

        // SearchView
        this.setSearchView(menu);
    }

    private void search(List<Event> val) {
        for (Iterator<Event> itor = val.iterator(); itor.hasNext(); ) {
            Event event = itor.next();
            StringTokenizer queryTokenizer = new StringTokenizer(this.query);
            int found = 0, max = queryTokenizer.countTokens();
            while (queryTokenizer.hasMoreTokens()) {
                String query = queryTokenizer.nextToken();
                StringTokenizer stringTokenizer = new StringTokenizer(event.getName());
                while (stringTokenizer.hasMoreTokens()) {
                    String current = stringTokenizer.nextToken();
                    if (query.length() < current.length()) {
                        current = current.substring(0, query.length());
                    }
                    if (current.toLowerCase().equals(query.toLowerCase())) {
                        found++;
                        break;
                    }
                }
            }
            if (found < max) itor.remove();
        }
    }

    @Override
    protected void doSearch(String query) {
        this.mCallback.hideKeyboard();

        EventListFragment fragment = new EventListFragment();

        Bundle b = new Bundle();
        b.putString(this.mContext.getString(R.string.bundle_query), query);

        fragment.setArguments(b);
        this.mCallback.setFragment(fragment);
    }

    @Override
    protected int doSort(BaseObject lhs, BaseObject rhs) {
        return this.mCallback.getToday().compareEvents((Event) lhs, (Event) rhs);
    }

    private boolean refreshing = false;

    @Override
    protected void onClick(AdapterView<?> parent, View view, int position, long id) {
        if (this.refreshing) return;
        Event e = (Event) parent.getItemAtPosition(position);
        String json = this.mCallback.getGson().toJson(e);

        Bundle b = new Bundle();
        b.putString(this.mContext.getString(R.string.bundle_json), json);

        EventDetailFragment fragment = new EventDetailFragment();
        fragment.setArguments(b);
        this.mCallback.setFragment(fragment);
    }

    @Override
    public void onRefresh() {
        this.refreshing = true;
        new EventListHttpRequest(getActivity()).execute(new HttpCallback<List<Event>>() {
            @Override
            public void onSuccess(List<Event> vals) {
                if (queried) search(vals);
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

