package slugapp.com.sluglife.fragments;

import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.Comparator;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.models.BaseObject;

/**
 * Created by isayyuhh on 2/21/16.
 */
public abstract class BaseListFragment extends BaseFragment {
    protected BaseAdapter mAdapter;

    protected void setView(View view, BaseAdapter adapter) {
        this.mAdapter = adapter;
        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListItemListener());
    }

    protected abstract int doSort(BaseObject lhs, BaseObject rhs);

    protected abstract void onClick(AdapterView<?> parent, View view, int position, long id);

    /*
     * Sorts ArrayList<Event> By Dates
     */
    protected class ListSort implements Comparator<BaseObject> {
        @Override
        public int compare(BaseObject lhs, BaseObject rhs) {
            return doSort(lhs, rhs);
        }
    }

    protected class ListItemListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mCallback.hideKeyboard();
            onClick(parent, view, position, id);
        }
    }
}
