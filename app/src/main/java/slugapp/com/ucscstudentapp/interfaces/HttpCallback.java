package slugapp.com.ucscstudentapp.interfaces;

/**
 * Created by simba on 7/31/15.
 */
public interface HttpCallback<T> {
    void onSuccess(T val);
    void onError(Exception e);
}