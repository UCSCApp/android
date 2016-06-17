package slugapp.com.sluglife.enums;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.fragments.DiningHallListFragment;
import slugapp.com.sluglife.fragments.MapFragment;
import slugapp.com.sluglife.fragments.SocialFragment;

/**
 * Created by isayyuhh on 3/16/16.
 */
public enum FragmentEnum {
    //EVENT(EventListFragment.class, R.id.events_button, R.drawable.ic_events, "Events"),
    MAP(MapFragment.class, R.id.button_1, R.drawable.ic_map, "Map"),
    DINING(DiningHallListFragment.class, R.id.button_2, R.drawable.ic_dining, "Dining Halls"),
    SOCIAL(SocialFragment.class, R.id.button_3, R.drawable.ic_social, "Social");

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
