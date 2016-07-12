package slugapp.com.sluglife.interfaces;

import android.os.Handler;
import android.support.v4.app.Fragment;

import slugapp.com.sluglife.runnables.LoopRunnable;

/**
 * Created by isaiah on 8/3/2015
 * <p/>
 * This file contains a callback for fragments to communicate with its activity.
 */

public interface ActivityCallback {

    /**
     * Sets the current fragment
     *
     * @param fragment Fragment to set
     */
    void setFragment(Fragment fragment);

    /**
     * Hides soft keyboard
     */
    void hideKeyboard();

    /**
     * Displays snackbar with text
     *
     * @param text Text to display
     */
    void showSnackBar(String text);

    /**
     * Sets new toolbar title
     *
     * @param text New toolbar title
     */
    void setToolbarTitle(String text);

    /**
     * Sets toolbar's home up enabled
     *
     * @param enabled If toolbar's home up is enabled
     */
    void setUpEnabled(boolean enabled);

    /**
     * Initializes timer
     */
    void initTimer();

    /**
     * Schedules timer given a handler, a runnable, a delay time, and a time period
     *
     * @param handler  Handler that handles process
     * @param runnable Runnable that runs process
     * @param delay    Delay time
     * @param period   Period of time
     */
    void scheduleTimer(final Handler handler, final LoopRunnable runnable, long delay, int period);

    /**
     * Cancels timer if active
     */
    void cancelTimer();
}
