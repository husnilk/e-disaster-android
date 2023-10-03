package com.example.pelaporanbencana.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pelaporanbencana.R;
import com.example.pelaporanbencana.model.DetailKejadian;

import java.util.ArrayList;

public class DetKejRecViewAdapter extends RecyclerView.Adapter<DetKejRecViewAdapter.ViewHolder> {

    private ArrayList<DetailKejadian> detailKejadians = new ArrayList<>();

    public void setDetailKejadians(ArrayList<DetailKejadian> detailKejadians) {
        this.detailKejadians = detailKejadians;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    private Context context;

    public DetKejRecViewAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_kejadian_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context)
                .asBitmap()
                .load(detailKejadians.get(position).getImageBencana())
                .into(holder.imageBencana2);
    }

    @Override
    public int getItemCount() {
        return detailKejadians.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageBencana2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageBencana2 = itemView.findViewById(R.id.imageBencana2);
        }
    }
}
