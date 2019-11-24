package example.proyectocibertec.adapter;

import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import example.proyectocibertec.CharlaDetalleFragment;
import example.proyectocibertec.DetailsTransition;
import example.proyectocibertec.R;
import example.proyectocibertec.clases.Charla;
import example.proyectocibertec.clases.CharlaNew;

public class CharlaAdapter extends RecyclerView.Adapter<CharlaAdapter.CharlaViewHolder> {

    Fragment fragment;
    List<CharlaNew> charlaList;
    CharlaAdapter.CharlaViewHolder charlaholder;
    public CharlaAdapter(List<CharlaNew> charlaList, Fragment fragment)
    {
        this.charlaList = charlaList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CharlaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_charla, null, false);
        return new CharlaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CharlaAdapter.CharlaViewHolder holder, int position) {
        CharlaNew charla = charlaList.get(position);

        holder.textViewTitle.setText(charla.getNombre());
        holder.textViewContent.setText(charla.getDescripcion());
        Picasso.get().load(charla.getImagen()).into(holder.imgCharla);

        ViewCompat.setTransitionName(holder.textViewTitle, String.valueOf(position) + "_transTitulo");
        ViewCompat.setTransitionName(holder.imgCharla, String.valueOf(position) + "_transImagenCharla");
        ViewCompat.setTransitionName(holder.textViewContent, String.valueOf(position) + "_transDescripCharla");
        holder.parent_layout_charla.setTag(Integer.valueOf(position));
        //charlaholder = holder;

        holder.parent_layout_charla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CharlaDetalleFragment charlaDetalleFragment = new CharlaDetalleFragment();


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    charlaDetalleFragment.setSharedElementEnterTransition(new DetailsTransition());
                    charlaDetalleFragment.setEnterTransition(new Fade());
                    charlaDetalleFragment.setExitTransition(new Fade());
                    charlaDetalleFragment.setSharedElementReturnTransition(new DetailsTransition());
                }
                fragment.getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addSharedElement( holder.textViewTitle, "transTitulo")
                        .addSharedElement( holder.imgCharla, "transImagenCharla")
                        .addSharedElement( holder.textViewContent, "transDescripCharla")
                        .replace(R.id.charlascontainer, charlaDetalleFragment)
                        .addToBackStack(null)
                        .commit();
                int x = ((Integer) view.getTag()).intValue();
                Bundle data = new Bundle();
                data.putString("lblIdCharla", String.valueOf(charlaList.get(x).getId()));
                data.putString("lblNombreCharla", charlaList.get(x).getNombre());
                data.putString("lblDescCharla", charlaList.get(x).getDescripcion());
                data.putString("lblDireccionCharla", charlaList.get(x).getDireccion());
                data.putString("lblLatitudCharla", charlaList.get(x).getLatitud());
                data.putString("lblLongitudCharla", charlaList.get(x).getLongitud());
                data.putString("lblObservacion", charlaList.get(x).getObservaciones());
                data.putString("imgFotoCharla", charlaList.get(x).getImagen());
                charlaDetalleFragment.setArguments(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return charlaList.size();
    }

    public class CharlaViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewContent;
        private ImageView imgCharla;
        RelativeLayout parent_layout_charla;

        public CharlaViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tv_charlaitem_nombre);
            textViewContent = itemView.findViewById(R.id.tv_charlaitem_descrip);
            imgCharla = itemView.findViewById(R.id.iv_charlaitem_item);
            parent_layout_charla = (RelativeLayout) itemView.findViewById(R.id.parent_layout_charla);
        }
    }
}
