package com.example.pelaporanbencana.adapter;

import android.content.Context;
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
import java.util.List;

public class TerdampakRecViewAdapter extends RecyclerView.Adapter<TerdampakRecViewAdapter.ViewHolder> implements Filterable {

    private ArrayList<Penduduk> terdampaks;
    private ArrayList<Penduduk> terdampaksFull;
    private Context context;
    private ChangeStatusListener changeStatusListener;

    public TerdampakRecViewAdapter(ArrayList<Penduduk> terdampaks, Context context, ChangeStatusListener changeStatusListener) {
        this.terdampaks = terdampaks;
        this.context = context;
        this.changeStatusListener = changeStatusListener;

        terdampaksFull = new ArrayList<>(terdampaks);
    }

    public interface ChangeStatusListener{
        void OnItemChangeListener(int position, Penduduk penduduk);
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
        Penduduk terdampak = terdampaks.get(position);
        if (terdampak!=null){
            holder.positions = position;
            holder.namaPenduduk.setText(terdampak.getName());
            holder.nikPenduduk.setText(terdampak.getNik());
//            holder.usiaPenduduk.setText(terdampak.getAge());
//
//            if (terdampak.isSelected()){
//                holder.view.setBackground(context.getDrawable(R.drawable.selected_bg));
//            }else{
//                holder.view.setBackground(context.getDrawable(R.drawable.unselected_bg));
//            }

            if (terdampak.getGender()=="perempuan"){
                holder.jkPenduduk.setImageResource(R.drawable.femenine);
            } else if(terdampak.getGender()=="laki-laki"){
                holder.jkPenduduk.setImageResource(R.drawable.masculine);
            } else{
                holder.jkPenduduk.setImageResource(R.drawable.ic_error_24);
            }
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
                Penduduk terdampak2 = terdampaks.get(position);
//                if (terdampak2.isSelected()){
//                    terdampak2.setSelected(false);
//                }else{
//                    terdampak2.setSelected(true);
//                }
                terdampaks.set(holder.positions, terdampak2);
                if (changeStatusListener!=null){
                    changeStatusListener.OnItemChangeListener(holder.positions, terdampak2);
                }
                notifyItemChanged(holder.positions);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (terdampaks != null){
            return terdampaks.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return FilterTerdampak;
    }

    private Filter FilterTerdampak = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Penduduk> filteredTerdampak = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredTerdampak.addAll(terdampaksFull);
            }else{
                String filteredPattern = charSequence.toString().toLowerCase().trim();

                for (Penduduk terdampak1 : terdampaksFull){
                    if (terdampak1.getName().toLowerCase().contains(filteredPattern)){
                        filteredTerdampak.add(terdampak1);
                    }else if (terdampak1.getNik().toLowerCase().contains(filteredPattern)){
                        filteredTerdampak.add(terdampak1);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredTerdampak;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            terdampaks.clear();
            terdampaks.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public int positions;
        TextView namaPenduduk,nikPenduduk,usiaPenduduk;
        ImageView jkPenduduk;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaPenduduk = itemView.findViewById(R.id.namaPenduduk);
            nikPenduduk = itemView.findViewById(R.id.nikPenduduk);
            usiaPenduduk = itemView.findViewById(R.id.usiaPenduduk);
            jkPenduduk = itemView.findViewById(R.id.jkPenduduk);
            view = itemView;
        }
    }
}
