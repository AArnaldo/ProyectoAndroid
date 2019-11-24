package example.proyectocibertec;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CharlaDetalleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CharlaDetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharlaDetalleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView lblIdCharla;
    private TextView lblNombreCharla;
    private TextView lblDescCharla;
    private TextView lblDireccionCharla;
    private TextView lblLatitudCharla;
    private TextView lblLongitudCharla;
    private TextView lblObservacionCharla;
    private ImageView ivCharlaDetFoto;
    private ImageButton ibCharlaDetLocation;
    private OnFragmentInteractionListener mListener;

    public CharlaDetalleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CharlaDetalleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CharlaDetalleFragment newInstance(String param1, String param2) {
        CharlaDetalleFragment fragment = new CharlaDetalleFragment();
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
        View vista =  inflater.inflate(R.layout.fragment_charla_detalle, container, false);

        //this.lblIdCharla = vista.findViewById(R.id.lblI);
        this.lblNombreCharla = vista.findViewById(R.id.lblNombreCharla);
        this.lblDescCharla= vista.findViewById(R.id.lblDescCharla);
        this.lblDireccionCharla= vista.findViewById(R.id.lblDireccionCharla);
        this.lblLatitudCharla= vista.findViewById(R.id.lblLatitudCharla);
        this.lblLongitudCharla= vista.findViewById(R.id.lblLongitudCharla);
        this.lblObservacionCharla= vista.findViewById(R.id.lblObservacionCharla);
        this.ivCharlaDetFoto= vista.findViewById(R.id.ivCharlaDetFoto);
        this.ibCharlaDetLocation = vista.findViewById(R.id.ibCharlaDetLocation);

        this.lblNombreCharla.setText(getArguments().getString("lblNombreCharla"));
        this.lblDescCharla.setText(getArguments().getString("lblDescCharla"));
        this.lblDireccionCharla.setText(getArguments().getString("lblDireccionCharla"));
        this.lblLatitudCharla.setText(getArguments().getString("lblLatitudCharla"));
        this.lblLongitudCharla.setText(getArguments().getString("lblLongitudCharla"));
        this.lblObservacionCharla.setText(getArguments().getString("lblObservacionCharla"));

        Picasso.get().load(getArguments().getString("imgFotoCharla")).into(this.ivCharlaDetFoto);

        ibCharlaDetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.latlong.net/c/?lat="+lblLatitudCharla.getText().toString()+"&long="+lblLongitudCharla.getText().toString();

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
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



}
