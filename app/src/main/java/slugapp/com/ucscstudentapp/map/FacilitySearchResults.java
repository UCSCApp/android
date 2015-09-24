package slugapp.com.ucscstudentapp.map;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.main.ActivityCallback;

/**
 * Created by isayyuhh_s on 7/26/2015.
 *
 * Fragment that displays Search results
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FacilitySearchResults extends Fragment {
    private ActivityCallback mCallBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallBack = (ActivityCallback) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_event_search, container, false);
        Bundle b = getArguments();
        mCallBack.setTitle("Search: \"" + b.getString("query") + "\"");
        doSearch(view, new String(b.getString("query")));
        return view;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        (getActivity().findViewById(R.id.toolbar_title)).setVisibility(View.VISIBLE);
    }
    /*
     * Event Search
     */
    private void doSearch(View view, String query) {
        ListView listView = (ListView) view.findViewById(R.id.list);
        ListAdapter adp = new ListAdapter(getActivity());
        listView.setAdapter(adp);
        //EventSearch upd = new EventSearch(getActivity(), adp, query);
        //upd.onRefresh();

        EventSearchListListener listListener =
                new EventSearchListListener(getActivity().getSupportFragmentManager());
        listView.setOnItemClickListener(listListener);

    }

    private static class EventSearchListListener implements AdapterView.OnItemClickListener {
        private FragmentManager fm;
        public EventSearchListListener(FragmentManager fm) {
            this.fm = fm;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    }
}
