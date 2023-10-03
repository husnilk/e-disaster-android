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
import com.example.pelaporanbencana.model.Relawan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RelawanRecViewAdapter extends RecyclerView.Adapter<RelawanRecViewAdapter.ViewHolder> implements Filterable {

    private ArrayList<Relawan> relawanArrayList = new ArrayList<>();
    private ArrayList<Relawan> relawanArrayListFull;
    private Context context;
//    private ChangeStatusListenerR changeStatusListenerR;

//    public RelawanRecViewAdapter(ArrayList<Relawan> relawanArrayList, Context context, ChangeStatusListenerR changeStatusListenerR) {
//        this.relawanArrayList = relawanArrayList;
//        this.context = context;
//        this.changeStatusListenerR = changeStatusListenerR;
//
//        relawanArrayListFull = new ArrayList<>(relawanArrayList);
//    }

    public void setRelawanArrayList(ArrayList<Relawan> relawanArrayList) {
        this.relawanArrayList = relawanArrayList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return RelawanFilter;
    }

    private Filter RelawanFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence sequence) {
            ArrayList<Relawan> filteredRelawan = new ArrayList<>();
            if (sequence == null || sequence.length()==0){
                filteredRelawan.addAll(relawanArrayListFull);
            }else{
                String filterPatternRelawan = sequence.toString().toLowerCase().trim();

                for (Relawan relawan : relawanArrayListFull){
                    if (relawan.getVolunteers_name().toLowerCase().contains(filterPatternRelawan)){
                        filteredRelawan.add(relawan);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredRelawan;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            relawanArrayList.clear();
            relawanArrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

//    public interface ChangeStatusListenerR{
//        void OnItemChangeListener(int position, Relawan relawan);
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.relawan_list_item, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Relawan relawan = relawanArrayList.get(position);
        if (relawan!=null){
            Date tglLahir = relawan.getVolunteers_birthdate();
            int usia = 0;

            if (tglLahir != null){
                Calendar tgl_lahir = Calendar.getInstance();
                tgl_lahir.setTime(tglLahir);

                Calendar thn_skrg = Calendar.getInstance();
                usia = thn_skrg.get(Calendar.YEAR)-tgl_lahir.get(Calendar.YEAR);

            }else{
                usia = 0;
            }


            holder.positions = position;
            holder.namaRelawan.setText(relawan.getVolunteers_name());
            holder.usiaRelawan.setText(String.valueOf(usia));
            holder.idRelawan.setText(relawan.getId_volunteers());

//            if (relawan.IsSelected()){
//                holder.view.setBackground(context.getDrawable(R.drawable.selected_bg));
//            }else{
//                holder.view.setBackground(context.getDrawable(R.drawable.unselected_bg));
//            }

            if (relawan.getVolunteers_gender() == "P"){
                holder.jkRelawan.setImageResource(R.drawable.femenine);
            }else if (relawan.getVolunteers_gender() == "L"){
                holder.jkRelawan.setImageResource(R.drawable.masculine);
            }else{
                holder.jkRelawan.setImageResource(R.drawable.ic_error_24);
            }

        }

//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Relawan relawanrelawan = relawanArrayList.get(position);
//                if (relawanrelawan.IsSelected()){
//                    relawanrelawan.setSelected(false);
//                }else{
//                    relawanrelawan.setSelected(true);
//                }
//                relawanArrayList.set(holder.positions, relawanrelawan);
//                if (changeStatusListenerR!=null){
//                    changeStatusListenerR.OnItemChangeListener(holder.positions, relawanrelawan);
//                }
//                notifyItemChanged(holder.positions);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (relawanArrayList != null){
            return relawanArrayList.size();
        }
        return 0;
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public int positions;
        TextView namaRelawan, usiaRelawan, idRelawan;
        ImageView imageRelawan, jkRelawan;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            namaRelawan = itemView.findViewById(R.id.namaRelawan);
            usiaRelawan = itemView.findViewById(R.id.usiaRelawan);
            jkRelawan = itemView.findViewById(R.id.jkRelawan);
            imageRelawan = itemView.findViewById(R.id.imageRelawan);
            idRelawan = itemView.findViewById(R.id.idRelawan);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (listener != relawanArrayList){
                listener.onClick(view, relawanArrayList.get(getAdapterPosition()));
            }
            return false;
        }
    }

    RelawanRecViewAdapter.RelawanClickOnListener listener = null;
    public interface RelawanClickOnListener {
        void onClick(View view, Relawan relawan);
    }

    public void setListener(RelawanRecViewAdapter.RelawanClickOnListener listener) {
        this.listener = listener;
    }
}
