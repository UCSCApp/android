package slugapp.com.sluglife.activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.fabric.sdk.android.Fabric;
import slugapp.com.sluglife.R;
import slugapp.com.sluglife.databinding.ActivityMainBinding;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.interfaces.ActivityCallback;
import slugapp.com.sluglife.runnables.LoopRunnable;

/**
 * Created by isaiah on 6/27/2015
 * <p/>
 * This file contains the main activity. It handles all of the pages of the app in the form of
 * fragments, and contains top and bottom Toolbars.
 */
public class MainActivity extends AppCompatActivity implements ActivityCallback {
    private static final List<FragmentEnum> TAB_FRAGMENTS = Arrays.asList(FragmentEnum.values());
    private static final FragmentEnum START_FRAGMENT = FragmentEnum.MAP;

    private static final int EMPTY = 0;
    private static final int FIRST_FRAGMENT = 0;

    private ActivityMainBinding mBinding;
    private TextView mTitle;
    private Timer mTimer;

    // TODO: remove all boilerplate code and train wrecks

    /**
     * Activity's onCreate method
     *
     * @param savedInstanceState Saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        this.setFields();
        this.setTopToolbar();
        this.setBottomToolbar();
        this.setTabFragment(getTabFragment(START_FRAGMENT));
    }

    /**
     * Initializes the activity's fields
     */
    private void setFields() {
        this.mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Fabric.with(this, new Twitter(
                new TwitterAuthConfig(
                        this.getString(R.string.social_key),
                        this.getString(R.string.social_secret))
        ));
    }

    /**
     * Initializes the top toolbar
     */
    private void setTopToolbar() {
        this.setSupportActionBar(this.mBinding.topToolbar);
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
        AHBottomNavigation bottomToolbar = this.mBinding.bottomToolbar;

        for (FragmentEnum fragmentEnum : TAB_FRAGMENTS) {
            bottomToolbar.addItem(new AHBottomNavigationItem(fragmentEnum.name,
                    fragmentEnum.imageId, R.color.ucsc_blue));
        }

        bottomToolbar.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.ucsc_blue));

        bottomToolbar.setAccentColor(ContextCompat.getColor(this, R.color.ucsc_yellow));
        bottomToolbar.setInactiveColor(Color.WHITE);

        bottomToolbar.setForceTint(true);
        bottomToolbar.setForceTitlesDisplay(true);

        bottomToolbar.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                for (FragmentEnum fragmentEnum : TAB_FRAGMENTS) {
                    if (position != fragmentEnum.position) continue;
                    setTabFragment(getTabFragment(fragmentEnum));
                }
                return true;
            }
        });
    }

    /**
     * Gets the fragment instance form the given tab fragment
     *
     * @param fragmentEnum Fragment enum
     * @return Tab fragment
     */
    @Nullable
    private static Fragment getTabFragment(FragmentEnum fragmentEnum) {
        try {
            Class<?> fragmentClass = fragmentEnum.fragment;
            Constructor<?> fragmentConstructor = fragmentClass.getConstructor();
            return (Fragment) fragmentConstructor.newInstance();
        } catch (NoSuchMethodException nsme) {
            return null;
        } catch (InstantiationException ie) {
            return null;
        } catch (IllegalAccessException iae) {
            return null;
        } catch (InvocationTargetException ite) {
            return null;
        }
    }

    /**
     * Sets the given tab fragment
     *
     * @param fragment Tab fragment to set
     */
    @Override
    public void setTabFragment(Fragment fragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > EMPTY) {
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(
                    FIRST_FRAGMENT);
            fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.view_fragment, fragment);
        ft.commit();
    }

    /**
     * Sets the given fragment
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
     * Hides soft keyboard
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
     * Shows soft keyboard to focus on view
     *
     * @param view View for keyboard to focus
     */
    @Override
    public void showKeyboard(View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Displays snack bar with text
     *
     * @param text Text to display
     */
    @Override
    public void showSnackBar(String text) {
        final Snackbar snackbar = Snackbar.make(this.mBinding.coordinatorLayout, text,
                Snackbar.LENGTH_INDEFINITE);

        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.ucsc_yellow));
        snackbar.setAction(this.getString(R.string.snackbar_dismiss), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    /**
     * Sets new toolbar title
     *
     * @param text New toolbar title
     */
    @Override
    public void setToolbarTitle(String text) {
        this.mTitle.setText(text);
    }

    /**
     * Sets toolbar's home up enabled
     *
     * @param enabled If toolbar's home up is enabled
     */
    @Override
    public void setUpEnabled(boolean enabled) {
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
        }
    }

    /**
     * Schedules timer given a handler, a runnable, a delay time, and a time period
     *
     * @param runnable Runnable that runs process
     * @param delay    Delay time
     * @param period   Period of time
     */
    @Override
    public void scheduleTimer(final LoopRunnable runnable, long delay,
                              int period) {
        final Handler handler = new Handler();
        this.mTimer = new Timer();
        this.mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, delay, period);
    }

    /**
     * Cancels timer if active
     */
    @Override
    public void cancelTimer() {
        if (this.mTimer != null) this.mTimer.cancel();
    }
}
