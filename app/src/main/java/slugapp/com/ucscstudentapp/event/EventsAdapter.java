package slugapp.com.ucscstudentapp.event;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.InputStream;
import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.event.Event;

/**
 * Created by simba on 5/31/15.
 */
public class EventsAdapter extends ArrayAdapter<Event> {
    public EventsAdapter(Context context, List<Event> objects) {
        super(context, R.layout.events_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.events_item, null);
        }
        Event e = getItem(position);
        if (e.name() == "" && e.date() == "" && e.desc() == "" && e.url() != "") {
            ((ViewManager) convertView).removeView(convertView.findViewById(R.id.text));
            //new DownloadImageTask((ImageView) convertView.findViewById(R.id.image)).execute(e.url());
            ImageLoader image_loader = ImageLoader.getInstance();
            image_loader.displayImage(e.url(), (ImageView) convertView.findViewById(R.id.image));
        } else {
            String short_description = e.desc().length() > 100 ? (e.desc().substring(0, 100) + "...") : e.desc();
            ((TextView) convertView.findViewById(R.id.name)).setText(e.name());
            ((TextView) convertView.findViewById(R.id.date)).setText(e.date());
            ((TextView) convertView.findViewById(R.id.description)).setText(short_description);
            if (e.url() != "") {
                //new DownloadImageTask((ImageView) convertView.findViewById(R.id.image)).execute(e.url());
                ImageLoader image_loader = ImageLoader.getInstance();
                image_loader.displayImage(e.url(), (ImageView) convertView.findViewById(R.id.image));
            }
        }
        return convertView;
    }
    /*
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bitmap_image;

        public DownloadImageTask(ImageView bitmap_image) {
            this.bitmap_image = bitmap_image;
        }

        protected Bitmap doInBackground(String... urls) {
            String url_display = urls[0];
            Bitmap icon = null;
            try {
                InputStream in = new java.net.URL(url_display).openStream();
                icon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return icon;
        }

        protected void onPostExecute(Bitmap result) {
            bitmap_image.setImageBitmap(result);
        }
    }
    */
}
