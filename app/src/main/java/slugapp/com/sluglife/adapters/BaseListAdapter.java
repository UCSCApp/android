package slugapp.com.sluglife.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import slugapp.com.sluglife.models.BaseObject;

/**
 * Created by simba on 5/31/15
 * Edited by isaiah on 6/27/2015
 * <p/>
 * This file is the base adapter for list views.
 */
public abstract class BaseListAdapter extends ArrayAdapter<BaseObject> {
    protected Context mContext;

    // TODO: do adapters with data binding

    /**
     * Constructor
     *
     * @param context Activity context
     * @param id Resource id
     */
    public BaseListAdapter(Context context, int id) {
        super(context, id);
        this.mContext = context;
    }

    /**
     * Sets the list adapter data given a list of objects
     *
     * @param items List of objects
     */
    public void setData(List<BaseObject> items) {
        this.clear();
        for (BaseObject item : items) this.add(item);
        this.notifyDataSetChanged();
    }
}
