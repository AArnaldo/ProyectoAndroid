package example.proyectocibertec.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import example.proyectocibertec.R;
import example.proyectocibertec.clases.ModelExpositor;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;

public class ExpositorAdapter extends RecyclerView.Adapter<ExpositorAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<ModelExpositor> dataModelArrayList;

    public ExpositorAdapter( Context mContext, ArrayList<ModelExpositor> dataModelArrayList){

        inflater = LayoutInflater.from(mContext);
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public ExpositorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= inflater.inflate(R.layout.item_expositor,parent,false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ExpositorAdapter.MyViewHolder holder, int position) {
        Picasso.get().load(dataModelArrayList.get(position).getImgURL()).into(holder.iv);
        holder.nombre.setText(dataModelArrayList.get(position).getName());
        holder.pais.setText(dataModelArrayList.get(position).getCountry());
        holder.ciudad.setText(dataModelArrayList.get(position).getCity());
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView pais, nombre, ciudad;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

            pais = (TextView) itemView.findViewById(R.id.country);
            nombre = (TextView) itemView.findViewById(R.id.name);
            ciudad = (TextView) itemView.findViewById(R.id.city);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}
