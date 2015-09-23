package slugapp.com.ucscstudentapp.main;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.fabric.sdk.android.Fabric;
import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.event.Date;
import slugapp.com.ucscstudentapp.event.EventList;

/**
 * Created by isaiah on 6/27/2015.
 * <p>
 * This file contains the MainActivity. It handles all of the pages of the app in the form of
 * Fragments, and contains Top and Bottom Toolbars.
 */

public class MainActivity extends AppCompatActivity implements ActivityReference {
    private SwipeRefreshLayout swipeLayout;
    private FragmentManager fm;
    private TextView title;
    private Date today;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "pkpaLGZDDFZyBViV2ScOOcz2R";
    private static final String TWITTER_SECRET = "8GqvJRMgLgbQpphUKfnUx7WLZaK2iRHxZ0VU27uYwtO1GrT82a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This is the Twitter Authorization for fabric to use Twitter services in the app
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        // sets views
        setContentView(R.layout.activity_main);
        setTopToolbar();
        setBottomToolbar();
        setFragment();
        setToday();
    }

    /*
     * Sets up the Top Toolbar
     */
    private void setTopToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        title = (TextView) findViewById(R.id.toolbar_title);
    }

    /*
     * Sets up the Bottom Toolbar
     */
    private void setBottomToolbar() {
        LinearLayout bottom = (LinearLayout) findViewById(R.id.bottom_toolbar);
        View child = getLayoutInflater().inflate(R.layout.toolbar_bottom, bottom, false);

        // Bottom Buttons
        ToggleImageButton events_button = (ToggleImageButton) child.findViewById(R.id.events_button);
        events_button.setImageResource(R.drawable.ic_events);
        ToggleImageButton dining_button = (ToggleImageButton) child.findViewById(R.id.dining_button);
        dining_button.setImageResource(R.drawable.ic_dining);
        ToggleImageButton map_button = (ToggleImageButton) child.findViewById(R.id.map_button);
        map_button.setImageResource(R.drawable.ic_map);

        /*
        ToggleImageButton social_button = (ToggleImageButton) child.findViewById(R.id.social_button);
        social_button.setImageResource(R.drawable.ic_social);
        ToggleImageButton settings_button = (ToggleImageButton) child.findViewById(R.id.settings_button);
        settings_button.setImageResource(R.drawable.ic_settings);
        */

        ToggleImageButton.setIds(events_button, dining_button, map_button); //, social_button, settings_button);
        bottom.addView(child);
        events_button.setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_on));
    }

    /*
     * Sets up the Fragment
     */
    private void setFragment() {
        // Initial Fragment
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        EventList llf = new EventList();
        ft.replace(R.id.listFragment, llf);
        ft.commit();
    }

    /**
     * Interface methods
     */
    @Override
    public Date today() {
        return today;
    }

    @Override
    public void setToday() {
        java.util.Date date = Calendar.getInstance().getTime();
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        SimpleDateFormat hour = new SimpleDateFormat("hh");
        SimpleDateFormat tod = new SimpleDateFormat("aa");
        String todaysMonth =
                slugapp.com.ucscstudentapp.event.Date.MONTHS[Integer.parseInt(month.format(date)) - 1];
        this.today = new slugapp.com.ucscstudentapp.event.Date(
                 Character.toUpperCase(todaysMonth.charAt(0)) + todaysMonth.substring(1) +
                        " " + day.format(date) + " " + Integer.parseInt(hour.format(date)) + tod.format(date)
                        + " " + Integer.parseInt(hour.format(date)) + tod.format(date));
    }

    @Override
    public String title () {
        return this.title.getText().toString();
    }

    @Override
    public void setTitle(String newTitle) {
        title.setText(newTitle);
    }

    @Override
    public FragmentManager fm() {
        return fm;
    }

    @Override
    public void setButtons(int buttonId) {
        findViewById(R.id.events_button).setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_off));
        findViewById(R.id.dining_button).setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_off));
        findViewById(R.id.map_button).setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_off));

        /*
        findViewById(R.id.social_button).setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_off));
        findViewById(R.id.settings_button).setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_off));
        */

        findViewById(buttonId).setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_on));
    }

    @Override
    public SearchView setSearchButton (Menu menu) {
        findViewById(R.id.toolbar_title).setVisibility(View.VISIBLE);
        final SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Associate searchable configuration with the SearchView
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            // SearchView OnSearchClickListener
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    (findViewById(R.id.toolbar_title)).setVisibility(View.GONE);
                }
            });

            // SearchView OnCloseListener
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    (findViewById(R.id.toolbar_title)).setVisibility(View.VISIBLE);
                    return false;
                }
            });
        }
        return searchView;
    }

    @Override
    public void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public SwipeRefreshLayout currentSwipeLayout() {
        return swipeLayout;
    }

    @Override
    public void setCurrentSwipeLayout(SwipeRefreshLayout newSwipeLayout) {
        this.swipeLayout = newSwipeLayout;
    }
}
