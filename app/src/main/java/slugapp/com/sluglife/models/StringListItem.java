package slugapp.com.sluglife.models;

/**
 * Created by isayyuhh on 3/16/16.
 */
public class StringListItem extends BaseListItem {
    public String mString;

    public StringListItem(String string) {
        this.mString = string;
    }

    public String getString() {
        return this.mString;
    }
}
