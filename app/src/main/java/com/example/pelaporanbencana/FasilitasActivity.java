package com.example.pelaporanbencana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.pelaporanbencana.adapter.FasilitasRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.DamageStore.DamageStoreResponse;
import com.example.pelaporanbencana.model.FacilityShowAll.DataItem;
import com.example.pelaporanbencana.model.FacilityShowAll.FacilityShowallResponse;
import com.example.pelaporanbencana.model.FacilityStore.FacilityStoreResponse;
import com.example.pelaporanbencana.model.Fasilitas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FasilitasActivity extends AppCompatActivity implements FasilitasRecViewAdapter.FasilitasOnClickListener{
    RecyclerView recViewFasilitas;
    FloatingActionButton fbFasilitas;
    FasilitasRecViewAdapter fasilitasRecViewAdapter;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasilitas);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        fbFasilitas = findViewById(R.id.fbFasilitas);
        fbFasilitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FasilitasActivity.this, FasilitasFormsActivity.class);
                startActivity(intent);
            }
        });

        recViewFasilitas = findViewById(R.id.recViewFasilitas);
        fasilitasRecViewAdapter = new FasilitasRecViewAdapter();
        fasilitasRecViewAdapter.setListener(this);
        recViewFasilitas.setAdapter(fasilitasRecViewAdapter);
        recViewFasilitas.setLayoutManager(new GridLayoutManager(this, 1));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<FacilityShowallResponse> call = apiInterface.getFacility("Bearer " + accessToken, id_disasters);
        call.enqueue(new Callback<FacilityShowallResponse>() {
            @Override
            public void onResponse(Call<FacilityShowallResponse> call, Response<FacilityShowallResponse> response) {
                FacilityShowallResponse facilityResponse = response.body();
                ArrayList<Fasilitas> listData = new ArrayList<>();

                if (facilityResponse != null) {
                    if (facilityResponse.isSuccess()){
                        List<DataItem> itemList = facilityResponse.getData();

                        for (DataItem item : itemList){
                            Fasilitas fasilitas = new Fasilitas(
                                    item.getIdFacilities(),
                                    item.getIdDisasters(),
                                    item.getFacilitiesCategory(),
                                    item.getDescription()
                            );
                            listData.add(fasilitas);
                        }
                        Log.d("retrofit", "success"+response.toString());
                    }else {
                        StyleableToast.makeText(FasilitasActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                        Log.d("retrofit", "error" + response.toString());
                    }
                }
                fasilitasRecViewAdapter.setFasilitasList(listData);
            }

            @Override
            public void onFailure(Call<FacilityShowallResponse> call, Throwable t) {
                StyleableToast.makeText(FasilitasActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    public void setMode(int selectedMode) {
        switch (selectedMode){
            case R.id.actionhome:
                Intent intent = new Intent(FasilitasActivity.this, MenusActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onClick(View view, Fasilitas fasilitas) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref.getString("id_disasters", "");

        //delete
        String id_facilities = String.valueOf(fasilitas.getId_facilities());
        String id_df = id_disasters + id_facilities;

        //alert ubah dan hapus
        AlertDialog.Builder dialogPesan = new AlertDialog.Builder(this);
        dialogPesan.setMessage("Pilih Aksi");
        dialogPesan.setCancelable(true);
        dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteData(id_df);
                dialogInterface.dismiss();
            }
        });

        dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(FasilitasActivity.this, FasilitasFormsEditActivity.class);
                intent.putExtra("id_facilities", id_facilities);
                startActivity(intent);
            }
        });

        dialogPesan.show();
    }

    private void deleteData(String id_df) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<FacilityStoreResponse> call1 = apiInterface.deleteFacility("Bearer " + accessToken, id_df);
        call1.enqueue(new Callback<FacilityStoreResponse>() {
            @Override
            public void onResponse(Call<FacilityStoreResponse> call, Response<FacilityStoreResponse> response) {
                if (response.isSuccessful()){
                    StyleableToast.makeText(FasilitasActivity.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT, R.style.success).show();
                }else {
                    StyleableToast.makeText(FasilitasActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<FacilityStoreResponse> call, Throwable t) {
                StyleableToast.makeText(FasilitasActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });
    }
}
