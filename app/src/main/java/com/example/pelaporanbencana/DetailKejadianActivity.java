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
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelaporanbencana.adapter.DetailKejadianRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.DevDisastersShowall.DataItem;
import com.example.pelaporanbencana.model.DevDisastersShowall.DevDisastersShowallResponse;
import com.example.pelaporanbencana.model.DevDisastersStore.DevDisastersStoreResponse;
import com.example.pelaporanbencana.model.DevKejadian;
import com.example.pelaporanbencana.model.DisasterShowInMenus.DisInMenusResponse;
import com.example.pelaporanbencana.model.ShelterStore.ShelterStoreResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailKejadianActivity extends AppCompatActivity implements DetailKejadianRecViewAdapter.DetailKejadianClickListener{
    TextView tvTglKejadian, tvIdKejadian, tvJenisBencana, tvWaktuKejadian, tvLatitude, tvLongitude,
    tvStatus, tvJorong, tvKelurahan, tvDeskripsi;
    FloatingActionButton fabTambahPerkembangan, fabEditKejadian;
    ApiInterface apiInterface;
    RecyclerView recViewDetailKejadian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kejadian);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String access_token = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        tvIdKejadian = findViewById(R.id.tvIdKejadian);
        tvTglKejadian = findViewById(R.id.tvTglKejadian);
        tvJenisBencana = findViewById(R.id.tvJenisBencana);
        tvWaktuKejadian = findViewById(R.id.tvWaktuKejadian);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        tvStatus = findViewById(R.id.tvStatus);
        tvJorong = findViewById(R.id.tvJorong);
        tvKelurahan = findViewById(R.id.tvKelurahan);
        fabTambahPerkembangan = findViewById(R.id.fabTambahPerkembangan);
        fabEditKejadian = findViewById(R.id.fabEditKejadian);
        recViewDetailKejadian = findViewById(R.id.recViewDetailKejadian);

        DetailKejadianRecViewAdapter detailKejadianRecViewAdapter = new DetailKejadianRecViewAdapter();
        detailKejadianRecViewAdapter.setListener(this);
        recViewDetailKejadian.setAdapter(detailKejadianRecViewAdapter);
        recViewDetailKejadian.setLayoutManager(new GridLayoutManager(this, 1));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DisInMenusResponse> call = apiInterface.getDisasterInMenu("Bearer " + access_token,
                id_disasters);
        call.enqueue(new Callback<DisInMenusResponse>() {
            @Override
            public void onResponse(Call<DisInMenusResponse> call, Response<DisInMenusResponse> response) {
                DisInMenusResponse response1 = response.body();
                if (response.body() != null && response.isSuccessful()){
                    tvIdKejadian.setText(id_disasters);
                    tvTglKejadian.setText(response1.getData().getDisastersDate());
                    tvWaktuKejadian.setText(response1.getData().getDisastersTime());
                    tvJenisBencana.setText(response1.getData().getDisastersTypes());
                    tvJorong.setText(response1.getData().getDisastersVillage());
                    tvKelurahan.setText(response1.getData().getUrbanVillageName());
                    tvLatitude.setText(response1.getData().getDisastersLat());
                    tvLongitude.setText(response1.getData().getDisastersLong());
//                    tvDeskripsi.setText(response1.getData().getDisastersDesc());

                    String disasters_date = response1.getData().getDisastersDate();
                    String disasters_time = response1.getData().getDisastersTime();
                    String status = response1.getData().getDisastersStatus();
                    if (status.equals("1")){
                        tvStatus.setText("On Progress");
                    }else{
                        tvStatus.setText("Done");
                    }

                    Call<DevDisastersShowallResponse> call1 = apiInterface.getDevDisaster("Bearer " + access_token, id_disasters, disasters_date, disasters_time);
                    call1.enqueue(new Callback<DevDisastersShowallResponse>() {
                        @Override
                        public void onResponse(Call<DevDisastersShowallResponse> call, Response<DevDisastersShowallResponse> response) {
                            DevDisastersShowallResponse showallResponse = response.body();
                            ArrayList<DevKejadian> listData = new ArrayList<>();

                            if (showallResponse != null && response.isSuccessful()){
                                List<DataItem> itemList = showallResponse.getData();

                                for (DataItem item : itemList){
                                    DevKejadian devKejadian = new DevKejadian(
                                            item.getIdDisasters(),
                                            item.getDisastersDate(),
                                            item.getDisastersTime(),
                                            item.getDisastersDesc(),
                                            item.getDisastersImpact(),
                                            item.getDisastersCauses(),
                                            item.getWeatherConditions(),
                                            item.getDisastersPotential(),
                                            item.getDisastersEffort()
                                    );
                                    listData.add(devKejadian);
                                }

                            }else{
                                StyleableToast.makeText(DetailKejadianActivity.this, "Gagal dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                                Log.d("retrofit", "error : " + response.toString());
                            }
                            detailKejadianRecViewAdapter.setArrayList(listData);
                        }

                        @Override
                        public void onFailure(Call<DevDisastersShowallResponse> call, Throwable t) {
                            StyleableToast.makeText(DetailKejadianActivity.this, "Gagal", Toast.LENGTH_SHORT, R.style.error).show();
                        }
                    });

                    fabTambahPerkembangan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(DetailKejadianActivity.this, KejadianPerkembanganFormsActivity.class);
                            startActivity(intent);
                        }
                    });

                    Log.d("retrofit", "success :" + response.toString());
                }else{
                    StyleableToast.makeText(DetailKejadianActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error :" + response.toString());
                }
            }

            @Override
            public void onFailure(Call<DisInMenusResponse> call, Throwable t) {
                StyleableToast.makeText(DetailKejadianActivity.this, "Gagal", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });


        fabEditKejadian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(DetailKejadianActivity.this, KoordinatKejadianEditActivity.class);
                startActivity(intent1);
                finish();
            }
        });

    }

    @Override
    public void onClick(View view, DevKejadian devKejadian) {

    }

    @Override
    public void onLongClick(View view, DevKejadian devKejadian) {
        String id_disasters = devKejadian.getId_disasters();
        String disasters_date = devKejadian.getDisasters_date();
        String disasters_time = devKejadian.getDisasters_time();

        String id_devDisasters = id_disasters + disasters_date + disasters_time;

        //longClick
        AlertDialog.Builder dialogPesan = new AlertDialog.Builder(this);
        dialogPesan.setMessage("Pilih Aksi");
        dialogPesan.setCancelable(true);
        dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteData(id_devDisasters);
                dialogInterface.dismiss();
            }
        });

        dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String id_disasters = devKejadian.getId_disasters();
                String disasters_date = devKejadian.getDisasters_date();
                String disasters_time = devKejadian.getDisasters_time();
                String disasters_desc = devKejadian.getDisasters_desc();
                String disasters_causes = devKejadian.getDisasters_causes();
                String disasters_impact = devKejadian.getDisasters_impact();
                String weather_conditions = devKejadian.getWeather_conditions();
                String disasters_potential = devKejadian.getDisasters_potential();
                String disasters_effort = devKejadian.getDisasters_effort();

                Intent intent = new Intent(DetailKejadianActivity.this, KejadianEditForms2Activity.class);
                intent.putExtra("id_disasters", id_disasters);
                intent.putExtra("disasters_date", disasters_date);
                intent.putExtra("disasters_time", disasters_time);
                intent.putExtra("disasters_desc", disasters_desc);
                intent.putExtra("disasters_causes", disasters_causes);
                intent.putExtra("disasters_impact", disasters_impact);
                intent.putExtra("weather_conditions", weather_conditions);
                intent.putExtra("disasters_potential", disasters_potential);
                intent.putExtra("disasters_effort", disasters_effort);
                startActivity(intent);
            }
        });

        dialogPesan.show();
    }

    private void deleteData(String id_devDisasters) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DevDisastersStoreResponse> call = apiInterface.deleteDevDisaster("Bearer " + accessToken, id_devDisasters);
        call.enqueue(new Callback<DevDisastersStoreResponse>() {
            @Override
            public void onResponse(Call<DevDisastersStoreResponse> call, Response<DevDisastersStoreResponse> response) {
                if (response.isSuccessful()){
                    StyleableToast.makeText(DetailKejadianActivity.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT, R.style.success).show();
                }else{
                    StyleableToast.makeText(DetailKejadianActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<DevDisastersStoreResponse> call, Throwable t) {
                StyleableToast.makeText(DetailKejadianActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });
    }
}