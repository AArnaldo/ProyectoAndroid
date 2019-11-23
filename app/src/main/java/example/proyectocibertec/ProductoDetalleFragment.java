package example.proyectocibertec;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import example.proyectocibertec.clases.ClientApiProductos;
import example.proyectocibertec.clases.Productos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductoDetalleFragment extends Fragment {
    private static final String TAG = "Productos";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageButton btnAnular;
    ImageButton btnCancelar;
    ImageButton btnGuardar;
    ImageView imgFoto;
    TextView lblCosto;
    TextView lblDescProducto;
    TextView lblId;
    TextView lblNombreProducto;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static ProductoDetalleFragment newInstance(String param1, String param2) {
        ProductoDetalleFragment fragment = new ProductoDetalleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_producto_detalle, container, false);
        final String img = getArguments().getString("imgFoto");
        this.lblId = (TextView) vista.findViewById(R.id.lblId);
        this.lblNombreProducto = (TextView) vista.findViewById(R.id.lblNombreProducto);
        this.lblDescProducto = (TextView) vista.findViewById(R.id.lblDescProducto);
        this.lblCosto = (TextView) vista.findViewById(R.id.lblCosto);
        this.imgFoto = (ImageView) vista.findViewById(R.id.imgFoto);
        this.lblId.setText(getArguments().getString("lblId"));
        this.lblNombreProducto.setText(getArguments().getString("lblNombreProducto"));
        this.lblDescProducto.setText(getArguments().getString("lblDescProducto"));
        this.lblCosto.setText(getArguments().getString("lblCosto"));
        Picasso.get().load(getArguments().getString("imgFoto")).into(this.imgFoto);
        this.btnCancelar = (ImageButton) vista.findViewById(R.id.btnCancelar);
        this.btnCancelar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ProductoListFragment productoListFragment = new ProductoListFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contenedor, productoListFragment);
                fragmentTransaction.commit();
            }
        });
        this.btnGuardar = (ImageButton) vista.findViewById(R.id.btnGuardar);
        this.btnGuardar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ProductoCreateFragment productoCreateFragment = new ProductoCreateFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contenedor, productoCreateFragment);
                fragmentTransaction.commit();
                Bundle data = new Bundle();
                data.putInt("ID_FRAGMENT", 2);
                data.putString("lblId", lblId.getText().toString());
                data.putString("lblNombreProducto", lblNombreProducto.getText().toString());
                data.putString("lblDescProducto", lblDescProducto.getText().toString());
                data.putString("lblCosto", String.valueOf(lblCosto.getText().toString()));
                data.putString("imgFoto", img);
                productoCreateFragment.setArguments(data);
            }
        });
        this.btnAnular = (ImageButton) vista.findViewById(R.id.btnAnular);
        this.btnAnular.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Builder builder = new Builder(ProductoDetalleFragment.this.getContext());
                builder.setMessage((CharSequence) "¿ Seguro que quiere borrar el producto ? ");
                builder.setTitle((CharSequence) "Productos");
                builder.setPositiveButton((CharSequence) "Si", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteJSON();

                    }
                });
                builder.setNegativeButton((CharSequence) "No", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        return vista;
    }

    private void deleteJSON(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ClientApiProductos.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClientApiProductos api = retrofit.create(ClientApiProductos.class);
        Call<Productos> call = api.deleteProducto(Integer.parseInt(lblId.getText().toString()));

        call.enqueue(new Callback<Productos>() {
            @Override
            public void onResponse(Call<Productos> call, Response<Productos> response) {
                if(response.isSuccessful()){
                    ProductoFragment productoFragment = new ProductoFragment();
                    FragmentTransaction fragmentTransaction = ProductoDetalleFragment.this.getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.contenedor, productoFragment);
                    fragmentTransaction.commit();
                    Toast.makeText(ProductoDetalleFragment.this.getActivity(), "Se borro el producto",0).show();
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

    public void onButtonPressed(Uri uri) {
        OnFragmentInteractionListener onFragmentInteractionListener = this.mListener;
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.onFragmentInteraction(uri);
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) context;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.toString());
        sb.append(" must implement OnFragmentInteractionListener");
        throw new RuntimeException(sb.toString());
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }
}
