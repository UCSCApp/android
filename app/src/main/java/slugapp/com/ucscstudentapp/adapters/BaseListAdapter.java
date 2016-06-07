package slugapp.com.ucscstudentapp.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import slugapp.com.ucscstudentapp.models.BaseListItem;

/**
 * Created by simba on 5/31/15.
 * <p/>
 * Edited by isaiah on 6/27/2015.
 * <p/>
 * This file is the Adapter for EventListFragment.java and displays each Event to the ListView.
 */
public abstract class BaseListAdapter extends ArrayAdapter<BaseListItem> {
    protected Context mContext;

    public BaseListAdapter(Context context, int id) {
        super(context, id);
        this.mContext = context;
    }

    public void setData(List<BaseListItem> items) {
        this.clear();
        for (BaseListItem item : items) this.add(item);
        this.notifyDataSetChanged();
    }
}
