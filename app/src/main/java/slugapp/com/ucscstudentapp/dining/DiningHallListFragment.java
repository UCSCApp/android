package slugapp.com.ucscstudentapp.dining;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.http.DiningListHttpRequest;
import slugapp.com.ucscstudentapp.http.HttpCallback;
import slugapp.com.ucscstudentapp.main.BaseFragment;

/**
 * Created by isayyuhh_s on 9/1/2015.
 */
public class DiningHallListFragment extends BaseFragment {
    private List<String> diningHalls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_dining_hall, container, false);
        this.setLayout("Dining Halls", R.id.dining_button);
        this.setView(view);
        return view;
    }

    @Override
    protected void setView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.list);
        final DiningHallListAdapter adp = new DiningHallListAdapter(getActivity());
        listView.setAdapter(adp);

        diningHalls = new ArrayList<>();
        new DiningListHttpRequest().execute(new HttpCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> val) {
                diningHalls = new ArrayList<>(val);
                adp.setData(diningHalls);
            }

            @Override
            public void onError(Exception e) {
            }
        });

        adp.setData(diningHalls);
        FacilitiesListListener listListener = new FacilitiesListListener();
        listView.setOnItemClickListener(listListener);
    }

    private class FacilitiesListListener implements AdapterView.OnItemClickListener {
        public FacilitiesListListener() {}

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ac.hideKeyboard();

            TextView tv = (TextView) view.findViewById(R.id.name);
            String name = tv.getText().toString();

            Bundle b = new Bundle();
            b.putString("name", name);

            FragmentTransaction ft = ac.fm().beginTransaction();
            DiningHallDetail fragment = new DiningHallDetail();
            fragment.setArguments(b);
            ft.replace(R.id.listFragment, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }
}
