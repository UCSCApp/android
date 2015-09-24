package slugapp.com.ucscstudentapp.dining;

import slugapp.com.ucscstudentapp.R;

/**
 * Created by isayyuhh on 9/24/2015.
 */
public enum AttributeEnum {
    EGGS("eggs", R.drawable.ic_egg),
    MILK("milk", R.drawable.ic_milk),
    SOY("soy", R.drawable.ic_soy),
    VEGGIE("veggie", R.drawable.ic_veggie),
    GLUTEN("gluten", R.drawable.ic_gluten),
    VEGAN("vegan", R.drawable.ic_vegan),
    NUTS("nuts", R.drawable.ic_nuts),
    FISH("fish", R.drawable.ic_fish),
    PORK("pork", R.drawable.ic_pork),
    BEEF("beef", R.drawable.ic_beef);

    private int icon;
    private String string;

    AttributeEnum(String string, int icon) {
        this.icon = icon;
        this.string = string;
    }

    public int getIcon() {
        return this.icon;
    }
    public String getString() {
        return this.string;
    }
}
