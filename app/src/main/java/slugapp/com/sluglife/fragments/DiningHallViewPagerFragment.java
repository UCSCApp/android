package slugapp.com.sluglife.fragments;

//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.databinding.ViewpagerDiningBinding;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.http.DiningHallHttpRequest;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.Date;
import slugapp.com.sluglife.models.DiningHall;
import slugapp.com.sluglife.models.FoodMenu;

/**
 * Created by isayyuhh on 6/3/16
 */
public class DiningHallViewPagerFragment extends BaseViewFragment {
    private ViewpagerDiningBinding mBinding;
    private String mName;
    private DiningHall mDiningHall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mBinding = DataBindingUtil.inflate(this.getActivity().getLayoutInflater(),
                R.layout.viewpager_dining, container, false);

        this.setViewFragment(this.mName);

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
                        mBinding.tabs.setSelectedTabIndicatorColor(getResources().getColor(
                                R.color.UcscYellow));
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
            FoodMenu menu;
            switch (position) {
                default:
                case 0:
                    menu = mDiningHall.breakfast;
                    break;
                case 1:
                    menu = mDiningHall.lunch;
                    break;
                case 2:
                    menu = mDiningHall.dinner;
                    break;
            }

            DiningHallViewFragment fragment = new DiningHallViewFragment();

            Bundle b = new Bundle();
            b.putSerializable(mContext.getString(R.string.bundle_json), menu);
            b.putString(mContext.getString(R.string.bundle_name), mName);
            fragment.setArguments(b);

            return fragment;
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
