package slugapp.com.ucscstudentapp.dining;

import slugapp.com.ucscstudentapp.R;

/**
 * Created by isayyuhh on 9/24/2015.
 */
public enum FoodAttribute {
    EGGS("Eggs", R.drawable.ic_egg),
    MILK("Milk", R.drawable.ic_milk),
    SOY("Soy", R.drawable.ic_soy),
    VEGGIE("Veggie", R.drawable.ic_veggie),
    GLUTEN("Gluten", R.drawable.ic_gluten),
    VEGAN("Vegan", R.drawable.ic_vegan),
    NUTS("Nuts", R.drawable.ic_nuts),
    FISH("Fish", R.drawable.ic_fish),
    PORK("Pork", R.drawable.ic_pork),
    BEEF("Beef", R.drawable.ic_beef),
    ALLERGY_FREE("Allergy free", R.drawable.ic_allergy_free);

    private int icon;
    private String string;

    FoodAttribute(String string, int icon) {
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
