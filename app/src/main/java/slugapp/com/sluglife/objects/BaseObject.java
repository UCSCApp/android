package slugapp.com.sluglife.objects;

import java.io.Serializable;

/**
 * Created by isaiah on 3/16/16
 * <p/>
 * This file contains a base object.
 */
public abstract class BaseObject implements Serializable {

    /**
     * Checks if string can be converted to an integer
     *
     * @param string String to be checked
     * @return Boolean if string can be converted to an integer
     */
    protected static boolean isInteger(String string) {
        try {
            int result = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if string can be converted to an double
     *
     * @param string String to be checked
     * @return Boolean if string can be converted to a double
     */
    protected static boolean isDouble(String string) {
        try {
            double result = Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }
}
