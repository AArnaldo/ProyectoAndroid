package example.proyectocibertec.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import example.proyectocibertec.R;
import example.proyectocibertec.clases.Expositor;

public class CharlaExpositorAdapter extends RecyclerView.Adapter<CharlaExpositorAdapter.CharlaExpositorViewHolder> {

    List<Expositor> charlaExpositorList;

    public CharlaExpositorAdapter(List<Expositor> charlaExpositorList){ this.charlaExpositorList = charlaExpositorList; }

    @NonNull
    @Override
    public CharlaExpositorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_charlaexpositor, parent, false);
        return new CharlaExpositorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharlaExpositorViewHolder holder, int position) {
        Expositor expositor = charlaExpositorList.get(position);
        holder.textViewNombre.setText(expositor.getNombre());
        holder.textViewDescripcion.setText(expositor.getDescripcion());
        holder.imgExpositor.setImageResource(expositor.getFoto());
    }

    @Override
    public int getItemCount() {
        return charlaExpositorList.size();
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
