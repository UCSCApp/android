package slugapp.com.sluglife.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by isaiah on 9/2/2015
 * <p/>
 * This file contains an food menu object.
 */
public class FoodMenuObject extends BaseObject {
    private List<FoodObject> items;

    /**
     * Constructor
     */
    public FoodMenuObject() {
        items = new ArrayList<>();
    }

    /**
     * Adds food to food menu
     *
     * @param item Food
     */
    public void add(FoodObject item) {
        this.items.add(item);
    }

    /**
     * Gets food at position
     *
     * @param position Position of food
     * @return Food at position
     */
    public FoodObject get(int position) {
        return this.items.get(position);
    }

    /**
     * Gets size of food menu
     *
     * @return Size of food menu
     */
    public int size() {
        return this.items.size();
    }

    /**
     * Checks if food menu is empty
     *
     * @return Boolean if food menu is empty
     */
    public boolean isEmpty() {
        return this.items.size() == 0;
    }

    /**
     * Gets an immutable copy of the food menu list
     *
     * @return Immutable copy of the food menu list
     */
    public List<FoodObject> getItems() {
        return Collections.unmodifiableList(this.items);
    }
}
