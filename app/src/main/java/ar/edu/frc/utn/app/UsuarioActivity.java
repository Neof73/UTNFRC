package ar.edu.frc.utn.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
    }

    public void enviarLogin(View view) {
        Intent result = new Intent();
        EditText usuario = (EditText) findViewById(R.id.editText);
        EditText password = (EditText) findViewById(R.id.editText2);
        result.putExtra("usuario", usuario.getText().toString());
        result.putExtra("password", password.getText().toString());
        setResult(RESULT_OK, result);
        finish();
    }
}
