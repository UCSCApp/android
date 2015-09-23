package slugapp.com.ucscstudentapp.event;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.main.ActivityReference;

/**
 * Created by isaiah on 6/23/2015.
 */
public class EventList extends Fragment {
    private ActivityReference mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (ActivityReference) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_event_center, container, false);
        mCallback.setTitle("Event Center");

        setListView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mCallback.setButtons(R.id.events_button);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.top_toolbar, menu);

        // SearchView
        SearchView searchView = mCallback.setSearchButton(menu);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                mCallback.hideKeyboard();
                FragmentTransaction ft = mCallback.fm().beginTransaction();
                EventSearchList llf = new EventSearchList();
                Bundle b = new Bundle();
                b.putString("query", query);
                llf.setArguments(b);
                ft.replace(R.id.listFragment, llf);
                ft.addToBackStack(null);
                ft.commit();
                return true;
            }
        });;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) return true;
        return super.onOptionsItemSelected(item);
    }

    /*
     * Calls the adapter for ListView
     */
    private void setListView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.list);
        EventListAdapter adp = new EventListAdapter(getActivity());
        listView.setAdapter(adp);
        EventUpdater upd = new EventUpdater(getActivity(), adp);
        mCallback.setCurrentSwipeLayout((SwipeRefreshLayout) view.findViewById(R.id.swipe_container));
        mCallback.currentSwipeLayout().setOnRefreshListener(upd);
        mCallback.currentSwipeLayout().setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // Initial refresh
        upd.onRefresh();
        EventCenterListListener listListener =
                new EventCenterListListener(getActivity().getSupportFragmentManager());
        listView.setOnItemClickListener(listListener);
    }

    private class EventCenterListListener implements AdapterView.OnItemClickListener {
        private FragmentManager fm;

        public EventCenterListListener(FragmentManager fm) {
            this.fm = fm;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mCallback.hideKeyboard();
            Event e = (Event) parent.getItemAtPosition(position);
            Bundle b = new Bundle();
            b.putString("name", e.name());
            b.putString("date", e.date().string());
            b.putString("description", e.desc());
            b.putString("url", e.url());
            FragmentTransaction ft = fm.beginTransaction();
            EventDetail fragment = new EventDetail();
            fragment.setArguments(b);
            ft.replace(R.id.listFragment, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }
}

