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

public class KorbanRecViewAdapter extends RecyclerView.Adapter<KorbanRecViewAdapter.Viewholder> implements Filterable {

    private ArrayList<Penduduk> korbanList;
    private ArrayList<Penduduk> korbanListFull;
    private Context context;
    private ChangeStatusListener changeStatusListener;

    public KorbanRecViewAdapter(ArrayList<Penduduk> korbanList, Context context, ChangeStatusListener changeStatusListener) {
        this.korbanList = korbanList;
        this.context = context;
        this.changeStatusListener = changeStatusListener;

        korbanListFull = new ArrayList<>(korbanList);
    }

    public void setKorbanList(ArrayList<Penduduk> korbanList) {
        this.korbanList = korbanList;
    }

    @Override
    public Filter getFilter() {
        return KorbanFilter;
    }

    private Filter KorbanFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Penduduk> filteredKorban = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredKorban.addAll(korbanListFull);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Penduduk korban : korbanListFull){
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
            korbanList.clear();
            korbanList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    //interface
    public interface ChangeStatusListener{
        void OnItemChangeListener(int position, Penduduk penduduk);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.penduduk_list_item, parent, false);
        Viewholder viewHolder = new Viewholder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Penduduk korban = korbanList.get(position);
        if(korban != null){
            holder.positions=position;
            holder.name.setText(korban.getName());
            holder.nik.setText(korban.getNik());
//            holder.age.setText(korban.getAge());
//
//            if (korban.isSelected()){
//                holder.view.setBackground(context.getDrawable(R.drawable.selected_bg));
//            }else{
//                holder.view.setBackground(context.getDrawable(R.drawable.unselected_bg));
//            }

            if (korban.getGender()=="P"){
                holder.gender.setImageResource(R.drawable.femenine);
            } else if(korban.getGender()=="L"){
                holder.gender.setImageResource(R.drawable.masculine);
            } else{
                holder.gender.setImageResource(R.drawable.ic_error_24);
            }
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
                Penduduk korbankorban = korbanList.get(position);
//                if (korbankorban.isSelected()){
//                    korbankorban.setSelected(false);
//                }else{
//                    korbankorban.setSelected(true);
//                }
                korbanList.set(holder.positions, korbankorban);
                if (changeStatusListener!=null){
                    changeStatusListener.OnItemChangeListener(holder.positions, korbankorban);
                }
                notifyItemChanged(holder.positions);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(korbanList!=null){
            return korbanList.size();
        }
        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public int positions;
        public View view;
        TextView name, nik, age;
        ImageView gender;
        
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namaPenduduk);
            nik = itemView.findViewById(R.id.nikPenduduk);
            age = itemView.findViewById(R.id.usiaPenduduk);
            gender = itemView.findViewById(R.id.jkPenduduk);
            view = itemView;
        }

    }



}
