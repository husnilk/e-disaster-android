package com.example.pelaporanbencana.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pelaporanbencana.R;
import com.example.pelaporanbencana.model.Kerusakan;
import com.example.pelaporanbencana.model.KerusakanKategori;

import java.util.ArrayList;
import java.util.List;

public class KerusakanKategoriRecViewAdapter extends RecyclerView.Adapter<KerusakanKategoriRecViewAdapter.ViewHolder> {

    ArrayList<KerusakanKategori> kerusakanKategoris = new ArrayList<>();
    ArrayList<KerusakanKategori> kerusakanKategorisFull;

    public void setKerusakanKategoris(ArrayList<KerusakanKategori> kerusakanKategoris) {
        this.kerusakanKategoris = kerusakanKategoris;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kerusakan_kategori_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.kategoriKerusakan.setText(kerusakanKategoris.get(position).getCategory());

    }

    @Override
    public int getItemCount() {
        return kerusakanKategoris.size();
    }

//    @Override
//    public Filter getFilter() {
//        return filterKerusakan;
//    }
//
//    public Filter filterKerusakan = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            ArrayList<Kerusakan> filteredKerusakan = new ArrayList<>();
//            if (charSequence == null || charSequence.length() == 0){
//                filteredKerusakan.addAll(kerusakanKategorisFull);
//            }else{
//                String filteredPattern = charSequence.toString().toLowerCase().trim();
//
//                for (KerusakanKategori kerusakanKategori : kerusakanKategoris){
//                    if (kerusakanKategoris.getCategory().toLowerCase().contains(filteredPattern)){
//                        filteredKerusakan.add(kerusakanKategoris);
//                    }
//                }
//            }
//            FilterResults filterResults = new FilterResults();
//            filterResults.values = filteredKerusakan;
//            return filterResults;
//        }
//
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//            kerusakanKategoris.clear();
//            kerusakanKategoris.addAll((List)filterResults.values);
//            notifyDataSetChanged();
//        }
//    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView kategoriKerusakan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kategoriKerusakan = itemView.findViewById(R.id.KategoriKerusakan);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(listener != kerusakanKategoris){
                listener.onClick(view, kerusakanKategoris.get(getAdapterPosition()));
            }
        }
    }

    KerusakanKategoriRecViewAdapter.KerusakanKategoriOnClickListener listener = null;
    public interface KerusakanKategoriOnClickListener {
        void onClick(View view, KerusakanKategori kerusakanKategori);
    }

    public void setListener(KerusakanKategoriRecViewAdapter.KerusakanKategoriOnClickListener listener) {
        this.listener = listener;
    }
}
