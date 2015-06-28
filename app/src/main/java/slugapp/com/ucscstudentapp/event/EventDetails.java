package slugapp.com.ucscstudentapp.event;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.ucscstudentapp.R;

/**
 * Created by isayyuhh_s on 6/27/2015.
 */
public class EventDetails extends AppCompatActivity {
    Event e;
    private static final String SERVER_URL_PREFIX = "http://ec2-52-8-25-141.us-west-1.compute.amazonaws.com/events/get/v1";
    public static final String PREF_POSTS = "pref_posts";
    private ServerCall uploader;

    private class HttpCall extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... args) {
            // code where data is processing
            // Then, we start the call.
            PostMessageSpec my_call_spec = new PostMessageSpec();
            my_call_spec.url = SERVER_URL_PREFIX;
            my_call_spec.context = EventDetails.this;
            // Actual server call.
            if (uploader != null) {
                // There was already an upload in progress.
                uploader.cancel(true);
            }
            uploader = new ServerCall();
            uploader.execute(my_call_spec);
            return null;
        }
        // This class is used to do the HTTP call, and it specifies how to use the result.
        class PostMessageSpec extends ServerCallSpec {
            @Override
            public void useResult(Context context, String result) {
                if (result != null) {
                    // Translates the string result, decoding the Json.
                    //if (result != null || result != "") displayResult(result);
                    // Stores in the settings the last messages received.
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(PREF_POSTS, result);
                    editor.commit();
                }
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);
        Bundle b = getIntent().getExtras();
        e = new Event(new String(b.getCharArray("name")),
                new String(b.getCharArray("date")),
                new String(b.getCharArray("description")),
                new String(b.getCharArray("url")));
        linkActionBar("Event");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Let us display the previous posts, if any.
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String result = settings.getString(PREF_POSTS, null);
        //if (result != null) displayResult(result);
        new HttpCall().execute();
        List<Event> events = new ArrayList<Event>();
        events.add(e);
        linkListView(events);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void linkActionBar(String name) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_navigation);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(name);
    }

    private void linkListView(List<Event> events) {
        ListView lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(new EventDetailsAdapter(this, events));
    }
}
