package slugapp.com.sluglife.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.databinding.ViewEventBinding;
import slugapp.com.sluglife.http.ImageHttpRequest;
import slugapp.com.sluglife.models.DateObject;
import slugapp.com.sluglife.models.EventObject;

/**
 * Created by isaiah on 6/27/2015.
 * <p/>
 * This file contains a view fragment that displays event information.
 */
public class EventViewFragment extends BaseViewFragment {
    private ViewEventBinding mBinding;
    private EventObject mEvent;

    /**
     * Gets a new instance of fragment
     *
     * @param context Activity context
     * @param event   Event
     * @return New instance of fragment
     */
    public static EventViewFragment newInstance(Context context, EventObject event) {
        EventViewFragment fragment = new EventViewFragment();

        Bundle b = new Bundle();
        b.putSerializable(context.getString(R.string.bundle_json), event);
        fragment.setArguments(b);

        return fragment;
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
                R.layout.view_event, container, false);

        this.setViewFragment();
        this.mCallback.setUpEnabled(true);

        return this.mBinding.getRoot();
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
            case android.R.id.home:
                this.getFragmentManager().popBackStackImmediate();
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
        this.mEvent = (EventObject) b.getSerializable(this.mContext.getString(R.string.bundle_json));
        if (this.mEvent != null) this.mName = this.mEvent.getTitleName();
    }

    /**
     * Sets fields
     */
    @Override
    protected void setFields() {
    }

    /**
     * Sets fragment view
     */
    @Override
    protected void setView() {
        DateObject date = this.mEvent.date;

        if (date.defined) {
            String dayString = this.mContext.getString(R.string.detail_event_day) + date.getDateString();
            String startString = this.mContext.getString(R.string.detail_event_start) + date.getTimeString();

            this.mBinding.title.setText(this.mEvent.name);
            this.mBinding.day.setText(dayString);
            this.mBinding.time.setText(startString);
        } else {
            String dayString = this.mContext.getString(R.string.detail_event_day) + this.mEvent.date.getDateString();
            this.mBinding.title.setText(this.mEvent.name);
            this.mBinding.day.setText(dayString);
            this.hideViews(this.mBinding.time);
        }

        this.mBinding.summary.setText(this.mEvent.summary);
        new ImageHttpRequest(this.mEvent.image).execute(this.mBinding.image);
    }
}
