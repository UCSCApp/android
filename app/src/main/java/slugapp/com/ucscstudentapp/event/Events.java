package slugapp.com.ucscstudentapp.event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.ucscstudentapp.R;


public class Events extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);
        linkActionBar("Events");
        List<Event> events = parseFakeData();
        Log.e("STring 2", "" + events.size());
        linkListView(events);
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

    private List<Event> parseFakeData() {
        String json = "[\n       {\n          \"name\":\"Edge of Eden\",\n          \"date\":\"July 18th 10pm 2am\",\n          \"description\":\"Musical festival with a finale by Squidward Tortellini\"\n       },\n       {\n          \"name\":\"Holi Festival\",\n          \"date\":\"May 25th 10am 12pm\",\n          \"description\":\"This event is super fun and super great!\"\n       },\n       {\n          \"name\":\"Meeting\",\n          \"date\":\"May 31st 2pm 4pm\",\n          \"description\":\"Simba is asking you to go to this super boring meeting thing again\"\n       },\n       {\n          \"name\":\"French Fried\",\n          \"date\":\"January 1st 13am 7pm\",\n          \"description\":\"TBH Im not entirely sure what this is, so please dont come to this event at all, This desciption is purposely awkwardly long to hopefully break all of your apps becase I am a devious motherfucker like that. So I am still writing random shit now to break your apps in my malicious ways. I wonder if anyone will actually handle this case. I sure as hell wouldnt. Who the fuck actually spends the time to write this long a description about a dumb event called French Fried holy fuck. Btw I hope you arent actually reading this when you should be coding the solution to fixing your description box that just broke due to my malicious test script. Unless of course it worked in which case, you should probably stop reading this anyway because holy fuck this is a long motha fucking description. It is called French Fried. Starts at 13am. Be there.\"\n       },\n       {\n          \"name\":\"Short but sweet\",\n          \"date\":\"never\",\n          \"description\":\"meow\"\n       }\n    ]";
        List<Event> rtrn = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(json);
            Log.e("HERE", "" + arr.length());
            for(int i = 0; i < arr.length(); ++i) {
                JSONObject obj = arr.getJSONObject(i);
                Event e = new Event(obj.getString("name"),
                                    obj.getString("date"),
                                    obj.getString("description"));
                rtrn.add(e);
            }

        }catch(JSONException je) {
            Log.e("string", "" + je.getMessage());
        }
        return rtrn;
    }
}
