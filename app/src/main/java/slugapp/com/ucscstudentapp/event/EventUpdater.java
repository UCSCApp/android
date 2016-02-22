package slugapp.com.ucscstudentapp.event;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import slugapp.com.ucscstudentapp.http.HttpCallback;
import slugapp.com.ucscstudentapp.http.TestEventHttpRequest;
import slugapp.com.ucscstudentapp.main.ActivityCallback;

/**
 * Created by simba on 8/2/15.
 */
public class EventUpdater implements SwipeRefreshLayout.OnRefreshListener {
    protected EventListAdapter adapter;
    protected ActivityCallback ac;
    private boolean initial = true;

    public EventUpdater(Context context, EventListAdapter adapter) {
        this.adapter = adapter;
        this.ac = (ActivityCallback) context;
    }

    @Override
    public void onRefresh() {
        new TestEventHttpRequest().execute(new HttpCallback<List<Event>>() {
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
        if (!initial) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ac.currentSwipeLayout().setRefreshing(false);
                }
            }, 1000);
        } else {
            ac.currentSwipeLayout().setRefreshing(false);
            initial = false;
        }
    }

    /*
     * Sorts ArrayList<Event> By Dates
     */
    protected class EventListSort implements Comparator<Event> {
        @Override
        public int compare(Event lhs, Event rhs) {
            return ac.getToday().compareEvents(lhs, rhs);
        }
    }
}
