package example.proyectocibertec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import example.proyectocibertec.adapter.CharlaExpositorAdapter;
import example.proyectocibertec.clases.Expositor;

public class CharlaExpositoresActivity extends AppCompatActivity implements View.OnClickListener {

    List<Expositor> listExpositor;
    RecyclerView recyclerViewCharlaExpositor;
    CharlaExpositorAdapter charlaExpositorAdapter;
    ImageButton btnAnterior, btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charla_expositores);

        inicializarControles();

        //Llenando la lista de expositores
        llenarListaExpositor();

        recyclerViewCharlaExpositor = findViewById(R.id.recyclerCharlaExpositores);
        recyclerViewCharlaExpositor.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        charlaExpositorAdapter = new CharlaExpositorAdapter(listExpositor);
        recyclerViewCharlaExpositor.setAdapter(charlaExpositorAdapter);
    }

    private void inicializarControles() {
        setTitle("Expositores");
        listExpositor = new ArrayList<Expositor>();

        btnAnterior = findViewById(R.id.btnAnteriorCharlaExpositores);
        btnSiguiente = findViewById(R.id.btnSiguienteCharlaExpositores);

        btnAnterior.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
    }

    private void llenarListaExpositor() {
        Expositor expositor1 = new Expositor(1,"Juan Perez","Expositor Principal", R.drawable.facebook);
        Expositor expositor2 = new Expositor(2,"Jose Cueva","Expositor Principal", R.drawable.github);
        Expositor expositor3 = new Expositor(3,"Fernando Gamarra","Expositor Secundario", R.drawable.instagram);
        Expositor expositor4 = new Expositor(4,"Miguel Paredes","Expositor Secundario", R.drawable.youtube);
        Expositor expositor5 = new Expositor(5,"Mario Verano","Capacitador", R.drawable.linkedin);
        listExpositor.add(expositor1);
        listExpositor.add(expositor2);
        listExpositor.add(expositor3);
        listExpositor.add(expositor4);
        listExpositor.add(expositor5);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAnteriorCharlaExpositores:
                Intent intentAnt = new Intent(CharlaExpositoresActivity.this, NuevaCharlaActivity.class);
                startActivity(intentAnt);
                break;
            case R.id.btnSiguienteCharlaExpositores:
                Intent intentSig = new Intent(CharlaExpositoresActivity.this, CharlaProductoActivity.class);
                startActivity(intentSig);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Registro Charla")
                .setMessage("Aún no termina con el registro de la Charla, Desea Salir?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Intent intent = new Intent(CharlaExpositoresActivity.this, DrawerActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar",null);
        builder.show();
    }
}
