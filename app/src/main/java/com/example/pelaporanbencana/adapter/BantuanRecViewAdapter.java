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
import com.example.pelaporanbencana.model.Bantuan;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class BantuanRecViewAdapter extends RecyclerView.Adapter<BantuanRecViewAdapter.ViewHolder> implements Filterable {

    private ArrayList<Bantuan> bantuans = new ArrayList<>();
    private ArrayList<Bantuan> bantuansFull ;

//    public BantuanRecViewAdapter(ArrayList<Bantuan> bantuans) {
//        this.bantuans = bantuans;
//        bantuansFull = new ArrayList<>(bantuans);
//    }

    public void setBantuans(ArrayList<Bantuan> bantuans) {
        this.bantuans = bantuans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bantuan_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String date_received = df.format(bantuans.get(position).getDateReceived());

        holder.pengirimBantuan.setText(bantuans.get(position).getDonor());
        holder.jenisBantuan.setText(bantuans.get(position).getSa_types_name());
        holder.tglDiterima.setText(date_received);
        holder.jumlahbantuan.setText(bantuans.get(position).getSocial_assistance_unit());
        holder.satuanBantuan.setText(String.valueOf(bantuans.get(position).getSocial_assistance_amount()));
        holder.batchBantuan.setText(String.valueOf(bantuans.get(position).getBatch()));
    }

    @Override
    public int getItemCount() {
        return bantuans.size();
    }

    @Override
    public Filter getFilter() {
        return filterBantuan;
    }

    public Filter filterBantuan = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Bantuan> filteredBantuan = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredBantuan.addAll(bantuansFull);
            }else{
                String filteredPattern = charSequence.toString().toLowerCase().trim();

                for (Bantuan bantuan : bantuansFull){
                    if (bantuan.getDonor().toLowerCase().contains(filteredPattern)){
                        filteredBantuan.add(bantuan);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredBantuan;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            bantuans.clear();
            bantuans.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView pengirimBantuan, jenisBantuan, tglDiterima, jumlahbantuan, satuanBantuan, batchBantuan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pengirimBantuan = itemView.findViewById(R.id.pengirim);
            jenisBantuan = itemView.findViewById(R.id.jenisBantuan);
            tglDiterima = itemView.findViewById(R.id.tgl_diterima);
            jumlahbantuan = itemView.findViewById(R.id.jumlahBantuan);
            satuanBantuan = itemView.findViewById(R.id.satuanBantuan);
            batchBantuan = itemView.findViewById(R.id.batchBantuan);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (listener != bantuans){
                listener.onClick(view, bantuans.get(getAdapterPosition()));
            }
            return false;
        }
    }

    BantuanRecViewAdapter.BantuanClickListener listener = null;
    public interface BantuanClickListener {
        void onClick(View view, Bantuan bantuan);
    }

    public void setListener(BantuanRecViewAdapter.BantuanClickListener listener){
        this.listener = listener;
    }
}
