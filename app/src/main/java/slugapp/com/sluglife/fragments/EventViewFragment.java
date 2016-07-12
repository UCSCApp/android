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
import slugapp.com.sluglife.models.Date;
import slugapp.com.sluglife.models.Event;

/**
 * Created by isaiah on 6/27/2015.
 * <p/>
 * This file displays all of the contents for each individual mEvent.
 */
public class EventViewFragment extends BaseViewFragment {
    private ViewEventBinding mBinding;
    private Event mEvent;

    public static EventViewFragment newInstance(Context context, Event event) {
        EventViewFragment fragment = new EventViewFragment();

        Bundle b = new Bundle();
        b.putSerializable(context.getString(R.string.bundle_json), event);
        fragment.setArguments(b);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mBinding = DataBindingUtil.inflate(this.getActivity().getLayoutInflater(),
                R.layout.view_event, container, false);

        this.setViewFragment();
        this.mCallback.setUpEnabled(true);

        return this.mBinding.getRoot();
    }

    @Override
    protected void setArgumentFields(Bundle b) {
        this.mEvent = (Event) b.getSerializable(this.mContext.getString(R.string.bundle_json));
        if (this.mEvent != null) this.mName = this.mEvent.name;
    }

    @Override
    protected void setFields() {
    }

    @Override
    protected void setView() {
        Date date = this.mEvent.date;

        if (date.defined) {
            String dayString = this.mContext.getString(R.string.detail_event_day) + date.getDateString();
            String startString = this.mContext.getString(R.string.detail_event_start) + date.getTimeString();
            /*
            String endString = this.mContext.getString(R.string.detail_event_end) + String.valueOf(this.mEvent.date.endTime) +
                    this.mEvent.date.endTOD;
            */

            this.mBinding.day.setText(dayString);
            this.mBinding.start.setText(startString);
            this.mBinding.end.setVisibility(View.GONE);
            //this.mBinding.end.setText(endString);
        } else {
            String dayString = this.mContext.getString(R.string.detail_event_day) + this.mEvent.date.getDateString();
            this.mBinding.day.setText(dayString);
            this.mBinding.start.setVisibility(View.GONE);
            this.mBinding.end.setVisibility(View.GONE);
        }

        this.mBinding.summary.setText(this.mEvent.summary);
        new ImageHttpRequest(this.mEvent.image).execute(this.mBinding.image);
    }

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
}
