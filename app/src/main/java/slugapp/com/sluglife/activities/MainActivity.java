package slugapp.com.sluglife.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import io.fabric.sdk.android.Fabric;
import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.interfaces.ActivityCallback;
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
     * Initializes the activity's fields
     */
    private void setFields() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(this.getString(R.string.social_key),
                this.getString(R.string.social_secret));
        Fabric.with(this, new Twitter(authConfig));

        this.mGson = new Gson();

        this.init = true;
    }

    /**
     * Initializes the top toolbar
     */
    private void setTopToolbar() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.top_toolbar);
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
     * Gets the fragment instance form the given fragment enum
     *
     * @param fragmentEnum Fragment enum
     * @return Fragment from fragment enum
     */
    private Fragment getTabFragment(FragmentEnum fragmentEnum) {
        try {
            Class<?> fragmentClass = fragmentEnum.getFragment();
            Constructor<?> fragmentConstructor = fragmentClass.getConstructor();
            return (Fragment) fragmentConstructor.newInstance();
        } catch (Exception e) {
            return null;
        }
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
        ft.replace(R.id.view_fragment, fragment);
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
            button.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.toggle_off));
        }
        View button = this.findViewById(buttonId);
        button.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.toggle_on));
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
     * Get Gson
     *
     * @return Gson
     */
    @Override
    public Gson getGson() {
        return this.mGson;
    }
}
