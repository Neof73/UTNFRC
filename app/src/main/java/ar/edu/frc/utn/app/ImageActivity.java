package ar.edu.frc.utn.app;

        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        //codigo para mostrar la imagen...
        Intent intent = this.getIntent();
        String str = intent.getExtras().getString(Intent.EXTRA_TEXT, "");
        Bitmap bm = BitmapFactory.decodeFile(str, null);
        ImageView imgView = (ImageView) findViewById(R.id.imgviewB);
        imgView.setImageBitmap(bm);
    }
}

