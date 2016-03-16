package slugapp.com.ucscstudentapp.main;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.event.Date;
import slugapp.com.ucscstudentapp.event.EventSearchListFragment;
import slugapp.com.ucscstudentapp.event.EventUpdater;

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
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.today = this.ac.getToday();
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.ac.setTitle(title);
        this.ac.setButtons(buttonId);
    }

    protected abstract void setView (View view);

    protected void setLayout (String title, int id) {
        this.title = title;
        this.buttonId = id;
    }

    protected void setSearchView (Menu menu) {
        getActivity().findViewById(R.id.toolbar_title).setVisibility(View.VISIBLE);
        final SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        SearchManager searchManager = (SearchManager) getActivity()
                .getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity()
                .getComponentName()));

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().findViewById(R.id.toolbar_title).setVisibility(View.GONE);
            }
        });

        // SearchView OnCloseListener
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                getActivity().findViewById(R.id.toolbar_title).setVisibility(View.VISIBLE);
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                ac.hideKeyboard();
                EventSearchListFragment fragment = new EventSearchListFragment();
                Bundle b = new Bundle();
                b.putString("query", query);
                fragment.setArguments(b);
                ac.setFragment(fragment);
                return true;
            }
        });
    }
    protected void setSwipeLayout (View view, EventUpdater upd) {
        this.ac.setCurrentSwipeLayout((SwipeRefreshLayout) view.findViewById(R.id.swipe_container));
        this.ac.currentSwipeLayout().setOnRefreshListener(upd);
        this.ac.currentSwipeLayout().setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
}
