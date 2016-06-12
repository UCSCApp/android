package slugapp.com.sluglife.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by isayyuhh_s on 9/2/2015.
 */
public class FoodMenu {
    private List<Food> mItems;

    public FoodMenu() {
        mItems = new ArrayList<>();
    }

    public void add(Food item) {
        this.mItems.add(item);
    }

    public Food get(int position) {
        return this.mItems.get(position);
    }

    public int size() {
        return this.mItems.size();
    }

    public boolean isEmpty() {
        return this.mItems.size() == 0;
    }

    public List<Food> getItems() {
        return Collections.unmodifiableList(this.mItems);
    }
}
