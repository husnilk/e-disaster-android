package com.example.pelaporanbencana.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pelaporanbencana.R;
import com.example.pelaporanbencana.model.BantuanT;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BantuanTRecViewAdapter extends RecyclerView.Adapter<BantuanTRecViewAdapter.ViewHolder> implements Filterable {

    ArrayList<BantuanT> bantuanTArrayList = new ArrayList<>();
    ArrayList<BantuanT> bantuanTFull;

//    public BantuanTRecViewAdapter(ArrayList<BantuanT> bantuanTArrayList) {
//        this.bantuanTArrayList = bantuanTArrayList;
//        bantuanTFull = new ArrayList<>(bantuanTArrayList);
//    }


    public void setBantuanTArrayList(ArrayList<BantuanT> bantuanTArrayList) {
        this.bantuanTArrayList = bantuanTArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bantuant_list_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String date_sent = df.format(bantuanTArrayList.get(position).getDate_sent());

        holder.tgl_dikirim.setText(date_sent);
        holder.penerima.setText(bantuanTArrayList.get(position).getRecipient());
        holder.jenisBantuant.setText(bantuanTArrayList.get(position).getSa_types_name());
        holder.jumlahBantuant.setText(String.valueOf(bantuanTArrayList.get(position).getSa_distributed_amount()));
        holder.satuanBantuant.setText(bantuanTArrayList.get(position).getSa_distributed_units());
        holder.batchBantuanT.setText(String.valueOf(bantuanTArrayList.get(position).getBatch()));
    }

    @Override
    public int getItemCount() {
        return bantuanTArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return FilterBantuanT;
    }

    private Filter FilterBantuanT = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<BantuanT> filteredBantuanT = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredBantuanT.addAll(bantuanTFull);
            }else{
                String filteredPattern = charSequence.toString().toLowerCase().trim();

//                for (BantuanT bantuanT : bantuanTFull){
//                    if (bantuanT.getJenisBantuant().toLowerCase().contains(filteredPattern)){
//                        filteredBantuanT.add(bantuanT);
//                    }
//                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredBantuanT;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            bantuanTArrayList.clear();
            bantuanTArrayList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView jenisBantuant, jumlahBantuant, satuanBantuant, penerima, tgl_dikirim, batchBantuanT;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jenisBantuant = itemView.findViewById(R.id.jenisBantuanT);
            jumlahBantuant = itemView.findViewById(R.id.jumlahBantuanT);
            satuanBantuant = itemView.findViewById(R.id.satuanBantuanT);
            penerima = itemView.findViewById(R.id.penerima);
            tgl_dikirim = itemView.findViewById(R.id.tgl_dikirim);
            batchBantuanT = itemView.findViewById(R.id.batchBantuanT);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (listener != bantuanTArrayList){
                listener.onClick(view, bantuanTArrayList.get(getAdapterPosition()));
            }
            return false;
        }
    }

    BantuanTRecViewAdapter.BantuanTClickListener listener = null;
    public interface BantuanTClickListener{
        void onClick(View view, BantuanT bantuanT);
    }

    public void setListener(BantuanTRecViewAdapter.BantuanTClickListener listener){
        this.listener = listener;
    }
}
