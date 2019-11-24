package example.proyectocibertec;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import example.proyectocibertec.adapter.CharlaAdapter;
import example.proyectocibertec.adapter.CharlaProductoAdapter;
import example.proyectocibertec.clases.Charla;
import example.proyectocibertec.clases.CharlaNew;
import example.proyectocibertec.clases.ClientApiCharla;
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
 * {@link CharlaListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CharlaListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharlaListFragment extends Fragment {

    List<CharlaNew> listCharla;
    RecyclerView recyclerViewCharla;
    CharlaAdapter charlaAdapter;
    FloatingActionButton newCharla;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CharlasFragment.OnFragmentInteractionListener mListener;

    public CharlaListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CharlaListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CharlaListFragment newInstance(String param1, String param2) {
        CharlaListFragment fragment = new CharlaListFragment();
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

        listCharla = new ArrayList<CharlaNew>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_charla_list, container, false);

        //Llenando la lista de charla
        llenarListaCharla();

        recyclerViewCharla =(RecyclerView) vista.findViewById(R.id.rv_charlalist_charlas);
        recyclerViewCharla.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        //charlaAdapter = new CharlaAdapter(listCharla,this);
        //recyclerViewCharla.setAdapter(charlaAdapter);

        newCharla = (FloatingActionButton) vista.findViewById(R.id.fb_charlalist_add);
        newCharla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NuevaCharlaActivity.class);
                startActivity(intent);
            }
        });

        return vista;
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
        if (context instanceof CharlasFragment.OnFragmentInteractionListener) {
            mListener = (CharlasFragment.OnFragmentInteractionListener) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void llenarListaCharla() {
        //Llamando al servicio
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ClientApiCharla.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClientApiCharla api = retrofit.create(ClientApiCharla.class);
        Call<List<CharlaNew>> call = api.getCharlas();

        call.enqueue(new Callback<List<CharlaNew>>() {
            @Override
            public void onResponse(Call<List<CharlaNew>> call, Response<List<CharlaNew>> response) {
                if(response.isSuccessful()){
                    List<CharlaNew> charla = (List<CharlaNew>)response.body();
                    for(int i = 0; i < charla.size(); i++){
                        CharlaNew item = new CharlaNew();
                        item.setId(charla.get(i).getId());
                        item.setNombre(charla.get(i).getNombre());
                        item.setDescripcion(charla.get(i).getDescripcion());
                        item.setImagen(charla.get(i).getImagen());
                        item.setDireccion(charla.get(i).getDireccion());
                        item.setLatitud(charla.get(i).getLatitud());
                        item.setLongitud(charla.get(i).getLongitud());
                        item.setObservaciones(charla.get(i).getObservaciones());
                        listCharla.add(item);
                    }
                    charlaAdapter = new CharlaAdapter(listCharla,CharlaListFragment.this);
                    recyclerViewCharla.setAdapter(charlaAdapter);
                }else{
                    //Log.e(TAG,"Error onResponse: " + response.errorBody());
                    Log.i("CharlaProducto", "response: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<CharlaNew>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        });


        /*listCharla.clear();
        Charla charla1 = new Charla("Charla 1","Descripcion 1");
        Charla charla2 = new Charla("Charla 2","Descripcion 2");
        listCharla.add(charla1);
        listCharla.add(charla2);*/
    }
}
