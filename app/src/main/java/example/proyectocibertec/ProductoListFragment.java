package example.proyectocibertec;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import example.proyectocibertec.adapter.ExpositorAdapter;
import example.proyectocibertec.adapter.ProductosAdapter;
import example.proyectocibertec.clases.ClientApiProductos;
import example.proyectocibertec.clases.Productos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductoListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductoListFragment extends Fragment {


    private static final String TAG = "Productos";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ProductoFragment.OnFragmentInteractionListener mListener;

    ExpositorAdapter retrofitAdapter;
    RecyclerView recyclerViewPosts;
    ArrayList<Productos> listProductos;
    FloatingActionButton fbadd;

    Fragment _activity;


    public ProductoListFragment() {

    }

    public static ProductoListFragment newInstance(String param1, String param2) {
        ProductoListFragment fragment = new ProductoListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _activity = this;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    ProductosAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_producto_list, container, false);
        listProductos=new ArrayList<>();
        recyclerViewPosts= vista.findViewById(R.id.recyclerViewPosts);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        ObtenerJSON();
        fbadd = vista.findViewById(R.id.fbadd);
        fbadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProductoCreateFragment productoCreateFragment = new ProductoCreateFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contenedor, productoCreateFragment);
                fragmentTransaction.commit();

                Bundle data = new Bundle();
                data.putInt("ID_FRAGMENT", 1);
                data.putString("lblId", "0");
                String str = BuildConfig.FLAVOR;
                data.putString("lblNombreProducto", str);
                data.putString("lblDescProducto", str);
                data.putString("lblCosto", str);
                data.putInt("imgFoto", 0);
                productoCreateFragment.setArguments(data);
            }
        });
        return vista;
    }

    private void ObtenerJSON(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ClientApiProductos.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClientApiProductos api = retrofit.create(ClientApiProductos.class);
        Call<List<Productos>> call = api.obtenerProductos(0,"a",.1200,"A","A");;

        call.enqueue(new Callback<List<Productos>>() {
            @Override
            public void onResponse(Call<List<Productos>> call, Response<List<Productos>> response) {
                if(response.isSuccessful()){

                    List<Productos> productos = (List<Productos>)response.body();


                    for(int i = 0; i < productos.size(); i++){

                        Productos prod = new Productos();

                        prod.setId(productos.get(i).getId());
                        prod.setNombre(productos.get(i).getNombre());
                        prod.setCosto(productos.get(i).getCosto());
                        prod.setDescripcion(productos.get(i).getDescripcion());
                        prod.setImagen(productos.get(i).getImagen());
                        listProductos.add(prod);

                        Log.e(TAG," getId: " + productos.get(i).getId());
                        Log.e(TAG," getNombre: " + productos.get(i).getNombre());
                        Log.e(TAG," getCosto: " + productos.get(i).getCosto());
                        Log.e(TAG," getDescripcion: " + productos.get(i).getDescripcion());
                        Log.e(TAG," getImagen: " + productos.get(i).getImagen());

                    }

                    adapter = new ProductosAdapter(listProductos, _activity);
                    recyclerViewPosts.setAdapter(adapter);


                }else{
                    Log.e(TAG,"Error onResponse: " + response.errorBody());

                }

            }

            @Override
            public void onFailure(Call<List<Productos>> call, Throwable t) {
                Log.e(TAG," onResponse: dddddd");
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProductoFragment.OnFragmentInteractionListener) {
            mListener = (ProductoFragment.OnFragmentInteractionListener) context;
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
        void onFragmentInteraction(Uri uri);
    }
}
