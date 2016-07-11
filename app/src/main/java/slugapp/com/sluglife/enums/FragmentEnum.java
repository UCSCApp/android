package slugapp.com.sluglife.enums;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.fragments.DiningHallListFragment;
import slugapp.com.sluglife.fragments.EventListFragment;
import slugapp.com.sluglife.fragments.MapViewFragment;
import slugapp.com.sluglife.fragments.SocialFragment;

/**
 * Created by isayyuhh on 3/16/16.
 */
public enum FragmentEnum {
    MAP(MapViewFragment.class, 0, R.drawable.ic_map, "Map"),
    EVENT(EventListFragment.class, 1, R.drawable.ic_events, "Events"),
    DINING(DiningHallListFragment.class, 2, R.drawable.ic_dining, "Dining Halls"),
    SOCIAL(SocialFragment.class, 3, R.drawable.ic_social, "Tweets");

    public Class fragment;
    public int position;
    public int imageId;
    public String name;

    FragmentEnum(Class fragment, int position, int imageId, String name) {
        this.fragment = fragment;
        this.position = position;
        this.imageId = imageId;
        this.name = name;
    }
}
