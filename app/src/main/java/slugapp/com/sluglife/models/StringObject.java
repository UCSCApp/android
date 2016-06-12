package slugapp.com.sluglife.models;

/**
 * Created by isayyuhh on 3/16/16.
 */
public class StringObject extends BaseObject {
    public String mString;

    public StringObject(String string) {
        this.mString = string;
    }

    public String getString() {
        return this.mString;
    }
}
