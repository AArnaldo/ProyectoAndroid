package example.proyectocibertec;

import android.Manifest;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpositorCreateFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView imgFoto;
    private ImageButton btnTomarFoto;
    private ImageButton btnSeleccionar;
    private ImageButton btnCancelar;
    private ImageButton btnGuardar;
    private TextInputEditText tiet_nombreExpo;
    private TextInputEditText  tiet_descripExpo;
    private TextView txtId;
    private boolean isVideo = false;

    private static final int REQUEST_TOMAR_FOTO = 100;
    private static final int REQUEST_PERMISO_CAMARA = 200;
    private static final int REQUEST_CONFIGURACION = 300;
    private static final int REQUEST_PERMISO_GALERIA = 400;
    private static final int REQUEST_ABRIR_GALERIA = 500;
    private static final int REQUEST_GRABAR_VIDEO = 600;
    public TextView ID_FRAGMENT;

    private String rutaFoto = "";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ExpositorCreateFragment() {
        // Required empty public constructor
    }

    public static ExpositorCreateFragment newInstance(String param1, String param2) {
        ExpositorCreateFragment fragment = new ExpositorCreateFragment();
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
        View vista = inflater.inflate(R.layout.fragment_expositor_create, container, false);
        ID_FRAGMENT = (TextView) vista.findViewById(R.id.ID_FRAGMENT);
        tiet_nombreExpo = (TextInputEditText) vista.findViewById(R.id.tiet_nombreExpo);
        tiet_descripExpo = (TextInputEditText) vista.findViewById(R.id.tiet_descripExpo);

        txtId = (TextView) vista.findViewById(R.id.txtId);
        imgFoto = (ImageView) vista.findViewById(R.id.imgFoto);
        ID_FRAGMENT.setText(String.valueOf(getArguments().getInt("ID_FRAGMENT")));
        txtId.setText(getArguments().getString("lblId"));
        tiet_nombreExpo.setText(getArguments().getString("lblNombreExpo"));
        tiet_descripExpo.setText(getArguments().getString("lblDescripExpo"));

        imgFoto.setImageResource(getArguments().getInt("imgFoto"));
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
                    ExpositorListFragment expositorListFragment = new ExpositorListFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.contenedorExpoA, expositorListFragment);
                    fragmentTransaction.commit();
                } else if (Integer.valueOf(ID_FRAGMENT.getText().toString()).intValue() == 2) {
                    ExpositorDetalleFragment expositorDetalleFragment = new ExpositorDetalleFragment();
                    FragmentTransaction fragmentTransaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.contenedorExpoA, expositorDetalleFragment);
                    fragmentTransaction2.commit();
                    Bundle data = new Bundle();
                    data.putString("lblId", getArguments().getString("lblId"));
                    data.putString("lblNombreExpositor", getArguments().getString("lblNombreExpositor"));
                    data.putString("lblDescripExpositor", getArguments().getString("lblDescripExpositor"));
                    data.putInt("imgFoto", getArguments().getInt("imgFoto"));
                    expositorDetalleFragment.setArguments(data);
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
        //CODIGO PARA GRABAR
        Toast.makeText(getActivity(), "Grabado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void abrirGaleria(){
        if(ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            // no tenemos el permiso
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setTitle("Abrir Galería")
                        .setMessage("Es necesario aceptar el permiso")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                solicitarPermisoGaleria();
                            }
                        })
                        .setNegativeButton("Cancelar", null);
                builder.show();
            } else {
                solicitarPermisoGaleria();
            }
        } else {
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

    private void abrirCamara(){
        //Verificar si se tienen los permisos necesarios
        if(ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

            //Si se necesita mostrar alguna explicación
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)){
                //Ya me han denegado el permiso minimo una vez
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setTitle("Abrir Camara")
                        .setMessage("Es necesario aceptar el permiso")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                solicitarPermisoCamara();
                            }
                        }).setNegativeButton("Cancelar", null);
                builder.show();
            } else {
                //Hay que pedir el permiso
                solicitarPermisoCamara();
            }
        } else {
            //Si tenemos el permiso
            if(isVideo){
                dispatchTakeVideo();
            } else {
                dispatchTakePhoto();
            }
        }
    }

    private void dispatchTakeVideo(){
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivityForResult(takeVideoIntent, REQUEST_GRABAR_VIDEO);
        }
    }

    private void dispatchTakePhoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(photoFile != null){
            Uri photoURI = FileProvider.getUriForFile(getContext(),
                    "example.proyectocibertec.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_TOMAR_FOTO);
        }
    }

    //private String rutaFoto;
    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //El nombre del file
        String imageFileName = "JPEG_" + timestamp + "_";
        //Directorio donde crearemos el file
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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

    private void solicitarPermisoCamara(){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA},
                REQUEST_PERMISO_CAMARA);
    }

    private void solicitarPermisoGaleria(){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_PERMISO_GALERIA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_TOMAR_FOTO && resultCode == getActivity().RESULT_OK){
            imgFoto.setVisibility(View.VISIBLE);
            //videoView.setVisibility(View.GONE);
            mostarImagen();
        }
        if(requestCode == REQUEST_ABRIR_GALERIA && resultCode == getActivity().RESULT_OK){
            imgFoto.setVisibility(View.VISIBLE);
            //videoView.setVisibility(View.GONE);
            Uri selectedImage = data.getData();
            imgFoto.setImageURI(selectedImage);
        }
//        if(requestCode == REQUEST_GRABAR_VIDEO && resultCode == RESULT_OK){
//            imgFoto.setVisibility(View.GONE);
//            //videoView.setVisibility(View.VISIBLE);
//            Uri videoUri = data.getData();
//            if(mediaController == null){
//                mediaController = new MediaController(getContext());
//                mediaController.setAnchorView(videoView);
//            }
//            videoView.setVideoURI(videoUri);
//            videoView.setMediaController(mediaController);
//            videoView.start();
//        }
    }

    private void mostarImagen(){
        //Obtener las dimensiones de nuestro imageview
        int targetW = imgFoto.getWidth();
        int targetH = imgFoto.getHeight();
        //Obtener las dimensiones del bitmap
        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        bmpOptions.inJustDecodeBounds = true;
        int fotoW = bmpOptions.outWidth;
        int fotoH = bmpOptions.outHeight;
        //Determinar la escala que se reducirá el bitmap
        int scaleFactor = Math.min(fotoW / targetW, fotoH / targetH);
        // poner las opciones al BitmapFactory
        bmpOptions.inJustDecodeBounds = false;
        bmpOptions.inSampleSize = scaleFactor;
        bmpOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(rutaFoto, bmpOptions);
        Bitmap rotado = bitmap;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
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
            bmpRotado = Bitmap.createBitmap(bitmap, 0 ,0 ,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bmpRotado;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISO_CAMARA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Si nos dieron el permiso
                    abrirCamara();
                } else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.CAMERA);
                    if(!showRationale){
                        openConfiguration();
                    } else {
                        Toast.makeText(getActivity(),"Requerimos el permiso para tomar fotos",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case REQUEST_PERMISO_GALERIA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    abrirGaleria();
                } else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE);
                    if(!showRationale){
                        openConfiguration();
                    } else {
                        Toast.makeText(getActivity(),"Requerimos el permiso para acceder a la galeria",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void openConfiguration(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
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
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CONFIGURACION);
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
