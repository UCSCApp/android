package slugapp.com.sluglife.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.FragmentEnum;

/**
 * Created by isayyuhh on 6/20/16
 */
public class MapViewFragment extends BaseDetailFragment {
    private FragmentEnum fragmentEnum = FragmentEnum.MAP;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_map, container, false);

        this.setLayout(this.fragmentEnum.getName(), this.fragmentEnum.getButtonId());
        this.setView(view);

        return view;
    }

    @Override
    protected void setView(final View view) {
        MapFragment fragment = new MapFragment();

        FragmentManager fm = this.getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.map_view, fragment);
        ft.commit();
    }
}
