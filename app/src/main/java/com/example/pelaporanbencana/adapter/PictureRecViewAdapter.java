package com.example.pelaporanbencana.adapter;

import android.content.Context;
import android.graphics.Picture;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.pelaporanbencana.R;
import com.example.pelaporanbencana.model.PicDisastersShowall.DataItem;
import com.example.pelaporanbencana.model.PicDisastersShowall.PicDisastersShowAllResponse;
import com.example.pelaporanbencana.model.PicturesDisasters;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PictureRecViewAdapter extends RecyclerView.Adapter<PictureRecViewAdapter.ViewHolder> {
//    List<DataItem> picturesDisasters ;
    ArrayList<PicturesDisasters> picturesDisasters = new ArrayList<>();
    Context context;

    public void setPicturesDisasters(ArrayList<PicturesDisasters> picturesDisasters) {
        this.picturesDisasters = picturesDisasters;
        notifyDataSetChanged();
    }

    //    public PictureRecViewAdapter(List<DataItem> picturesDisasters, Context context) {
//        this.picturesDisasters = picturesDisasters;
//        this.context = context;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pictures_list_items, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String image_name = picturesDisasters.get(position).getPictures();
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(R.mipmap.ic_launcher_round)
//                .error(R.mipmap.ic_launcher_round);
//
//        Glide.with(context)
//                .load(image_name)
//                .apply(options)
//                .centerCrop()
//                .into(holder.pictures);

        Picasso.get().load(image_name).into(holder.pictures);

    }

    @Override
    public int getItemCount() {
        return picturesDisasters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pictures;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pictures = itemView.findViewById(R.id.pictures);
        }
    }
}
