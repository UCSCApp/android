package slugapp.com.sluglife.interfaces;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.gson.Gson;

import java.util.List;
import java.util.Timer;

import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.models.Date;

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
     * Get mToday's date
     *
     * @return Today's date
     */
    Date getToday();

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

    /**
     * Get Gson
     *
     * @return Gson
     */
    Gson getGson();
}
