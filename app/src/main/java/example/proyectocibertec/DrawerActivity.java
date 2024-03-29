package example.proyectocibertec;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import example.proyectocibertec.clases.UsuarioEdit;

public class DrawerActivity extends AppCompatActivity
        implements ProductoFragment.OnFragmentInteractionListener,
        ProductoListFragment.OnFragmentInteractionListener,
        ProductoCreateFragment.OnFragmentInteractionListener,
        ProductoDetalleFragment.OnFragmentInteractionListener,

        ExpositorFragment.OnFragmentInteractionListener,
        ExpositorListFragment.OnFragmentInteractionListener,
        ExpositorCreateFragment.OnFragmentInteractionListener,
        ExpositorDetalleFragment.OnFragmentInteractionListener,

        CharlaDetalleFragment.OnFragmentInteractionListener,
        CharlasFragment.OnFragmentInteractionListener,
        CharlaListFragment.OnFragmentInteractionListener{

    private AppBarConfiguration mAppBarConfiguration;

    private ImageView iv_nav_header_profilepic;
    private TextView tvUsuario_Nombre_Nav;
    private UsuarioEdit usuarioEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Crear Nueva Charla", Snackbar.LENGTH_LONG)
                        .setAction("Nueva Charla", null).show();
            }
        });
        */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_charla, R.id.nav_asistencia, R.id.nav_expositor,
                R.id.nav_productos, R.id.nav_salir)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        View headerview = navigationView.getHeaderView(0);
        iv_nav_header_profilepic = (ImageView) headerview.findViewById(R.id.iv_nav_header_profilepic );
        tvUsuario_Nombre_Nav = (TextView) headerview.findViewById(R.id.tvUsuario_Nombre_Nav );
        /*iv_nav_header_profilepic = findViewById(R.id.iv_nav_header_profilepic );*/

        iv_nav_header_profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(),"Click en profile pic",Toast.LENGTH_SHORT).show();
                //iv_nav_header_profilepic.setImageResource(R.drawable.angular);
                Intent intent = new Intent(DrawerActivity.this,UsuarioActivity.class);
                intent.putExtra("Usuario",usuarioEdit);
                startActivity(intent);
            }
        });
        usuarioEdit = (UsuarioEdit) getIntent().getSerializableExtra("Usuario");
        setProfilePhoto();
        tvUsuario_Nombre_Nav.setText("Usuario: "+usuarioEdit.getCorreo());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setProfilePhoto();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void setProfilePhoto() {
        String sPath= "";
        try {
            Glide.with(getApplicationContext())
                    .load(usuarioEdit.getFoto())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_person_black_24dp))
                    .into(iv_nav_header_profilepic);
          /*  SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            sPath = sharedPreferences.getString(this.getResources().getString(R.string.ProfilePhotoPath), "");

            if (sPath == null || sPath.equals(""))
            {
            }
            else {
                mostrarImagen(sPath);
            }*/

        }catch (Exception ex)
        {
        }
    }

    private  void mostrarImagen(String pRuta){
        File file = new File(pRuta);
        Uri imageUri = Uri.fromFile(file);

        Glide.with(getApplicationContext())
                .load(imageUri)
                .apply(new RequestOptions().placeholder(R.drawable.ic_person_black_24dp))
                .into(iv_nav_header_profilepic);
    }
}
