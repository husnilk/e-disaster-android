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
import com.example.pelaporanbencana.model.SumberDaya;

import java.util.ArrayList;
import java.util.List;

public class SumDaRecViewAdapter extends RecyclerView.Adapter<SumDaRecViewAdapter.ViewHolder> implements Filterable{

    private ArrayList<SumberDaya> sumberDayas = new ArrayList<>();
    private ArrayList<SumberDaya> sumberDayasFull;

    public void setSumberDayas(ArrayList<SumberDaya> sumberDayas) {
        this.sumberDayas = sumberDayas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sumberdaya_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.jenisKebutuhanSumDa.setText(sumberDayas.get(position).getResources_types());
        holder.jumlahSumDaKurang.setText(String.valueOf(sumberDayas.get(position).getLack_of_resources()));
        holder.jumlahSumDaTersedia.setText(String.valueOf(sumberDayas.get(position).getResources_available()));
        holder.satuanSumDa.setText(sumberDayas.get(position).getResources_units());
        holder.satuanSumDa2.setText(sumberDayas.get(position).getResources_units());
        holder.detailSumDa.setText(sumberDayas.get(position).getAdditional_info());
    }

    @Override
    public int getItemCount() {
        return sumberDayas.size();
    }

    @Override
    public Filter getFilter() {
        return filterSumDa;
    }

    public Filter filterSumDa = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<SumberDaya> filteredSumDa = new ArrayList<>();
            if (charSequence == null || charSequence.length()==0){
                filteredSumDa.addAll(sumberDayasFull);
            }else{
                String filteredPattern = charSequence.toString().toLowerCase().trim();

                for (SumberDaya sumberDaya : sumberDayasFull){
                    if (sumberDaya.getResources_types().toLowerCase().contains(filteredPattern)){
                        filteredSumDa.add(sumberDaya);
                    }else if (sumberDaya.getResources_units().toLowerCase().contains(filteredPattern)){
                        filteredSumDa.add(sumberDaya);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredSumDa;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            sumberDayas.clear();
            sumberDayas.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView jumlahSumDaTersedia, jenisKebutuhanSumDa, jumlahSumDaKurang, satuanSumDa, satuanSumDa2, detailSumDa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jumlahSumDaTersedia = itemView.findViewById(R.id.jumlahSumDaTersedia);
            jenisKebutuhanSumDa = itemView.findViewById(R.id.sumberDaya);
            jumlahSumDaKurang = itemView.findViewById(R.id.jumlahSumDaKurang);
            satuanSumDa = itemView.findViewById(R.id.satuanSumDa);
            satuanSumDa2 = itemView.findViewById(R.id.satuanSumDa2);
            detailSumDa = itemView.findViewById(R.id.detailSumDa);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (listener != sumberDayas){
                listener.onClick(view, sumberDayas.get(getAdapterPosition()));
            }
            return false;
        }
    }

    SumDaRecViewAdapter.SumDaOnClickListener listener = null;
    public interface SumDaOnClickListener {
        void onClick(View view, SumberDaya sumberDaya);
    }

    public void setListener(SumDaRecViewAdapter.SumDaOnClickListener listener){
        this.listener = listener;
    }

}
