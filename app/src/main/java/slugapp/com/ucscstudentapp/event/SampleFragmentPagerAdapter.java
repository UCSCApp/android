package slugapp.com.ucscstudentapp.event;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;

import slugapp.com.ucscstudentapp.R;

/**
 * Created by isayyuhh_s on 6/27/2015.
 */
class SampleFragmentPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
    final int PAGE_COUNT = 4;
    private int tabIcons[] = {R.drawable.ic_events, R.drawable.ic_map, R.drawable.ic_social, R.drawable.ic_settings};
    public SampleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }
    @Override
    public int getPageIconResId(int position) {
        // Generate title based on item position
        return tabIcons[position];
    }
}
