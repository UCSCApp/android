package slugapp.com.ucscstudentapp.event;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.ucscstudentapp.R;


public class Events extends AppCompatActivity {

    /*
     * Variables and Classes
     */
    private static final String SERVER_URL_PREFIX = "http://ec2-52-8-25-141.us-west-1.compute.amazonaws.com/events/get/v1";
    public static final String PREF_POSTS = "pref_posts";
    private ServerCall uploader;
    private List<Event> events;

    private class HttpCall extends AsyncTask<Void, Void, Void> {

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

    /*
     * Code and Functions
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        setContentView(R.layout.events);
        events = new ArrayList<Event>();
        parseFakeData();
        linkActionBar("Events");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Let us display the previous posts, if any.
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String result = settings.getString(PREF_POSTS, null);
        //if (result != null) displayResult(result);
        new HttpCall().execute();
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
            for (int i = 0; i < arr.length(); ++i) {
                JSONObject obj = arr.getJSONObject(i);
                Event e = new Event(
                        obj.has("name") ? obj.getString("name") : "",
                        obj.has("date") ? obj.getString("date") : "",
                        obj.has("description") ? obj.getString("description") : "",
                        obj.has("url") ? obj.getString("url") : "");
                events.add(e);
            }

        } catch (JSONException je) {
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

    private void parseFakeData() {
        String json = "[\n" +
                "       {\n" +
                "          \"name\":\"Edge of Eden\",\n" +
                "          \"date\":\"July 18th 10pm 2am\",\n" +
                "          \"description\":\"Musical festival with a finale by Squidward Tortellini\",\n" +
                "          \"url\":\"http://tmcdigitalmedia.com/wp-content/uploads/2013/03/2_27_13-FSNA_Flyer_web_versionF.jpeg\"\n" +
                "       },\n" +
                "       {\n" +
                "          \"name\":\"Holi Festival\",\n" +
                "          \"date\":\"May 25th 10am 12pm\",\n" +
                "          \"description\":\"This event is super fun and super great!\",\n" +
                "          \"url\":\"https://uh.collegiatelink.net/images/W460xL600/0/noshadow/Event/c40dcb200abb430c9c20c632473b959f.jpg\"\n" +
                "       },\n" +
                "       {\n" +
                "          \"name\":\"Meeting\",\n" +
                "          \"date\":\"May 31st 2pm 4pm\",\n" +
                "          \"description\":\"Simba is asking you to go to this super boring meeting thing again\",\n" +
                "          \"url\":\"http://img01.deviantart.net/d568/i/2012/062/a/1/steam_event_flyer_design_by_danwilko-d4rke4s.jpg\"\n" +
                "       },\n" +
                "       {\n" +
                "          \"name\":\"French Fried\",\n" +
                "          \"date\":\"January 1st 13am 7pm\",\n" +
                "          \"description\":\"TBH Im not entirely sure what this is, so please dont come to this event at all, This desciption is purposely awkwardly long to hopefully break all of your apps becase I am a devious motherfucker like that. So I am still writing random shit now to break your apps in my malicious ways. I wonder if anyone will actually handle this case. I sure as hell wouldnt. Who the fuck actually spends the time to write this long a description about a dumb event called French Fried holy fuck. Btw I hope you arent actually reading this when you should be coding the solution to fixing your description box that just broke due to my malicious test script. Unless of course it worked in which case, you should probably stop reading this anyway because holy fuck this is a long motha fucking description. It is called French Fried. Starts at 13am. Be there.\",\n" +
                "          \"url\":\"http://xdesigns.net/wp-content/uploads/2013/06/42-events-flyer.jpg\"\n" +
                "       },\n" +
                "       {\n" +
                "          \"name\":\"Short but sweet\",\n" +
                "          \"date\":\"never\",\n" +
                "          \"description\":\"meow\",\n" +
                "          \"url\":\"https://www.designmaz.net/wp-content/uploads/2014/11/psd-event-flyer-templates.jpg\"\n" +
                "       }\n" +
                "]";
        events.clear();
        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); ++i) {
                JSONObject obj = arr.getJSONObject(i);
                Event e = new Event(
                        obj.has("name") ? obj.getString("name") : "",
                        obj.has("date") ? obj.getString("date") : "",
                        obj.has("description") ? obj.getString("description") : "",
                        obj.has("url") ? obj.getString("url") : "");
                events.add(e);
            }

        } catch (JSONException je) {
            Log.e("string", "" + je.getMessage());
        }
    }

}

