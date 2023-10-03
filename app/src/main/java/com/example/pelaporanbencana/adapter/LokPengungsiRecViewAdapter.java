package com.example.pelaporanbencana.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pelaporanbencana.R;
import com.example.pelaporanbencana.model.LokasiPengungsi;

import java.util.ArrayList;
import java.util.List;

public class LokPengungsiRecViewAdapter extends RecyclerView.Adapter<LokPengungsiRecViewAdapter.ViewHolder> implements Filterable {

    ArrayList<LokasiPengungsi> lokasiPengungsis;
    ArrayList<LokasiPengungsi> lokasiPengungsisFull;


    public void setLokasiPengungsis(ArrayList<LokasiPengungsi> lokasiPengungsis) {
        this.lokasiPengungsis = lokasiPengungsis;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lokasi_pengungsian_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.kodeLokasiPengungsi.setText(lokasiPengungsis.get(position).getId_shelter());
        holder.lokPengungsi.setText(lokasiPengungsis.get(position).getLocation());
        holder.alamatPengungsi.setText(String.valueOf(lokasiPengungsis.get(position).getAddress()));
        holder.kapasitas.setText(String.valueOf(lokasiPengungsis.get(position).getCapacity()));
        holder.jenisHunian.setText(lokasiPengungsis.get(position).getHunian_types());
        holder.jmlOrg.setText(String.valueOf(lokasiPengungsis.get(position).getJml_org()));
    }

    @Override
    public int getItemCount() {
        if (lokasiPengungsis != null){
            return lokasiPengungsis.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return FilterLokPengungsi;
    }

    private Filter FilterLokPengungsi = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<LokasiPengungsi> filteredLokPengungsi = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredLokPengungsi.addAll(lokasiPengungsisFull);
            }else{
                String filteredPattern = charSequence.toString().toLowerCase().trim();

//                for (LokasiPengungsi lokasiPengungsi : lokasiPengungsisFull){
//                    if (lokasiPengungsi.getCapacity().toLowerCase().contains(filteredPattern)){
//                        filteredLokPengungsi.add(lokasiPengungsi);
//                    }else if (lokasiPengungsi.getCapacity().toLowerCase().contains(filteredPattern)){
//                        filteredLokPengungsi.add(lokasiPengungsi);
//                    }else if (lokasiPengungsi.getCapacity().toLowerCase().contains(filteredPattern)){
//                        filteredLokPengungsi.add(lokasiPengungsi);
//                    }
//                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredLokPengungsi;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            lokasiPengungsis.clear();
            lokasiPengungsis.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView kodeLokasiPengungsi, lokPengungsi, alamatPengungsi, kapasitas, jenisHunian, jmlOrg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kodeLokasiPengungsi = itemView.findViewById(R.id.kodeLokasiP);
            lokPengungsi = itemView.findViewById(R.id.namaKelurahanP);
            alamatPengungsi = itemView.findViewById(R.id.alamatLengkap);
            kapasitas = itemView.findViewById(R.id.kapasitas);
            jenisHunian = itemView.findViewById(R.id.jenisHunian);
            jmlOrg = itemView.findViewById(R.id.totalPengungsi);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if(listener != lokasiPengungsis){
                listener.onClick(view, lokasiPengungsis.get(getAdapterPosition()));
            }
        }
    }

    //Clicklistener
    LokPengungsiRecViewAdapter.LokPengungsiClickListener listener = null;
    public interface LokPengungsiClickListener {
        void onClick(View view, LokasiPengungsi lokasiPengungsi);
    }
    public void setListener(LokPengungsiRecViewAdapter.LokPengungsiClickListener listener){
        this.listener = listener;
    }
}
