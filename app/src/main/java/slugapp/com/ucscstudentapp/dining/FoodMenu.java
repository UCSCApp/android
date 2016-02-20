package slugapp.com.ucscstudentapp.dining;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isayyuhh_s on 9/2/2015.
 */
public class FoodMenu {
    private List<Food> items;

    public FoodMenu () {
        items = new ArrayList<>();
    }

    public void add (Food item) {
        items.add(item);
    }
    public Food get (int position) {
        return items.get(position);
    }
    public int size () {
        return items.size();
    }
}
