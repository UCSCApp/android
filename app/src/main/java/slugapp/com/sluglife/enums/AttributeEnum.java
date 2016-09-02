package slugapp.com.sluglife.enums;

import java.io.Serializable;

import slugapp.com.sluglife.R;

/**
 * Created by isaiah on 9/24/2015
 * <p/>
 * This file contains an enum containing information about dining hall attributes.
 */
public enum AttributeEnum implements Serializable {
    EGGS("Eggs", R.drawable.ic_egg),
    MILK("Milk", R.drawable.ic_milk),
    SOY("Soy", R.drawable.ic_soy),
    VEGGIE("Veggie", R.drawable.ic_veggie),
    GLUTEN("Gluten Free", R.drawable.ic_gluten),
    VEGAN("Vegan", R.drawable.ic_vegan),
    NUTS("Nuts", R.drawable.ic_nuts),
    FISH("Fish", R.drawable.ic_fish),
    PORK("Pork", R.drawable.ic_pork),
    BEEF("Beef", R.drawable.ic_beef);

    public int icon;
    public String name;

    // TODO: change name to resource

    /**
     * Constructor
     *
     * @param name Name of attribute
     * @param icon Resource of attribute image
     */
    AttributeEnum(String name, int icon) {
        this.icon = icon;
        this.name = name;
    }
}
