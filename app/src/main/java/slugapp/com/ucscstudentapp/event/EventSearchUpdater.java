package slugapp.com.ucscstudentapp.event;

import android.content.Context;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import slugapp.com.ucscstudentapp.http.HttpCallback;
import slugapp.com.ucscstudentapp.http.TestEventHttpRequest;

/**
 * Created by isayyuhh_s on 8/3/2015.
 *
 * Does Search
 */
public class EventSearchUpdater extends EventUpdater {
    private String query;

    public EventSearchUpdater(Context context, EventListAdapter adapter,
                              String query) {
        super(context, adapter);
        this.query = query;
    }

    @Override
    public void onRefresh() {
        new TestEventHttpRequest().execute(new HttpCallback<List<Event>>() {
            @Override
            public void onSuccess(List<Event> val) {
                search(val);
                EventListSort comparator = new EventListSort();
                Collections.sort(val, comparator);
                adapter.setData(val);
            }

            @Override
            public void onError(Exception e) {
                // Currently Impossible to get here
            }
        });
    }

    private void search (List<Event> val) {
        for (Iterator<Event> itor = val.iterator(); itor.hasNext() ;) {
            Event event = itor.next();
            StringTokenizer queryTokenizer = new StringTokenizer(this.query);
            int found = 0, max = queryTokenizer.countTokens();
            while (queryTokenizer.hasMoreTokens()) {
                String query = queryTokenizer.nextToken();
                StringTokenizer stringTokenizer = new StringTokenizer(event.name());
                while (stringTokenizer.hasMoreTokens()) {
                    String current = stringTokenizer.nextToken();
                    if (query.length() < current.length()) {
                        current = current.substring(0, query.length());
                    }
                    if (current.toLowerCase().equals(query.toLowerCase())) {
                        found++;
                        break;
                    }
                }
            }
            if (found < max) itor.remove();
        }
    }
}
