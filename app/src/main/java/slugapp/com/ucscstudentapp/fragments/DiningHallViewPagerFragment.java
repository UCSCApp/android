package slugapp.com.ucscstudentapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.http.DiningHallHttpRequest;
import slugapp.com.ucscstudentapp.interfaces.ActivityCallback;
import slugapp.com.ucscstudentapp.interfaces.HttpCallback;
import slugapp.com.ucscstudentapp.models.DiningHall;
import slugapp.com.ucscstudentapp.models.FoodMenu;

/**
 * Created by isayyuhh on 6/3/16.
 */
public class DiningHallViewPagerFragment extends BaseDetailFragment {
    private String diningHallName;
    private DiningHall diningHall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Bundle b = getArguments();
        this.diningHallName = b.getString("name");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_dining_hall, container, false);
        this.setLayout(diningHallName, R.id.dining_button);
        this.setView(view);

        return view;
    }

    @Override
    protected void setView(final View view) {
        new DiningHallHttpRequest(getActivity(), diningHallName).execute(new HttpCallback<DiningHall>() {
            @Override
            public void onSuccess(DiningHall val) {
                diningHall = val;
                ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
                pager.setOffscreenPageLimit(2);
                pager.setAdapter(new MyPagerAdapter(ac, getChildFragmentManager()));
                pager.setCurrentItem(getTod());
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }

    private int getTod() {
        int currentTime = today.getStartTime();
        String currentTOD = today.getStartTOD().toLowerCase();

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
            b.putString("name", diningHall.getCollege());
            MapFragment fragment = new MapFragment();
            fragment.setArguments(b);
            this.ac.setFragment(fragment);
            */
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        private ActivityCallback ac;
        private String tabtitles[] = new String[] { "Breakfast", "Lunch", "Dinner" };

        public MyPagerAdapter(ActivityCallback ac, FragmentManager fm) {
            super(fm);
            this.ac = ac;
        }

        @Override
        public Fragment getItem(int position) {
            FoodMenu menu;
            switch (position) {
                default:
                case 0:
                    menu = diningHall.getBreakfast();
                    break;
                case 1:
                    menu = diningHall.getLunch();
                    break;
                case 2:
                    menu = diningHall.getDinner();
                    break;
            }
            Log.e("TEST", menu.get(0).name());

            Bundle b = new Bundle();
            b.putString("json", this.ac.getGson().toJson(menu));
            b.putString("name", diningHallName);
            DiningHallDetailFragment fragment = new DiningHallDetailFragment();
            fragment.setArguments(b);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabtitles[position];
        }
    }
}
