package govhackathon.pl.mobileapp.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;

import govhackathon.pl.mobileapp.R;

public class DetailViewActivity extends AppCompatActivity {

    public static ApiDataMarker apiDataMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView descriptionTv = (TextView)findViewById(R.id.description);
        TextView titleTv = (TextView)findViewById(R.id.title);
        TextView priceTv = (TextView)findViewById(R.id.price);

        if (apiDataMarker != null) {
            if (apiDataMarker.getDescription() != null)
                descriptionTv.setText(apiDataMarker.getDescription());

            if (apiDataMarker.getName() != null) {
                titleTv.setText(apiDataMarker.getName());

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(apiDataMarker.getName());
                }
            }

            priceTv.setText(NumberFormat.getNumberInstance(Locale.US).format(apiDataMarker.getPrice()) + " zł");

            if (apiDataMarker.getLink() != null) {
                new DownloadImageTask((ImageView) findViewById(R.id.image))
                        .execute(apiDataMarker.getLink());
            }

            if (getActionBar() != null) {
                setTitle(apiDataMarker.getName());
                getActionBar().setTitle(apiDataMarker.getName());
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
