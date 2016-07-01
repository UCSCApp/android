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
import android.widget.EditText;

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
    private View mView;
    private View mSearchBar;
    private String mQuery;

    private boolean searchShowing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(),
                R.layout.list_event, container, false);
        View view = this.mBinding.getRoot();

        this.setSwipeListFragment(view, container, FRAGMENT, new EventListAdapter(
                this.mContext));

        return view;
    }

    @Override
    protected void setArgumentFields(Bundle b) {
    }

    @Override
    protected void setSwipeListFields(View view, ViewGroup container) {
        this.mView = view;
        this.mSearchBar = view.findViewById(R.id.search);

        this.searchShowing = false;
    }

    @Override
    protected void setView(View view, BaseAdapter adapter) {
        super.setView(view, adapter);

        final EditText searchEditText = (EditText) view.findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
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
                    this.mSearchBar.setVisibility(View.VISIBLE);
                    this.searchShowing = true;
                } else {
                    this.mSearchBar.setVisibility(View.GONE);
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
        Event e = (Event) parent.getItemAtPosition(position);

        Bundle b = new Bundle();
        b.putSerializable(this.mContext.getString(R.string.bundle_json), e);

        EventViewFragment fragment = new EventViewFragment();
        fragment.setArguments(b);
        this.mCallback.setFragment(fragment);
    }

    @Override
    public void onSwipeListRefresh() {
        new EventListHttpRequest(getActivity()).execute(new HttpCallback<List<Event>>() {
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

