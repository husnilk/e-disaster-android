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
import com.example.pelaporanbencana.model.Korban;
import com.example.pelaporanbencana.model.Penduduk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KorbanListRecViewAdapter extends RecyclerView.Adapter<KorbanListRecViewAdapter.ViewHolder> implements Filterable {

    ArrayList<Korban> korbanlist = new ArrayList<>();
    private ArrayList<Korban> korbanlistFull;

    public void setKorbanlist(ArrayList<Korban> korbanlist) {
        this.korbanlist = korbanlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.korban_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int usia ;

        if (korbanlist.get(position).getBirthdate() != null){
            Calendar tgl_lahir = Calendar.getInstance();
            tgl_lahir.setTime(korbanlist.get(position).getBirthdate());

            Calendar thn_skrg = Calendar.getInstance();
            usia = thn_skrg.get(Calendar.YEAR)-tgl_lahir.get(Calendar.YEAR);

        }else{
            usia = 0;
        }

        holder.namaPenduduk.setText(korbanlist.get(position).getName());
        holder.nikPenduduk.setText(korbanlist.get(position).getNik());
        holder.usiaPenduduk.setText(String.valueOf(usia));

        if (korbanlist.get(position).getGender() != null){
            if (korbanlist.get(position).getGender().equals("P")){
                holder.jkPenduduk.setImageResource(R.drawable.femenine);
            }else if (korbanlist.get(position).getGender().equals("L")){
                holder.jkPenduduk.setImageResource(R.drawable.masculine);
            }
        }else {
            holder.jkPenduduk.setImageResource(R.drawable.ic_error_24);
        }

        if (korbanlist.get(position).getVictims_status() != null) {
            if (korbanlist.get(position).getVictims_status().equals("1")) {
                holder.statusKorban.setText("Meninggal");
            } else if (korbanlist.get(position).getVictims_status().equals("2")) {
                holder.statusKorban.setText("Hilang");
            } else if (korbanlist.get(position).getVictims_status().equals("3")) {
                holder.statusKorban.setText("Luka Ringan");
            } else if (korbanlist.get(position).getVictims_status().equals("4")) {
                holder.statusKorban.setText("Luka Berat");
            }
        }else {
            holder.statusKorban.setText("Tidak ada status");
        }
    }

    @Override
    public int getItemCount() {
        return korbanlist.size();
    }

    @Override
    public Filter getFilter() {
        return FilterKorbanList;
    }

    private Filter FilterKorbanList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Korban> filteredKorban = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredKorban.addAll(korbanlistFull);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Korban korban : korbanlistFull){
                    if (korban.getName().toLowerCase().contains(filterPattern)){
                        filteredKorban.add(korban);
                    }else if (korban.getNik().toLowerCase().contains(filterPattern)){
                        filteredKorban.add(korban);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredKorban;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            korbanlist.clear();
            korbanlist.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView namaPenduduk, nikPenduduk, usiaPenduduk, statusKorban;
        ImageView jkPenduduk, imgStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaPenduduk = itemView.findViewById(R.id.namaPenduduk);
            nikPenduduk = itemView.findViewById(R.id.nikPenduduk);
            usiaPenduduk = itemView.findViewById(R.id.usiaPenduduk);
            jkPenduduk = itemView.findViewById(R.id.jkPenduduk);
            imgStatus = itemView.findViewById(R.id.imgStatus);
            statusKorban = itemView.findViewById(R.id.statusKorban);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (listener != korbanlist){
                listener.onClick(view, korbanlist.get(getAdapterPosition()));
            }
            return false;
        }
    }

    KorbanListRecViewAdapter.KorbanClickListener listener = null;
    public interface KorbanClickListener{
        void onClick(View view, Korban korban);
    }

    public void setListener(KorbanListRecViewAdapter.KorbanClickListener listener){
        this.listener = listener;
    }
}
