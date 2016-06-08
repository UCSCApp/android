package slugapp.com.ucscstudentapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.models.Event;
import slugapp.com.ucscstudentapp.http.ImageHttpRequest;

/**
 * Created by isaiah on 6/27/2015.
 * <p/>
 * This file displays all of the contents for each individual event.
 */
public class EventDetailFragment extends BaseDetailFragment {
    private Event event;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        this.event = this.mCallback.getGson().fromJson(b.getString("json"), Event.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_event, container, false);
        //this.setLayout(event.name(), R.id.events_button);
        this.setView(view);
        return view;
    }

    @Override
    protected void setView(View view) {
        TextView day = (TextView) view.findViewById(R.id.day);
        TextView start = (TextView) view.findViewById(R.id.start);
        TextView end = (TextView) view.findViewById(R.id.end);
        TextView description = (TextView) view.findViewById(R.id.description);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        if (event.date().isDefined()) {
            String dayString = "When: " + event.date().getMonth().getVal() + " " + event.date().getDay();
            String startString = "Starts: " + String.valueOf(event.date().getStartTime()) +
                    event.date().getStartTOD();
            String endString = "Ends: " + String.valueOf(event.date().getEndTime()) +
                    event.date().getEndTOD();

            day.setText(dayString);
            start.setText(startString);
            end.setText(endString);
        } else day.setText("When: " + event.date().getString());

        description.setText(event.getDesc());
        new ImageHttpRequest(event.url()).execute(image);
    }
}
