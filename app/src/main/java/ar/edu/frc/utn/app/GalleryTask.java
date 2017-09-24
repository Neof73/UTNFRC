package ar.edu.frc.utn.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Usuario- on 17/11/2016.
 */

public class GalleryTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView mImageView;

    public GalleryTask(ImageView imgView){
        mImageView = imgView;
    }

    public interface Listener {
        void onImageLoaded(Bitmap bitmap);
        void onError();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return decodeSampledBitmapFromUri(params[0], 200, 200);//BitmapFactory.decodeStream((InputStream)new URL(params[0]).getContent());
    }

    @Override
    protected void onPostExecute(Bitmap bitmap){
        if (bitmap != null){
            mImageView.setImageBitmap(bitmap);
        }
    }

    private Bitmap decodeSampledBitmapFromUri(String path, int width, int height) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        options.inSampleSize = calculateSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path,options);
        return bm;
    }

    private int calculateSampleSize(BitmapFactory.Options options, int width, int height) {
        int iHeight = options.outHeight;
        int iWidth = options.outWidth;
        int inSampleSize = 1;
        if (iHeight > height || iWidth > width ){
            if (iWidth > iHeight) {
                inSampleSize = Math.round((float)iHeight / (float)height);
            } else {
                inSampleSize = Math.round((float)iWidth / (float)width);
            }
        }
        return inSampleSize;
    }

}
