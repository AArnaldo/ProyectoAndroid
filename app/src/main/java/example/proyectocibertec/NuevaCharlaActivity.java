package example.proyectocibertec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NuevaCharlaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_charla);

        setTitle("Nueva Charla");
    }
}
