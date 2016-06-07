package slugapp.com.ucscstudentapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import slugapp.com.ucscstudentapp.interfaces.ActivityCallback;
import slugapp.com.ucscstudentapp.models.Date;

/**
 * Created by isayyuhh on 2/21/16.
 */
public abstract class BaseFragment extends Fragment {
    protected ActivityCallback ac;
    protected String title;
    protected int buttonId;
    protected Date today;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.ac = (ActivityCallback) activity;
        this.today = this.ac.getToday();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.ac.setTitle(title);
        this.ac.setButtons(buttonId);
    }

    protected void setLayout(String title, int id) {
        this.title = title;
        this.buttonId = id;
    }
}
