package slugapp.com.sluglife.activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import io.fabric.sdk.android.Fabric;
import slugapp.com.sluglife.R;
import slugapp.com.sluglife.databinding.ActivityMainBinding;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.interfaces.ActivityCallback;

/**
 * Created by isaiah on 6/27/2015.
 * <p/>
 * This file contains the MainActivity. It handles all of the pages of the app in the form of
 * Fragments, and contains Top and Bottom Toolbars.
 */

public class MainActivity extends AppCompatActivity implements ActivityCallback {
    private static final List<FragmentEnum> sTabFragments = Arrays.asList(FragmentEnum.values());
    private static final FragmentEnum sStartFragment = FragmentEnum.MAP;

    private ActivityMainBinding mBinding;
    private TextView mTitle;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setFields();
        this.setTopToolbar();
        this.setBottomToolbar();
        this.setTabFragment(this.getTabFragment(sStartFragment));
    }

    /**
     * Initializes the activity's fields
     */
    private void setFields() {
        this.mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(
                this.getString(R.string.social_key),
                this.getString(R.string.social_secret));
        Fabric.with(this, new Twitter(authConfig));
    }

    /**
     * Initializes the top toolbar
     */
    private void setTopToolbar() {
        Toolbar toolbar = this.mBinding.topToolbar;
        this.setSupportActionBar(toolbar);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayShowTitleEnabled(false);
            this.getSupportActionBar().setHomeButtonEnabled(true);
        }
        this.mTitle = this.mBinding.toolbarTitle;
    }

    /**
     * Initializes the bottom toolbar
     */
    private void setBottomToolbar() {
        AHBottomNavigation bottom = this.mBinding.bottomToolbar;

        for (FragmentEnum fragmentEnum : sTabFragments) {
            bottom.addItem(new AHBottomNavigationItem(fragmentEnum.name, fragmentEnum.imageId,
                    R.color.UcscBlue));
        }

        bottom.setDefaultBackgroundColor(this.getResources().getColor(R.color.UcscBlue));

        bottom.setAccentColor(this.getResources().getColor(R.color.UcscYellow));
        bottom.setInactiveColor(Color.WHITE);

        bottom.setForceTint(true);
        bottom.setForceTitlesDisplay(true);

        bottom.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                for (FragmentEnum fragmentEnum : sTabFragments) {
                    if (position != fragmentEnum.position) continue;
                    setTabFragment(getTabFragment(fragmentEnum));
                }
                return true;
            }
        });
    }

    /**
     * Gets the fragment instance form the given fragment enum
     *
     * @param fragmentEnum Fragment enum
     * @return Fragment from fragment enum
     */
    private Fragment getTabFragment(FragmentEnum fragmentEnum) {
        try {
            Class<?> fragmentClass = fragmentEnum.fragment;
            Constructor<?> fragmentConstructor = fragmentClass.getConstructor();
            return (Fragment) fragmentConstructor.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Sets the current tab fragment
     *
     * @param fragment Fragment to set
     */
    private void setTabFragment(Fragment fragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
            fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.view_fragment, fragment);
        ft.commit();
    }

    /**
     * Sets the current fragment
     *
     * @param fragment Fragment to set
     */
    @Override
    public void setFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.view_fragment, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * Hide soft keyboard
     */
    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view == null) return;
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Set new toolbar title
     *
     * @param newTitle New toolbar title
     */
    @Override
    public void setTitle(String newTitle) {
        this.mTitle.setText(newTitle);
    }

    /**
     * Initializes timer
     */
    @Override
    public void initTimer() {
        this.mTimer = new Timer();
    }

    /**
     * Get timer
     *
     * @return Timer
     */
    @Override
    public Timer getTimer() {
        return this.mTimer;
    }
}
