package slugapp.com.ucscstudentapp.http;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import slugapp.com.ucscstudentapp.interfaces.HttpCallback;
import slugapp.com.ucscstudentapp.models.Event;
import slugapp.com.ucscstudentapp.models.EventListWrapper;

/**
 * Created by simba on 8/1/15.
 */
public class TestEventListHttpRequest extends EventListHttpRequest {
    private final static String json = "[\n" +
            "       {\n" +
            "          \"name\":\"Edge of Eden\",\n" +
            "          \"date\":\"July 18th 10pm 2am\",\n" +
            "          \"description\":\"Musical festival with a finale by Squidward Tortellini\",\n" +
            "          \"url\":\"http://www.preparingu.com/wiki/images/d/d6/Burning_Bush_flier_A.jpg\"\n" +
            "       },\n" +
            "       {\n" +
            "          \"name\":\"Holi Festival\",\n" +
            "          \"date\":\"May 25th 10am 12pm\",\n" +
            "          \"description\":\"This event is super fun and super great!\",\n" +
            "          \"url\":\"http://www.taradarcydesigns.com/images/portfolio/fliers/farmers-market-flier2012legal.jpg\"\n" +
            "       },\n" +
            "       {\n" +
            "          \"name\":\"Meeting\",\n" +
            "          \"date\":\"May 31st 2pm 4pm\",\n" +
            "          \"description\":\"Simba is asking you to go to this super boring meeting thing again\",\n" +
            "          \"url\":\"http://bcskateboardingalliance.org/wordpress/wp-content/uploads/2011/03/BCSA-Diego-Flier1-600x927.jpg\"\n" +
            "       },\n" +
            "       {\n" +
            "          \"name\":\"French Fried\",\n" +
            "          \"date\":\"January 1st 13am 7pm\",\n" +
            "          \"description\":\"TBH Im not entirely sure what this is, so please dont come to this event at all, This desciption is purposely awkwardly long to hopefully break all of your apps becase I am a devious motherfucker like that. So I am still writing random shit now to break your apps in my malicious ways. I wonder if anyone will actually handle this case. I sure as hell wouldnt. Who the fuck actually spends the time to write this long a description about a dumb event called French Fried holy fuck. Btw I hope you arent actually reading this when you should be coding the solution to fixing your description box that just broke due to my malicious test script. Unless of course it worked in which case, you should probably stop reading this anyway because holy fuck this is a long motha fucking description. It is called French Fried. Starts at 13am. Be there.\",\n" +
            "          \"url\":\"http://pre07.deviantart.net/a682/th/pre/f/2013/132/2/4/butter_rebellion_flier_by_sharkynobarky-d63wewa.png\"\n" +
            "       },\n" +
            "       {\n" +
            "          \"name\":\"Short but sweet\",\n" +
            "          \"date\":\"never\",\n" +
            "          \"description\":\"meow\",\n" +
            "          \"url\":\"https://www.designmaz.net/wp-content/uploads/2014/11/psd-event-flyer-templates.jpg\"\n" +
            "       }\n" +
            "]";

    @Override
    public void execute(HttpCallback<List<Event>> callback) {
        try {
            JSONArray arr = new JSONArray(json);
            callback.onSuccess(new EventListWrapper(arr));
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }
}
