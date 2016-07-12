package slugapp.com.sluglife.fragments;

import android.content.Context;
import android.support.v4.app.DialogFragment;

/**
 * Created by isayyuhh on 6/27/16
 */
public abstract class BaseDialogFragment extends DialogFragment {
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}
