package slugapp.com.ucscstudentapp.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import slugapp.com.ucscstudentapp.R;

/**
 * Created by isayyuhh_s on 7/4/2015.
 *
 * Basically a placeholder Fragment until each page is added
 */
public class EmptyPageFragment extends Fragment {
    private ActivityCallback mCallBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (ActivityCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_event_center, container, false);
        mCallBack.setTitle("Empty");
        return view;
    }
}
