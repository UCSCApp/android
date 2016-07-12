package slugapp.com.sluglife.fragments;

import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.Comparator;

import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.models.BaseObject;

/**
 * Created by isaiah on 2/21/16
 * <p/>
 * This file contains a base list fragment class.
 */
public abstract class BaseListFragment extends BaseFragment {
    protected BaseAdapter mAdapter;

    /**
     * Sets list fragment
     *
     * @param fragmentEnum Fragment enum containing fragment information
     * @param listView     List view
     * @param adapter      List adapter
     */
    protected void setListFragment(FragmentEnum fragmentEnum, ListView listView,
                                   BaseAdapter adapter) {
        this.setFragment(fragmentEnum.name);
        this.setView(listView, adapter);
    }

    /**
     * Sets fragment view
     *
     * @param listView List view
     * @param adapter  List adapter
     */
    protected void setView(ListView listView, BaseAdapter adapter) {
        this.mAdapter = adapter;
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListItemListener());
    }

    /**
     * Allows user to set sorting algorithm for list
     *
     * @param lhs Left operand
     * @param rhs Right operand
     * @return Integer showing which order the operands are in
     */
    protected abstract int doSort(BaseObject lhs, BaseObject rhs);

    /**
     * Allows user to set what happens on list item click
     *
     * @param parent   Parent view of list item
     * @param view     List item view
     * @param position List item position
     * @param id       Id of list item
     */
    protected abstract void onClick(AdapterView<?> parent, View view, int position, long id);

    /**
     * Class that sorts list
     */
    protected class ListSort implements Comparator<BaseObject> {

        /**
         * Compares objects in list
         *
         * @param lhs Left operand
         * @param rhs Right operand
         * @return Integer showing which order the operands are in
         */
        @Override
        public int compare(BaseObject lhs, BaseObject rhs) {
            return doSort(lhs, rhs);
        }
    }

    /**
     * Class that listens for list item click
     */
    protected class ListItemListener implements AdapterView.OnItemClickListener {

        /**
         * Listens for list item click
         *
         * @param parent   Parent view of list item
         * @param view     List item view
         * @param position List item position
         * @param id       Id of list item
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mCallback.hideKeyboard();
            onClick(parent, view, position, id);
        }
    }
}
