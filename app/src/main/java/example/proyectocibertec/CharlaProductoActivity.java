package example.proyectocibertec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import example.proyectocibertec.adapter.CharlaProductoAdapter;
import example.proyectocibertec.clases.Productos;

public class CharlaProductoActivity extends AppCompatActivity implements View.OnClickListener {

    List<Productos> listProducto;
    RecyclerView recyclerViewCharlaProducto;
    CharlaProductoAdapter charlaProductoAdapter;
    ImageButton btnAnterior, btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charla_producto);

        inicializarControles();

        //Llenando la lista de productos
        llenarListaProductos();

        recyclerViewCharlaProducto = findViewById(R.id.recyclerCharlaProductos);
        recyclerViewCharlaProducto.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        charlaProductoAdapter = new CharlaProductoAdapter(listProducto);
        recyclerViewCharlaProducto.setAdapter(charlaProductoAdapter);
    }

    private void inicializarControles() {
        setTitle("Productos");
        listProducto = new ArrayList<Productos>();

        btnAnterior = findViewById(R.id.btnAnteriorCharlaProductos);
        btnSiguiente = findViewById(R.id.btnSiguienteCharlaProductos);

        btnAnterior.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
    }

    private void llenarListaProductos() {
        Productos producto1 = new Productos(1,"Proyector","Marca EPSON color blanco",250.00,R.drawable.github);
        Productos producto2 = new Productos(2,"Pizzarra","Acrilico en pared",120.00, R.drawable.instagram);
        Productos producto3 = new Productos(3,"Computadora","Core i7 HP",2500.00, R.drawable.linkedin);
        Productos producto4 = new Productos(4,"Carpeta","Division para 3 personas",450.50, R.drawable.facebook);
        Productos producto5 = new Productos(5,"Puerta","Antiruidpos",320.50, R.drawable.youtube);
        listProducto.add(producto1);
        listProducto.add(producto2);
        listProducto.add(producto3);
        listProducto.add(producto4);
        listProducto.add(producto5);
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
                startActivity(intentSig);
                break;
        }
    }
}
