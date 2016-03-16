package slugapp.com.ucscstudentapp.models;

import java.util.List;

import slugapp.com.ucscstudentapp.enums.AttributeEnum;

/**
 * Created by isayyuhh_s on 9/2/2015.
 */
public class Food {
    private String name;
    private List<AttributeEnum> attributes;

    public Food(String name, List<AttributeEnum> attributes) {
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
