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
import slugapp.com.sluglife.models.DateObject;
import slugapp.com.sluglife.models.DiningHallObject;

/**
 * Created by isaiah on 6/3/16
 * <p/>
 * This file contains a viewpager fragment that displays dining hall food information.
 */
public class DiningViewPagerFragment extends BaseViewFragment {
    private static final int BREAKFAST_START = 0;
    private static final int BREAKFAST_END = 11;
    private static final int BREAKFAST = 0;

    private static final int LUNCH_START = 11;
    private static final int LUNCH_END = 17;
    private static final int LUNCH = 1;

    private static final int DINNER_START = 17;
    private static final int DINNER_END = 24;
    private static final int DINNER = 2;

    private static final int OFFSCREEN_PAGE_LIMIT = 2;

    private ViewpagerDiningBinding mBinding;
    private String mName;
    private DiningHallObject mDiningHall;

    /**
     * Gets a new instance of fragment
     *
     * @param context Activity context
     * @param name    Fragment name
     * @return New instance of fragment
     */
    public static DiningViewPagerFragment newInstance(Context context, String name) {
        DiningViewPagerFragment fragment = new DiningViewPagerFragment();

        Bundle b = new Bundle();
        b.putString(context.getString(R.string.bundle_name), name);
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
                R.layout.viewpager_dining, container, false);

        this.setViewFragment();
        this.mCallback.setUpEnabled(true);

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
        inflater.inflate(R.menu.toolbar_dining_legend, menu);
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
            case R.id.dining_legend:
                this.setDialogFragment(DiningLegendDialogFragment.newInstance());
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
        this.mName = b.getString(this.mContext.getString(R.string.bundle_name));
    }

    /**
     * Sets fields
     */
    @Override
    protected void setFields() {
        this.mDiningHall = new DiningHallObject();
    }

    /**
     * Sets fragment view
     */
    @Override
    protected void setView() {
        this.mBinding.date.setText(DateObject.getToday(this.mContext).getDateString());

        new DiningHallHttpRequest(this.mContext, this.mName).execute(
                new HttpCallback<DiningHallObject>() {

                    /**
                     * On request success
                     *
                     * @param value Dining hall object from request
                     */
                    @Override
                    public void onSuccess(DiningHallObject value) {
                        mDiningHall = value;
                        this.setPager(mBinding.pager);
                    }

                    /**
                     * On request error
                     *
                     * @param e Exception
                     */
                    @Override
                    public void onError(Exception e) {
                        mDiningHall = new DiningHallObject();
                        this.setPager(mBinding.pager);
                    }

                    /**
                     * Sets viewpager with request information
                     *
                     * @param pager Viewpager
                     */
                    private void setPager(ViewPager pager) {
                        pager.setOffscreenPageLimit(OFFSCREEN_PAGE_LIMIT);
                        pager.setAdapter(new DiningPagerAdapter(getChildFragmentManager()));
                        pager.setCurrentItem(getTimeOfDay());
                        mBinding.tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(mContext,
                                R.color.ucsc_yellow));
                        mBinding.tabs.setTabTextColors(Color.WHITE, Color.WHITE);
                        mBinding.tabs.setupWithViewPager(pager);
                    }
                });
    }

    /**
     * Gets the current time of day from today's date
     *
     * @return Gets tab index based on time of day
     */
    private int getTimeOfDay() {
        int currentTime = DateObject.getToday(this.mContext).hour;

        if (currentTime >= BREAKFAST_START && currentTime < BREAKFAST_END) return BREAKFAST;
        else if (currentTime >= LUNCH_START && currentTime < LUNCH_END) return LUNCH;
        else if (currentTime >= DINNER_START && currentTime < DINNER_END) return DINNER;

        return BREAKFAST;
    }

    /**
     * Class containing a viewpager adapter
     */
    private class DiningPagerAdapter extends FragmentStatePagerAdapter {
        private final String mTabTitles[] = new String[]{mContext.getString(R.string.title_dining_viewpager_breakfast), mContext.getString(R.string.title_dining_viewpager_lunch), mContext.getString(R.string.title_dining_viewpager_dinner)};

        /**
         * Constructor
         *
         * @param fragmentManager Fragment manager
         */
        public DiningPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        /**
         * Gets viewpager item at position
         *
         * @param position Position of viewpager item
         * @return Fragment at position
         */
        @Override
        public Fragment getItem(int position) {
            return DiningViewFragment.newInstance(mContext, mName,
                    position == DINNER ? mDiningHall.dinner
                            : position == LUNCH ? mDiningHall.lunch
                            : mDiningHall.breakfast);
        }

        /**
         * Gets number of views in viewpager
         *
         * @return Number of views in viewpager
         */
        @Override
        public int getCount() {
            return 3;
        }

        /**
         * Gets tab title at position
         *
         * @param position Position of tab title
         * @return Tab title
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }
}
