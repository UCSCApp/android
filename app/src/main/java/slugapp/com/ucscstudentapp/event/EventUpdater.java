package slugapp.com.ucscstudentapp.event;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import slugapp.com.ucscstudentapp.http.Callback;
import slugapp.com.ucscstudentapp.http.TestEventHttpRequest;
import slugapp.com.ucscstudentapp.main.ActivityReference;

/**
 * Created by simba on 8/2/15.
 */
public class EventUpdater implements SwipeRefreshLayout.OnRefreshListener {
    protected EventListAdapter adapter;
    private ActivityReference mCallBack;
    private boolean initial = true;

    public EventUpdater(Context context, EventListAdapter adapter) {
        this.adapter = adapter;
        this.mCallBack = (ActivityReference) context;
    }

    @Override
    public void onRefresh() {
        new TestEventHttpRequest().execute(new Callback<List<Event>>() {
            @Override
            public void onSuccess(List<Event> val) {
                EventListSort comparator = new EventListSort();
                Collections.sort(val, comparator);
                adapter.setData(val);
            }

            @Override
            public void onError(Exception e) {
            }
        });
        if (! initial) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCallBack.currentSwipeLayout().setRefreshing(false);
                }
            }, 1000);
        } else {
            mCallBack.currentSwipeLayout().setRefreshing(false);
            initial = false;
        }
    }

    /*
     * Sorts ArrayList<Event> By Dates
     */
    protected class EventListSort implements Comparator<Event> {
        @Override
        public int compare(Event lhs, Event rhs) {
            return mCallBack.today().compareEvents(lhs, rhs);
        }
    }
}
