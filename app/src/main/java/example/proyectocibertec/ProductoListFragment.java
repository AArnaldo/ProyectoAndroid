package example.proyectocibertec;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import example.proyectocibertec.adapter.ProductosAdapter;
import example.proyectocibertec.clases.Productos;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductoListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductoListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ProductoFragment.OnFragmentInteractionListener mListener;

    RecyclerView recyclerViewPosts;
    ArrayList<Productos> listProductos;
    FloatingActionButton fbadd;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_producto_list, container, false);

        listProductos=new ArrayList<>();
        recyclerViewPosts= vista.findViewById(R.id.recyclerViewPosts);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarLista();

        ProductosAdapter adapter=new ProductosAdapter(listProductos, this);
        recyclerViewPosts.setAdapter(adapter);


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

    private void llenarLista() {
        listProductos.add(new Productos(1,"ANDROID NIVEL BÁSICO","Obtendrás conocimientos para el diseño, despliegue y desarrollo de aplicaciones para Android utilizando la plataforma Google. También utilizarás los patrones de diseño y buenas prácticas que te permitirán desarrollar aplicaciones sostenibles y con un adecuado rendimiento en el tiempo.", 1600.00, R.drawable.android));
        listProductos.add(new Productos(2,"ANDROID NIVEL INTERMEDIO","Profundizarás tus conocimientos y habilidades para el desarrollo de aplicaciones móviles Android en dispositivos móviles. Así mismo, aprenderás técnicas avanzadas de optimización que interactuará y consumirá servicios web, manteniendo un rendimiento óptimo de la aplicación.", 1600.00, R.drawable.android));
        listProductos.add(new Productos(3,"ANDROID NIVEL AVANZADO","Con el curso Android 8.0 Mobile Developer profundizarás tus conocimientos y técnicas de ingeniería de software que ayudarán en el proceso de definir una adecuada arquitectura Android para lograr automatizar las tareas de integración y delivery de la aplicación bajo mejores prácticas.", 1600.00, R.drawable.android));
        listProductos.add(new Productos(4,"ANGULAR 5.0 FRONT END APPLICATION DEVELOPER","Con angular obtendrás conocimientos para el diseño de la capa de presentación manejando a detalle las especificaciones de las tecnologías de desarrollo Web. Definirás una arquitectura idónea asegurando flexibilidad y robustez de la aplicación sin sacrificar la experiencia del usuario.", 1700.00, R.drawable.angular));
        listProductos.add(new Productos(5,"REACT JS FRONT END APPLICATION DEVELOPER","Con react js obtendrás conocimientos para diseñar capas de presentación manejando las especificaciones de las tecnologías de desarrollo Web. Aprenderás a definir una arquitectura idónea para asegurar la flexibilidad y robustez de la aplicación sin sacrificar la experiencia del usuario.", 2100.00, R.drawable.react));
        listProductos.add(new Productos(6,"PHP 7.0 APLICATION DEVELOPER","Con Php 7.0 obtendrás los conocimientos y habilidades necesarias para la programación php y el patrón de arquitectura MVC. Además, aprenderás las tecnologías jQuery, JSON, Ajax, HTML, XML para la gestión de la capa de presentación que garanticen un buen performance de la aplicación", 1500.00, R.drawable.php));
        listProductos.add(new Productos(7,"VISUAL STUDIO 2017 WEB DEVELOPER","Con visual studio obtendrás conocimientos para el desarrollo de aplicaciones de escritorio con WCF y Web con ASP.NET WebForms y MVC. Aprenderás tecnologías ADO.NET, LINQ, Entity Framework 6.0 (EF) y Dapper para la gestión de la capa de datos que brinde buen performance en la aplicación.", 1500.00, R.drawable.visual));
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
