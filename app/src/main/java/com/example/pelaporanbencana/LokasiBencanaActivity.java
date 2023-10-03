package com.example.pelaporanbencana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

import com.example.pelaporanbencana.adapter.LokBencanaRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.LokasiBencana;
import com.example.pelaporanbencana.model.UrbanVillage.DataItem;
import com.example.pelaporanbencana.model.UrbanVillage.UrbanVillageResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LokasiBencanaActivity extends AppCompatActivity implements LokBencanaRecViewAdapter.LokClickListener{

    RecyclerView recViewLokBencana;
    ArrayList<LokasiBencana> lokasiBencanas;
    LokBencanaRecViewAdapter lokBencanaRecViewAdapter;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_bencana);

        recViewLokBencana = findViewById(R.id.recViewLokBencana);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

//        lokasiBencanas = new ArrayList<>();

        lokBencanaRecViewAdapter = new LokBencanaRecViewAdapter();
        lokBencanaRecViewAdapter.setListener(this);
        recViewLokBencana.setAdapter(lokBencanaRecViewAdapter);
        recViewLokBencana.setLayoutManager(new GridLayoutManager(this, 1));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UrbanVillageResponse> call = apiInterface.getUrbanVillage("Bearer " + accessToken);
        call.enqueue(new Callback<UrbanVillageResponse>() {
            @Override
            public void onResponse(Call<UrbanVillageResponse> call, Response<UrbanVillageResponse> response) {
                UrbanVillageResponse urbanVillageResponse = response.body();
                ArrayList<LokasiBencana> bencanaArrayList = new ArrayList<>();

                if (urbanVillageResponse != null && urbanVillageResponse.isSuccess()){
                    List<DataItem> itemList = urbanVillageResponse.getData();

                    for (DataItem item : itemList){
                        LokasiBencana lokasiBencana = new LokasiBencana(
                                item.getUrbanVillageName(),
                                item.getSubDistrictName(),
                                item.getDistrictName(),
                                item.getProvinceName(),
                                item.getIdUrbanVillageName()
                        );
                        bencanaArrayList.add(lokasiBencana);
                    }
                }else {
                    Toast.makeText(LokasiBencanaActivity.this, "Gagal dapat data" + accessToken, Toast.LENGTH_SHORT).show();
                    Log.d("retrofit", "error : " + response.toString());
                }
                lokBencanaRecViewAdapter.setLokasiBencanas(bencanaArrayList);
            }

            @Override
            public void onFailure(Call<UrbanVillageResponse> call, Throwable t) {
                Toast.makeText(LokasiBencanaActivity.this, "Gagal dapat data (2)", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.actionsearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lokBencanaRecViewAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }


    @Override
    public void onClick(View view, LokasiBencana lokasiBencana) {
        String idUrbanVillage = lokasiBencana.getId_urban_village();
        String urbanVillageName = lokasiBencana.getUrban_village_name();

        Intent intent1 = getIntent();
        String lat_bencana = intent1.getStringExtra("latBecana");
        String long_bencana = intent1.getStringExtra("longBecana");
        String jorong = intent1.getStringExtra("jorong");

//        SharedPreferences pref = getApplicationContext().getSharedPreferences("PrefLokBencana", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("id_urban_village", idUrbanVillage);

        Intent intent = new Intent(this, KejadianForms1Activity.class);
        intent.putExtra("urban_village_name", urbanVillageName);
        intent.putExtra("id_urban_village", idUrbanVillage);
        intent.putExtra("jorong", jorong);
        intent.putExtra("lat_bencana", lat_bencana);
        intent.putExtra("long_bencana", long_bencana);
        startActivity(intent);
        finish();
    }
}