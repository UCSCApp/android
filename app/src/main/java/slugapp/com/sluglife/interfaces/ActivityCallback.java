package slugapp.com.sluglife.interfaces;

import android.support.v4.app.Fragment;

import com.google.gson.Gson;

import java.util.Timer;

/**
 * Created by isayyuhh_s on 8/3/2015.
 */
public interface ActivityCallback {

    /**
     * Sets the current fragment
     *
     * @param fragment Fragment to set
     */
    void setFragment(Fragment fragment);

    /**
     * Hide soft keyboard
     */
    void hideKeyboard();

    /**
     * Displays snackbar with text
     *
     * @param text text to display
     */
    void showSnackBar(String text);

    /**
     * Set new toolbar mTitle
     *
     * @param newTitle New toolbar mTitle
     */
    void setTitle(String newTitle);

    /**
     * Enables home up
     *
     * @param enabled If home up is enabled
     */
    void setUpEnabled(boolean enabled);

    /**
     * Initializes Timer
     */
    void initTimer();

    /**
     * Get timer
     *
     * @return Timer
     */
    Timer getTimer();
}
