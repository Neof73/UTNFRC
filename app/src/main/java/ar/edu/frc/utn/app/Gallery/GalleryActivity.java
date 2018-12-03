package ar.edu.frc.utn.app.Gallery;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;

import ar.edu.frc.utn.app.R;

public class GalleryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private GalleryAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        GridView gridView = (GridView) findViewById(R.id.gridview);
        imageAdapter = new GalleryAdapter(this);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(this);

        String ExternalStorageDirectoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
        String targetPath = ExternalStorageDirectoryPath +
                File.separator + "Camera" + File.separator;
        File targetDirectory = new File(targetPath);
        File[] files = targetDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                imageAdapter.add(file.getAbsolutePath());
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, imageAdapter.getItem(position).toString());
        startActivity(intent);
    }
}
