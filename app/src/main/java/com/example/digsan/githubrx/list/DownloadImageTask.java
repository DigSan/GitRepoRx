package com.example.digsan.githubrx.list;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.digsan.githubrx.R;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by digsan on 10.09.2016.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    static Map<String, Bitmap> picturesRepository = new HashMap<>();

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    @Override
    protected void onPreExecute() {
        bmImage.setImageResource(R.mipmap.ic_launcher);
    }

    protected Bitmap doInBackground(String... urls) {
        if (picturesRepository.containsKey(urls[0]))
            return picturesRepository.get(urls[0]);

        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {


            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
            picturesRepository.put(urldisplay, mIcon11);
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
