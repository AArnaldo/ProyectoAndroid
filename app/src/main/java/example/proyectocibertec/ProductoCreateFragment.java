package example.proyectocibertec;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductoCreateFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView imgFoto;
    private ImageButton btnTomarFoto;
    private ImageButton btnSeleccionar;
    private ImageButton btnCancelar;
    private ImageButton btnGuardar;
    private TextInputEditText  tiet_newProducto_Nombre;
    private TextInputEditText  tiet_newProducto_Descrip;
    private TextInputEditText  tiet_newProducto_Capacidad;

    private static final int RESQUEST_TOMAR_FOTO = 100;
    private static final int RESQUEST_PERMISO_CAMARA = 200;
    private static final int RESQUEST_CONFIGURACION = 300;
    private static final int RESQUEST_PERMISO_GALERIA = 400;
    private static final int RESQUEST_ABRIR_GALERIA = 500;

    private String rutaFoto = "";
    private MediaController mediaController;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProductoCreateFragment() {
        // Required empty public constructor
    }

    public static ProductoCreateFragment newInstance(String param1, String param2) {
        ProductoCreateFragment fragment = new ProductoCreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_producto_create, container, false);

        tiet_newProducto_Nombre = vista.findViewById(R.id.tiet_newProducto_Nombre);
        tiet_newProducto_Descrip = vista.findViewById(R.id.tiet_newProducto_Descrip);
        tiet_newProducto_Capacidad = vista.findViewById(R.id.tiet_newProducto_Capacidad);

        imgFoto = vista.findViewById(R.id.imgFoto);
        btnTomarFoto = vista.findViewById(R.id.btnTomarFoto);
        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });
        btnSeleccionar = vista.findViewById(R.id.btnSeleccionar);
        btnSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
        btnCancelar = vista.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductoListFragment productoListFragment = new ProductoListFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contenedor, productoListFragment);
                fragmentTransaction.commit();
            }
        });
        btnGuardar = vista.findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabar();
            }
        });
        return vista;
    }


    private void grabar(){


        if (tiet_newProducto_Nombre.length() == 0){
            Toast.makeText(getActivity(), "Ingrese un nombre al Producto", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tiet_newProducto_Descrip.length() == 0){
            Toast.makeText(getActivity(), "Ingrese una descripción al Producto", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tiet_newProducto_Capacidad.length() == 0){
            Toast.makeText(getActivity(), "Ingrese un costo al Producto", Toast.LENGTH_SHORT).show();
            return;
        }

        if(imgFoto.getDrawable() == null){
            Toast.makeText(getActivity(), "Debe seleccionar una imagen al  Producto", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductoFragment productoFragment = new ProductoFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor, productoFragment);
        fragmentTransaction.commit();

       Toast.makeText(getActivity(), "Se grabo con exito el producto", Toast.LENGTH_SHORT).show();
    }

    private void abrirGaleria(){
        if(ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //Ya se ha denegado el permiso una vez
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Abrir Galeria")
                        .setMessage("Es necesario Aceptar el Permiso")
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
            //Tipo de archivo
            intent.setType("image/*");
            //Definir extensiones
            String[] mimeType = {"image/jpeg","image/png"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType);
            startActivityForResult(intent,RESQUEST_ABRIR_GALERIA);
        }

    }
    private void abrirCamara(){
        //Verificar permisos
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //Si se necesita mostrar alguna explicacion
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)){
                //Ya se ha denegado el permiso una vez
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Abrir Camara")
                        .setMessage("Es necesario Aceptar el Permiso")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                solicitarPermisoCamara();
                            }
                        }).setNegativeButton("Cancelar", null);
                builder.show();
            }else{
                //Hay que pedir el permiso
                solicitarPermisoCamara();
            }
        }else {
            disptchTakePhoto();
        }
    }

    private void disptchTakePhoto(){
        //Si tenemos el permiso
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;

        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (photoFile != null){
            Uri photoUri = FileProvider.getUriForFile(getContext(),
                    "example.proyectocibertec.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
            startActivityForResult(takePictureIntent, RESQUEST_TOMAR_FOTO);
        }
    }


    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_EEmmss").format(new Date());
        String imageFileName = "JPEG_" + timestamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                "jpg",
                storageDir
        );
        rutaFoto = image.getPath();
        return image;
    }

    private void solicitarPermisoCamara(){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA},
                RESQUEST_PERMISO_CAMARA);
    }

    private void solicitarPermisoGaleria(){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                RESQUEST_PERMISO_GALERIA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESQUEST_TOMAR_FOTO && resultCode == getActivity().RESULT_OK){
            mostrarImagen();
        }
        if(requestCode == RESQUEST_ABRIR_GALERIA && resultCode == getActivity().RESULT_OK){
            Uri selectedImage = data.getData();
            imgFoto.setImageURI(selectedImage);
        }
    }


    private  void mostrarImagen(){

      /*
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        imgFoto.setImageBitmap(imageBitmap);
      */

        int targetW = imgFoto.getWidth();
        int targetH = imgFoto.getHeight();

        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        bmpOptions.inJustDecodeBounds = true;
        int fotoW = bmpOptions.outWidth;
        int fotoH = bmpOptions.outHeight;

        int scaleFactor = Math.min(fotoW / targetW, fotoH / targetH);

        bmpOptions.inJustDecodeBounds = false;
        bmpOptions.inSampleSize = scaleFactor;
        bmpOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(rutaFoto,bmpOptions);
        Bitmap rotado = bitmap;

        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            rotado = rotarSiSeRequiere(bitmap);
        }

        imgFoto.setImageBitmap(rotado);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    Bitmap rotarSiSeRequiere(Bitmap bitmap){
        Bitmap bmpRotado = null;
        InputStream in = null;
        try {
            in = getActivity().getContentResolver().openInputStream(Uri.fromFile(new File(rutaFoto)));
            ExifInterface exifInterface = new ExifInterface(in);
            int rotation = 0;
            int orientatio = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
            );
            switch (orientatio){
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
            bmpRotado = Bitmap.createBitmap(bitmap, 0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        }catch (FileNotFoundException e){
            e.printStackTrace();

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (in != null){
                try {
                    in.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return bmpRotado;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case RESQUEST_PERMISO_CAMARA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Si nos dieron el permiso
                    abrirCamara();
                }else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.CAMERA);
                    if(!showRationale){
                        openConfiguration();
                    }else{
                        Toast.makeText(getActivity(),"Requerimos el permiso para tomar fotos",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case RESQUEST_PERMISO_GALERIA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Si nos dieron el permiso
                    abrirGaleria();
                }else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE);
                    if(!showRationale){
                        openConfiguration();
                    }else{
                        Toast.makeText(getActivity(),"Requerimos el permiso para acceder a la galeria",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
    }


    private void openConfiguration(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Abrir Configuracion")
                .setMessage("Si deseas abrir esta funcionalidad, gestione el permiso")
                .setPositiveButton("Abrir configuracion",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                abrirConfiguracion();
                            }
                        }).setNegativeButton("Cancelar",null);
        builder.show();
    }
    private void abrirConfiguracion(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(),null);
        intent.setData(uri);
        startActivityForResult(intent,RESQUEST_CONFIGURACION);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
