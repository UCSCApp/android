package slugapp.com.ucscstudentapp.map;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.event.EventDetail;
import slugapp.com.ucscstudentapp.main.ActivityReference;

/**
 * Created by isayyuhh_s on 8/29/2015.
 */
public class FacilityList extends Fragment {
    private ActivityReference mCallBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mCallBack = (ActivityReference) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_dining_hall, container, false);
        setListView(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.top_toolbar, menu);
        mCallBack.setTitle("Facilities");

        mCallBack.setSearchButton(menu);
    }

    private void setListView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.list);
        ListAdapter adp = new ListAdapter(getActivity());
        listView.setAdapter(adp);
        //adp.setData(getResources().getStringArray(R.array.facilities));
        FacilitiesListListener listListener =
                new FacilitiesListListener(getActivity().getSupportFragmentManager());
        listView.setOnItemClickListener(listListener);
    }

    private class FacilitiesListListener implements AdapterView.OnItemClickListener {
        private FragmentManager fm;

        public FacilitiesListListener(FragmentManager fm) {
            this.fm = fm;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mCallBack.hideKeyboard();
            FragmentTransaction ft = fm.beginTransaction();
            EventDetail llf = new EventDetail();
            ft.replace(R.id.listFragment, llf);
            ft.addToBackStack(null);
            ft.commit();
        }
    }
}
