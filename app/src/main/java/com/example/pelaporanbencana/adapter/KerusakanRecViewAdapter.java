package com.example.pelaporanbencana.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pelaporanbencana.R;
import com.example.pelaporanbencana.model.Kerusakan;
import com.example.pelaporanbencana.model.VolunteerOrg;

import java.util.ArrayList;
import java.util.List;

public class KerusakanRecViewAdapter extends RecyclerView.Adapter<KerusakanRecViewAdapter.ViewHolder> implements Filterable {

    ArrayList<Kerusakan> kerusakans = new ArrayList<>();
    ArrayList<Kerusakan> kerusakansFull;
    private Context ctx;
//    public KerusakanRecViewAdapter(ArrayList<Kerusakan> kerusakans) {
//        this.kerusakans = kerusakans;
//        kerusakansFull = new ArrayList<>(kerusakans);
//    }

    public void setKerusakans(ArrayList<Kerusakan> kerusakans) {
        this.kerusakans = kerusakans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kerusakan_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String village_name = kerusakans.get(position).getDisasters_village();
        String sub_district = kerusakans.get(position).getSub_district_name();
        String urban_village = kerusakans.get(position).getUrban_village_name();
        String lokasiKerusakan = village_name + ", " + urban_village + ", " + sub_district;
        String id_damages = String.valueOf(kerusakans.get(position).getId_damages());
        String id_disasters = kerusakans.get(position).getId_disasters();
        String id_damage_category = kerusakans.get(position).getId_damage_category();
        String damage_types = kerusakans.get(position).getDamage_types();

        holder.jenisKerusakan.setText(kerusakans.get(position).getDamage_types());
        holder.textIdKejadian.setText(id_damages+id_damage_category+id_disasters+damage_types);
        holder.bidangKerusakan.setText(kerusakans.get(position).getCategory());
        holder.lokKerusakan.setText(lokasiKerusakan);
        holder.jumlah.setText(String.valueOf(kerusakans.get(position).getDamage_amount()));
        holder.satuan.setText(kerusakans.get(position).getDamage_units());
        holder.DetailBidangKerusakan.setText(kerusakans.get(position).getDamage_name());

        String jenis_kerusakan = kerusakans.get(position).getDamage_types();
        if (jenis_kerusakan.equals("1") ){
            holder.jenisKerusakan.setText("Rusak Ringan");
        }else if(jenis_kerusakan.equals("2") ){
            holder.jenisKerusakan.setText("Rusak Sedang");
        }else if(jenis_kerusakan.equals("3")){
            holder.jenisKerusakan.setText("Rusak Berat");
        }
    }

    @Override
    public int getItemCount() {
        return kerusakans.size();
    }

    @Override
    public Filter getFilter() {
        return filterKerusakan;
    }

    public Filter filterKerusakan = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Kerusakan> filteredKerusakan = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredKerusakan.addAll(kerusakansFull);
            }else{
                String filteredPattern = charSequence.toString().toLowerCase().trim();

                for (Kerusakan kerusakan : kerusakansFull){
                    if (kerusakan.getCategory().toLowerCase().contains(filteredPattern)){
                        filteredKerusakan.add(kerusakan);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredKerusakan;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            kerusakans.clear();
            kerusakans.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView textIdKejadian, bidangKerusakan, jenisKerusakan, lokKerusakan, jumlah, satuan, DetailBidangKerusakan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textIdKejadian = itemView.findViewById(R.id.IdKerusakan);
            bidangKerusakan = itemView.findViewById(R.id.BidangKerusakan);
            jenisKerusakan = itemView.findViewById(R.id.JenisKerusakan);
            lokKerusakan = itemView.findViewById(R.id.lokasiKerusakan);
            jumlah = itemView.findViewById(R.id.jumlahKerusakan);
            satuan = itemView.findViewById(R.id.satuanKerusakan);
            DetailBidangKerusakan = itemView.findViewById(R.id.DetailBidangKerusakan);

            itemView.setOnLongClickListener(this);
        }


        @Override
        public boolean onLongClick(View view) {
            if(listener != kerusakans){
                listener.onClick(view, kerusakans.get(getAdapterPosition()));
            }
            return false;
        }
    }

    KerusakanRecViewAdapter.KerusakanClickListener listener = null;
    public interface KerusakanClickListener {
        void onClick(View view, Kerusakan kerusakan);
    }

    public void setListener(KerusakanRecViewAdapter.KerusakanClickListener listener) {
        this.listener = listener;
    }

}
