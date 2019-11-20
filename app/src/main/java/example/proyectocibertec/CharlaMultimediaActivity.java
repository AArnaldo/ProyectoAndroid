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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CharlaMultimediaActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_TOMAR_FOTO = 100;
    private static final int REQUEST_PERMISO_CAMARA_ = 200;
    private static final int REQUEST_CONFIGURACION = 300;
    private static final int REQUEST_PERMISO_GALERIA = 400;
    private static final int REQUEST_ABRIR_GALERIA = 500;

    private ImageView imgFoto;
    private ImageButton btnAnterior, btnSiguiente, btnTomarFoto, btnAbrirGaleria;

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
        btnTomarFoto = findViewById(R.id.btnTomarFotoCharlaMultimedia);
        btnAbrirGaleria = findViewById(R.id.btnSeleccionarFotoCharlaMultimedia);
        imgFoto = findViewById(R.id.imgFotoCharlaMultimedia);

        btnAnterior.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
        btnTomarFoto.setOnClickListener(this);
        btnAbrirGaleria.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTomarFotoCharlaMultimedia:
                abrirCamara();
                break;
            case R.id.btnSeleccionarFotoCharlaMultimedia:
                abrirGaleria();
                break;
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

    private void abrirCamara() {
        //Verificar si se tienen los permisos necesarios
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            //Si se necesita mostrar alguna explicación
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)){
                //Ya me han denegado el permiso minimo una vez
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Abrir Camara")
                        .setMessage("Es necesario aceptar el permiso")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                solicitarPermisoCamara();
                            }
                        }).setNegativeButton("Cancelar", null);
                builder.show();
            }else {
                //Hay que pedir el permiso
                solicitarPermisoCamara();
            }
        }else {
            //Si tenemos el permiso
            dispatchTakePhoto();
        }
    }

    private void abrirGaleria() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //no tenemos el permiso
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Abrir Galeria")
                        .setMessage("Es necesario abrir el persmiso")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                solicitarPermisoGaleria();
                            }
                        })
                        .setNegativeButton("Cancelar",null);
                builder.show();
            }
            else{
                solicitarPermisoGaleria();
            }
        }else{
            // si tenemos el permiso
            //Crear un intent con action de ACTION_PICK
            Intent intent = new Intent(Intent.ACTION_PICK);
            //Definir que tipo de archivo vamos a escoger
            intent.setType("image/*");
            //Definir las extensiones que vamos a aceptar
            String[] mimeTypes = {"images/jpeg", "image/png"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            //Lanzamos el intent
            startActivityForResult(intent, REQUEST_ABRIR_GALERIA);
        }
    }

    private void dispatchTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(photoFile != null){
            Uri photoURI = FileProvider.getUriForFile(this,
                    "example.proyectocibertec.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_TOMAR_FOTO);
        }
    }

    private String rutaFoto;
    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //El nombre del file
        String imageFileName = "JPEG_" + timestamp + "_";
        //Directorio donde crearemos el file
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //Creamos el file
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        //Capturamos la ruta de la foto
        rutaFoto = image.getPath();
        return image;
    }

    private void solicitarPermisoCamara() {
        ActivityCompat.requestPermissions(CharlaMultimediaActivity.this,
                new String[]{Manifest.permission.CAMERA},
                REQUEST_PERMISO_CAMARA_);
    }

    private void solicitarPermisoGaleria() {
        ActivityCompat.requestPermissions(CharlaMultimediaActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_PERMISO_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TOMAR_FOTO && resultCode == RESULT_OK) {   
            mostrarImagen();
        }
        if (requestCode == REQUEST_ABRIR_GALERIA && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            imgFoto.setImageURI(selectedImage);
        }
    }

    private void mostrarImagen() {
        //Obtener dimensiones de nuestro imageview
        int targetW = imgFoto.getWidth();
        int targetH = imgFoto.getHeight();

        //Obtener las dimensiones del bitmap
        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        bmpOptions.inJustDecodeBounds = true;
        int fotoW = bmpOptions.outWidth;
        int fotoH = bmpOptions.outHeight;

        //Determinar la escala que se reducirá el bitmap
        int scaleFactor = Math.min(fotoW/targetW,fotoH/targetH);

        //Poner las opciones al BitmapFactory
        bmpOptions.inJustDecodeBounds = false;
        bmpOptions.inSampleSize = scaleFactor;
        bmpOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(rutaFoto,bmpOptions);
        Bitmap rotado = bitmap;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            rotado = rotarSiSeRequiere(bitmap);
        }

        imgFoto.setImageBitmap(rotado);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Bitmap rotarSiSeRequiere(Bitmap bitmap) {
        Bitmap bmpRotado = null;
        InputStream in = null;
        try{
            in = getContentResolver().openInputStream(Uri.fromFile(new File(rutaFoto)));
            ExifInterface exifInterface = new ExifInterface(in);
            int rotation = 0;
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotation = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotation = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotation = 270;
                    break;
            }
            Matrix matrix = new Matrix();
            matrix.postRotate(rotation);
            bmpRotado = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(in!=null){
                try{
                    in.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return bmpRotado;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISO_CAMARA_:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Si nos dieron el permiso
                    abrirCamara();
                } else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CAMERA);
                    if(!showRationale){
                        openConfiguration();
                    } else {
                        Toast.makeText(this, "Requerimos el permiso para tomar fotos.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case REQUEST_PERMISO_GALERIA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    abrirGaleria();
                } else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE);
                    if(!showRationale){
                        openConfiguration();
                    } else {
                        Toast.makeText(this,"Requerimos el permiso para acceder a la galería",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }

    private void openConfiguration() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Abrir Configuración")
                .setMessage("Si desea usar esta funcionalidad, gestione el permiso.")
                .setPositiveButton("Abrir configuración",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                abrirConfiguracion();
                            }
                        }).setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void abrirConfiguracion(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CONFIGURACION);
    }
}

