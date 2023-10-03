package com.example.pelaporanbencana.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pelaporanbencana.HomeActivity;
import com.example.pelaporanbencana.R;
import com.example.pelaporanbencana.model.Home;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeRecViewAdapter extends RecyclerView.Adapter<HomeRecViewAdapter.ViewHolder> implements Filterable {

    private ArrayList<Home> homes = new ArrayList<>();
    private ArrayList<Home> homesFull;

//    public HomeRecViewAdapter() {
////        this.homes = homes;
//        homesFull = new ArrayList<>(homes);
//    }

    public void setHomes(ArrayList<Home> homes) {
        this.homes = homes;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String urban = homes.get(position).getUrban_village_name();
        String sub_dis = homes.get(position).getSub_district_name();
        String dis = homes.get(position).getDistrict_name();
        holder.kodeKejadian.setText(homes.get(position).getId_disasters());
        holder.namaKejadian.setText(homes.get(position).getDisasters_types_name());
        holder.lokasiKejadian.setText(homes.get(position).getDisasters_village() + ", " + urban +  ", " + sub_dis + ", " + dis);
        holder.tglKejadian.setText(homes.get(position).getDisasters_date());
        holder.waktuKej.setText(homes.get(position).getDisasters_time());
    }

    @Override
    public int getItemCount() {
         return homes.size();
    }

    @Override
    public Filter getFilter() {
        return HomeFilter;
    }

    private Filter HomeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Home> filteredHome = new ArrayList<>();
            if (charSequence==null || charSequence.length()==0){
                filteredHome.addAll(homesFull);
            }else{
                String filteredPattern = charSequence.toString().toLowerCase().trim();

                for (Home home : homesFull){
                    if (home.getId_disasters().toLowerCase().contains(filteredPattern)){
                        filteredHome.add(home);
                    }else if (home.getDisasters_types().toLowerCase().contains(filteredPattern)){
                        filteredHome.add(home);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredHome;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            homes.clear();
            homes.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView kodeKejadian, namaKejadian, lokasiKejadian, tglKejadian, waktuKej;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kodeKejadian = itemView.findViewById(R.id.kodeKejadian);
            namaKejadian = itemView.findViewById(R.id.namaKejadian);
            lokasiKejadian = itemView.findViewById(R.id.lokasiKejadian);
            tglKejadian = itemView.findViewById(R.id.tglKejadian);
            waktuKej = itemView.findViewById(R.id.waktuKej);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (listener != homes){
                listener.onClick(view, homes.get(getAdapterPosition()));
            }
        }
    }

    //ClickListener
    HomeRecViewAdapter.HomeClickListener listener = null;
    public interface HomeClickListener{
        void onClick(View view, Home home);
   }
   public void setListener(HomeRecViewAdapter.HomeClickListener listener){
        this.listener = listener;
   }
}
