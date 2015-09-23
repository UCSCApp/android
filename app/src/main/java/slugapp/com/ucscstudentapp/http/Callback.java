package slugapp.com.ucscstudentapp.http;

/**
 * Created by simba on 7/31/15.
 */
public interface Callback<T> {
    void onSuccess(T val);
    void onError(Exception e);
}