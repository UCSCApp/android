package slugapp.com.ucscstudentapp.event;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.http.ImageHttpRequest;
import slugapp.com.ucscstudentapp.main.ActivityReference;

/**
 * Created by isaiah on 6/27/2015.
 *
 * This file displays all of the contents for each individual event.
 */
public class EventDetail extends Fragment {
    private Event event;
    private ActivityReference mCallBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallBack = (ActivityReference) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Bundle b = getArguments();
        event = new Event(b.getString("name"),
                b.getString("date"),
                b.getString("description"),
                b.getString("url"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_event, container, false);
        mCallBack.setTitle(event.name());

        ((TextView) view.findViewById(R.id.date)).setText(event.date().string());
        ((TextView) view.findViewById(R.id.description)).setText(event.desc());
        new ImageHttpRequest(event.url()).execute((ImageView) view.findViewById(R.id.image));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mCallBack.setButtons(R.id.events_button);
    }
}
