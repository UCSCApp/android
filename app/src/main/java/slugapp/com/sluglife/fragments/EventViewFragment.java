package slugapp.com.sluglife.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.databinding.ViewEventBinding;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.http.ImageHttpRequest;
import slugapp.com.sluglife.models.Event;

/**
 * Created by isaiah on 6/27/2015.
 * <p/>
 * This file displays all of the contents for each individual mEvent.
 */
public class EventViewFragment extends BaseViewFragment {
    private static final FragmentEnum FRAGMENT = FragmentEnum.EVENT;

    private ViewEventBinding mBinding;
    private Event mEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mBinding = DataBindingUtil.inflate(this.getActivity().getLayoutInflater(),
                R.layout.view_event, container, false);

        this.setViewFragment(FRAGMENT, this.mName);

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
        if (this.mEvent.date.defined) {
            String dayString = this.mContext.getString(R.string.detail_event_day) + this.mEvent.date.month.month + " " + this.mEvent.date.day;
            String startString = this.mContext.getString(R.string.detail_event_start) + String.valueOf(this.mEvent.date.startTime) +
                    this.mEvent.date.startTOD;
            String endString = this.mContext.getString(R.string.detail_event_end) + String.valueOf(this.mEvent.date.endTime) +
                    this.mEvent.date.endTOD;

            this.mBinding.day.setText(dayString);
            this.mBinding.start.setText(startString);
            this.mBinding.end.setText(endString);
        } else {
            String dayString = this.mContext.getString(R.string.detail_event_day) + this.mEvent.date.string;
            this.mBinding.day.setText(dayString);
            this.mBinding.start.setVisibility(View.GONE);
            this.mBinding.end.setVisibility(View.GONE);
        }

        this.mBinding.summary.setText(this.mEvent.summary);
        new ImageHttpRequest(this.mEvent.image).execute(this.mBinding.image);
    }
}
