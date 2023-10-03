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

import com.example.pelaporanbencana.adapter.TerdampakListRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.AffectedShowAllAsId.AffectedShowAllAsIdResponse;
import com.example.pelaporanbencana.model.AffectedShowAllAsId.DataItem;
import com.example.pelaporanbencana.model.AffectedStore.AffectedStoreResponse;
import com.example.pelaporanbencana.model.Penduduk;
import com.example.pelaporanbencana.model.VictimStore.VictimStoreResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TerdampakActivity extends AppCompatActivity implements TerdampakListRecViewAdapter.TerdampakClickListener{

    FloatingActionButton fbTambahTerdampak;
    RecyclerView recViewTerdampakList;
    TerdampakListRecViewAdapter terdampakListRecViewAdapter;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terdampak);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        fbTambahTerdampak = findViewById(R.id.fbTambahTerdampak);
        recViewTerdampakList = findViewById(R.id.recViewTerdampakList);

        fbTambahTerdampak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TerdampakActivity.this, TerdampakFormsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        terdampakListRecViewAdapter = new TerdampakListRecViewAdapter();
        terdampakListRecViewAdapter.setListener(this);
        recViewTerdampakList.setAdapter(terdampakListRecViewAdapter);
        recViewTerdampakList.setLayoutManager(new GridLayoutManager(this, 1));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AffectedShowAllAsIdResponse> call = apiInterface.getAffectedShowAllAsId("Bearer " + accessToken,
                id_disasters);
        call.enqueue(new Callback<AffectedShowAllAsIdResponse>() {
            @Override
            public void onResponse(Call<AffectedShowAllAsIdResponse> call, Response<AffectedShowAllAsIdResponse> response) {
                AffectedShowAllAsIdResponse affectedResponse = response.body();
                ArrayList<Penduduk> listData = new ArrayList<>();

                if (response.isSuccessful()){
                    List<DataItem> itemList = affectedResponse.getData();

                    for (DataItem item : itemList){
                        Penduduk terdampak = new Penduduk(
                                item.getName(),
                                item.getNik(),
                                item.getGender(),
                                item.getBirthdate()
                        );
                        listData.add(terdampak);
                    }
                }else{
                    StyleableToast.makeText(TerdampakActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error: " + response.toString());
                }
                terdampakListRecViewAdapter.setTerdampakList(listData);
            }

            @Override
            public void onFailure(Call<AffectedShowAllAsIdResponse> call, Throwable t) {
                StyleableToast.makeText(TerdampakActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
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
                terdampakListRecViewAdapter.getFilter().filter(newText);
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
                Intent intent = new Intent(TerdampakActivity.this, MenusActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onClick(View view, Penduduk penduduk) {
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        String nik = penduduk.getNik();
        String id_affected = nik + id_disasters;

        //longClick
        AlertDialog.Builder dialogPesan = new AlertDialog.Builder(this);
        dialogPesan.setMessage("Pilih Aksi");
        dialogPesan.setCancelable(true);
        dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteData(id_affected);
                dialogInterface.dismiss();
            }
        });

        dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //intent
                Intent intent = new Intent(TerdampakActivity.this, PendudukFormsEditActivity.class);
                intent.putExtra("nik", nik);
                startActivity(intent);
            }
        });

        dialogPesan.show();
    }

    private void deleteData(String id_affected) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AffectedStoreResponse> call = apiInterface.deleteAffected("Bearer " + accessToken, id_affected);
        call.enqueue(new Callback<AffectedStoreResponse>() {
            @Override
            public void onResponse(Call<AffectedStoreResponse> call, Response<AffectedStoreResponse> response) {
                if (response.isSuccessful()){
                    StyleableToast.makeText(TerdampakActivity.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT, R.style.success).show();
                }else{
                    StyleableToast.makeText(TerdampakActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<AffectedStoreResponse> call, Throwable t) {
                StyleableToast.makeText(TerdampakActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });

    }
}