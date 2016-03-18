package slugapp.com.ucscstudentapp.interfaces;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

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
     * Get today's date
     *
     * @return Today's date
     */
    Date getToday();

    /**
     * Set new toolbar title
     *
     * @param newTitle New toolbar title
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
}
