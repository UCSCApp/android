package slugapp.com.ucscstudentapp.fragments;

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

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.http.DiningHallHttpRequest;
import slugapp.com.ucscstudentapp.http.TestDiningHallHttpRequest;
import slugapp.com.ucscstudentapp.interfaces.ActivityCallback;
import slugapp.com.ucscstudentapp.interfaces.HttpCallback;
import slugapp.com.ucscstudentapp.models.DiningHall;
import slugapp.com.ucscstudentapp.models.FoodMenu;

/**
 * Created by isayyuhh on 6/3/16.
 */
public class DiningHallViewPagerFragment extends BaseDetailFragment {
    private String mName;
    private DiningHall mDiningHall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        this.mName = b.getString(getActivity().getString(R.string.name));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_dining_hall, container, false);
        this.setLayout(mName, R.id.dining_button);
        this.setView(view);

        return view;
    }

    @Override
    protected void setView(final View view) {
        new DiningHallHttpRequest(getActivity(), mName).execute(new HttpCallback<DiningHall>() {
            @Override
            public void onSuccess(DiningHall val) {
                mDiningHall = val;
                ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
                pager.setOffscreenPageLimit(2);
                pager.setAdapter(new MyPagerAdapter(mCallback, getChildFragmentManager()));
                pager.setCurrentItem(getTod());
            }

            @Override
            public void onError(Exception e) {
                mDiningHall = new DiningHall();
                ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
                pager.setOffscreenPageLimit(2);
                pager.setAdapter(new MyPagerAdapter(mCallback, getChildFragmentManager()));
                pager.setCurrentItem(getTod());
            }
        });
    }

    private int getTod() {
        int currentTime = this.mToday.getStartTime();
        String currentTOD = this.mToday.getStartTOD().toLowerCase();

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.find_on_map_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // find on map
        if (id == R.id.map_find) {
            /*
            Bundle b = new Bundle();
            b.putString("name", mDiningHall.getCollege());
            MapFragment fragment = new MapFragment();
            fragment.setArguments(b);
            this.mCallback.setFragment(fragment);
            */
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        private ActivityCallback mCallback;
        private final String mTabTitles[] = new String[] { "Breakfast", "Lunch", "Dinner" };

        public MyPagerAdapter(ActivityCallback mCallback, FragmentManager fm) {
            super(fm);
            this.mCallback = mCallback;
        }

        @Override
        public Fragment getItem(int position) {
            FoodMenu menu;
            switch (position) {
                default:
                case 0:
                    menu = mDiningHall.getBreakfast();
                    break;
                case 1:
                    menu = mDiningHall.getLunch();
                    break;
                case 2:
                    menu = mDiningHall.getDinner();
                    break;
            }

            DiningHallDetailFragment fragment = new DiningHallDetailFragment();

            Bundle b = new Bundle();
            b.putString(mContext.getString(R.string.json), this.mCallback.getGson().toJson(menu));
            b.putString(mContext.getString(R.string.name), mName);
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
