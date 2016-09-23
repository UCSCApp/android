package slugapp.com.sluglife.interfaces;

import android.support.v4.app.Fragment;
import android.view.View;

import slugapp.com.sluglife.runnables.LoopRunnable;

/**
 * Created by isaiah on 8/3/2015
 * <p/>
 * This file contains a callback for fragments to communicate with its activity.
 */

public interface FragmentCallback {

    /**
     * Sets the given tab fragment
     *
     * @param fragment Tab fragment to set
     */
    void setTabFragment(Fragment fragment);
}
