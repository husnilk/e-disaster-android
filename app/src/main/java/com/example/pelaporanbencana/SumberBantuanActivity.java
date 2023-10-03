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

import com.example.pelaporanbencana.adapter.BantuanRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.Bantuan;
import com.example.pelaporanbencana.model.SocialAssistanceShowall.DataItem;
import com.example.pelaporanbencana.model.SocialAssistanceShowall.SocAssitShowAllResponse;
import com.example.pelaporanbencana.model.SocialAssistanceStore.SocAssitStoreResponse;
import com.example.pelaporanbencana.model.VictimStore.VictimStoreResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SumberBantuanActivity extends AppCompatActivity implements BantuanRecViewAdapter.BantuanClickListener {

    private RecyclerView recViewBantuan;
    BantuanRecViewAdapter bantuanRecViewAdapter;
    FloatingActionButton fbTambahBantuan;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sumber_bantuan);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        fbTambahBantuan = findViewById(R.id.fbTambahBantuan);
        recViewBantuan = findViewById(R.id.recViewBantuan);

        bantuanRecViewAdapter = new BantuanRecViewAdapter();
        bantuanRecViewAdapter.setListener(this);
        recViewBantuan.setAdapter(bantuanRecViewAdapter);
        recViewBantuan.setLayoutManager(new GridLayoutManager(this, 1));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SocAssitShowAllResponse> call = apiInterface.getSocialAssistance("Bearer " + accessToken, id_disasters);
        call.enqueue(new Callback<SocAssitShowAllResponse>() {
            @Override
            public void onResponse(Call<SocAssitShowAllResponse> call, Response<SocAssitShowAllResponse> response) {
                SocAssitShowAllResponse allResponse = response.body();
                ArrayList<Bantuan> listData = new ArrayList<>();

                if (allResponse != null && allResponse.isSuccess()){
                    List<DataItem> itemList = allResponse.getData();

                    for (DataItem item : itemList){
                        Bantuan bantuan = new Bantuan(
                                item.getIdSaTypes(),
                                item.getDonor(),
                                item.getSaTypesName(),
                                item.getSocialAssistanceUnit(),
                                item.getSocialAssistanceAmount(),
                                item.getBatch(),
                                item.getDateReceived()
                        );
                        listData.add(bantuan);
                    }
                }else {
                    StyleableToast.makeText(SumberBantuanActivity.this, "Gagal dapat Data", Toast.LENGTH_SHORT, R.style.success).show();
                    Log.d("retrofit", "error" + response.toString());
                }
                bantuanRecViewAdapter.setBantuans(listData);
            }

            @Override
            public void onFailure(Call<SocAssitShowAllResponse> call, Throwable t) {
                StyleableToast.makeText(SumberBantuanActivity.this, "Gagal dapat Data", Toast.LENGTH_SHORT, R.style.success).show();
            }
        });

        fbTambahBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SumberBantuanActivity.this, SumberBantuanFormsActivity.class);
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
                bantuanRecViewAdapter.getFilter().filter(newText);
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
                Intent intent = new Intent(SumberBantuanActivity.this, MenusActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onClick(View view, Bantuan bantuan) {
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        String id_sa_types = String.valueOf(bantuan.getId_sa_types());
        String batch = String.valueOf(bantuan.getBatch());
        String id_sa =  id_sa_types + id_disasters + batch;

        //longClick
        AlertDialog.Builder dialogPesan = new AlertDialog.Builder(this);
        dialogPesan.setMessage("Pilih Aksi");
        dialogPesan.setCancelable(true);
        dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteData(id_sa);
                dialogInterface.dismiss();
            }
        });

        dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(SumberBantuanActivity.this, SumberBantuanFormsEditActivity.class);
                intent.putExtra("id_sa_types", id_sa_types);
                intent.putExtra("batch", batch);
                startActivity(intent);
            }
        });

        dialogPesan.show();
    }

    private void deleteData(String id_sa) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SocAssitStoreResponse> call = apiInterface.deleteSocialAssistance("Bearer " + accessToken, id_sa);
        call.enqueue(new Callback<SocAssitStoreResponse>() {
            @Override
            public void onResponse(Call<SocAssitStoreResponse> call, Response<SocAssitStoreResponse> response) {
                if (response.isSuccessful()){
                    StyleableToast.makeText(SumberBantuanActivity.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT, R.style.success).show();
                    Log.d("retrofit", "error " + response.toString());
                }else{
                    StyleableToast.makeText(SumberBantuanActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<SocAssitStoreResponse> call, Throwable t) {
                StyleableToast.makeText(SumberBantuanActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });
    }
}