package slugapp.com.sluglife.models;

import java.util.List;

import slugapp.com.sluglife.enums.AttributeEnum;

/**
 * Created by isaiah on 9/2/2015
 * <p/>
 * This file contains a food object.
 */
public class FoodObject extends BaseObject {
    public String name;
    public List<AttributeEnum> attributes;

    /**
     * Constructor
     *
     * @param name       Name of food
     * @param attributes Food attributes
     */
    public FoodObject(String name, List<AttributeEnum> attributes) {
        this.name = name;
        this.attributes = attributes;
    }
}
