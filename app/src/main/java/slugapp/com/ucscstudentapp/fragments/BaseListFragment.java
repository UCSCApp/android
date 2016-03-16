package slugapp.com.ucscstudentapp.fragments;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Comparator;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.adapters.BaseListAdapter;
import slugapp.com.ucscstudentapp.models.BaseListItem;
import slugapp.com.ucscstudentapp.models.Date;
import slugapp.com.ucscstudentapp.interfaces.ActivityCallback;

/**
 * Created by isayyuhh on 2/21/16.
 */
public abstract class BaseListFragment extends Fragment {
    protected ActivityCallback ac;
    protected BaseListAdapter adapter;
    protected String title;
    protected int buttonId;
    protected Date today;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.ac = (ActivityCallback) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) return true;
        return super.onOptionsItemSelected(item);
    }

    protected void setLayout(String title, int id) {
        this.title = title;
        this.buttonId = id;
    }

    protected void setView(View view, BaseListAdapter adapter) {
        this.adapter = adapter;
        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListItemListener());
    }

    protected abstract void doSearch(String query);

    protected abstract int doSort(BaseListItem lhs, BaseListItem rhs);

    protected abstract void onClick(AdapterView<?> parent, View view, int position, long id);

    protected void setSearchView(Menu menu) {
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
                doSearch(query);
                return true;
            }
        });
    }

    /*
     * Sorts ArrayList<Event> By Dates
     */
    protected class ListSort implements Comparator<BaseListItem> {
        @Override
        public int compare(BaseListItem lhs, BaseListItem rhs) {
            return doSort(lhs, rhs);
        }
    }

    public class ListItemListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ac.hideKeyboard();
            onClick(parent, view, position, id);
        }
    }
}
