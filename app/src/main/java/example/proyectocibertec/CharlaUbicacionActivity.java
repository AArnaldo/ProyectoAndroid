package example.proyectocibertec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CharlaUbicacionActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnAnterior, btnFinalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charla_ubicacion);

        inicializarControles();


    }

    private void inicializarControles() {
        setTitle("Ubicación");

        btnAnterior = findViewById(R.id.btnAnteriorCharlaUbicacion);
        btnFinalizar = findViewById(R.id.btnFinalizarCharlaUbicacion);

        btnAnterior.setOnClickListener(this);
        btnFinalizar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAnteriorCharlaUbicacion:
                Intent intentAnt = new Intent(this, CharlaMultimediaActivity.class);
                startActivity(intentAnt);
                break;
            case R.id.btnFinalizarCharlaUbicacion:
                Intent intentFin = new Intent(this, NuevaCharlaActivity.class);
                startActivity(intentFin);
                break;
        }
    }
}
