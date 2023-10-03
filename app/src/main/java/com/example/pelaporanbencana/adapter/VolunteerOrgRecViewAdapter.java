package com.example.pelaporanbencana.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pelaporanbencana.R;
import com.example.pelaporanbencana.model.Relawan;
import com.example.pelaporanbencana.model.VolunteerOrg;

import java.util.ArrayList;

public class VolunteerOrgRecViewAdapter extends RecyclerView.Adapter<VolunteerOrgRecViewAdapter.ViewHolder> {
    ArrayList<VolunteerOrg> volunteerOrgList = new ArrayList<>();

    public void setVolunteerOrgList(ArrayList<VolunteerOrg> volunteerOrgList) {
        this.volunteerOrgList = volunteerOrgList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VolunteerOrgRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.volunteer_org_list_item, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VolunteerOrgRecViewAdapter.ViewHolder holder, int position) {
        holder.IdOrgRelawan.setText(volunteerOrgList.get(position).getId_volunteer_org());
        holder.NamaOrgRelawan.setText(volunteerOrgList.get(position).getVolunteer_org_name());
        holder.lokasiOrgRelawan.setText(volunteerOrgList.get(position).getVolunteer_org_address());

        String jenis_org_relawan = volunteerOrgList.get(position).getVolunteer_org_status();
        if (jenis_org_relawan == "1"){
            holder.JenisOrgRelawan.setText("Organisasi Dalam Negeri");
        }else if (jenis_org_relawan == "2"){
            holder.JenisOrgRelawan.setText("Organisasi Luar Negeri");
        }else {
            holder.JenisOrgRelawan.setText("Organisasi Dalam Negeri");
        }
    }

    @Override
    public int getItemCount() {
        return volunteerOrgList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView IdOrgRelawan, NamaOrgRelawan, JenisOrgRelawan, lokasiOrgRelawan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            IdOrgRelawan = itemView.findViewById(R.id.IdOrgRelawan);
            NamaOrgRelawan = itemView.findViewById(R.id.NamaOrgRelawan);
            JenisOrgRelawan = itemView.findViewById(R.id.JenisOrgRelawan);
            lokasiOrgRelawan = itemView.findViewById(R.id.lokasiOrgRelawan);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(listener != volunteerOrgList){
                listener.onClick(view, volunteerOrgList.get(getAdapterPosition()));
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if(listener != volunteerOrgList){
                listener.onLongClick(view, volunteerOrgList.get(getAdapterPosition()));
            }
            return false;
        }
    }

    VolunteerOrgRecViewAdapter.VolunteerOrgClickListener listener = null;
    public interface VolunteerOrgClickListener {
        void onClick(View view, VolunteerOrg volunteerOrg);

        void onLongClick(View view, VolunteerOrg volunteerOrg);


    }

    public void setListener(VolunteerOrgRecViewAdapter.VolunteerOrgClickListener listener) {
        this.listener = listener;
    }
}
