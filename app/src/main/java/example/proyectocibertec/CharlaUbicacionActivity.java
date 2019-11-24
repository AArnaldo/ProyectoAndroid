package example.proyectocibertec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

import example.proyectocibertec.clases.CharlaNew;
import example.proyectocibertec.clases.ClientApiCharla;
import example.proyectocibertec.clases.ClientApiProductos;
import example.proyectocibertec.clases.Productos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharlaUbicacionActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private String url = "";
    private CharlaNew charla;
    private GoogleMap map;
    EditText edtDireccion, edtLatitud, edtLongitud;
    ImageButton btnAnterior, btnFinalizar;
    ProgressDialog progressDialog;

    private static final int REQUEST_PERMISO_LOCATION = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charla_ubicacion);

        progressDialog = new ProgressDialog(this);


        inicializarControles();
    }

    private void inicializarControles() {
        setTitle("Ubicación");

        edtDireccion = findViewById(R.id.tiet_charlaubicacion_Direccion);
        edtLatitud = findViewById(R.id.tiet_charlaubicacion_Latitud);
        edtLongitud = findViewById(R.id.tiet_charlaubicacion_Longitud);
        btnAnterior = findViewById(R.id.btnAnteriorCharlaUbicacion);
        btnFinalizar = findViewById(R.id.btnFinalizarCharlaUbicacion);

        btnAnterior.setOnClickListener(this);
        btnFinalizar.setOnClickListener(this);

        //Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapCharlaUbicacion);
        mapFragment.getMapAsync(this);

        //obteniendo la data del anterior activity
        charla = getIntent().getParcelableExtra("objCharla");
        byte[] b = getIntent().getByteArrayExtra("bitmap");

        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        charla.setImagen(temp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAnteriorCharlaUbicacion:
                Intent intentAnt = new Intent(this, CharlaMultimediaActivity.class);
                startActivity(intentAnt);
                break;
            case R.id.btnFinalizarCharlaUbicacion:
                if (edtDireccion.length() == 0){
                    Toast.makeText(this, "Debe ingresar una dirección a la Charla", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edtLatitud.length() == 0){
                    Toast.makeText(this, "Debe seleccionar una latitud a la Charla", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edtLongitud.length() == 0){
                    Toast.makeText(this, "Debe seleccionar una longitud a la Charla", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Registro Charla")
                        .setMessage("Estas seguro de terminar el registro de la charla?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                                progressDialog.setTitle("CIBERTEC");
                                progressDialog.setMessage("Procesando ...");
                                progressDialog.show();

                                charla.setDireccion(edtDireccion.getText().toString());
                                charla.setLatitud(edtLatitud.getText().toString());
                                charla.setLongitud(edtLongitud.getText().toString());

                                //Llamando al servicio para subir la foto al servidor
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(ClientApiCharla.JSONURL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                ClientApiCharla api = retrofit.create(ClientApiCharla.class);
                                Call<CharlaNew> call = api.subirImagen(1,
                                        charla.getNombre()+ ".jpg",
                                         "" ,
                                        "",
                                        0,
                                        "",
                                        "aaa",
                                        "",
                                        "",
                                        "",
                                        charla.getImagen()
                                        );
                                call.enqueue(new Callback<CharlaNew>() {
                                    @Override
                                    public void onResponse(Call<CharlaNew> callImage, Response<CharlaNew> response) {
                                        if(response.isSuccessful()){
                                            url = response.body().getImagen();
                                            restGuardarCharla(url);
                                        }else{
                                            Log.i("CharlaUbicación", "responseImagen: " + response.body().toString());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<CharlaNew> callImage, Throwable t) {
                                        t.printStackTrace();
                                        Toast.makeText(CharlaUbicacionActivity.this, "Ocurrio un error al subir la imagen", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancelar",null);
                builder.show();
                break;
        }
    }

    private void restGuardarCharla(String im){
        //Llamando al servicio para guardar la charla
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ClientApiCharla.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClientApiCharla api = retrofit.create(ClientApiCharla.class);
        Call<CharlaNew> call = api.insertCharla(0,charla.getNombre()
                ,charla.getDescripcion()
                ,charla.getFechaHoraInicio()
                ,charla.getCapacidad()
                ,charla.getObservaciones()
                ,im
                ,charla.getDireccion()
                ,charla.getLatitud()
                ,charla.getLongitud());

        call.enqueue(new Callback<CharlaNew>() {
            @Override
            public void onResponse(Call<CharlaNew> call, Response<CharlaNew> response) {
                if(response.isSuccessful()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CharlaUbicacionActivity.this);
                    builder.setMessage("Se guardo con exito la charla")
                            .setTitle("Registro Charla");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            progressDialog.cancel();
                            Intent intentAnt = new Intent(CharlaUbicacionActivity.this, DrawerActivity.class);
                            startActivity(intentAnt);
                            finish();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    Log.i("CharlaUbicación", "response: " + response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<CharlaNew> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CharlaUbicacionActivity.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        double latitude=-12.046519;
        double longitude= -77.042820;
        float zoomLevel = 10f;

        LatLng limaLatLng = new LatLng(latitude,longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(limaLatLng,zoomLevel));

        setMapOnClick(map);
        enableMyLocation();
    }

    private void setMapOnClick(final GoogleMap googleMap) {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //latLng.latitude,
                //latLng.longitude
                map.clear();
                googleMap.addMarker(
                        new MarkerOptions().position(latLng)
                );
                edtLatitud.setText(String.format("%1$.5f",latLng.latitude));
                edtLongitud.setText(String.format("%1$.5f",latLng.longitude));
            }
        });
    }

    private void enableMyLocation() {
        if(isPermissionGranted()){
            map.setMyLocationEnabled(true);
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISO_LOCATION);
        }
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Registro Charla")
                .setMessage("Aún no termina con el registro de la Charla, Desea Salir?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Intent intent = new Intent(CharlaUbicacionActivity.this, DrawerActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar",null);
        builder.show();
    }


}
