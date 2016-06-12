package slugapp.com.sluglife.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import slugapp.com.sluglife.models.BaseObject;

/**
 * Created by simba on 5/31/15.
 * <p/>
 * Edited by isaiah on 6/27/2015.
 * <p/>
 * This file is the Adapter for EventListFragment.java and displays each Event to the ListView.
 */
public abstract class BaseListAdapter extends ArrayAdapter<BaseObject> {
    protected Context mContext;

    public BaseListAdapter(Context context, int id) {
        super(context, id);
        this.mContext = context;
    }

    public void setData(List<BaseObject> items) {
        this.clear();
        for (BaseObject item : items) this.add(item);
        this.notifyDataSetChanged();
    }
}
