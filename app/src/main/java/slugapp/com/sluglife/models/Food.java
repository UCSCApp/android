package slugapp.com.sluglife.models;

import java.util.List;

import slugapp.com.sluglife.enums.AttributeEnum;

/**
 * Created by isayyuhh_s on 9/2/2015.
 */
public class Food {
    private String mName;
    private List<AttributeEnum> mAttributes;

    public Food(String name, List<AttributeEnum> attributes) {
        this.mName = name;
        this.mAttributes = attributes;
    }

    public String getName() {
        return this.mName;
    }

    public List<AttributeEnum> getAttributes() {
        return this.mAttributes;
    }
}
