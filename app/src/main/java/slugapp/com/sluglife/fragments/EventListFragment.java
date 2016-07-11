package slugapp.com.sluglife.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.adapters.BaseListAdapter;
import slugapp.com.sluglife.adapters.EventListAdapter;
import slugapp.com.sluglife.databinding.ListEventBinding;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.http.EventListHttpRequest;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.BaseObject;
import slugapp.com.sluglife.models.Date;
import slugapp.com.sluglife.models.Event;

/**
 * Created by isaiah on 6/23/2015
 */
public class EventListFragment extends BaseSwipeListFragment {
    private static final FragmentEnum FRAGMENT = FragmentEnum.EVENT;

    private ListEventBinding mBinding;
    private String mQuery;

    private boolean searchShowing;

    public static EventListFragment newInstance() {
        return new EventListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mBinding = DataBindingUtil.inflate(this.getActivity().getLayoutInflater(),
                R.layout.list_event, container, false);

        this.setSwipeListFragment(FRAGMENT, this.mBinding.list,
                new EventListAdapter(this.mContext));

        return this.mBinding.getRoot();
    }

    @Override
    protected void setArgumentFields(Bundle b) {
    }

    @Override
    protected void setSwipeListFields() {
        this.mSwipeLayout = this.mBinding.swipeContainer;

        this.searchShowing = false;
    }

    @Override
    protected void setView(ListView listView, BaseAdapter adapter) {
        super.setView(listView, adapter);

        this.mBinding.search.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mQuery = s.toString();
                onRefresh();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_event_search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                if (!searchShowing) {
                    this.mBinding.search.searchBar.setVisibility(View.VISIBLE);
                    this.searchShowing = true;
                } else {
                    this.mBinding.search.searchBar.setVisibility(View.GONE);
                    this.searchShowing = false;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected int doSort(BaseObject lhs, BaseObject rhs) {
        return Date.compareEvents((Event) lhs, (Event) rhs);
    }

    @Override
    protected void onSwipeListItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.mCallback.setFragment(EventViewFragment.newInstance(this.mContext,
                (Event) parent.getItemAtPosition(position)));
    }

    @Override
    public void onSwipeListRefresh() {
        new EventListHttpRequest(this.mContext).execute(new HttpCallback<List<Event>>() {
            @Override
            public void onSuccess(List<Event> vals) {
                if (mQuery != null) doSearch(vals);
                Collections.sort(vals, new ListSort());
                List<BaseObject> events = new ArrayList<>();
                for (BaseObject val : vals) events.add(val);

                mBinding.swipeContainer.setVisibility(events.isEmpty() ? View.GONE : View.VISIBLE);
                mBinding.failed.setVisibility(events.isEmpty() ? View.VISIBLE : View.GONE);

                ((BaseListAdapter) mAdapter).setData(events);

                stopRefreshing();
            }

            @Override
            public void onError(Exception e) {
                mBinding.swipeContainer.setVisibility(View.GONE);
                mBinding.failed.setVisibility(View.VISIBLE);

                stopRefreshing();
            }
        });
    }

    // TODO: implement better search algorithm

    private void doSearch(List<Event> events) {
        for (Event event : events) {
            if (event.name.toLowerCase().contains(this.mQuery.toLowerCase())) continue;
            events.remove(event);
        }
    }
}

