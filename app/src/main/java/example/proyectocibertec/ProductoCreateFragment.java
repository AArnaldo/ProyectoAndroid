package example.proyectocibertec;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
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
import android.util.Base64;
import android.util.Log;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import example.proyectocibertec.adapter.ProductosAdapter;
import example.proyectocibertec.clases.ClientApiProductos;
import example.proyectocibertec.clases.Productos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductoCreateFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Productos";
    public static Context contextOfApplication;

    private ImageView imgFoto;
    private ImageButton btnTomarFoto;
    private ImageButton btnSeleccionar;
    private ImageButton btnCancelar;
    private ImageButton btnGuardar;
    private TextInputEditText  tiet_newProducto_Nombre;
    private TextInputEditText  tiet_newProducto_Descrip;
    private TextInputEditText  tiet_newProducto_Capacidad;
    private TextView txtId;

    private static final int RESQUEST_TOMAR_FOTO = 100;
    private static final int RESQUEST_PERMISO_CAMARA = 200;
    private static final int RESQUEST_CONFIGURACION = 300;
    private static final int RESQUEST_PERMISO_GALERIA = 400;
    private static final int RESQUEST_ABRIR_GALERIA = 500;
    public TextView ID_FRAGMENT;

    private String rutaFoto = "";

    private String mParam1;
    private String mParam2;
    private String foto;
    private File file;
    private Uri output;
    private String url = "";

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
        ID_FRAGMENT = (TextView) vista.findViewById(R.id.ID_FRAGMENT);
        final String img = getArguments().getString("imgFoto");
        tiet_newProducto_Nombre = (TextInputEditText) vista.findViewById(R.id.tiet_newProducto_Nombre);
        tiet_newProducto_Descrip = (TextInputEditText) vista.findViewById(R.id.tiet_newProducto_Descrip);
        tiet_newProducto_Capacidad = (TextInputEditText) vista.findViewById(R.id.tiet_newProducto_Capacidad);
        txtId = (TextView) vista.findViewById(R.id.txtId);
        imgFoto = (ImageView) vista.findViewById(R.id.imgFoto);
        ID_FRAGMENT.setText(String.valueOf(getArguments().getInt("ID_FRAGMENT")));
        txtId.setText(getArguments().getString("lblId"));
        tiet_newProducto_Nombre.setText(getArguments().getString("lblNombreProducto"));
        tiet_newProducto_Descrip.setText(getArguments().getString("lblDescProducto"));
        tiet_newProducto_Capacidad.setText(getArguments().getString("lblCosto"));
        Picasso.get().load(getArguments().getString("imgFoto")).into(imgFoto);
        btnTomarFoto = (ImageButton) vista.findViewById(R.id.btnTomarFoto);
        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                abrirCamara();
            }
        });
        btnSeleccionar = (ImageButton) vista.findViewById(R.id.btnSeleccionar);
        btnSeleccionar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                abrirGaleria();
            }
        });
        btnCancelar = (ImageButton) vista.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Integer.valueOf(ID_FRAGMENT.getText().toString()).intValue() == 1) {
                    ProductoListFragment productoListFragment = new ProductoListFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.contenedor, productoListFragment);
                    fragmentTransaction.commit();
                } else if (Integer.valueOf(ID_FRAGMENT.getText().toString()).intValue() == 2) {
                    ProductoDetalleFragment productoDetalleFragment = new ProductoDetalleFragment();
                    FragmentTransaction fragmentTransaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.contenedor, productoDetalleFragment);
                    fragmentTransaction2.commit();
                    Bundle data = new Bundle();
                    data.putString("lblId", getArguments().getString("lblId"));
                    data.putString("lblNombreProducto", getArguments().getString("lblNombreProducto"));
                    data.putString("lblDescProducto", getArguments().getString("lblDescProducto"));
                    data.putString("lblCosto", getArguments().getString("lblCosto"));
                    data.putString("imgFoto", img);
                    productoDetalleFragment.setArguments(data);
                }
            }
        });
        btnGuardar = (ImageButton) vista.findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
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

        AlertDialog.Builder builder = new  AlertDialog.Builder(getContext());
        builder.setMessage((CharSequence) "¿ Quiere guardar los cambios? ");
        builder.setTitle((CharSequence) "Productos");
        builder.setPositiveButton((CharSequence) "Si", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                if (ID_FRAGMENT.getText().equals("1")){
                    create();

                }else {
                    update();
                }

            }
        });
        builder.setNegativeButton((CharSequence) "No", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    private void create(){

        BitmapDrawable drawable = (BitmapDrawable) imgFoto.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte[] b=baos.toByteArray();


        String temp = Base64.encodeToString(b, Base64.DEFAULT);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ClientApiProductos.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClientApiProductos api = retrofit.create(ClientApiProductos.class);

        Log.e(TAG,"Image " + temp);
        String nombre = tiet_newProducto_Nombre.getText().toString() + ".jpg";
        Log.e(TAG,"Image " + nombre);
        Call<Productos> call = api.subirImagen(1,nombre, 1.20,"a","aaa",temp);

        call.enqueue(new Callback<Productos>() {

            @Override
            public void onResponse(Call<Productos> call, Response<Productos> response) {
                if(response.isSuccessful()){
                    url = response.body().getImagen();
                    createJSON(url);
                    Log.e(TAG,"Image " + response.body().getImagen());
                }else{
                    Log.e(TAG,"Error onResponse Imagen: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Productos> call, Throwable t) {
                Log.e(TAG," onResponse: dddddd");
            }
        });


    }


    private void update(){

        BitmapDrawable drawable = (BitmapDrawable) imgFoto.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte[] b=baos.toByteArray();


        String temp = Base64.encodeToString(b, Base64.DEFAULT);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ClientApiProductos.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClientApiProductos api = retrofit.create(ClientApiProductos.class);

        Log.e(TAG,"Image " + temp);
        String nombre = tiet_newProducto_Nombre.getText().toString() + ".jpg";
        Log.e(TAG,"Image " + nombre);
        Call<Productos> call = api.subirImagen(1,nombre, 1.20,"a","aaa",temp);

        call.enqueue(new Callback<Productos>() {

            @Override
            public void onResponse(Call<Productos> call, Response<Productos> response) {
                if(response.isSuccessful()){
                    url = response.body().getImagen();
                    updateJSON(url);
                    Log.e(TAG,"Image " + response.body().getImagen());
                }else{
                    Log.e(TAG,"Error onResponse Imagen: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Productos> call, Throwable t) {
                Log.e(TAG," onResponse: dddddd");
            }
        });


    }

    private void createJSON(String im){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ClientApiProductos.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClientApiProductos api = retrofit.create(ClientApiProductos.class);


        Call<Productos> call = api.insertProducto(0,tiet_newProducto_Nombre.getText().toString(), Double.valueOf(tiet_newProducto_Capacidad.getText().toString()),tiet_newProducto_Descrip.getText().toString(),im);

        call.enqueue(new Callback<Productos>() {
            @Override
            public void onResponse(Call<Productos> call, Response<Productos> response) {
                if(response.isSuccessful()){
                    ProductoFragment productoFragment = new ProductoFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.contenedor, productoFragment);
                    fragmentTransaction.commit();
                    Toast.makeText(getActivity(), "Se guardo con exito el producto",Toast.LENGTH_SHORT ).show();
                }else{
                    Log.e(TAG,"Error onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Productos> call, Throwable t) {
                Log.e(TAG," onResponse: dddddd");
            }
        });
    }

    private void updateJSON(String im){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ClientApiProductos.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClientApiProductos api = retrofit.create(ClientApiProductos.class);
        Call<Productos> call = api.updateProducto(Integer.parseInt(txtId.getText().toString()),tiet_newProducto_Nombre.getText().toString(), Double.valueOf(tiet_newProducto_Capacidad.getText().toString()),tiet_newProducto_Descrip.getText().toString(),im);

        call.enqueue(new Callback<Productos>() {
            @Override
            public void onResponse(Call<Productos> call, Response<Productos> response) {
                if(response.isSuccessful()){
                    ProductoFragment productoFragment = new ProductoFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.contenedor, productoFragment);
                    fragmentTransaction.commit();
                    Toast.makeText(getActivity(), "Se guardo con exito el producto",Toast.LENGTH_SHORT ).show();
                }else{
                    Log.e(TAG,"Error onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Productos> call, Throwable t) {
                Log.e(TAG," onResponse: dddddd");
            }
        });
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
