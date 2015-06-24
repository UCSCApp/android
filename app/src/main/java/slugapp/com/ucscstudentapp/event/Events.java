package slugapp.com.ucscstudentapp.event;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.ucscstudentapp.R;


public class Events extends AppCompatActivity {

    private static final String SERVER_URL_PREFIX = "http://ec2-52-8-25-141.us-west-1.compute.amazonaws.com/events/get/v1";
    public static final String PREF_POSTS = "pref_posts";
    private ServerCall uploader;
    private List<Event> events;

    private class YourAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... args) {
            // code where data is processing
            // Then, we start the call.
            PostMessageSpec my_call_spec = new PostMessageSpec();
            my_call_spec.url = SERVER_URL_PREFIX;
            my_call_spec.context = Events.this;
            // Actual server call.
            if (uploader != null) {
                // There was already an upload in progress.
                uploader.cancel(true);
            }
            uploader = new ServerCall();
            uploader.execute(my_call_spec);
            return null;
        }
    }

    /**
     * This class is used to do the HTTP call, and it specifies how to use the result.
     */
    class PostMessageSpec extends ServerCallSpec {
        @Override
        public void useResult(Context context, String result) {
            if (result == null) {
                // Do something here, e.g. tell the user that the server cannot be contacted.
                //Log.i(LOG_TAG, "The server call failed.");
            } else {
                // Translates the string result, decoding the Json.
                //Log.i(LOG_TAG, "Received string: " + result);
                if(result != null || result != "")
                    displayResult(result);
                // Stores in the settings the last messages received.
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(PREF_POSTS, result);
                editor.commit();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);
        events = new ArrayList<Event>();
        linkActionBar("Events");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Let us display the previous posts, if any.
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String result = settings.getString(PREF_POSTS, null);
        if (result != null) {
            displayResult(result);
        }
        new YourAsyncTask().execute();
        linkListView(events);
    }

    @Override
    protected void onPause() {
        // Stops the upload if any.
        if (uploader != null) {
            uploader.cancel(true);
            uploader = null;
        }
        super.onPause();
    }

    private void displayResult(String json) {
        events.clear();
        try {
            JSONArray arr = new JSONArray(json);
            Log.e("HERE", "" + arr.length());
            for(int i = 0; i < arr.length(); ++i) {
                JSONObject obj = arr.getJSONObject(i);
                Event e = new Event(obj.getString("name"),
                        obj.getString("date"),
                        obj.getString("description"));//,
                        //obj.getString("url"));
                events.add(e);
            }

        }catch(JSONException je) {
            Log.e("string", "" + je.getMessage());
        }
    }

    private void linkActionBar(String name) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(name);
    }

    private void linkListView(List<Event> events) {
        ListView lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(new EventsAdapter(this, events));
    }
}
