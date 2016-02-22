package slugapp.com.ucscstudentapp.http;

import slugapp.com.ucscstudentapp.dining.DiningHall;

/**
 * Created by isayyuhh_s on 9/1/2015.
 */
public class DiningHallHttpRequest extends HttpRequest {
    private static final String url =
            "http://ec2-52-8-25-141.us-west-1.compute.amazonaws.com/events/get/v1";
    public DiningHallHttpRequest() {
        super(url, HttpRequest.Method.GET);
    }

    public void execute(final HttpCallback<DiningHall> callback) {}
}
