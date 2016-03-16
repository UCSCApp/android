package slugapp.com.ucscstudentapp.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.gson.Gson;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;

import io.fabric.sdk.android.Fabric;
import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.event.Date;
import slugapp.com.ucscstudentapp.event.EventListFragment;
import slugapp.com.ucscstudentapp.event.Month;

/**
 * Created by isaiah on 6/27/2015.
 * <p/>
 * This file contains the MainActivity. It handles all of the pages of the app in the form of
 * Fragments, and contains Top and Bottom Toolbars.
 */

public class MainActivity extends AppCompatActivity implements ActivityCallback {
    private SwipeRefreshLayout swipeLayout;
    private FragmentManager fm;
    private TextView title;
    private Timer timer;
    private Gson gson;
    private boolean init = true;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "pkpaLGZDDFZyBViV2ScOOcz2R";
    private static final String TWITTER_SECRET = "8GqvJRMgLgbQpphUKfnUx7WLZaK2iRHxZ0VU27uYwtO1GrT82a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This is the Twitter Authorization for fabric to use Twitter services in the app
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        this.gson = new Gson();

        // sets views
        setContentView(R.layout.activity_main);
        setTopToolbar();
        setBottomToolbar();
        setFragment(new EventListFragment());
    }

    @Override
    protected void onPause() {
        super.onPause();
        //if (this.timer != null) this.timer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //if (this.timer != null) this.timer.cancel();
    }

    /*
     * Sets up the Top Toolbar
     */
    private void setTopToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        this.title = (TextView) findViewById(R.id.toolbar_title);
    }

    /*
     * Sets up the Bottom Toolbar
     */
    private void setBottomToolbar() {
        LinearLayout bottom = (LinearLayout) findViewById(R.id.bottom_toolbar);
        View child = getLayoutInflater().inflate(R.layout.toolbar_bottom, bottom, false);

        // Bottom Buttons
        BottomToolbarButton events_button = (BottomToolbarButton) child.findViewById(R.id.events_button);
        BottomToolbarButton dining_button = (BottomToolbarButton) child.findViewById(R.id.dining_button);
        BottomToolbarButton map_button = (BottomToolbarButton) child.findViewById(R.id.map_button);
        events_button.setImageResource(R.drawable.ic_events);
        dining_button.setImageResource(R.drawable.ic_dining);
        map_button.setImageResource(R.drawable.ic_map);
        BottomToolbarButton.setIds(events_button, dining_button, map_button); //, social_button, settings_button);
        bottom.addView(child);
        events_button.setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_on));
    }

    /*
     * Sets up the Fragment
     */
    @Override
    public void setFragment(Fragment fragment) {
        this.fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.listFragment, fragment);
        if (! this.init) ft.addToBackStack(null);
        ft.commit();
        this.init = false;
    }

    /**
     * Interface methods
     */
    @Override
    public Date getToday() {
        java.util.Date date = Calendar.getInstance().getTime();
        String month = new SimpleDateFormat("MM").format(date);
        String day = new SimpleDateFormat("dd").format(date);
        String hour = new SimpleDateFormat("hh").format(date);
        String tod = new SimpleDateFormat("aa").format(date);
        String todayMonth = Month.JANUARY.getVal();
        for (Month currMonth : Month.values()) {
            if (currMonth.getOrder() == Integer.valueOf(month)) {
                todayMonth = currMonth.getVal();
                break;
            }
        }
        String string = todayMonth + " " + day + " " + hour + tod + " " + hour + tod;
        Date today = new Date(string);
        return today;
    }

    @Override
    public Timer getTimer() {
        return this.timer;
    }

    @Override
    public void initTimer() {
        this.timer = new Timer();
    }

    @Override
    public String toStr(int id) {
        return this.getResources().getString(id);
    }

    @Override
    public BitmapDescriptor toBitMap(int id) {
        return BitmapDescriptorFactory.fromResource(id);
    }

    @Override
    public Gson getGson() {
        return this.gson;
    }

    @Override
    public void setTitle(String newTitle) {
        this.title.setText(newTitle);
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
        findViewById(buttonId).setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_on));
    }

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

    @Override
    public SwipeRefreshLayout currentSwipeLayout() {
        return swipeLayout;
    }

    @Override
    public void setCurrentSwipeLayout(SwipeRefreshLayout newSwipeLayout) {
        this.swipeLayout = newSwipeLayout;
    }
}
