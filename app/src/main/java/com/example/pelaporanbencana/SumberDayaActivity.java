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

import com.example.pelaporanbencana.adapter.SumDaRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.ResourcesShowAll.DataItem;
import com.example.pelaporanbencana.model.ResourcesShowAll.ResourcesShowAllResponse;
import com.example.pelaporanbencana.model.ResourcesStore.ResourcesStoreResponse;
import com.example.pelaporanbencana.model.SumberDaya;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SumberDayaActivity extends AppCompatActivity implements SumDaRecViewAdapter.SumDaOnClickListener{
    private RecyclerView recViewSumDa;
    SumDaRecViewAdapter sumDaRecViewAdapter;
    FloatingActionButton fbTambahSumDa;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sumber_daya);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        fbTambahSumDa = findViewById(R.id.fbTambahSumDa);
        recViewSumDa = findViewById(R.id.recViewSumDa);
        sumDaRecViewAdapter = new SumDaRecViewAdapter();
        sumDaRecViewAdapter.setListener(this);
        recViewSumDa.setAdapter(sumDaRecViewAdapter);
        recViewSumDa.setLayoutManager(new GridLayoutManager(this, 1));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResourcesShowAllResponse> call = apiInterface.getResources("Bearer " + accessToken, id_disasters);
        call.enqueue(new Callback<ResourcesShowAllResponse>() {
            @Override
            public void onResponse(Call<ResourcesShowAllResponse> call, Response<ResourcesShowAllResponse> response) {
                ResourcesShowAllResponse resourcesResponse = response.body();
                ArrayList<SumberDaya> listData = new ArrayList<>();

                if (resourcesResponse.isSuccess()){
                    List<DataItem> itemList = resourcesResponse.getData();

                    for (DataItem item : itemList){
                        SumberDaya sumberDaya = new SumberDaya(
                                item.getIdResources(),
                                item.getResourcesAvailable(),
                                item.getLackOfResources(),
                                item.getAdditionalInfo(),
                                item.getResourcesTypes(),
                                item.getResourcesUnits(),
                                item.getIdDisasterResources()
                        );
                        listData.add(sumberDaya);
                        Log.d("retrofit", "success " + response.toString());
                    }
                }else {
                    StyleableToast.makeText(SumberDayaActivity.this, "Gagal dapat Data", Toast.LENGTH_SHORT, R.style.success).show();
                    Log.d("retrofit", "error" + response.toString());
                }
                sumDaRecViewAdapter.setSumberDayas(listData);
            }

            @Override
            public void onFailure(Call<ResourcesShowAllResponse> call, Throwable t) {
                StyleableToast.makeText(SumberDayaActivity.this, "Gagal dapat Data", Toast.LENGTH_SHORT, R.style.success).show();
            }
        });

        fbTambahSumDa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SumberDayaActivity.this, SumDayaFormsActivity.class);
                startActivity(intent);
                finish();
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
                sumDaRecViewAdapter.getFilter().filter(newText);
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
                Intent intent = new Intent(SumberDayaActivity.this, MenusActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onClick(View view, SumberDaya sumberDaya) {
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        String id_resources = String.valueOf(sumberDaya.getId_resources());
        String id_disasters_resources = String.valueOf(sumberDaya.getId_disaster_resources());
        String id_dr = id_disasters + id_resources + id_disasters_resources;

        //longClick
        AlertDialog.Builder dialogPesan = new AlertDialog.Builder(this);
        dialogPesan.setMessage("Pilih Aksi");
        dialogPesan.setCancelable(true);
        dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteData(id_dr);
                dialogInterface.dismiss();
//                Toast.makeText(SumberDayaActivity.this, id_disasters_resources, Toast.LENGTH_SHORT).show();
            }
        });

        dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(SumberDayaActivity.this, SumDayaFormsEditActivity.class);
                intent.putExtra("id_resources", id_resources);
                intent.putExtra("id_disasters_resources", id_disasters_resources);
                startActivity(intent);
            }
        });

        dialogPesan.show();
    }

    private void deleteData(String id_dr) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResourcesStoreResponse> call = apiInterface.deleteResources("Bearer " + accessToken, id_dr);
        call.enqueue(new Callback<ResourcesStoreResponse>() {
            @Override
            public void onResponse(Call<ResourcesStoreResponse> call, Response<ResourcesStoreResponse> response) {
                if (response.isSuccessful()){
                    StyleableToast.makeText(SumberDayaActivity.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT, R.style.success).show();
                    Log.d("retrofit", "error " + response.toString());
                }else{
                    StyleableToast.makeText(SumberDayaActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResourcesStoreResponse> call, Throwable t) {
                StyleableToast.makeText(SumberDayaActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });
    }
}