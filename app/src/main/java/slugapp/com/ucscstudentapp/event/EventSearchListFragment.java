package slugapp.com.ucscstudentapp.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.main.ActivityCallback;
import slugapp.com.ucscstudentapp.main.BaseFragment;

/**
 * Created by isayyuhh_s on 7/26/2015.
 *
 * Fragment that displays Search results
 */
public class EventSearchListFragment extends BaseFragment {
    private String query;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        this.query = b.getString("query");
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_event_search, container, false);
        this.setLayout("Search: \"" + this.query + "\"", R.id.events_button);
        this.setView(view);
        return view;
    }

    @Override
    protected void setView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.list);
        EventListAdapter adp = new EventListAdapter(getActivity());
        listView.setAdapter(adp);
        EventSearchUpdater upd = new EventSearchUpdater(getActivity(), adp, this.query);
        upd.onRefresh();
        EventSearchListListener listListener = new EventSearchListListener(this.ac);
        listView.setOnItemClickListener(listListener);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        getActivity().findViewById(R.id.toolbar_title).setVisibility(View.VISIBLE);
    }

    private static class EventSearchListListener implements AdapterView.OnItemClickListener {
        private ActivityCallback ac;
        public EventSearchListListener(ActivityCallback ac) {
            this.ac = ac;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Event e = (Event) parent.getItemAtPosition(position);
            Bundle b = new Bundle();
            b.putString("name", e.name());
            b.putString("date", e.date().toString());
            b.putString("description", e.getDesc());
            b.putString("url", e.url());
            EventDetail fragment = new EventDetail();
            fragment.setArguments(b);
            ac.setFragment(fragment);
        }
    }
}
