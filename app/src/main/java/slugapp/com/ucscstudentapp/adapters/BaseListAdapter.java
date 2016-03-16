package slugapp.com.ucscstudentapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.http.ImageHttpRequest;
import slugapp.com.ucscstudentapp.models.BaseListItem;
import slugapp.com.ucscstudentapp.models.Event;

/**
 * Created by simba on 5/31/15.
 * <p/>
 * Edited by isaiah on 6/27/2015.
 * <p/>
 * This file is the Adapter for EventListFragment.java and displays each Event to the ListView.
 */
public abstract class BaseListAdapter extends ArrayAdapter<BaseListItem> {
    public BaseListAdapter(Context context, int id) {
        super(context, id);
    }

    public void setData(List<BaseListItem> items) {
        clear();
        for (BaseListItem item : items) add(item);
        notifyDataSetChanged();
    }
}
