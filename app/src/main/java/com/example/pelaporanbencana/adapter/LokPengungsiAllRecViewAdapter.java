package com.example.pelaporanbencana.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pelaporanbencana.R;
import com.example.pelaporanbencana.model.LokasiPengungsiAll;

import java.util.ArrayList;

public class LokPengungsiAllRecViewAdapter extends RecyclerView.Adapter<LokPengungsiAllRecViewAdapter.ViewHolder> {
    ArrayList<LokasiPengungsiAll> lokasiPengungsiAlls;

    public void setLokasiPengungsiAlls(ArrayList<LokasiPengungsiAll> lokasiPengungsiAlls) {
        this.lokasiPengungsiAlls = lokasiPengungsiAlls;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lok_pengungsian_all_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.kodeLokasiAllP.setText(lokasiPengungsiAlls.get(position).getId_shelter());
        holder.namaKelurahanAllP.setText(lokasiPengungsiAlls.get(position).getLocation());
        holder.alamatAllP.setText(lokasiPengungsiAlls.get(position).getAddress());
        holder.kapasitasAllP.setText(String.valueOf(lokasiPengungsiAlls.get(position).getCapacity()));
        holder.jenisHunianAllP.setText(lokasiPengungsiAlls.get(position).getHunian_types());

    }

    @Override
    public int getItemCount() {
        if (lokasiPengungsiAlls != null){
            return lokasiPengungsiAlls.size();
        }
        return 0;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView kodeLokasiAllP, namaKelurahanAllP, alamatAllP, kapasitasAllP, jenisHunianAllP;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kodeLokasiAllP = itemView.findViewById(R.id.kodeLokasiAllP);
            namaKelurahanAllP = itemView.findViewById(R.id.namaKelurahanAllP);
            alamatAllP = itemView.findViewById(R.id.alamatLengkapAll);
            kapasitasAllP = itemView.findViewById(R.id.kapasitasAll);
            jenisHunianAllP = itemView.findViewById(R.id.jenisHunianAll);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != lokasiPengungsiAlls){
                listener.onClick(view, lokasiPengungsiAlls.get(getAdapterPosition()));
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (listener != lokasiPengungsiAlls){
                listener.onLongClick(view, lokasiPengungsiAlls.get(getAdapterPosition()));
            }
            return false;
        }
    }
    //Clicklistener
    LokPengungsiAllRecViewAdapter.LokPengungsiAllClickListener listener = null;
    public interface LokPengungsiAllClickListener {
        void onClick(View view, LokasiPengungsiAll lokasiPengungsiAll);

        void onLongClick(View view, LokasiPengungsiAll lokasiPengungsiAll);
    }
    public void setListener(LokPengungsiAllRecViewAdapter.LokPengungsiAllClickListener listener){
        this.listener = listener;
    }

}
