package example.proyectocibertec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import example.proyectocibertec.adapter.CharlaProductoAdapter;
import example.proyectocibertec.clases.CharlaNew;
import example.proyectocibertec.clases.ClientApiProductos;
import example.proyectocibertec.clases.Productos;
import example.proyectocibertec.clases.UsuarioEdit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharlaProductoActivity extends AppCompatActivity implements View.OnClickListener {

    private CharlaNew charla;
    List<Productos> listProducto;
    RecyclerView recyclerViewCharlaProducto;
    CharlaProductoAdapter charlaProductoAdapter;
    ImageButton btnAnterior, btnSiguiente;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charla_producto);

        inicializarControles();
        llenarListaProductos();
    }

    private void inicializarControles() {
        setTitle("Productos");
        listProducto = new ArrayList<Productos>();
        btnAnterior = findViewById(R.id.btnAnteriorCharlaProductos);
        btnSiguiente = findViewById(R.id.btnSiguienteCharlaProductos);
        btnAnterior.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
        recyclerViewCharlaProducto = findViewById(R.id.recyclerCharlaProductos);
        recyclerViewCharlaProducto.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        //obteniendo la data del anterior activity
        charla = getIntent().getParcelableExtra("objCharla");
    }

    private void llenarListaProductos() {
        //Llamando al servicio
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ClientApiProductos.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClientApiProductos api = retrofit.create(ClientApiProductos.class);
        Call<List<Productos>> call = api.obtenerProductos(0,"a",.1200,"A","A");

        call.enqueue(new Callback<List<Productos>>() {
            @Override
            public void onResponse(Call<List<Productos>> call, Response<List<Productos>> response) {
                if(response.isSuccessful()){

                    List<Productos> productos = (List<Productos>)response.body();
                    for(int i = 0; i < productos.size(); i++){
                        Productos prod = new Productos();
                        prod.setId(productos.get(i).getId());
                        prod.setNombre(productos.get(i).getNombre());
                        prod.setCosto(productos.get(i).getCosto());
                        prod.setDescripcion(productos.get(i).getDescripcion());
                        prod.setImagen(productos.get(i).getImagen());

                        listProducto.add(prod);
                    }

                    charlaProductoAdapter = new CharlaProductoAdapter(listProducto);
                    recyclerViewCharlaProducto.setAdapter(charlaProductoAdapter);
                }else{
                    //Log.e(TAG,"Error onResponse: " + response.errorBody());
                    Log.i("CharlaProducto", "response: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Productos>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CharlaProductoActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });


        /*Productos producto1 = new Productos(1,"Proyector","Marca EPSON color blanco",250.00,R.drawable.github);
        Productos producto2 = new Productos(2,"Pizzarra","Acrilico en pared",120.00, R.drawable.instagram);
        Productos producto3 = new Productos(3,"Computadora","Core i7 HP",2500.00, R.drawable.linkedin);
        Productos producto4 = new Productos(4,"Carpeta","Division para 3 personas",450.50, R.drawable.facebook);
        Productos producto5 = new Productos(5,"Puerta","Antiruidpos",320.50, R.drawable.youtube);
        listProducto.add(producto1);
        listProducto.add(producto2);
        listProducto.add(producto3);
        listProducto.add(producto4);
        listProducto.add(producto5);*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAnteriorCharlaProductos:
                Intent intentAnt = new Intent(this, CharlaExpositoresActivity.class);
                startActivity(intentAnt);
                break;
            case R.id.btnSiguienteCharlaProductos:
                Intent intentSig = new Intent(this, CharlaMultimediaActivity.class);
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
                        sharedPreferences = getSharedPreferences(getResources().getString(R.string.sp_file_mensajes_key),MODE_PRIVATE);

                        //Obtener correo de Session
                        UsuarioEdit objUsuario = new UsuarioEdit();
                        objUsuario.setCorreo(sharedPreferences.getString("campo_correo","0"));

                        Intent intent = new Intent(CharlaProductoActivity.this, DrawerActivity.class);
                        intent.putExtra("Usuario",objUsuario);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar",null);
        builder.show();
    }
}
