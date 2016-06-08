package slugapp.com.ucscstudentapp.interfaces;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.gson.Gson;

import java.util.List;
import java.util.Timer;

import slugapp.com.ucscstudentapp.enums.FragmentEnum;
import slugapp.com.ucscstudentapp.models.Date;

/**
 * Created by isayyuhh_s on 8/3/2015.
 */
public interface ActivityCallback {

    ActionBar getToolbar();

    /**
     * Get list of tab fragments
     *
     * @return List of tab fragments
     */
    List<FragmentEnum> getFragments();

    /**
     * Get current fragment manager
     *
     * @return Current fragment manager
     */
    FragmentManager fm();

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
     * Get resource string
     *
     * @param id Id of resource
     * @return String
     */
    String toStr(int id);

    /**
     * Get resource bitmap
     *
     * @param id Id of bitmap
     * @return Bitmap
     */
    BitmapDescriptor toBitMap(int id);

    /**
     * Get Gson
     *
     * @return Gson
     */
    Gson getGson();

    Fragment getTabFragment(FragmentEnum fragmentEnum);
}
