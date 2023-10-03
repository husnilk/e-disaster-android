package com.example.pelaporanbencana.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pelaporanbencana.R;
import com.example.pelaporanbencana.model.Fasilitas;

import java.util.ArrayList;

public class FasilitasRecViewAdapter extends RecyclerView.Adapter<FasilitasRecViewAdapter.ViewHolder> {

    ArrayList<Fasilitas> fasilitasList = new ArrayList<>();

    public void setFasilitasList(ArrayList<Fasilitas> fasilitasList) {
        this.fasilitasList = fasilitasList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FasilitasRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fasilitas_list_items, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FasilitasRecViewAdapter.ViewHolder holder, int position) {
        holder.fasilitasKategori.setText(fasilitasList.get(position).getFacilities_category());
        holder.description.setText(fasilitasList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return fasilitasList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView fasilitasKategori, description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fasilitasKategori = itemView.findViewById(R.id.fasilitasKategori);
            description = itemView.findViewById(R.id.description);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (listener != fasilitasList){
                listener.onClick(view, fasilitasList.get(getAdapterPosition()));
            }
            return false;
        }
    }

    FasilitasRecViewAdapter.FasilitasOnClickListener listener = null;
    public interface FasilitasOnClickListener {
        void onClick(View view, Fasilitas fasilitas);
    }

    public void setListener(FasilitasRecViewAdapter.FasilitasOnClickListener listener) {
        this.listener = listener;
    }
}
