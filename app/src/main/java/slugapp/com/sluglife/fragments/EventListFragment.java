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
import java.util.ListIterator;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.adapters.BaseListAdapter;
import slugapp.com.sluglife.adapters.EventListAdapter;
import slugapp.com.sluglife.databinding.ListEventBinding;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.http.EventListHttpRequest;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.BaseObject;
import slugapp.com.sluglife.models.EventObject;

/**
 * Created by isaiah on 6/23/2015
 * <p/>
 * This file contains a list fragment that displays a list of events.
 */
public class EventListFragment extends BaseSwipeListFragment {
    private static final FragmentEnum FRAGMENT = FragmentEnum.EVENT;

    private ListEventBinding mBinding;
    private String mQuery;

    private boolean searchShowing;

    /**
     * Gets a new instance of fragment
     *
     * @return New instance of fragment
     */
    public static EventListFragment newInstance() {
        return new EventListFragment();
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
                R.layout.list_event, container, false);

        this.setSwipeListFragment(FRAGMENT, this.mBinding.list,
                new EventListAdapter(this.mContext));

        return this.mBinding.getRoot();
    }

    /**
     * Fragment's onCreateOptionsMenu method
     *
     * @param menu     Menu
     * @param inflater View inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_event_search, menu);
    }

    /**
     * Does action on toolbar item click
     *
     * @param item Toolbar item
     * @return Boolean if toolbar item is clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                if (!searchShowing) {
                    this.showViews(this.mBinding.search.searchBar);

                    this.mCallback.showKeyboard(this.mBinding.search.searchEditText);
                } else {
                    this.mCallback.hideKeyboard();

                    this.mBinding.search.searchEditText.setText(EMPTY_STRING);

                    this.hideViews(this.mBinding.search.searchBar);
                }
                this.searchShowing = !this.searchShowing;

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
     * Sets fields for swipe list fragment
     */
    @Override
    protected void setSwipeListFields() {
        this.mSwipeLayout = this.mBinding.swipeContainer;

        this.searchShowing = false;
    }

    /**
     * Sets fragment view
     *
     * @param listView List view
     * @param adapter  List adapter
     */
    @Override
    protected void setView(ListView listView, BaseAdapter adapter) {
        super.setView(listView, adapter);

        this.mBinding.search.searchEditText.addTextChangedListener(new TextWatcher() {

            /**
             * Before edit text changed
             *
             * @param s Edit text character sequence
             * @param start Edit text start index
             * @param count Edit text current length
             * @param after Edit text length after change
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            /**
             * On edit text changed
             *
             * @param s Edit text character sequence
             * @param start Edit text start index
             * @param before Edit text length before change
             * @param count Edit text current length
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            /**
             * After edit text changed
             *
             * @param s Edit text character sequence
             */
            @Override
            public void afterTextChanged(Editable s) {
                mQuery = s.toString();
                onRefresh();
            }
        });

        this.mBinding.search.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.hideKeyboard();

                mBinding.search.searchEditText.setText(EMPTY_STRING);

                hideViews(mBinding.search.searchBar);

                searchShowing = !searchShowing;
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
        return EventObject.compareEvents((EventObject) lhs, (EventObject) rhs);
    }

    /**
     * Does action on swipe list item click
     *
     * @param parent   Parent view of list item
     * @param view     List item view
     * @param position List item position
     * @param id       Id of list item
     */
    @Override
    protected void onSwipeListItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.mCallback.setFragment(EventViewFragment.newInstance(this.mContext,
                (EventObject) parent.getItemAtPosition(position)));
    }

    /**
     * Does action on swipe list refresh
     */
    @Override
    public void onSwipeListRefresh() {
        new EventListHttpRequest(this.mContext).execute(new HttpCallback<List<EventObject>>() {

            /**
             * On request success
             *
             * @param values List of values from request
             */
            @Override
            public void onSuccess(List<EventObject> values) {
                evaluateQuery(values);
                Collections.sort(values, new ListSort());
                List<BaseObject> events = new ArrayList<>();
                for (BaseObject val : values) events.add(val);

                if (events.isEmpty()) {
                    hideViews(mBinding.swipeContainer);
                    showViews(mBinding.failed);
                } else {
                    hideViews(mBinding.failed);
                    showViews(mBinding.swipeContainer);
                }

                ((BaseListAdapter) mAdapter).setData(events);

                stopRefreshing();
            }

            /**
             * On request error
             *
             * @param e Exception
             */
            @Override
            public void onError(Exception e) {
                hideViews(mBinding.swipeContainer);
                showViews(mBinding.failed);

                stopRefreshing();
            }
        });
    }

    /**
     * Adjusts list based on query
     *
     * @param events List of events
     */
    private void evaluateQuery(List<EventObject> events) {
        ListIterator<EventObject> iterator = events.listIterator();
        while (iterator.hasNext()) {
            EventObject event = iterator.next();
            if (!event.date.defined) iterator.remove();
            else if (this.mQuery != null &&
                    !event.name.toLowerCase().contains(this.mQuery.toLowerCase()) &&
                    !event.summary.toLowerCase().contains(this.mQuery.toLowerCase()) &&
                    !event.date.getDateString().toLowerCase().contains(this.mQuery.toLowerCase())) {
                iterator.remove();
            }
        }
    }
}

