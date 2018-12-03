package ar.edu.frc.utn.app.Gallery;

        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.GridView;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;

        import ar.edu.frc.utn.app.R;

/**
 * Created by Mario Di Giorgio on 01/06/2017.
 */

public class GalleryAdapter extends BaseAdapter {
    private Context context;
    ArrayList<String> itemlist;

    public GalleryAdapter(Context context) {

        this.context = context;
        itemlist = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        return itemlist.size();
    }

    @Override
    public Object getItem(int position) {
        return itemlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.activity_image, null);
        }
        try {

            ImageView imageView = (ImageView) v.findViewById(R.id.imgviewB);
            //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(4, 4, 4, 4);
            Bitmap bmp = decodeSampledBitmapFromUri(itemlist.get(position), 200, 200);
            //Bitmap bmp = decodeURI(itemlist.get(position));
            //BitmapFactory.decodeFile(mUrls[position].getPath());
            imageView.setImageBitmap(bmp);
            //new GalleryTask(imageView).execute(itemlist.get(position));
            //TextView txtName = (TextView) v.findViewById(R.id.TextView01);
            //txtName.setText(mNames[position]);
        } catch (Exception e) {

        }
        return v;
    }

    public View getView2(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(this.context);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
            //Bitmap bmp = BitmapFactory.decodeFile(itemlist.get(position));
            Bitmap bmp = decodeSampledBitmapFromUri(itemlist.get(position), 200, 200);
            imageView.setImageBitmap(bmp);
            //new GalleryTask(imageView).execute(itemlist.get(position).toString());
        } else {
            imageView = (ImageView) convertView;
        }

        //codigo para tratar la imagen de manera eficiente....

        //Bitmap bmp = decodeSampledBitmapFromUri(itemlist.get(position), 200, 200);
        //imageView.setImageBitmap(bmp);
        //new GalleryTask(imageView).execute(itemlist.get(position).toString());
        return imageView;
    }

    /**
     * This method is to scale down the image
     */
    public Bitmap decodeURI(String filePath){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Only scale if we need to
        // (16384 buffer for img processing)
        Boolean scaleByHeight = Math.abs(options.outHeight - 100) >= Math.abs(options.outWidth - 100);
        if(options.outHeight * options.outWidth * 2 >= 16384){
            // Load, scaling to smallest power of 2 that'll get it <= desired dimensions
            double sampleSize = scaleByHeight
                    ? options.outHeight / 100
                    : options.outWidth / 100;
            options.inSampleSize =
                    (int)Math.pow(2d, Math.floor(
                            Math.log(sampleSize)/Math.log(2d)));
        }

        // Do the actual decoding
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[512];
        Bitmap output = BitmapFactory.decodeFile(filePath, options);

        return output;
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

    public void add(String path){
        itemlist.add(path);
    }
}
