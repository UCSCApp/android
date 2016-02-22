package slugapp.com.ucscstudentapp.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.http.ImageHttpRequest;
import slugapp.com.ucscstudentapp.main.BaseFragment;

/**
 * Created by isaiah on 6/27/2015.
 * <p/>
 * This file displays all of the contents for each individual event.
 */
public class EventDetail extends BaseFragment {
    private Event event;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        this.event = new Event(
                b.getString("name"),
                b.getString("date"),
                b.getString("description"),
                b.getString("url"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_event, container, false);
        this.setLayout(event.name(), R.id.events_button);
        this.setView(view);
        return view;
    }

    @Override
    protected void setView(View view) {
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView description = (TextView) view.findViewById(R.id.description);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        date.setText(event.date().getString());
        description.setText(event.getDesc());
        new ImageHttpRequest(event.url()).execute(image);
    }
}
