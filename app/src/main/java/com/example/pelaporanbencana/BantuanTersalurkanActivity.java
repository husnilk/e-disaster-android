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

//import com.example.pelaporanbencana.adapter.BantuanTRecViewAdapter;
import com.example.pelaporanbencana.adapter.BantuanTRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.BantuanT;
import com.example.pelaporanbencana.model.SocAssistDistributed.SocAssistDistributedResponse;
import com.example.pelaporanbencana.model.SocAssistDistributedShowAll.DataItem;
import com.example.pelaporanbencana.model.SocAssistDistributedShowAll.SocAssistDistributedShowAllResponse;
import com.example.pelaporanbencana.model.SocialAssistanceStore.SocAssitStoreResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BantuanTersalurkanActivity extends AppCompatActivity implements BantuanTRecViewAdapter.BantuanTClickListener{
    RecyclerView recViewBantuanT;
    BantuanTRecViewAdapter bantuanTRecViewAdapter;
    FloatingActionButton fbTambahBantuanT;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan_tersalurkan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(
                "MyPref", Context.MODE_PRIVATE
        );
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences(
                "MyPref1", Context.MODE_PRIVATE
        );
        String id_disasters = pref1.getString("id_disasters", "");

        recViewBantuanT = findViewById(R.id.recViewBantuanT);
        fbTambahBantuanT = findViewById(R.id.fbTambahBantuanT);

        bantuanTRecViewAdapter = new BantuanTRecViewAdapter();
        bantuanTRecViewAdapter.setListener(this);
        recViewBantuanT.setAdapter(bantuanTRecViewAdapter);
        recViewBantuanT.setLayoutManager(new GridLayoutManager(this, 1));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SocAssistDistributedShowAllResponse> call = apiInterface.getSocialAssistDistributed("Bearer " + accessToken, id_disasters);
        call.enqueue(new Callback<SocAssistDistributedShowAllResponse>() {
            @Override
            public void onResponse(Call<SocAssistDistributedShowAllResponse> call, Response<SocAssistDistributedShowAllResponse> response) {
                SocAssistDistributedShowAllResponse showResponse = response.body();
                ArrayList<BantuanT> listData = new ArrayList<>();

                if (showResponse != null || showResponse.isSuccess()){
                    List<com.example.pelaporanbencana.model.SocAssistDistributedShowAll.DataItem> itemList = showResponse.getData();

                    for (DataItem item : itemList){
                        BantuanT bantuanT = new BantuanT(
                                item.getId_sa_types(),
                                item.getSaTypesName(),
                                item.getRecipient(),
                                item.getDateSent(),
                                item.getSaDistributedUnits(),
                                item.getSaDistributedAmount(),
                                item.getBatch()
                        );

                        listData.add(bantuanT);
                    }
                }else {
                    StyleableToast.makeText(BantuanTersalurkanActivity.this, "Gagal dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error" + response.toString());
                }

                bantuanTRecViewAdapter.setBantuanTArrayList(listData);

            }

            @Override
            public void onFailure(Call<SocAssistDistributedShowAllResponse> call, Throwable t) {
                StyleableToast.makeText(BantuanTersalurkanActivity.this, "Gagal", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });


        fbTambahBantuanT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BantuanTersalurkanActivity.this, BantuanTersalurkanFormsActivity.class);
                startActivity(intent);
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
//                bantuanTRecViewAdapter.getFilter().filter(newText);
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
                Intent intent = new Intent(BantuanTersalurkanActivity.this, MenusActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onClick(View view, BantuanT bantuanT) {
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        String id_sa_types = String.valueOf(bantuanT.getId_sa_types());
        String batch = String.valueOf(bantuanT.getBatch());
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
                Intent intent = new Intent(BantuanTersalurkanActivity.this, BantuanTersalurkanFormsEditActivity.class);
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
        Call<SocAssistDistributedResponse> call = apiInterface.deleteSaDistributed("Bearer " + accessToken, id_sa);
        call.enqueue(new Callback<SocAssistDistributedResponse>() {
            @Override
            public void onResponse(Call<SocAssistDistributedResponse> call, Response<SocAssistDistributedResponse> response) {
                if (response.isSuccessful()){
                    StyleableToast.makeText(BantuanTersalurkanActivity.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT, R.style.success).show();
                    Log.d("retrofit", "error " + response.toString());
                }else{
                    StyleableToast.makeText(BantuanTersalurkanActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<SocAssistDistributedResponse> call, Throwable t) {
                StyleableToast.makeText(BantuanTersalurkanActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });
    }
}