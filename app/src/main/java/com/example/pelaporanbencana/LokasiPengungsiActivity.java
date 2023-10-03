package com.example.pelaporanbencana;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelaporanbencana.adapter.LokPengungsiRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.LokasiPengungsi;
import com.example.pelaporanbencana.model.LokasiPengungsiAll;
import com.example.pelaporanbencana.model.ShelterShowallAsId.DataItem;
import com.example.pelaporanbencana.model.ShelterShowallAsId.ShelterShowallAsIdResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LokasiPengungsiActivity extends AppCompatActivity implements LokPengungsiRecViewAdapter.LokPengungsiClickListener{

    FloatingActionButton fbLokPengungsi;
    LokPengungsiRecViewAdapter lokPengungsiRecViewAdapter;
    RecyclerView recViewLokPengungsi;
    ApiInterface apiInterface;
    TextView kodeKejadianLokP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_pengungsi);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        Intent intent = getIntent();
        String disastersType = intent.getStringExtra("disasters_type");

        kodeKejadianLokP = findViewById(R.id.kodeKejadianLokP);
        kodeKejadianLokP.setText(disastersType);

        fbLokPengungsi = findViewById(R.id.fbLokPengungsi);
        recViewLokPengungsi = findViewById(R.id.recViewLokPengungsi);

        fbLokPengungsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LokasiPengungsiActivity.this, LokasiPengungsiAllActivity.class);
                startActivity(intent);
                finish();
            }
        });

        lokPengungsiRecViewAdapter = new LokPengungsiRecViewAdapter();
        lokPengungsiRecViewAdapter.setListener(this);
        recViewLokPengungsi.setAdapter(lokPengungsiRecViewAdapter);
        recViewLokPengungsi.setLayoutManager(new GridLayoutManager(this, 1));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ShelterShowallAsIdResponse> call = apiInterface.getShelterShowAllAsId("Bearer " + accessToken, id_disasters);
        call.enqueue(new Callback<ShelterShowallAsIdResponse>() {
            @Override
            public void onResponse(Call<ShelterShowallAsIdResponse> call, Response<ShelterShowallAsIdResponse> response) {
                ShelterShowallAsIdResponse shelterShowallResponse = response.body();
                ArrayList<LokasiPengungsi> listData = new ArrayList<>();

                if (shelterShowallResponse != null && response.isSuccessful()){
                    List<DataItem> itemList = shelterShowallResponse.getData();

                    for (DataItem item : itemList){

                        LokasiPengungsi lokasiPengungsi = new LokasiPengungsi(
                                item.getIdShelter(),
                                item.getLocation(),
                                item.getAddress(),
                                item.getHunianTypes(),
                                item.getCapacity(),
                                item.getJmlOrg()
                        );

                        listData.add(lokasiPengungsi);
                    }
                }else {
                    Toast.makeText(LokasiPengungsiActivity.this, "Gagal dapat data", Toast.LENGTH_SHORT).show();
                    Log.d("retrofit", "error: " + response.toString());
                }
                lokPengungsiRecViewAdapter.setLokasiPengungsis(listData);
            }

            @Override
            public void onFailure(Call<ShelterShowallAsIdResponse> call, Throwable t) {
                Toast.makeText(LokasiPengungsiActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
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
                lokPengungsiRecViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
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
                Intent intent = new Intent(LokasiPengungsiActivity.this, MenusActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onClick(View view, LokasiPengungsi lokasiPengungsi) {
        String kode_shelter = lokasiPengungsi.getId_shelter();

        Intent intent = new Intent(this, PengungsiActivity.class);
        intent.putExtra("kode_shelter", kode_shelter);
        startActivity(intent);
    }
}