package slugapp.com.ucscstudentapp.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.main.BaseFragment;

/**
 * Created by isaiah on 6/23/2015.
 */
public class EventListFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_event, container, false);
        this.setLayout("Event Center", R.id.events_button);
        this.setView(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_toolbar, menu);

        // SearchView
        this.setSearchView(menu);
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
    @Override
    protected void setView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.list);
        EventListAdapter adp = new EventListAdapter(getActivity());
        listView.setAdapter(adp);
        EventUpdater upd = new EventUpdater(getActivity(), adp);
        this.setSwipeLayout(view, upd);
        upd.onRefresh();
        EventListListener listListener = new EventListListener(this.ac);
        listView.setOnItemClickListener(listListener);
    }
}

