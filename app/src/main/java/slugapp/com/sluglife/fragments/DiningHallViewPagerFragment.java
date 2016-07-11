package slugapp.com.sluglife.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.databinding.ViewpagerDiningBinding;
import slugapp.com.sluglife.http.DiningHallHttpRequest;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.Date;
import slugapp.com.sluglife.models.DiningHall;

/**
 * Created by isayyuhh on 6/3/16
 */
public class DiningHallViewPagerFragment extends BaseViewFragment {
    private ViewpagerDiningBinding mBinding;
    private String mName;
    private DiningHall mDiningHall;

    public static DiningHallViewPagerFragment newInstance(Context context, String name) {
        DiningHallViewPagerFragment fragment = new DiningHallViewPagerFragment();

        Bundle b = new Bundle();
        b.putString(context.getString(R.string.bundle_name), name);
        fragment.setArguments(b);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mBinding = DataBindingUtil.inflate(this.getActivity().getLayoutInflater(),
                R.layout.viewpager_dining, container, false);

        this.setViewFragment();
        this.mCallback.setUpEnabled(true);

        return this.mBinding.getRoot();
    }

    @Override
    protected void setArgumentFields(Bundle b) {
        this.mName = b.getString(this.mContext.getString(R.string.bundle_name));
    }

    @Override
    protected void setFields() {
        this.mDiningHall = new DiningHall();
    }

    @Override
    protected void setView() {
        String date = Date.getToday().month + " " + Date.getToday().day;
        this.mBinding.date.setText(date);

        new DiningHallHttpRequest(this.mContext, this.mName).execute(
                new HttpCallback<DiningHall>() {
                    @Override
                    public void onSuccess(DiningHall val) {
                        mDiningHall = val;
                        this.setPager(mBinding.pager);
                    }

                    @Override
                    public void onError(Exception e) {
                        mDiningHall = new DiningHall();
                        this.setPager(mBinding.pager);
                    }

                    private void setPager(ViewPager pager) {
                        pager.setOffscreenPageLimit(2);
                        pager.setAdapter(new DiningPagerAdapter(getChildFragmentManager()));
                        pager.setCurrentItem(getTimeOfDay());
                        mBinding.tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(mContext,
                                R.color.ucsc_yellow));
                        mBinding.tabs.setTabTextColors(Color.WHITE, Color.WHITE);
                        mBinding.tabs.setupWithViewPager(pager);
                    }
                });
    }

    private int getTimeOfDay() {
        int currentTime = Date.getCurrentTime();

        if (currentTime >= 0 && currentTime < 11) return 0;
        else if (currentTime >= 11 && currentTime < 17) return 1;
        else if (currentTime >= 17 && currentTime < 24) return 2;
        return 0;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_dining_legend, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.getFragmentManager().popBackStackImmediate();
                return true;
            case R.id.dining_legend:
                this.setDialogFragment(new DiningLegendDialogFragment());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class DiningPagerAdapter extends FragmentStatePagerAdapter {
        private final String mTabTitles[] = new String[]{mContext.getString(R.string.title_dining_viewpager_breakfast), mContext.getString(R.string.title_dining_viewpager_lunch), mContext.getString(R.string.title_dining_viewpager_dinner)};

        public DiningPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DiningHallViewFragment.newInstance(mContext, mName,
                    position == 2 ? mDiningHall.dinner : position == 1 ? mDiningHall.lunch :
                                    mDiningHall.breakfast);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }
}
