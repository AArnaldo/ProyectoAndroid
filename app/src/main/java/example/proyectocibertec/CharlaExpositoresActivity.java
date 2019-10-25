package example.proyectocibertec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import example.proyectocibertec.adapter.CharlaExpositorAdapter;
import example.proyectocibertec.clases.Expositor;

public class CharlaExpositoresActivity extends AppCompatActivity {

    List<Expositor> listExpositor;
    RecyclerView recyclerViewCharlaExpositor;
    CharlaExpositorAdapter charlaExpositorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charla_expositores);

        setTitle("Expositores");

        listExpositor = new ArrayList<Expositor>();

        //Llenando la lista de expositores
        llenarListaExpositor();

        recyclerViewCharlaExpositor = findViewById(R.id.recyclerCharlaExpositores);
        recyclerViewCharlaExpositor.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        charlaExpositorAdapter = new CharlaExpositorAdapter(listExpositor);
        recyclerViewCharlaExpositor.setAdapter(charlaExpositorAdapter);
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
}
