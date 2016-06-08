package slugapp.com.ucscstudentapp.enums;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.fragments.DiningHallListFragment;
import slugapp.com.ucscstudentapp.fragments.EventListFragment;
import slugapp.com.ucscstudentapp.fragments.MapFragment;
import slugapp.com.ucscstudentapp.social.InstagramFeedFragment;

/**
 * Created by isayyuhh on 3/16/16.
 */
public enum FragmentEnum {
    //EVENT(EventListFragment.class, R.id.events_button, R.drawable.ic_events, "Events"),
    MAP(MapFragment.class, R.id.map_button, R.drawable.ic_map, "Map"),
    DINING(DiningHallListFragment.class, R.id.dining_button, R.drawable.ic_dining, "Dining");

    private Class fragment;
    private int buttonId;
    private int imageId;
    private String name;

    FragmentEnum(Class fragment, int buttonId, int imageId, String name) {
        this.fragment = fragment;
        this.buttonId = buttonId;
        this.imageId = imageId;
        this.name = name;
    }

    public Class getFragment() {
        return this.fragment;
    }

    public int getButtonId() {
        return this.buttonId;
    }

    public int getImageId() {
        return this.imageId;
    }

    public String getName() {
        return this.name;
    }
}
