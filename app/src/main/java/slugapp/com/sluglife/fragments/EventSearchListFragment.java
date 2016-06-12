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
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.http.TestEventListHttpRequest;
import slugapp.com.sluglife.models.BaseListItem;
import slugapp.com.sluglife.models.Event;
import slugapp.com.sluglife.adapters.EventListAdapter;

/**
 * Created by isayyuhh_s on 7/26/2015.
 * <p/>
 * Fragment that displays Search results
 */
public class EventSearchListFragment extends BaseSwipeListFragment {
    private String query;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        this.query = b.getString("query");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_event, container, false);
        //this.setLayout("Search: \"" + this.query + "\"", R.id.events_button);
        this.setView(view, new EventListAdapter(getActivity()));
        this.onRefresh();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().findViewById(R.id.toolbar_title).setVisibility(View.VISIBLE);
    }

    @Override
    protected void doSearch(String query) {
    }

    @Override
    protected int doSort(BaseListItem lhs, BaseListItem rhs) {
        return mCallback.getToday().compareEvents((Event) lhs, (Event) rhs);
    }

    @Override
    protected void onClick(AdapterView<?> parent, View view, int position, long id) {
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
        new TestEventListHttpRequest(getActivity()).execute(new HttpCallback<List<Event>>() {
            @Override
            public void onSuccess(List<Event> vals) {
                search(vals);
                Collections.sort(vals, new ListSort());
                List<BaseListItem> events = new ArrayList<>();
                for (BaseListItem val : vals) events.add(val);
                mAdapter.setData(events);
            }

            @Override
            public void onError(Exception e) {
                // Currently Impossible to get here
            }
        });
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
}
