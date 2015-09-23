package slugapp.com.ucscstudentapp.dining;

import java.util.List;

/**
 * Created by isayyuhh_s on 9/2/2015.
 */
public class FoodItem {
    private String name;
    private List<DiningHall.Attribute> attributes;

    public FoodItem (String name, List<DiningHall.Attribute> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String name () {
        return this.name;
    }
    public List<DiningHall.Attribute> attributes () {
        return this.attributes;
    }
}
