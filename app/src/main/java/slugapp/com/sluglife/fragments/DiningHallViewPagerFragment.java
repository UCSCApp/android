package slugapp.com.sluglife.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.http.DiningHallHttpRequest;
import slugapp.com.sluglife.interfaces.ActivityCallback;
import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.DiningHall;
import slugapp.com.sluglife.models.FoodMenu;

/**
 * Created by isayyuhh on 6/3/16.
 */
public class DiningHallViewPagerFragment extends BaseViewFragment {
    private FragmentEnum fragmentEnum = FragmentEnum.DINING;

    private String mName;
    private DiningHall mDiningHall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_dining, container, false);

        this.setDetailFragment(view, this.fragmentEnum, this.mName);

        return view;
    }

    @Override
    protected void setFields(View view) {
        Bundle b = this.getArguments();

        this.mName = b.getString(this.mContext.getString(R.string.bundle_name));
        this.mDiningHall = new DiningHall();
    }

    @Override
    protected void setView(final View view) {
        new DiningHallHttpRequest(this.mContext, this.mName).execute(
                new HttpCallback<DiningHall>() {
            @Override
            public void onSuccess(DiningHall val) {
                mDiningHall = val;
                ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
                pager.setOffscreenPageLimit(2);
                pager.setAdapter(new DiningPagerAdapter(mCallback, getChildFragmentManager()));
                pager.setCurrentItem(getTimeOfDay());
            }

            @Override
            public void onError(Exception e) {
                mDiningHall = new DiningHall();
                ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
                pager.setOffscreenPageLimit(2);
                pager.setAdapter(new DiningPagerAdapter(mCallback, getChildFragmentManager()));
                pager.setCurrentItem(getTimeOfDay());
            }
        });
    }

    private int getTimeOfDay() {
        int currentTime = this.mToday.startTime;
        String currentTOD = this.mToday.startTOD.toLowerCase();

        if ((currentTime == 12 || currentTime < 11) && currentTOD.compareTo("am") == 0) {
            return 0;
        } else if (currentTime == 11 && currentTOD.compareTo("am") == 0 ||
                currentTime == 12 && currentTOD.compareTo("pm") == 0 ||
                currentTime > 0 && currentTime < 5 && currentTOD.compareTo("pm") == 0) {
            return 1;
        } else if (currentTime >= 5 && currentTime < 12 && currentTOD.compareTo("pm") == 0) {
            return 2;
        }
        return 0;
    }

    private class DiningPagerAdapter extends FragmentStatePagerAdapter {
        private ActivityCallback mCallback;
        private final String mTabTitles[] = new String[] { "Breakfast", "Lunch", "Dinner" };

        public DiningPagerAdapter(ActivityCallback mCallback, FragmentManager fm) {
            super(fm);
            this.mCallback = mCallback;
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
            b.putString(mContext.getString(R.string.bundle_json), this.mCallback.getGson().toJson(menu));
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
