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

public class TerdampakListRecViewAdapter extends RecyclerView.Adapter<TerdampakListRecViewAdapter.ViewHolder> implements Filterable {

    ArrayList<Penduduk> terdampakList = new ArrayList<>();
    ArrayList<Penduduk> terdampakListFull;

    public void setTerdampakList(ArrayList<Penduduk> terdampakList) {
        this.terdampakList = terdampakList;
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
        Date tglLahir = terdampakList.get(position).getBirthdate();
        int usia = 0;

        if (tglLahir != null){
            Calendar tgl_lahir = Calendar.getInstance();
            tgl_lahir.setTime(tglLahir);

            Calendar thn_skrg = Calendar.getInstance();
            usia = thn_skrg.get(Calendar.YEAR)-tgl_lahir.get(Calendar.YEAR);

        }else{
            usia = 0;
        }

        holder.namaPenduduk.setText(terdampakList.get(position).getName());
        holder.nikPenduduk.setText(terdampakList.get(position).getNik());
        holder.usiaPenduduk.setText(String.valueOf(usia));

        if (terdampakList.get(position).getGender() != null){
            if (terdampakList.get(position).getGender().equals("P")){
                holder.jkPenduduk.setImageResource(R.drawable.femenine);
            }else if (terdampakList.get(position).getGender().equals("L")){
                holder.jkPenduduk.setImageResource(R.drawable.masculine);
            }
        }else {
            holder.jkPenduduk.setImageResource(R.drawable.ic_error_24);
        }

    }

    @Override
    public int getItemCount() {
        return terdampakList.size();
    }

    @Override
    public Filter getFilter() {
        return FilterTerdampakList;
    }

    private Filter FilterTerdampakList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Penduduk> filteredTerdampak = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredTerdampak.addAll(terdampakListFull);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Penduduk terdampak : terdampakListFull){
                    if (terdampak.getName().toLowerCase().contains(filterPattern)){
                        filteredTerdampak.add(terdampak);
                    }else if (terdampak.getNik().toLowerCase().contains(filterPattern)){
                        filteredTerdampak.add(terdampak);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredTerdampak;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            terdampakList.clear();
            terdampakList.addAll((List) filterResults.values);
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
            if (listener != terdampakList){
                listener.onClick(view, terdampakList.get(getAdapterPosition()));
            }

            return false;
        }
    }

    TerdampakListRecViewAdapter.TerdampakClickListener listener = null;
    public interface TerdampakClickListener{
        void onClick(View view, Penduduk penduduk);
    }

    public void setListener(TerdampakListRecViewAdapter.TerdampakClickListener listener){
        this.listener = listener;
    }
}
