package slugapp.com.ucscstudentapp.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by isayyuhh_s on 9/2/2015.
 */
public class FoodMenu {
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

    public List<Food> getItems() {
        return Collections.unmodifiableList(this.items);
    }
}
