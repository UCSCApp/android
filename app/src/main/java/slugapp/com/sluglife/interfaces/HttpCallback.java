package slugapp.com.sluglife.interfaces;

/**
 * Created by simba on 7/31/15
 * Edited by isaiah on 8/3/2015
 * <p/>
 * This file contains a callback for http requests to relay information back to the calling
 * fragments/activities.
 */
public interface HttpCallback<T> {

    /**
     * On http request success
     *
     * @param val Retrieved value
     */
    void onSuccess(T val);

    /**
     * On http request error
     *
     * @param e Exception
     */
    void onError(Exception e);
}