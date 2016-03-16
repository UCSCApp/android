package slugapp.com.ucscstudentapp.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.gson.Gson;

import java.util.Timer;

import slugapp.com.ucscstudentapp.event.Date;

/**
 * Created by isayyuhh_s on 8/3/2015.
 */
public interface ActivityCallback {
    // Fragment Manager
    FragmentManager fm ();
    void setFragment (Fragment fragment);
    void setButtons (int buttonId);

    // Search
    void hideKeyboard();

    // Refresh Layout
    SwipeRefreshLayout currentSwipeLayout ();
    void setCurrentSwipeLayout (SwipeRefreshLayout newSwipeLayout);

    // Events Page
    Date getToday();
    void setTitle(String title);

    Timer getTimer();
    void initTimer();

    String toStr (int id);
    BitmapDescriptor toBitMap (int id);

    Gson getGson();
}
