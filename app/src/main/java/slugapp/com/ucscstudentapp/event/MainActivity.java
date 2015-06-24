package slugapp.com.ucscstudentapp.event;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;

import slugapp.com.ucscstudentapp.R;

/**
 * Created by isayyuhh_s on 6/23/2015.
 */
public class MainActivity extends ActionBarActivity {

    private static final String SERVER_URL_PREFIX = "http://ec2-52-8-25-141.us-west-1.compute.amazonaws.com/events/get/v1";
    public static final String PREF_POSTS = "pref_posts";
    private ServerCall uploader;
    private ArrayList<Event> aList;
    private EventsAdapter aa;
    private PostMessageSpec myCallSpec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);
        aList = new ArrayList<Event>();
        aa = new EventsAdapter(this, R.layout.list_element, aList);
        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(aa);
        aa.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Let us display the previous posts, if any.
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String result = settings.getString(PREF_POSTS, null);
        if (result != null) {
            myCallSpec.displayResult(result);
        }
        new YourAsyncTask().execute();
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


    private class YourAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... args) {
            // code where data is processing
            // Then, we start the call.
            myCallSpec = new PostMessageSpec();
            myCallSpec.url = SERVER_URL_PREFIX;
            myCallSpec.context = MainActivity.this;
            // Actual server call.
            if (uploader != null) {
                // There was already an upload in progress.
                uploader.cancel(true);
            }
            uploader = new ServerCall();
            uploader.execute(myCallSpec);
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

    private void displayResult(String result) {
        Gson gson = new Gson();
        //MessageList ml = gson.fromJson(result, MessageList.class);
        ArrayList<HashMap<String, String>> key = new Gson().fromJson(result,
                new TypeToken<ArrayList<HashMap<String, String>>>() {}.getType());

        // Fills aList, so we can fill the listView.
        aList.clear();
        for (int q = 0; q < key.size(); q++) {
            HashMap<String, String> key2 = key.get(q);
            Event ael = new Event(key2.get("name"), key2.get("date"), key2.get("description"), key2.get("url"));
            aList.add(ael);
        }
        aa.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
