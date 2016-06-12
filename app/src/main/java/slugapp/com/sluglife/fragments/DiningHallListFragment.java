package slugapp.com.sluglife.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.adapters.BaseListAdapter;
import slugapp.com.sluglife.adapters.DiningHallListAdapter;
import slugapp.com.sluglife.http.DiningListHttpRequest;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.BaseListItem;
import slugapp.com.sluglife.models.StringListItem;

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
    protected void setView(final View view, final BaseListAdapter adapter) {
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
                ListView list = (ListView) view.findViewById(R.id.list);
                list.setVisibility(View.GONE);
                TextView failed = (TextView) view.findViewById(R.id.failed);
                failed.setVisibility(View.VISIBLE);
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
        this.mCallback.setFragment(fragment);
    }
}
