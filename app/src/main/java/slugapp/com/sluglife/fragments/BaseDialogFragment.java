package slugapp.com.sluglife.fragments;

import android.content.Context;
import android.support.v4.app.DialogFragment;

/**
 * Created by isayyuhh on 6/27/16
 * <p/>
 * This file contains a base dialog fragment class.
 */
public abstract class BaseDialogFragment extends DialogFragment {
    protected Context mContext;

    /**
     * Fragment's onAttach method
     *
     * @param context Activity context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}
