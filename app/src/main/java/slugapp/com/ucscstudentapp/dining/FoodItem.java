package slugapp.com.ucscstudentapp.dining;

import java.util.List;

/**
 * Created by isayyuhh_s on 9/2/2015.
 */
public class FoodItem {
    private String name;
    private List<AttributeEnum> attributes;

    public FoodItem (String name, List<AttributeEnum> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String name () {
        return this.name;
    }
    public List<AttributeEnum> attributes () {
        return this.attributes;
    }
}
