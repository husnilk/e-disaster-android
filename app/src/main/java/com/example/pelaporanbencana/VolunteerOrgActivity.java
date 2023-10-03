package com.example.pelaporanbencana;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pelaporanbencana.adapter.VolunteerOrgRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.Relawan;
import com.example.pelaporanbencana.model.VolunteerOrg;
import com.example.pelaporanbencana.model.VolunteerOrganization.DataItem;
import com.example.pelaporanbencana.model.VolunteerOrganization.VolunteerOrgShowallResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerOrgActivity extends AppCompatActivity implements VolunteerOrgRecViewAdapter.VolunteerOrgClickListener {
    VolunteerOrgRecViewAdapter volunteerOrgRecViewAdapter;
    FloatingActionButton fbTambahRelawan;
    RecyclerView recViewRelawanList;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_org);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        fbTambahRelawan = findViewById(R.id.fbTambahRelawan);

        recViewRelawanList = findViewById(R.id.recViewRelawanList);
        volunteerOrgRecViewAdapter = new VolunteerOrgRecViewAdapter();
        volunteerOrgRecViewAdapter.setListener(this);
        recViewRelawanList.setAdapter(volunteerOrgRecViewAdapter);
        recViewRelawanList.setLayoutManager(new GridLayoutManager(this, 1));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<VolunteerOrgShowallResponse> call = apiInterface.getVolunteerOrg("Bearer " + accessToken);
        call.enqueue(new Callback<VolunteerOrgShowallResponse>() {
            @Override
            public void onResponse(Call<VolunteerOrgShowallResponse> call, Response<VolunteerOrgShowallResponse> response) {
                VolunteerOrgShowallResponse volunteerOrgResponse = response.body();
                ArrayList<VolunteerOrg> listData = new ArrayList<>();

                if (volunteerOrgResponse.isSuccess()){
                    List<DataItem> itemList = volunteerOrgResponse.getData();

                    for (DataItem item : itemList){
                        VolunteerOrg volunteerOrg = new VolunteerOrg(
                                item.getIdVolunteerOrg(),
                                item.getVolunteerOrgName(),
                                item.getVolunteerOrgAddress(),
                                item.getVolunteerOrgStatus()
                        );
                        listData.add(volunteerOrg);
                    }
                    Log.d("retrofit", "success"+response.toString());
                }else{
                    StyleableToast.makeText(VolunteerOrgActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error" + response.toString());
                }
                volunteerOrgRecViewAdapter.setVolunteerOrgList(listData);
            }

            @Override
            public void onFailure(Call<VolunteerOrgShowallResponse> call, Throwable t) {
                StyleableToast.makeText(VolunteerOrgActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });

        fbTambahRelawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VolunteerOrgActivity.this, VolunteerOrgFormsActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onClick(View view, VolunteerOrg volunteerOrg) {
        String id_volunteer_org = volunteerOrg.getId_volunteer_org();
//        Toast.makeText(this, id_volunteer_org, Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(VolunteerOrgActivity.this, RelawanActivity.class);
        intent1.putExtra("idVolunteerOrg", id_volunteer_org);
        startActivity(intent1);
    }

    @Override
    public void onLongClick(View view, VolunteerOrg volunteerOrg) {
        String id_volunteer_org = volunteerOrg.getId_volunteer_org();

        //longClick
        AlertDialog.Builder dialogPesan = new AlertDialog.Builder(this);
        dialogPesan.setMessage("Pilih Aksi");
        dialogPesan.setCancelable(true);
//        dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                deleteData(id_shelter);
//                dialogInterface.dismiss();
//            }
//        });

        dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //intent
                Intent intent = new Intent(VolunteerOrgActivity.this, VolunteerOrgEditFormsActivity.class);
                intent.putExtra("id_volunteer_org", id_volunteer_org);
                startActivity(intent);
            }
        });

        dialogPesan.show();
    }
}