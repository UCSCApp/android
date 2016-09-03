package slugapp.com.sluglife.enums;

import java.io.Serializable;

import slugapp.com.sluglife.R;

/**
 * Created by isaiah on 9/24/2015
 * <p/>
 * This file contains an enum containing information about dining hall attributes.
 */
public enum AttributeEnum implements Serializable {
    EGGS(R.string.dining_food_attribute_eggs, R.drawable.ic_egg),
    MILK(R.string.dining_food_attribute_milk, R.drawable.ic_milk),
    SOY(R.string.dining_food_attribute_soy, R.drawable.ic_soy),
    VEGGIE(R.string.dining_food_attribute_veggie, R.drawable.ic_veggie),
    GLUTEN(R.string.dining_food_attribute_gluten_free, R.drawable.ic_gluten),
    VEGAN(R.string.dining_food_attribute_vegan, R.drawable.ic_vegan),
    NUTS(R.string.dining_food_attribute_nuts, R.drawable.ic_nuts),
    FISH(R.string.dining_food_attribute_fish, R.drawable.ic_fish),
    PORK(R.string.dining_food_attribute_pork, R.drawable.ic_pork),
    BEEF(R.string.dining_food_attribute_beef, R.drawable.ic_beef);

    public int name;
    public int icon;

    /**
     * Constructor
     *
     * @param name Name of attribute
     * @param icon Resource of attribute image
     */
    AttributeEnum(int name, int icon) {
        this.name = name;
        this.icon = icon;
    }
}
