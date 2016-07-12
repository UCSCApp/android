package slugapp.com.sluglife.enums;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.fragments.DiningHallListFragment;
import slugapp.com.sluglife.fragments.EventListFragment;
import slugapp.com.sluglife.fragments.MapViewFragment;
import slugapp.com.sluglife.fragments.SocialFragment;

/**
 * Created by isayyuhh on 3/16/16
 * <p/>
 * This file contains an enum containing information about tab fragments.
 */
public enum FragmentEnum {
    MAP(MapViewFragment.class, 0, R.drawable.ic_map, R.string.tab_map),
    EVENT(EventListFragment.class, 1, R.drawable.ic_events, R.string.tab_event),
    DINING(DiningHallListFragment.class, 2, R.drawable.ic_dining, R.string.tab_dining),
    SOCIAL(SocialFragment.class, 3, R.drawable.ic_social, R.string.tab_social);

    public Class fragment;
    public int position;
    public int imageId;
    public int name;

    /**
     * Constructor
     *
     * @param fragment Tab fragment
     * @param position Position of tab fragment on tab
     * @param imageId Resource of tab image
     * @param name Name of tab
     */
    FragmentEnum(Class fragment, int position, int imageId, int name) {
        this.fragment = fragment;
        this.position = position;
        this.imageId = imageId;
        this.name = name;
    }
}
