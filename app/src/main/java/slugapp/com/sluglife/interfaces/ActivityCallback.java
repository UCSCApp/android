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
     * Set bottom toolbar buttons
     *
     * @param buttonId Id of button to set
     */
    void setButtons(int buttonId);

    /**
     * Hide soft keyboard
     */
    void hideKeyboard();

    /**
     * Set new toolbar mTitle
     *
     * @param newTitle New toolbar mTitle
     */
    void setTitle(String newTitle);

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
