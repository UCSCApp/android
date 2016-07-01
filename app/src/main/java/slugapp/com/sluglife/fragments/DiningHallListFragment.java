package slugapp.com.sluglife.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.adapters.BaseListAdapter;
import slugapp.com.sluglife.adapters.DiningListAdapter;
import slugapp.com.sluglife.databinding.ListDiningBinding;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.http.DiningListHttpRequest;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.BaseObject;
import slugapp.com.sluglife.models.StringObject;

/**
 * Created by isayyuhh_s on 9/1/2015
 */
public class DiningHallListFragment extends BaseListFragment {
    private static final FragmentEnum FRAGMENT = FragmentEnum.DINING;

    private ListDiningBinding mBinding;
    private List<BaseObject> mDiningHalls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(),
                R.layout.list_dining, container, false);
        View view = this.mBinding.getRoot();

        this.setListFragment(view, container, FRAGMENT, new DiningListAdapter(this.mContext));

        return view;
    }

    @Override
    protected void setArgumentFields(Bundle b) {
    }

    @Override
    protected void setFields(View view, ViewGroup container) {
        this.mDiningHalls = new ArrayList<>();
    }

    @Override
    protected void setView(final View view, final BaseAdapter adapter) {
        super.setView(view, adapter);
        final BaseListAdapter baseListAdapter = (BaseListAdapter) adapter;

        new DiningListHttpRequest(getActivity()).execute(new HttpCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> vals) {
                for (String val : vals) mDiningHalls.add(new StringObject(val));
                baseListAdapter.setData(mDiningHalls);
            }

            @Override
            public void onError(Exception e) {
                mBinding.list.setVisibility(View.GONE);
                mBinding.failed.setVisibility(View.VISIBLE);
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
