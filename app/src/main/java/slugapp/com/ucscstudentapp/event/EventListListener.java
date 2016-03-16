package slugapp.com.ucscstudentapp.event;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;

import slugapp.com.ucscstudentapp.main.ActivityCallback;

/**
 * Created by isayyuhh on 2/21/16.
 */
public class EventListListener implements AdapterView.OnItemClickListener {
    private ActivityCallback ac;

    public EventListListener (ActivityCallback ac) { this.ac = ac; }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.ac.hideKeyboard();
        Event e = (Event) parent.getItemAtPosition(position);
        Bundle b = new Bundle();

        String json = this.ac.getGson().toJson(e);
        b.putString("json", json);
        EventDetail fragment = new EventDetail();
        fragment.setArguments(b);
        this.ac.setFragment(fragment);
    }
}

