package slugapp.com.sluglife.models;

/**
 * Created by isayyuhh on 3/16/16.
 */
public abstract class BaseObject {
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
