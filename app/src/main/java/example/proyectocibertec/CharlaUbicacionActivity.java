package example.proyectocibertec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class CharlaUbicacionActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private GoogleMap map;
    EditText edtDireccion, edtLatitud, edtLongitud;
    ImageButton btnAnterior, btnFinalizar;

    private static final int REQUEST_PERMISO_LOCATION = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charla_ubicacion);

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAnteriorCharlaUbicacion:
                Intent intentAnt = new Intent(this, CharlaMultimediaActivity.class);
                startActivity(intentAnt);
                break;
            case R.id.btnFinalizarCharlaUbicacion:
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Registro Charla")
                        .setMessage("Estas seguro de terminar el registro de la charla?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Intent intentAnt = new Intent(CharlaUbicacionActivity.this, DrawerActivity.class);
                                startActivity(intentAnt);
                            }
                        })
                        .setNegativeButton("Cancelar",null);
                builder.show();
                break;
        }
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
