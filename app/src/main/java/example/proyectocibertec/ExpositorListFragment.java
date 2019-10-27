package example.proyectocibertec;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import example.proyectocibertec.adapter.ExpositorAdapter;
import example.proyectocibertec.clases.ClientApi;
import example.proyectocibertec.clases.ModelExpositor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ExpositorListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ExpositorAdapter retrofitAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    FloatingActionButton fabNuevo;

    private ExpositorFragment.OnFragmentInteractionListener mListener;

    public ExpositorListFragment() {

    }

    public static ExpositorListFragment newInstance(String param1, String param2) {
        ExpositorListFragment fragment = new ExpositorListFragment();
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
        ObtenerJSON();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expositor_list, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.progressBar);

        fabNuevo = view.findViewById(R.id.fabNuevo);
        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ExpositorCreateFragment expositorCreateFragment = new ExpositorCreateFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contenedorExpoA, expositorCreateFragment);
                fragmentTransaction.commit();

                Bundle data = new Bundle();
                data.putInt("ID_FRAGMENT", 1);
                data.putString("lblId", "0");
                String str = BuildConfig.FLAVOR;
                data.putString("lblNombreExpo", str);
                data.putString("lblDescripExpo", str);
                data.putInt("imgFoto", 0);
                expositorCreateFragment.setArguments(data);
            }
        });

        return view;
    }

    private void ObtenerJSON(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ClientApi.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        ClientApi api = retrofit.create(ClientApi.class);

        Call<String> call = api.getString();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body().toString());

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("micha1", response.body().toString());

                        String jsonresponse = response.body().toString();
                        LlenarRecycler(jsonresponse);

                    } else {
                        Log.i("onEmptyResponse", "No hay respuesta");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Error al cargar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void LlenarRecycler(String response){

        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);
            if(obj.optString("status").equals("true")){

                ArrayList<ModelExpositor> modelRecyclerArrayList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    ModelExpositor expositor = new ModelExpositor();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    expositor.setImgURL(dataobj.getString("imgURL"));
                    expositor.setName(dataobj.getString("name"));
                    expositor.setCountry(dataobj.getString("country"));
                    expositor.setCity(dataobj.getString("city"));

                    modelRecyclerArrayList.add(expositor);
                }

                retrofitAdapter = new ExpositorAdapter(getContext(),modelRecyclerArrayList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(retrofitAdapter);

                progressBar.setVisibility(View.INVISIBLE);

            }else {
                Toast.makeText(getActivity(), ("message")+"", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ExpositorFragment.OnFragmentInteractionListener) {
            mListener = (ExpositorFragment.OnFragmentInteractionListener) context;
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
