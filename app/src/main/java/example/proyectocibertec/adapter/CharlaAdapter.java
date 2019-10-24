package example.proyectocibertec.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import example.proyectocibertec.R;
import example.proyectocibertec.clases.Charla;

public class CharlaAdapter extends RecyclerView.Adapter<CharlaAdapter.CharlaViewHolder> {

    List<Charla> charlaList;

    public CharlaAdapter(List<Charla> charlaList){ this.charlaList = charlaList; }

    @NonNull
    @Override
    public CharlaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_charla, parent, false);
        return new CharlaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharlaAdapter.CharlaViewHolder holder, int position) {
        Charla charla = charlaList.get(position);
        holder.textViewTitle.setText(charla.getNombre());
        holder.textViewContent.setText(charla.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return charlaList.size();
    }

    public class CharlaViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewContent;

        public CharlaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tv_charlaitem_nombre);
            textViewContent = itemView.findViewById(R.id.tv_charlaitem_descrip);
        }
    }
}
