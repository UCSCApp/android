package slugapp.com.sluglife.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.gson.Gson;

import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.enums.MonthEnum;
import slugapp.com.sluglife.interfaces.ActivityCallback;
import slugapp.com.sluglife.models.Date;
import slugapp.com.sluglife.models.ToolbarButton;

/**
 * Created by isaiah on 6/27/2015.
 * <p/>
 * This file contains the MainActivity. It handles all of the pages of the app in the form of
 * Fragments, and contains Top and Bottom Toolbars.
 */

public class MainActivity extends AppCompatActivity implements ActivityCallback {
    private static final List<FragmentEnum> sTabFragments = Arrays.asList(FragmentEnum.values());
    private static final FragmentEnum sStartFragment = FragmentEnum.MAP;

    private FragmentManager mFragmentManager;
    private TextView mTitle;
    private Timer mTimer;
    private Gson mGson;

    private boolean init;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_main);
        this.setFields();
        this.setTopToolbar();
        this.setBottomToolbar();
        this.setFragment(this.getTabFragment(sStartFragment));
    }

    /**
     * Initializes the main activity's fields
     */
    private void setFields() {
        this.init = true;
        this.mGson = new Gson();
    }

    /**
     * Initializes the top toolbar
     */
    private void setTopToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        this.setSupportActionBar(toolbar);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayShowTitleEnabled(false);
            this.getSupportActionBar().setHomeButtonEnabled(true);
        }
        this.mTitle = (TextView) findViewById(R.id.toolbar_title);
    }

    /**
     * Initializes the bottom toolbar
     */
    private void setBottomToolbar() {
        LinearLayout bottom = (LinearLayout) this.findViewById(R.id.bottom_toolbar);
        View child = this.getLayoutInflater().inflate(R.layout.toolbar_bottom, bottom, false);
        for (FragmentEnum fragment : sTabFragments) {
            this.setButton(child, fragment.getButtonId(), fragment.getImageId());
        }
        bottom.addView(child);

        ToolbarButton startButton = (ToolbarButton) this.findViewById(sStartFragment.getButtonId());
        startButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_on));
    }

    /**
     * Sets bottom toolbar button
     *
     * @param child    Child view
     * @param buttonId Id of button
     * @param imageId  Id of image
     */
    private void setButton(View child, int buttonId, int imageId) {
        ToolbarButton button = (ToolbarButton) child.findViewById(buttonId);
        button.setImageResource(imageId);
    }

    /**
     * Get list of tab sTabFragments
     *
     * @return List of tab sTabFragments
     */
    @Override
    public List<FragmentEnum> getFragments() {
        return sTabFragments;
    }

    /**
     * Get current fragment manager
     *
     * @return Current fragment manager
     */
    @Override
    public FragmentManager fm() {
        return mFragmentManager;
    }

    /**
     * Sets the current fragment
     *
     * @param fragment Fragment to set
     */
    @Override
    public void setFragment(Fragment fragment) {
        this.mFragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = this.mFragmentManager.beginTransaction();
        ft.replace(R.id.listFragment, fragment);
        if (!this.init) ft.addToBackStack(null);
        ft.commit();
        this.init = false;
    }

    /**
     * Set bottom toolbar buttons
     *
     * @param buttonId Id of button to set
     */
    @Override
    public void setButtons(int buttonId) {
        for (FragmentEnum fragment : sTabFragments) {
            View button = this.findViewById(fragment.getButtonId());
            button.setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_off));
        }
        View button = this.findViewById(buttonId);
        button.setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_on));
    }

    /**
     * Hide soft keyboard
     */
    @Override
    public void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Get mToday's date
     *
     * @return Today's date
     */
    @Override
    public Date getToday() {
        java.util.Date date = Calendar.getInstance().getTime();
        String month = new SimpleDateFormat("MM").format(date);
        String day = new SimpleDateFormat("dd").format(date);
        String hour = new SimpleDateFormat("hh").format(date);
        String tod = new SimpleDateFormat("aa").format(date);
        String todayMonth = MonthEnum.JANUARY.getVal();

        for (MonthEnum currMonth : MonthEnum.values()) {
            if (currMonth.getOrder() != Integer.valueOf(month)) continue;
            todayMonth = currMonth.getVal();
            break;
        }
        return new Date(todayMonth, day, hour + tod, hour + tod);
    }

    /**
     * Set new toolbar mTitle
     *
     * @param newTitle New toolbar mTitle
     */
    @Override
    public void setTitle(String newTitle) {
        this.mTitle.setText(newTitle);
    }

    /**
     * Initializes Timer
     */
    @Override
    public void initTimer() {
        this.mTimer = new Timer();
    }

    /**
     * Get mTimer
     *
     * @return Timer
     */
    @Override
    public Timer getTimer() {
        return this.mTimer;
    }

    /**
     * Get resource string
     *
     * @param id Id of resource
     * @return String
     */
    @Override
    public String toStr(int id) {
        return this.getResources().getString(id);
    }

    /**
     * Get resource bitmap
     *
     * @param id Id of bitmap
     * @return Bitmap
     */
    @Override
    public BitmapDescriptor toBitMap(int id) {
        return BitmapDescriptorFactory.fromResource(id);
    }

    /**
     * Get Gson
     *
     * @return Gson
     */
    @Override
    public Gson getGson() {
        return this.mGson;
    }

    /**
     * Gets the fragment instance form the given fragment enum
     *
     * @param fragmentEnum Fragment enum
     * @return Fragment from fragment enum
     */
    @Override
    public Fragment getTabFragment(FragmentEnum fragmentEnum) {
        try {
            Class<?> fragmentClass = fragmentEnum.getFragment();
            Constructor<?> fragmentConstructor = fragmentClass.getConstructor();
            return (Fragment) fragmentConstructor.newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
