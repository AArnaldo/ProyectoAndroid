package example.proyectocibertec.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.proyectocibertec.ProductoDetalleFragment;
import example.proyectocibertec.R;
import example.proyectocibertec.clases.Productos;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder>{

    private static final String TAG = "Productos";
    Fragment fragment;
    ArrayList<Productos> listProductos;

    public ProductosAdapter(ArrayList<Productos> listProductos, Fragment fragment) {
        this.listProductos=listProductos;
        this.fragment = fragment;
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
        holder.txtId.setText(String.valueOf(listProductos.get(position).getId()));
        holder.txtCosto.setText(String.valueOf(listProductos.get(position).getCosto()));
        Picasso.get().load(listProductos.get(position).getImagen()).into(holder.foto);
        holder.parentLayout.setTag(Integer.valueOf(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ProductoDetalleFragment productoDetalleFragment = new ProductoDetalleFragment();
                fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, productoDetalleFragment).commit();
                int x = ((Integer) view.getTag()).intValue();
                Bundle data = new Bundle();
                data.putString("lblId", String.valueOf(listProductos.get(x).getId()));
                data.putString("lblNombreProducto", listProductos.get(x).getNombre());
                data.putString("lblDescProducto", listProductos.get(x).getDescripcion());
                data.putString("lblCosto", String.valueOf(listProductos.get(x).getCosto()));
                data.putString("imgFoto", listProductos.get(x).getImagen());
                productoDetalleFragment.setArguments(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProductos.size();
    }

    public class ProductosViewHolder extends RecyclerView.ViewHolder {

        ImageView foto;
        RelativeLayout parentLayout;
        TextView txtCosto;
        TextView txtId;
        TextView txtInformacion;
        TextView txtNombre;

        public ProductosViewHolder(View itemView) {
            super(itemView);
            this.txtNombre = (TextView) itemView.findViewById(R.id.idNombre);
            this.txtInformacion = (TextView) itemView.findViewById(R.id.idInfo);
            this.txtId = (TextView) itemView.findViewById(R.id.txtId);
            this.txtCosto = (TextView) itemView.findViewById(R.id.txtCosto);
            this.foto = (ImageView) itemView.findViewById(R.id.idImagen);
            this.parentLayout = (RelativeLayout) itemView.findViewById(R.id.parent_layout);
        }
    }
}
