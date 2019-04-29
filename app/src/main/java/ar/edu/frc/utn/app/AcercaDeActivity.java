package ar.edu.frc.utn.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class AcercaDeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
        ImageView imgView = (ImageView) findViewById(R.id.about_logo);
        imgView.setImageResource(R.drawable.logoutn7);
        TextView utnEmail = (TextView) findViewById(R.id.about_email);
        utnEmail.setMovementMethod(LinkMovementMethod.getInstance());
        TextView utnWeb = (TextView) findViewById(R.id.about_website);
        utnWeb.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
