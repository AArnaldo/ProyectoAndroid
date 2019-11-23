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
import example.proyectocibertec.clases.Productos;

public class CharlaProductoAdapter extends RecyclerView.Adapter<CharlaProductoAdapter.CharlaProductoViewHolder> {

    List<Productos> charlaProductoList;

    public CharlaProductoAdapter(List<Productos> charlaProductoList){ this.charlaProductoList = charlaProductoList; }

    @NonNull
    @Override
    public CharlaProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_charlaproducto, parent, false);
        return new CharlaProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharlaProductoViewHolder holder, int position) {
        Productos producto = charlaProductoList.get(position);
        holder.textViewNombre.setText(producto.getNombre());
        holder.textViewDescripcion.setText(producto.getDescripcion());
        //holder.imgProducto.setImageResource(producto.getImagenId());
    }

    @Override
    public int getItemCount() {
        return charlaProductoList.size();
    }

    public class CharlaProductoViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombre;
        private TextView textViewDescripcion;
        private ImageView imgProducto;

        public CharlaProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.txt_charlaproductoitem_nombre);
            textViewDescripcion = itemView.findViewById(R.id.txt_charlaproductoitem_descripcion);
            imgProducto = itemView.findViewById(R.id.img_charlaproductoitem_imagen);
        }
    }
}
