package com.example.pelaporanbencana.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pelaporanbencana.R;
import com.example.pelaporanbencana.model.DevKejadian;

import java.util.ArrayList;

public class DetailKejadianRecViewAdapter extends RecyclerView.Adapter<DetailKejadianRecViewAdapter.ViewHolder> {
    ArrayList<DevKejadian> arrayList = new ArrayList<>();

    public void setArrayList(ArrayList<DevKejadian> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DetailKejadianRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.perkembangan_disasters_list_items, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailKejadianRecViewAdapter.ViewHolder holder, int position) {
        holder.tglRincian.setText(arrayList.get(position).getDisasters_date());
        holder.waktuRincian.setText(arrayList.get(position).getDisasters_time());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tglRincian, waktuRincian;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tglRincian = itemView.findViewById(R.id.tglRincian);
            waktuRincian = itemView.findViewById(R.id.waktuRincian);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener !=  arrayList){
                listener.onClick(view, arrayList.get(getAdapterPosition()));
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (listener !=  arrayList){
                listener.onLongClick(view, arrayList.get(getAdapterPosition()));
            }
            return false;
        }
    }

    DetailKejadianRecViewAdapter.DetailKejadianClickListener listener = null;
    public interface DetailKejadianClickListener {
        void onClick(View view, DevKejadian devKejadian);

        void onLongClick(View view, DevKejadian devKejadian);
    }

    public void setListener(DetailKejadianRecViewAdapter.DetailKejadianClickListener listener){
        this.listener = listener;
    }
}
