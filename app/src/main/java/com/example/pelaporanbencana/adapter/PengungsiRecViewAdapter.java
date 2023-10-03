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

public class PengungsiRecViewAdapter extends RecyclerView.Adapter<PengungsiRecViewAdapter.ViewHolder> implements Filterable {

    private ArrayList<Penduduk> pengungsis;
    private ArrayList<Penduduk> pengungsisFull;
    private Context context;
    private PengungsiRecViewAdapter.ChangeStatusListener changeStatusListenerP;

    public PengungsiRecViewAdapter(ArrayList<Penduduk> pengungsis, Context context, ChangeStatusListener changeStatusListenerP) {
        this.pengungsis = pengungsis;
        this.context = context;
        this.changeStatusListenerP = changeStatusListenerP;

        pengungsisFull = new ArrayList<>(pengungsis);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.penduduk_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Penduduk pengungsi = pengungsis.get(position);
        if (pengungsi != null){
            holder.positions=position;
            holder.namaPenduduk.setText(pengungsi.name);
            holder.nikPenduduk.setText(pengungsi.nik);
//            holder.usiaPenduduk.setText(pengungsi.age);
//
//            if (pengungsi.isSelected()){
//                holder.view.setBackground(context.getDrawable(R.drawable.selected_bg));
//            }else{
//                holder.view.setBackground(context.getDrawable(R.drawable.unselected_bg));
//            }

            if (pengungsi.getGender()=="perempuan"){
                holder.jkPenduduk.setImageResource(R.drawable.femenine);
            } else if(pengungsi.getGender()=="laki-laki"){
                holder.jkPenduduk.setImageResource(R.drawable.masculine);
            } else{
                holder.jkPenduduk.setImageResource(R.drawable.ic_error_24);
            }

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
                    Penduduk pengungsipengungsi = pengungsis.get(position);
//                    if (pengungsipengungsi.isSelected()){
//                        pengungsipengungsi.setSelected(false);
//                    }else{
//                        pengungsipengungsi.setSelected(true);
//                    }
                    pengungsis.set(holder.positions, pengungsipengungsi);
                    if (changeStatusListenerP!=null){
                        changeStatusListenerP.OnItemChangeListener(holder.positions, pengungsipengungsi);
                    }
                    notifyItemChanged(holder.positions);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (pengungsis!=null){
            return pengungsis.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return FilterPengungsi;
    }

    private Filter FilterPengungsi = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Penduduk> filteredPengungsi = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredPengungsi.addAll(pengungsisFull);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Penduduk pengungsi : pengungsisFull){
                    if (pengungsi.name.toLowerCase().contains(filterPattern)){
                        filteredPengungsi.add(pengungsi);
                    }else if (pengungsi.nik.toLowerCase().contains(filterPattern)){
                        filteredPengungsi.add(pengungsi);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredPengungsi;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            pengungsis.clear();
            pengungsis.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public interface ChangeStatusListener {
        void OnItemChangeListener(int position, Penduduk pengungsi);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public int positions;
        TextView namaPenduduk, nikPenduduk, usiaPenduduk;
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
