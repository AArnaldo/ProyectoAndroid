package example.proyectocibertec.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import example.proyectocibertec.CharlaDetalleFragment;
import example.proyectocibertec.R;
import example.proyectocibertec.clases.Charla;
import example.proyectocibertec.clases.CharlaNew;

public class CharlaAdapter extends RecyclerView.Adapter<CharlaAdapter.CharlaViewHolder> {

    Fragment fragment;
    List<CharlaNew> charlaList;

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
    public void onBindViewHolder(@NonNull CharlaAdapter.CharlaViewHolder holder, int position) {
        CharlaNew charla = charlaList.get(position);
        holder.textViewTitle.setText(charla.getNombre());
        holder.textViewContent.setText(charla.getDescripcion());
        Picasso.get().load(charla.getImagen()).into(holder.imgCharla);

        //holder.parent_layout_charla.setTag(Integer.valueOf(position));
        holder.parent_layout_charla.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CharlaDetalleFragment charlaDetalleFragment = new CharlaDetalleFragment();
                fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.charlascontainer, charlaDetalleFragment).commit();
                //charlaDetalleFragment.setArguments();
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
