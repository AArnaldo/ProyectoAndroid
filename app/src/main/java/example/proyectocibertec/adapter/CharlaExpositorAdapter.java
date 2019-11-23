package example.proyectocibertec.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import example.proyectocibertec.R;
import example.proyectocibertec.clases.ExpositorNew;

public class CharlaExpositorAdapter extends RecyclerView.Adapter<CharlaExpositorAdapter.CharlaExpositorViewHolder> {

    List<ExpositorNew> expositorNewList;

    public CharlaExpositorAdapter(List<ExpositorNew> expositorNewList){ this.expositorNewList = expositorNewList; }

    @NonNull
    @Override
    public CharlaExpositorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_charlaexpositor, parent, false);
        return new CharlaExpositorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharlaExpositorViewHolder holder, int position) {
        ExpositorNew expositor = expositorNewList.get(position);
        holder.textViewNombre.setText(expositor.getNombre());
        holder.textViewDescripcion.setText(expositor.getDescripcion());
        //holder.imgExpositor.setImageResource(expositor.getFoto());
        Picasso.get().load(expositor.getFoto()).into(holder.imgExpositor);
    }

    @Override
    public int getItemCount() {
        return expositorNewList.size();
    }

    public class CharlaExpositorViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombre;
        private TextView textViewDescripcion;
        private ImageView imgExpositor;

        public CharlaExpositorViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.txt_charlaexpositoritem_nombre);
            textViewDescripcion = itemView.findViewById(R.id.txt_charlaexpositoritem_descripcion);
            imgExpositor = itemView.findViewById(R.id.img_charlaexpositoritem_imagen);
        }
    }
}
