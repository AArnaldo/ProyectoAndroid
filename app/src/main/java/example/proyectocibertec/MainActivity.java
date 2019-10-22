package example.proyectocibertec;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton ibtnLinkedIn, ibtnYouTube, ibtnInstagram;
    private Button btnIngresar;
    private EditText etUsuario, etPassword;
    private String Link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializar
        ibtnLinkedIn = findViewById(R.id.ibtn_LinkedIn);
        ibtnYouTube = findViewById(R.id.ibtn_YouTube);
        ibtnInstagram = findViewById(R.id.ibtn_Instagram);
        btnIngresar = findViewById(R.id.btnIngresar);

        etUsuario = (EditText)findViewById(R.id.txt_Usuario);
        etPassword = (EditText)findViewById(R.id.txt_Password);
    }

    public void Ingresar(View view){
        String nombre = etUsuario.getText().toString();
        String password = etPassword.getText().toString();

        if (nombre.length() == 0){
            Toast.makeText(this, "Ingrese el usuario", Toast.LENGTH_SHORT).show();
        }
        if (password.length() == 0){
            Toast.makeText(this, "Ingrese la Contraseña", Toast.LENGTH_SHORT).show();
        }

        if (nombre.length()!= 0 && password.length() != 0){
            Toast.makeText(this, "Registro en Proceso...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, DrawerActivity.class);
            startActivity(intent);

            /*
            btnIngresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, DrawerActivity.class);
                    startActivity(intent);
                }
            });
            * */
        }
    }

    private void goLinkedIn(View view){
        String URLLinkedInd = "www.https://www.linkedin.com/school/cibertec/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(URLLinkedInd));
        startActivity(i);
    }
}
