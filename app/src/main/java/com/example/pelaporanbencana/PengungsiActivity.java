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

import com.example.pelaporanbencana.adapter.PengungsiListRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.AffectedStore.AffectedStoreResponse;
import com.example.pelaporanbencana.model.EvacueeShowall.DataItem;
import com.example.pelaporanbencana.model.EvacueeShowall.EvacueeShowallResponse;
import com.example.pelaporanbencana.model.EvacueeStore.EvacueeStoreResponse;
import com.example.pelaporanbencana.model.Penduduk;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengungsiActivity extends AppCompatActivity implements PengungsiListRecViewAdapter.PengungsiClickListener {
    FloatingActionButton fbTambahPengungsi;
    RecyclerView recViewPengungsiList;
    PengungsiListRecViewAdapter pengungsiListRecViewAdapter;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengungsi);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(
                "MyPref", Context.MODE_PRIVATE
        );
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences(
                "MyPref1", Context.MODE_PRIVATE
        );
        String id_disasters = pref1.getString("id_disasters", "");

        Intent intent = getIntent();
        String kodeShelter = intent.getStringExtra("kode_shelter");

//        Toast.makeText(this, id_disasters+" "+kodeShelter, Toast.LENGTH_SHORT).show();

        fbTambahPengungsi = findViewById(R.id.fbTambahPengungsi);

        fbTambahPengungsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PengungsiActivity.this, PengungsisFormsActivity.class);
                intent.putExtra("kode_shelter", kodeShelter);
                startActivity(intent);
                finish();
            }
        });

        recViewPengungsiList = findViewById(R.id.recViewPengungsiList);
        pengungsiListRecViewAdapter = new PengungsiListRecViewAdapter();
        pengungsiListRecViewAdapter.setListener(this);
        recViewPengungsiList.setAdapter(pengungsiListRecViewAdapter);
        recViewPengungsiList.setLayoutManager(new GridLayoutManager(this, 1));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<EvacueeShowallResponse> call = apiInterface.getEvacueeShowall("Bearer " + accessToken, id_disasters, kodeShelter);
        call.enqueue(new Callback<EvacueeShowallResponse>() {
            @Override
            public void onResponse(Call<EvacueeShowallResponse> call, Response<EvacueeShowallResponse> response) {
                EvacueeShowallResponse evacueeShowallResponse = response.body();
                ArrayList<Penduduk> listData = new ArrayList<>();

                if (evacueeShowallResponse.isSuccess()){
                    List<DataItem> itemList = evacueeShowallResponse.getData();

                    for (DataItem item : itemList){
                        Penduduk penduduk = new Penduduk(
                                item.getName(),
                                item.getNik(),
                                item.getGender(),
                                item.getBirthdate()
                        );
                        listData.add(penduduk);
                    }

                    Log.d("retrofit", "success"+response.toString());
                }else {
                    StyleableToast.makeText(PengungsiActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error" + response.toString());
                }
                pengungsiListRecViewAdapter.setPengungsiList(listData);
            }

            @Override
            public void onFailure(Call<EvacueeShowallResponse> call, Throwable t) {
                Toast.makeText(PengungsiActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
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
                pengungsiListRecViewAdapter.getFilter().filter(newText);
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
                Intent intent = new Intent(PengungsiActivity.this, MenusActivity.class);
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
        String id_evacuee = nik + id_disasters;

        //longClick
        AlertDialog.Builder dialogPesan = new AlertDialog.Builder(this);
        dialogPesan.setMessage("Pilih Aksi");
        dialogPesan.setCancelable(true);
        dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteData(id_evacuee);
                dialogInterface.dismiss();
            }
        });

        dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //intent
                Intent intent = new Intent(PengungsiActivity.this, PendudukFormsEditActivity.class);
                intent.putExtra("nik", nik);
                startActivity(intent);
            }
        });

        dialogPesan.show();
    }

    private void deleteData(String id_evacuee) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<EvacueeStoreResponse> call = apiInterface.deleteEvacuee("Bearer " + accessToken, id_evacuee);
        call.enqueue(new Callback<EvacueeStoreResponse>() {
            @Override
            public void onResponse(Call<EvacueeStoreResponse> call, Response<EvacueeStoreResponse> response) {
                if (response.isSuccessful()){
                    StyleableToast.makeText(PengungsiActivity.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT, R.style.success).show();
                }else{
                    StyleableToast.makeText(PengungsiActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<EvacueeStoreResponse> call, Throwable t) {
                StyleableToast.makeText(PengungsiActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });
    }
}