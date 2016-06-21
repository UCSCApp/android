package slugapp.com.sluglife.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.FragmentEnum;

/**
 * Created by isayyuhh on 6/20/16
 */
public class MapViewFragment extends BaseDetailFragment {
    private FragmentEnum fragmentEnum = FragmentEnum.MAP;
    private boolean searchShowing;
    private View searchBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_map, container, false);

        this.searchShowing = false;
        this.searchBar = view.findViewById(R.id.search);

        this.setLayout(this.fragmentEnum.getName(), this.fragmentEnum.getButtonId());
        this.setView(view);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_map_search, menu);
    }

    @Override
    protected void setView(final View view) {
        MapFragment fragment = new MapFragment();

        FragmentManager fm = this.getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.map_view, fragment);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                if (!searchShowing) {
                    this.searchBar.setVisibility(View.VISIBLE);
                    this.searchShowing = true;
                } else {
                    this.searchBar.setVisibility(View.GONE);
                    this.searchShowing = false;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
