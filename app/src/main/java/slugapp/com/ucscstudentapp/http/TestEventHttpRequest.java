package slugapp.com.ucscstudentapp.http;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import slugapp.com.ucscstudentapp.event.Event;
import slugapp.com.ucscstudentapp.event.EventListWrapper;

/**
 * Created by simba on 8/1/15.
 */
public class TestEventHttpRequest extends EventHttpRequest {
    private final static String json = "[\n" +
            "       {\n" +
            "          \"name\":\"Edge of Eden\",\n" +
            "          \"date\":\"July 18th 10pm 2am\",\n" +
            "          \"description\":\"Musical festival with a finale by Squidward Tortellini\",\n" +
            "          \"url\":\"http://tmcdigitalmedia.com/wp-content/uploads/2013/03/2_27_13-FSNA_Flyer_web_versionF.jpeg\"\n" +
            "       },\n" +
            "       {\n" +
            "          \"name\":\"Holi Festival\",\n" +
            "          \"date\":\"May 25th 10am 12pm\",\n" +
            "          \"description\":\"This event is super fun and super great!\",\n" +
            "          \"url\":\"https://uh.collegiatelink.net/images/W460xL600/0/noshadow/Event/c40dcb200abb430c9c20c632473b959f.jpg\"\n" +
            "       },\n" +
            "       {\n" +
            "          \"name\":\"Meeting\",\n" +
            "          \"date\":\"May 31st 2pm 4pm\",\n" +
            "          \"description\":\"Simba is asking you to go to this super boring meeting thing again\",\n" +
            "          \"url\":\"http://img01.deviantart.net/d568/i/2012/062/a/1/steam_event_flyer_design_by_danwilko-d4rke4s.jpg\"\n" +
            "       },\n" +
            "       {\n" +
            "          \"name\":\"French Fried\",\n" +
            "          \"date\":\"January 1st 13am 7pm\",\n" +
            "          \"description\":\"TBH Im not entirely sure what this is, so please dont come to this event at all, This desciption is purposely awkwardly long to hopefully break all of your apps becase I am a devious motherfucker like that. So I am still writing random shit now to break your apps in my malicious ways. I wonder if anyone will actually handle this case. I sure as hell wouldnt. Who the fuck actually spends the time to write this long a description about a dumb event called French Fried holy fuck. Btw I hope you arent actually reading this when you should be coding the solution to fixing your description box that just broke due to my malicious test script. Unless of course it worked in which case, you should probably stop reading this anyway because holy fuck this is a long motha fucking description. It is called French Fried. Starts at 13am. Be there.\",\n" +
            "          \"url\":\"http://www.hiddenorchestra.com/wp-content/gallery/flyers-and-posters/soundcrash-daedelus_-a5-flyer-01.jpg\"\n" +
            "       },\n" +
            "       {\n" +
            "          \"name\":\"Short but sweet\",\n" +
            "          \"date\":\"never\",\n" +
            "          \"description\":\"meow\",\n" +
            "          \"url\":\"https://www.designmaz.net/wp-content/uploads/2014/11/psd-event-flyer-templates.jpg\"\n" +
            "       }\n" +
            "]";

    @Override
    public void execute(Callback<List<Event>> callback) {
        try {
            JSONArray arr = new JSONArray(json);
            callback.onSuccess(new EventListWrapper(arr));
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }
}
