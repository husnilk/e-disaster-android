package com.example.pelaporanbencana.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pelaporanbencana.R;
import com.example.pelaporanbencana.model.Penduduk;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PengungsiListRecViewAdapter extends RecyclerView.Adapter<PengungsiListRecViewAdapter.ViewHolder> implements Filterable {

    ArrayList<Penduduk> pengungsiList = new ArrayList<>();
    ArrayList<Penduduk> pengungsiListFull;

//    public PengungsiListRecViewAdapter(ArrayList<Penduduk> pengungsiList) {
//        this.pengungsiList = pengungsiList;
//    }

    public void setPengungsiList(ArrayList<Penduduk> pengungsiList) {
        this.pengungsiList = pengungsiList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.penduduk_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Calendar tgl_lahir = Calendar.getInstance();
        tgl_lahir.setTime(pengungsiList.get(position).getBirthdate());

        Calendar thn_skrg = Calendar.getInstance();

        int usia = thn_skrg.get(Calendar.YEAR)-tgl_lahir.get(Calendar.YEAR);

        holder.namaPenduduk.setText(pengungsiList.get(position).getName());
        holder.nikPenduduk.setText(pengungsiList.get(position).getNik());
        holder.usiaPenduduk.setText(String.valueOf(usia));

        if (pengungsiList.get(position).getGender() != null){
            if (pengungsiList.get(position).getGender().equals("P")){
                holder.jkPenduduk.setImageResource(R.drawable.femenine);
            }else if (pengungsiList.get(position).getGender().equals("L")) {
                holder.jkPenduduk.setImageResource(R.drawable.masculine);
            }
        }else {
            holder.jkPenduduk.setImageResource(R.drawable.ic_error_24);
        }
    }

    @Override
    public int getItemCount() {
        return pengungsiList.size();
    }

    @Override
    public Filter getFilter() {
        return FilterPengungsiList;
    }

    private Filter FilterPengungsiList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Penduduk> filteredPengungsi = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredPengungsi.addAll(pengungsiListFull);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Penduduk pengungsi : pengungsiListFull){
                    if (pengungsi.getName().toLowerCase().contains(filterPattern)){
                        filteredPengungsi.add(pengungsi);
                    }else if (pengungsi.getNik().toLowerCase().contains(filterPattern)){
                        filteredPengungsi.add(pengungsi);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredPengungsi;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            pengungsiList.clear();
            pengungsiList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView namaPenduduk, nikPenduduk, usiaPenduduk;
        ImageView jkPenduduk;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaPenduduk = itemView.findViewById(R.id.namaPenduduk);
            nikPenduduk = itemView.findViewById(R.id.nikPenduduk);
            usiaPenduduk = itemView.findViewById(R.id.usiaPenduduk);
            jkPenduduk = itemView.findViewById(R.id.jkPenduduk);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (listener != pengungsiList){
                listener.onClick(view, pengungsiList.get(getAdapterPosition()));
            }
            return false;
        }
    }

    PengungsiListRecViewAdapter.PengungsiClickListener listener = null;
    public interface PengungsiClickListener {
        void onClick(View view, Penduduk penduduk);
    }
    public void setListener(PengungsiListRecViewAdapter.PengungsiClickListener listener){
        this.listener = listener;
    }
}
