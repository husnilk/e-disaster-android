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

import com.example.pelaporanbencana.adapter.LokPengungsiAllRecViewAdapter;
import com.example.pelaporanbencana.adapter.LokPengungsiRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.LokasiPengungsi;
import com.example.pelaporanbencana.model.LokasiPengungsiAll;
import com.example.pelaporanbencana.model.ShelterShowAll.DataItem;
import com.example.pelaporanbencana.model.ShelterShowAll.ShelterShowAllResponse;
import com.example.pelaporanbencana.model.ShelterStore.ShelterStoreResponse;
import com.example.pelaporanbencana.model.VictimStore.VictimStoreResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LokasiPengungsiAllActivity extends AppCompatActivity implements LokPengungsiAllRecViewAdapter.LokPengungsiAllClickListener{
    RecyclerView recViewLokPengungsiAll;
    FloatingActionButton fbTambahLok2;
    LokPengungsiAllRecViewAdapter adapter;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_pengungsi_all);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        fbTambahLok2 = findViewById(R.id.fbTambahLok2);
        fbTambahLok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LokasiPengungsiAllActivity.this, KoordinatLokasiPengungsiActivity.class);
                startActivity(intent);
                finish();
            }
        });
        recViewLokPengungsiAll = findViewById(R.id.recViewLokPengungsiAll);
        adapter = new LokPengungsiAllRecViewAdapter();
        adapter.setListener(this);
        recViewLokPengungsiAll.setAdapter(adapter);
        recViewLokPengungsiAll.setLayoutManager(new GridLayoutManager(this,1));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ShelterShowAllResponse> call = apiInterface.getShelterShowAll("Bearer " + accessToken);
        call.enqueue(new Callback<ShelterShowAllResponse>() {
            @Override
            public void onResponse(Call<ShelterShowAllResponse> call, Response<ShelterShowAllResponse> response) {
                ShelterShowAllResponse showAllResponse = response.body();
                ArrayList<LokasiPengungsiAll> listData = new ArrayList<>();

                if (showAllResponse != null && response.isSuccessful()){
                    List<DataItem> itemList = showAllResponse.getData();

                    for (DataItem item : itemList){
                        LokasiPengungsiAll pengungsiAll = new LokasiPengungsiAll(
                                item.getIdShelter(),
                                item.getLocation(),
                                item.getAddress(),
                                item.getHunianTypes(),
                                item.getCapacity()
                        );
                        listData.add(pengungsiAll);
                    }
                }else {
                    Toast.makeText(LokasiPengungsiAllActivity.this, "Gagal dapat Data", Toast.LENGTH_SHORT).show();
                    Log.d("retrofit", "error: "+response.toString());
                }
                adapter.setLokasiPengungsiAlls(listData);
            }

            @Override
            public void onFailure(Call<ShelterShowAllResponse> call, Throwable t) {
                Toast.makeText(LokasiPengungsiAllActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View view, LokasiPengungsiAll lokasiPengungsiAll) {
        String kode_shelter = lokasiPengungsiAll.getId_shelter();

        Intent intent = new Intent(this, PengungsisFormsActivity.class);
        intent.putExtra("kode_shelter", kode_shelter);
        startActivity(intent);

    }

    @Override
    public void onLongClick(View view, LokasiPengungsiAll lokasiPengungsiAll) {

        String id_shelter = lokasiPengungsiAll.getId_shelter();

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
                Intent intent = new Intent(LokasiPengungsiAllActivity.this, KoordinatLokasiPengungsiEditActivity.class);
                intent.putExtra("id_shelter", id_shelter);
                startActivity(intent);
            }
        });

        dialogPesan.show();
    }

    private void deleteData(String id_shelter) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ShelterStoreResponse> call = apiInterface.deleteShelter("Bearer " + accessToken, id_shelter);
        call.enqueue(new Callback<ShelterStoreResponse>() {
            @Override
            public void onResponse(Call<ShelterStoreResponse> call, Response<ShelterStoreResponse> response) {
                if (response.isSuccessful()){
                    StyleableToast.makeText(LokasiPengungsiAllActivity.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT, R.style.success).show();
                }else{
                    StyleableToast.makeText(LokasiPengungsiAllActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<ShelterStoreResponse> call, Throwable t) {
                StyleableToast.makeText(LokasiPengungsiAllActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });
    }

}