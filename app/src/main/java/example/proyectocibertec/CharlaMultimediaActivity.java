package example.proyectocibertec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CharlaMultimediaActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAnterior, btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charla_multimedia);

        inicializarControles();


    }

    private void inicializarControles() {
        setTitle("Multimedia");

        btnAnterior = findViewById(R.id.btnAnteriorCharlaMultimedia);
        btnSiguiente = findViewById(R.id.btnSiguienteCharlaMultimedia);

        btnAnterior.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAnteriorCharlaMultimedia:
                Intent intentAnt = new Intent(this, CharlaProductoActivity.class);
                startActivity(intentAnt);
                break;
            case R.id.btnSiguienteCharlaMultimedia:
                Intent intentSig = new Intent(this, CharlaUbicacionActivity.class);
                startActivity(intentSig);
                break;
        }
    }
}
