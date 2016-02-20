package slugapp.com.ucscstudentapp.dining;

import java.util.List;

/**
 * Created by isayyuhh_s on 9/2/2015.
 */
public class Food {
    private String name;
    private List<FoodAttribute> attributes;

    public Food(String name, List<FoodAttribute> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String name () {
        return this.name;
    }
    public List<FoodAttribute> attributes () {
        return this.attributes;
    }
}
