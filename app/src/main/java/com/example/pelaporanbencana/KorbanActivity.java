package com.example.pelaporanbencana;

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

import com.example.pelaporanbencana.adapter.KorbanListRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.Korban;
import com.example.pelaporanbencana.model.VictimShowAllAsId.DataItem;
import com.example.pelaporanbencana.model.VictimShowAllAsId.VictimShowAllAsIdResponse;
import com.example.pelaporanbencana.model.VictimStore.VictimStoreResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KorbanActivity extends AppCompatActivity implements KorbanListRecViewAdapter.KorbanClickListener{

    RecyclerView recViewKorbanList;
    FloatingActionButton fbTambahKorban;
    KorbanListRecViewAdapter korbanListRecViewAdapter;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korban);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        fbTambahKorban = findViewById(R.id.fbTambahKorban);
        recViewKorbanList = findViewById(R.id.recViewKorbanList);

        fbTambahKorban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KorbanActivity.this, KorbanForms1Activity.class);
                startActivity(intent);
                finish();
            }
        });

        korbanListRecViewAdapter = new KorbanListRecViewAdapter();
        korbanListRecViewAdapter.setListener(this);
        recViewKorbanList.setAdapter(korbanListRecViewAdapter);
        recViewKorbanList.setLayoutManager(new GridLayoutManager(this, 1));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<VictimShowAllAsIdResponse> call = apiInterface.getVictimShowAllAsId("Bearer " + accessToken,
                id_disasters);
        call.enqueue(new Callback<VictimShowAllAsIdResponse>() {
            @Override
            public void onResponse(Call<VictimShowAllAsIdResponse> call, Response<VictimShowAllAsIdResponse> response) {
                VictimShowAllAsIdResponse idResponse = response.body();
                ArrayList<Korban> listData = new ArrayList<>();

                if (idResponse != null && idResponse.isSuccess()){
                    List<DataItem> itemList = idResponse.getData();

                    for (DataItem item : itemList){
                        Korban korban = new Korban(
                                item.getNik(),
                                item.getVictimsStatus(),
                                item.getGender(),
                                item.getName(),
                                item.getBirthdate(),
                                item.getHeir(),
                                item.getAddress()
                        );
                        listData.add(korban);
                    }
                }else {
                    StyleableToast.makeText(KorbanActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error: " + response.toString());
                }

                korbanListRecViewAdapter.setKorbanlist(listData);
            }

            @Override
            public void onFailure(Call<VictimShowAllAsIdResponse> call, Throwable t) {
                StyleableToast.makeText(KorbanActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
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
                korbanListRecViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onClick(View view, Korban korban) {
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        String nik = korban.getNik();
        String id_victims = nik + id_disasters;

        //longClick
        AlertDialog.Builder dialogPesan = new AlertDialog.Builder(this);
        dialogPesan.setMessage("Pilih Aksi");
        dialogPesan.setCancelable(true);
        dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteData(id_victims);
                dialogInterface.dismiss();
            }
        });

        dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //intent
                Intent intent = new Intent(KorbanActivity.this, KorbanFormsEditActivity.class);
                intent.putExtra("nik", korban.getNik());
                intent.putExtra("id_victims", id_victims);
                startActivity(intent);
            }
        });

        dialogPesan.show();

    }

    private void deleteData(String id_victims) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<VictimStoreResponse> call = apiInterface.deleteVictim("Bearer " + accessToken, id_victims);
        call.enqueue(new Callback<VictimStoreResponse>() {
            @Override
            public void onResponse(Call<VictimStoreResponse> call, Response<VictimStoreResponse> response) {
                if (response.isSuccessful()){
                    StyleableToast.makeText(KorbanActivity.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT, R.style.success).show();
                }else{
                    StyleableToast.makeText(KorbanActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<VictimStoreResponse> call, Throwable t) {
                StyleableToast.makeText(KorbanActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });
    }
}