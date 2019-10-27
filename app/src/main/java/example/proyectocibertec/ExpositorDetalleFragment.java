package example.proyectocibertec;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ExpositorDetalleFragment  extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageButton btnAnular;
    ImageButton btnCancelar;
    ImageButton btnGuardar;
    ImageView imgFoto;
    TextView lblDescripExpositor;
    TextView lblId;
    TextView lblNombreExpositor;
    private ExpositorDetalleFragment.OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static ExpositorDetalleFragment newInstance(String param1, String param2) {
        ExpositorDetalleFragment fragment = new ExpositorDetalleFragment();
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
        View vista = inflater.inflate(R.layout.fragment_expositor_detalle, container, false);
        this.lblId = (TextView) vista.findViewById(R.id.lblId);
        this.lblNombreExpositor = (TextView) vista.findViewById(R.id.lblNombreExpositor);
        this.lblDescripExpositor = (TextView) vista.findViewById(R.id.lblDescripExpositor);
        this.imgFoto = (ImageView) vista.findViewById(R.id.imgFoto);
        this.lblId.setText(getArguments().getString("lblId"));
        this.lblNombreExpositor.setText(getArguments().getString("lblNombreExpositor"));
        this.lblDescripExpositor.setText(getArguments().getString("lblDescripExpositor"));
        this.imgFoto.setImageResource(getArguments().getInt("imgFoto"));
        this.btnCancelar = (ImageButton) vista.findViewById(R.id.btnCancelar);
        this.btnCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ExpositorListFragment expositorListFragment = new ExpositorListFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contenedorExpoA, expositorListFragment);
                fragmentTransaction.commit();
            }
        });
        this.btnGuardar = (ImageButton) vista.findViewById(R.id.btnGuardar);
        this.btnGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                ExpositorCreateFragment expositorCreateFragment = new ExpositorCreateFragment();
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.contenedorExpo, expositorCreateFragment);
//                fragmentTransaction.commit();
//                Bundle data = new Bundle();
//                data.putInt("ID_FRAGMENT", 2);
//                data.putString("lblId", lblId.getText().toString());
//                data.putString("lblNombreExpositor", lblNombreExpositor.getText().toString());
//                data.putString("lblDescripExpositor", lblDescripExpositor.getText().toString());
//                data.putInt("imgFoto", getArguments().getInt("imgFoto"));
//                expositorCreateFragment.setArguments(data);
            }
        });
        this.btnAnular = (ImageButton) vista.findViewById(R.id.btnAnular);
        this.btnAnular.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ExpositorDetalleFragment.this.getContext());
                builder.setMessage((CharSequence) "¿ Seguro que quiere borrar el Expositor ? ");
                builder.setTitle((CharSequence) "Expositor");
                builder.setPositiveButton((CharSequence) "Si", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ExpositorFragment expositorFragment = new ExpositorFragment();
                        FragmentTransaction fragmentTransaction = ExpositorDetalleFragment.this.getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.contenedorExpoA, expositorFragment);
                        fragmentTransaction.commit();
                        Toast.makeText(ExpositorDetalleFragment.this.getActivity(), "Se borro el expositor", 0).show();
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

    public void onButtonPressed(Uri uri) {
        ExpositorDetalleFragment.OnFragmentInteractionListener onFragmentInteractionListener = this.mListener;
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.onFragmentInteraction(uri);
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ExpositorDetalleFragment.OnFragmentInteractionListener) {
            this.mListener = (ExpositorDetalleFragment.OnFragmentInteractionListener) context;
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
