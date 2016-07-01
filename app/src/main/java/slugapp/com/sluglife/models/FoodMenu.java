package slugapp.com.sluglife.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by isayyuhh_s on 9/2/2015.
 */
public class FoodMenu extends BaseObject {
    private List<Food> items;

    public FoodMenu() {
        items = new ArrayList<>();
    }

    public void add(Food item) {
        this.items.add(item);
    }

    public Food get(int position) {
        return this.items.get(position);
    }

    public int size() {
        return this.items.size();
    }

    public boolean isEmpty() {
        return this.items.size() == 0;
    }

    public List<Food> getItems() {
        return Collections.unmodifiableList(this.items);
    }
}
