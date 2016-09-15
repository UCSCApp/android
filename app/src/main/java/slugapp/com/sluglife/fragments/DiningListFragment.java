package slugapp.com.sluglife.fragments;

import android.databinding.DataBindingUtil;
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
import slugapp.com.sluglife.adapters.DiningListAdapter;
import slugapp.com.sluglife.databinding.ListDiningBinding;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.http.DiningListHttpRequest;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.objects.BaseObject;
import slugapp.com.sluglife.objects.StringObject;

/**
 * Created by isaiah on 9/1/2015
 * <p/>
 * This file contains a list fragment that displays a list of dining halls.
 */
public class DiningListFragment extends BaseListFragment {
    private static final FragmentEnum FRAGMENT = FragmentEnum.DINING;

    private ListDiningBinding mBinding;
    private List<BaseObject> mDiningHalls;

    /**
     * Gets a new instance of fragment
     *
     * @return New instance of fragment
     */
    public static DiningListFragment newInstance() {
        return new DiningListFragment();
    }

    /**
     * Fragment's onCreateView method
     *
     * @param inflater           Layout inflater
     * @param container          Container of fragment
     * @param savedInstanceState Saved instance state
     * @return Inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mBinding = DataBindingUtil.inflate(this.getActivity().getLayoutInflater(),
                R.layout.list_dining, container, false);

        this.setListFragment(FRAGMENT, this.mBinding.list, new DiningListAdapter(this.mContext));

        return this.mBinding.getRoot();
    }

    /**
     * Sets fields from fragment arguments
     *
     * @param b Bundle from fragment arguments
     */
    @Override
    protected void setArgumentFields(Bundle b) {
    }

    /**
     * Sets fields
     */
    @Override
    protected void setFields() {
        this.mDiningHalls = new ArrayList<>();
    }

    /**
     * Sets fragment view
     *
     * @param listView List view
     * @param adapter  List adapter
     */
    @Override
    protected void setView(ListView listView, final BaseAdapter adapter) {
        super.setView(listView, adapter);
        final BaseListAdapter baseListAdapter = (BaseListAdapter) adapter;

        new DiningListHttpRequest(this.getActivity()).execute(new HttpCallback<List<String>>() {

            /**
             * On request success
             *
             * @param values List of values from request
             */
            @Override
            public void onSuccess(List<String> values) {
                for (String val : values) mDiningHalls.add(new StringObject(val));
                baseListAdapter.setData(mDiningHalls);
            }

            /**
             * On request error
             *
             * @param e Exception
             */
            @Override
            public void onError(Exception e) {
                hideViews(mBinding.list);
                showViews(mBinding.failed);
            }
        });
    }

    /**
     * Sorts list
     *
     * @param lhs Left operand
     * @param rhs Right operand
     * @return Integer showing which order the operands are in
     */
    @Override
    protected int doSort(BaseObject lhs, BaseObject rhs) {
        return 0;
    }

    /**
     * Does action on list item click
     *
     * @param parent   Parent view of list item
     * @param view     List item view
     * @param position List item position
     * @param id       Id of list item
     */
    @Override
    protected void onClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView) view.findViewById(R.id.name);
        String name = tv.getText().toString();

        this.mCallback.setFragment(DiningViewPagerFragment.newInstance(this.mContext, name));
    }
}
