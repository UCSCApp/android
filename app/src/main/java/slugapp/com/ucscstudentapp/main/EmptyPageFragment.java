package slugapp.com.ucscstudentapp.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import slugapp.com.ucscstudentapp.R;

/**
 * Created by isayyuhh_s on 7/4/2015.
 *
 * Basically a placeholder Fragment until each page is added
 */
public class EmptyPageFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_event, container, false);
        this.ac.setTitle("Empty");
        return view;
    }

    @Override
    protected void setView(View view) {

    }
}
