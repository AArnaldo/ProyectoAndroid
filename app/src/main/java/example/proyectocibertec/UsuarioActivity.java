package example.proyectocibertec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsuarioActivity extends AppCompatActivity {

    ImageButton ibtn_usuario_foto;
    ImageView iv_usuario_foto;
    ImageView ibtn_usuario_galeria;

    private static final int REQUEST_TOMAR_FOTO =100, REQUEST_PERMISO_CAMARA =200, REQUEST_CONFIGURACION = 300;
    private static final int RESQUEST_PERMISO_GALERIA = 400;
    private static final int RESQUEST_ABRIR_GALERIA = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        setTitle("Perfil");

        ibtn_usuario_foto = findViewById(R.id.ibtn_usuario_foto);
        iv_usuario_foto = findViewById(R.id.iv_usuario_foto);
        ibtn_usuario_galeria = findViewById(R.id.ibtn_usuario_galeria);

        ibtn_usuario_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(),"Capturar foto", Toast.LENGTH_SHORT).show();
                abrirCamara();
            }
        });

        ibtn_usuario_galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirGaleria();
            }
        });

        setProfilePhoto();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //

    }

    private void abrirCamara()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            //Muestra explicacion si ya se ha denego el permiso una vez
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Abrir Camara")
                        .setMessage("Para cambiar la foto de perfil es necesario aceptar el permiso")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                solicitarPermisosCamara();
                            }
                        })
                        .setNegativeButton("Cancelar",null);
                builder.show();
            }
            else
            {
                solicitarPermisosCamara();
            }
        }
        else {
            disptchTakePhoto();
        }
    }

    private void abrirGaleria(){
        if(ContextCompat.checkSelfPermission(getBaseContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //Ya se ha denegado el permiso una vez
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Abrir Galeria")
                        .setMessage("Es necesario Aceptar el Permiso para abrir la galeria")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                solicitarPermisoGaleria();
                            }
                        }).setNegativeButton("Cancelar", null);
                builder.show();
            }else{
                solicitarPermisoGaleria();
            }
        }else{
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            String[] mimeType = {"image/jpeg","image/png"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType);
            startActivityForResult(intent,RESQUEST_ABRIR_GALERIA);
        }
    }

    private void disptchTakePhoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;

        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (photoFile != null){
            Uri photoUri = FileProvider.getUriForFile(getBaseContext(),
                    "example.proyectocibertec.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
            startActivityForResult(takePictureIntent, REQUEST_TOMAR_FOTO);
        }
    }
    private String rutaProfilePhoto = "";
    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timestamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                "jpg",
                storageDir
        );
        rutaProfilePhoto = image.getPath();

        return image;
    }

    private  void mostrarImagen(String pRuta){
        File file = new File(pRuta);
        Uri imageUri = Uri.fromFile(file);

        Glide.with(getApplicationContext())
                .load(imageUri)
                .apply(new RequestOptions().placeholder(R.drawable.ic_person_black_24dp))
                .into(iv_usuario_foto);
    }


    private void solicitarPermisosCamara()
    {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISO_CAMARA);
    }

    private void solicitarPermisoGaleria(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RESQUEST_PERMISO_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TOMAR_FOTO && resultCode == RESULT_OK)
        {
            setProfilePhotoPath (rutaProfilePhoto);
            mostrarImagen(rutaProfilePhoto);
            /* Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extras.get("data");
            iv_usuario_foto.setImageBitmap(imageBitmap);*/
        }
        if(requestCode == RESQUEST_ABRIR_GALERIA && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();
            setProfilePhotoPath (convertMediaUriToPath(selectedImage));
            mostrarImagen(convertMediaUriToPath(selectedImage));
            //iv_usuario_foto.setImageURI(selectedImage);
        }
    }

    public String convertMediaUriToPath(Uri uri) {
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj,  null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_PERMISO_CAMARA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    abrirCamara();
                }
                else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED)
                {
                    boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA);
                    if (!showRationale)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                                .setTitle("Abrir Configuracion")
                                .setMessage("Si desea cambiar su foto de perfil, gestione el permiso")
                                .setPositiveButton("Abrir configuración",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                abrirConfiguracion();
                                            }
                                        })
                                .setNegativeButton("Cancelar",null);
                        builder.show();
                    }
                    else
                    {
                        Toast.makeText(this,"Para cambiar la foto de perfil es necesario aceptar el permiso",Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case RESQUEST_PERMISO_GALERIA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    abrirGaleria();
                }else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE);
                    if(!showRationale){
                        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                                .setTitle("Abrir Configuracion")
                                .setMessage("Si desea acceder a la galeria, gestione el permiso")
                                .setPositiveButton("Abrir configuración",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                abrirConfiguracion();
                                            }
                                        })
                                .setNegativeButton("Cancelar",null);
                        builder.show();
                    }else{
                        Toast.makeText(this,"Requerimos el permiso seleccionar una imagen de la galeria",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void abrirConfiguracion()
    {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",getPackageName(),null);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CONFIGURACION);
    }

    public void setProfilePhotoPath(String pPath) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(getBaseContext().getResources().getString(R.string.ProfilePhotoPath), pPath);
        editor.apply();
    }

    public void setProfilePhoto() {
        String sPath= "";
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            sPath = sharedPreferences.getString(this.getResources().getString(R.string.ProfilePhotoPath), "");

            if (sPath == null || sPath.equals(""))
            {
            }
            else {
                mostrarImagen(sPath);
            }

        }catch (Exception ex)
        {
            Toast.makeText(getBaseContext(),"Error en setProfilePhoto(): "+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
