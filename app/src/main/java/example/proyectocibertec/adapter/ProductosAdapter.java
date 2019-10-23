package example.proyectocibertec.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import example.proyectocibertec.R;
import example.proyectocibertec.clases.Productos;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder>{

    ArrayList<Productos> listProductos;

    public ProductosAdapter(ArrayList<Productos> listProductos) {
        this.listProductos=listProductos;
    }

    @Override
    public ProductosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productos,null,false);
        return new ProductosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductosViewHolder holder, int position) {
        holder.txtNombre.setText(listProductos.get(position).getNombre());
        holder.txtInformacion.setText(listProductos.get(position).getDescripcion());
        holder.foto.setImageResource(listProductos.get(position).getImagenId());
    }

    @Override
    public int getItemCount() {
        return listProductos.size();
    }

    public class ProductosViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre,txtInformacion;
        ImageView foto;

        public ProductosViewHolder(View itemView) {
            super(itemView);
            txtNombre= (TextView) itemView.findViewById(R.id.idNombre);
            txtInformacion= (TextView) itemView.findViewById(R.id.idInfo);
            foto= (ImageView) itemView.findViewById(R.id.idImagen);
        }
    }
}
