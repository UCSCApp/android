package slugapp.com.ucscstudentapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.adapters.BaseListAdapter;
import slugapp.com.ucscstudentapp.adapters.DiningHallListAdapter;
import slugapp.com.ucscstudentapp.http.DiningListHttpRequest;
import slugapp.com.ucscstudentapp.interfaces.HttpCallback;
import slugapp.com.ucscstudentapp.models.BaseListItem;
import slugapp.com.ucscstudentapp.models.StringListItem;

/**
 * Created by isayyuhh_s on 9/1/2015.
 */
public class DiningHallListFragment extends BaseListFragment {
    private List<BaseListItem> diningHalls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_dining_hall, container, false);
        this.setLayout("Dining Halls", R.id.dining_button);
        this.setView(view, new DiningHallListAdapter(getActivity()));
        return view;
    }

    @Override
    protected void setView(View view, final BaseListAdapter adapter) {
        super.setView(view, adapter);

        this.diningHalls = new ArrayList<>();
        new DiningListHttpRequest(getActivity()).execute(new HttpCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> vals) {
                diningHalls = new ArrayList<>();
                for (String val : vals) diningHalls.add(new StringListItem(val));
                adapter.setData(diningHalls);
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }

    @Override
    protected void doSearch(String query) {
    }

    @Override
    protected int doSort(BaseListItem lhs, BaseListItem rhs) {
        return 0;
    }

    @Override
    protected void onClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView) view.findViewById(R.id.name);
        String name = tv.getText().toString();

        Bundle b = new Bundle();
        b.putString("name", name);

        DiningHallViewPagerFragment fragment = new DiningHallViewPagerFragment();
        fragment.setArguments(b);
        this.ac.setFragment(fragment);
    }
}
