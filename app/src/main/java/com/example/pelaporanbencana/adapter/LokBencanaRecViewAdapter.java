package com.example.pelaporanbencana.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pelaporanbencana.LokasiBencanaActivity;
import com.example.pelaporanbencana.R;
import com.example.pelaporanbencana.model.LokasiBencana;

import java.util.ArrayList;
import java.util.List;

public class LokBencanaRecViewAdapter extends RecyclerView.Adapter<LokBencanaRecViewAdapter.ViewHolder> implements Filterable {

    ArrayList<LokasiBencana> lokasiBencanas;
    ArrayList<LokasiBencana> lokasiBencanasFull;

//    public LokBencanaRecViewAdapter(ArrayList<LokasiBencana> lokasiBencanas) {
//        this.lokasiBencanas = lokasiBencanas;
//        lokasiBencanasFull = new ArrayList<>(lokasiBencanas);
//    }


    public void setLokasiBencanas(ArrayList<LokasiBencana> lokasiBencanas) {
        this.lokasiBencanas = lokasiBencanas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lokasi_bencana_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String sub_disc = lokasiBencanas.get(position).getSub_district_name();
        String disc = lokasiBencanas.get(position).getDistrict_name();
        String prov = lokasiBencanas.get(position).getProvince_name();
        String id_urban = lokasiBencanas.get(position).getId_urban_village();
        holder.namaKelurahan.setText(id_urban +"-"+ lokasiBencanas.get(position).getUrban_village_name());
        holder.alamat.setText(sub_disc + ", " + disc + ", " + prov);
    }

    @Override
    public int getItemCount() {
        if (lokasiBencanas != null){
            return lokasiBencanas.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return FilterLokBencana;
    }

    private Filter FilterLokBencana = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<LokasiBencana> filteredLokBencana = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredLokBencana.addAll(lokasiBencanasFull);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (LokasiBencana lokasiBencana : lokasiBencanasFull){
                    if (lokasiBencana.getUrban_village_name().toLowerCase().contains(filterPattern)){
                        filteredLokBencana.add(lokasiBencana);
                    }else if (lokasiBencana.getSub_district_name().toLowerCase().contains(filterPattern)){
                        filteredLokBencana.add(lokasiBencana);
                    }else if (lokasiBencana.getDistrict_name().toLowerCase().contains(filterPattern)){
                        filteredLokBencana.add(lokasiBencana);
                    }else if (lokasiBencana.getProvince_name().toLowerCase().contains(filterPattern)){
                        filteredLokBencana.add(lokasiBencana);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredLokBencana;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            lokasiBencanas.clear();
            lokasiBencanas.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView namaKelurahan, alamat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaKelurahan = itemView.findViewById(R.id.namaKelurahan);
            alamat = itemView.findViewById(R.id.alamat);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != lokasiBencanas){
                listener.onClick(view, lokasiBencanas.get(getAdapterPosition()));
            }
        }
    }

    //saat klik list
    LokBencanaRecViewAdapter.LokClickListener listener = null;

    public interface LokClickListener {
        void onClick(View view, LokasiBencana lokasiBencana);
    }

    public void setListener(LokBencanaRecViewAdapter.LokClickListener listener){
        this.listener = listener;
    }

}
