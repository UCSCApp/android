package slugapp.com.ucscstudentapp.enums;

import slugapp.com.ucscstudentapp.fragments.DiningHallListFragment;
import slugapp.com.ucscstudentapp.fragments.EventListFragment;
import slugapp.com.ucscstudentapp.fragments.MapFragment;

/**
 * Created by isayyuhh on 3/16/16.
 */
public enum FragmentEnum {
    EVENT(EventListFragment.class),
    DINING(DiningHallListFragment.class),
    MAP(MapFragment.class);

    private Class fragment;

    FragmentEnum(Class fragment) {
        this.fragment = fragment;
    }

    public Class getFragment() {
        return this.fragment;
    }
}
