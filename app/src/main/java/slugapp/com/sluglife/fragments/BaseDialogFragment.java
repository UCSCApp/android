package slugapp.com.sluglife.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.DialogFragment;

/**
 * Created by isayyuhh on 6/27/16.
 */
public class BaseDialogFragment extends DialogFragment {
    protected Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }
}
