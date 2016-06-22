package slugapp.com.sluglife.models;

import java.util.List;

import slugapp.com.sluglife.enums.AttributeEnum;

/**
 * Created by isayyuhh_s on 9/2/2015.
 */
public class Food {
    public String name;
    public List<AttributeEnum> attributes;

    public Food(String name, List<AttributeEnum> attributes) {
        this.name = name;
        this.attributes = attributes;
    }
}
