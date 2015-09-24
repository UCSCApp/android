package slugapp.com.ucscstudentapp.main;

import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import slugapp.com.ucscstudentapp.event.Date;

/**
 * Created by isayyuhh_s on 8/3/2015.
 */
public interface ActivityCallback {
    // Fragment Manager
    FragmentManager fm();
    void setButtons(int buttonId);

    // Search
    SearchView setSearchButton(Menu menu);
    void hideKeyboard();

    // Refresh Layout
    SwipeRefreshLayout currentSwipeLayout ();
    void setCurrentSwipeLayout (SwipeRefreshLayout newSwipeLayout);

    // Events Page
    String title ();
    Date today();
    void setTitle(String title);
    void setToday();
}
