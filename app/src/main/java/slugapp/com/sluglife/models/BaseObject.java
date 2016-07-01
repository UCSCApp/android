package slugapp.com.sluglife.models;

import java.io.Serializable;

/**
 * Created by isayyuhh on 3/16/16.
 */
public abstract class BaseObject implements Serializable {
    protected boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }
}
