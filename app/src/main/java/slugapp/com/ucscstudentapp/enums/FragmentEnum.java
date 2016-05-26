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
    EVENT(EventListFragment.class, R.id.events_button, R.drawable.ic_events),
    DINING(DiningHallListFragment.class, R.id.dining_button, R.drawable.ic_dining),
    MAP(MapFragment.class, R.id.map_button, R.drawable.ic_map);
    //,SOCIAL(InstagramFeedFragment.class, R.id.social_button, R.drawable.ic_social);

    private Class fragment;
    private int buttonId;
    private int imageId;

    FragmentEnum(Class fragment, int buttonId, int imageId) {
        this.fragment = fragment;
        this.buttonId = buttonId;
        this.imageId = imageId;
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
}
