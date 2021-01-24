 package mimo.krokodil.downloadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

 public class MainActivity extends AppCompatActivity {

    private DownloadImageTask downloadImageTask;
    private ImageView imageView;
    private Bitmap bitmap;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

     public void onClickDownloadImage(View view) {
        url = getString(R.string.image_url);
        imageView = findViewById(R.id.imageView);

        downloadImageTask = new DownloadImageTask();
        try {
            bitmap = downloadImageTask.execute(url).get();
        } catch(Exception e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);
     }

     private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... strings) {
            URL url;
            HttpURLConnection httpURLConnection = null;
            Bitmap bitmap = null;

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return bitmap;
        }
    }
}

