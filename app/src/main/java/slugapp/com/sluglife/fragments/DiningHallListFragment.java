package slugapp.com.sluglife.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.adapters.BaseListAdapter;
import slugapp.com.sluglife.adapters.DiningHallListAdapter;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.http.DiningListHttpRequest;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.BaseObject;
import slugapp.com.sluglife.models.StringObject;

/**
 * Created by isayyuhh_s on 9/1/2015
 */
public class DiningHallListFragment extends BaseListFragment {
    private FragmentEnum fragmentEnum = FragmentEnum.DINING;

    private List<BaseObject> mDiningHalls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_dining, container, false);

        this.setListFragment(view, this.fragmentEnum, new DiningHallListAdapter(this.mContext));

        return view;
    }

    @Override
    protected void setFields(View view) {
    }

    @Override
    protected void setView(final View view, final BaseAdapter adapter) {
        super.setView(view, adapter);
        final BaseListAdapter baseListAdapter = (BaseListAdapter) adapter;

        this.mDiningHalls = new ArrayList<>();
        new DiningListHttpRequest(getActivity()).execute(new HttpCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> vals) {
                mDiningHalls = new ArrayList<>();
                for (String val : vals) mDiningHalls.add(new StringObject(val));
                baseListAdapter.setData(mDiningHalls);
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
    protected int doSort(BaseObject lhs, BaseObject rhs) {
        return 0;
    }

    @Override
    protected void onClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView) view.findViewById(R.id.name);
        String name = tv.getText().toString();

        Bundle b = new Bundle();
        b.putString(this.mContext.getString(R.string.bundle_name), name);

        DiningHallViewPagerFragment fragment = new DiningHallViewPagerFragment();
        fragment.setArguments(b);
        this.mCallback.setFragment(fragment);
    }
}
