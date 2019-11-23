package example.proyectocibertec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import example.proyectocibertec.adapter.CharlaExpositorAdapter;
import example.proyectocibertec.clases.CharlaNew;
import example.proyectocibertec.clases.ExpositorNew;
import example.proyectocibertec.clases.ClientApiExpositor;
import example.proyectocibertec.clases.ClientApiProductos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharlaExpositoresActivity extends AppCompatActivity implements View.OnClickListener {

    private CharlaNew charla;
    private List<ExpositorNew> listExpositor;
    private RecyclerView recyclerViewCharlaExpositor;
    private CharlaExpositorAdapter charlaExpositorAdapter;
    private ImageButton btnAnterior, btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charla_expositores);

        inicializarControles();
        llenarListaExpositor();


    }

    private void inicializarControles() {
        setTitle("Expositores");
        listExpositor = new ArrayList<ExpositorNew>();
        btnAnterior = findViewById(R.id.btnAnteriorCharlaExpositores);
        btnSiguiente = findViewById(R.id.btnSiguienteCharlaExpositores);
        btnAnterior.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
        recyclerViewCharlaExpositor = findViewById(R.id.recyclerCharlaExpositores);
        recyclerViewCharlaExpositor.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        //obteniendo la data del anterior activity
        charla = getIntent().getParcelableExtra("objCharla");
    }

    private void llenarListaExpositor() {
        //Llamando al servicio
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ClientApiProductos.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClientApiExpositor api = retrofit.create(ClientApiExpositor.class);
        Call<List<ExpositorNew>> call = api.getExpositor(0);

        call.enqueue(new Callback<List<ExpositorNew>>() {
            @Override
            public void onResponse(Call<List<ExpositorNew>> call, Response<List<ExpositorNew>> response) {
                if(response.isSuccessful()){

                    List<ExpositorNew> expositores = (List<ExpositorNew>)response.body();
                    for(int i = 0; i < expositores.size(); i++){
                        ExpositorNew expo = new ExpositorNew();
                        expo.setId(expositores.get(i).getId());
                        expo.setNombre(expositores.get(i).getNombre());
                        expo.setDescripcion(expositores.get(i).getDescripcion());
                        expo.setFoto("https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/React.svg/1200px-React.svg.png");

                        listExpositor.add(expo);
                    }

                    charlaExpositorAdapter = new CharlaExpositorAdapter(listExpositor);
                    recyclerViewCharlaExpositor.setAdapter(charlaExpositorAdapter);
                }else{
                    //Log.e(TAG,"Error onResponse: " + response.errorBody());
                    Log.i("CharlaProducto", "response: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<ExpositorNew>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CharlaExpositoresActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });


        /*Expositor expositor1 = new Expositor(1,"Juan Perez","Expositor Principal", R.drawable.facebook);
        Expositor expositor2 = new Expositor(2,"Jose Cueva","Expositor Principal", R.drawable.github);
        Expositor expositor3 = new Expositor(3,"Fernando Gamarra","Expositor Secundario", R.drawable.instagram);
        Expositor expositor4 = new Expositor(4,"Miguel Paredes","Expositor Secundario", R.drawable.youtube);
        Expositor expositor5 = new Expositor(5,"Mario Verano","Capacitador", R.drawable.linkedin);
        listExpositor.add(expositor1);
        listExpositor.add(expositor2);
        listExpositor.add(expositor3);
        listExpositor.add(expositor4);
        listExpositor.add(expositor5);*/
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
                intentSig.putExtra("objCharla", charla);
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
