package com.example.pelaporanbencana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pelaporanbencana.adapter.KerusakanRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.DamageShowall.DamageShowallResponse;
import com.example.pelaporanbencana.model.DamageShowall.DataItem;
import com.example.pelaporanbencana.model.DamageStore.DamageStoreResponse;
import com.example.pelaporanbencana.model.Kerusakan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KerusakanActivity extends AppCompatActivity implements KerusakanRecViewAdapter.KerusakanClickListener{

    private RecyclerView KerusakanRecView;
    KerusakanRecViewAdapter kerusakanRecViewAdapter;
    FloatingActionButton fbTambahKerusakan;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerusakan);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        Toast.makeText(this, accessToken + id_disasters, Toast.LENGTH_SHORT).show();
        Log.d("dskd", "token : " + accessToken);
        KerusakanRecView = findViewById(R.id.KerusakanRecView);
        fbTambahKerusakan = findViewById(R.id.fbTambahKerusakan);

        kerusakanRecViewAdapter = new KerusakanRecViewAdapter();
        kerusakanRecViewAdapter.setListener(this);
        KerusakanRecView.setAdapter(kerusakanRecViewAdapter);
        KerusakanRecView.setLayoutManager(new GridLayoutManager(this,1));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DamageShowallResponse> call = apiInterface.getDamageShowAll("Bearer " + accessToken, id_disasters);
        call.enqueue(new Callback<DamageShowallResponse>() {
            @Override
            public void onResponse(Call<DamageShowallResponse> call, Response<DamageShowallResponse> response) {
                DamageShowallResponse damageResponse = response.body();
                ArrayList<Kerusakan> list = new ArrayList<>();

//                if (damageResponse != null) {
                    if (damageResponse.isSuccess()) {
                        List<DataItem> itemList = damageResponse.getData();

                        for (DataItem item : itemList) {
                            Kerusakan kerusakan = new Kerusakan(
                                    item.getIdDisasters(),
                                    item.getIdDamageCategory(),
                                    item.getCategory(),
                                    item.getDamageTypes(),
                                    item.getDamageUnits(),
                                    item.getDisastersVillage(),
                                    item.getUrbanVillageName(),
                                    item.getSubDistrictName(),
                                    item.getDamageAmount(),
                                    item.getIdDamages(),
                                    item.getDamageName()
                            );

                            list.add(kerusakan);

                        }
                    }else{
                        StyleableToast.makeText(KerusakanActivity.this, "Gagal dapat data" + accessToken, Toast.LENGTH_SHORT, R.style.error).show();
                        Log.d("retrofit", "error: " + response.toString());
                    }
//                }else{
//                    StyleableToast.makeText(KerusakanActivity.this, "Gagal dapat data" + accessToken, Toast.LENGTH_SHORT, R.style.error).show();
//                }
                kerusakanRecViewAdapter.setKerusakans(list);
            }

            @Override
            public void onFailure(Call<DamageShowallResponse> call, Throwable t) {
                Toast.makeText(KerusakanActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });


        fbTambahKerusakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(KerusakanActivity.this, "" + id_disasters, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(KerusakanActivity.this, KerusakanFormsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onClick(View view, Kerusakan kerusakan) {
        //delete
        String id_damage_category = kerusakan.getId_damage_category();
        String id_disasters = kerusakan.getId_disasters();
        String damage_types = kerusakan.getDamage_types();
        String id_damages = String.valueOf(kerusakan.getId_damages());

        String id_damage = id_damages + id_damage_category + id_disasters + damage_types;
        String damage_unit = kerusakan.getDamage_units();
        int damage_amount = kerusakan.getDamage_amount();
        String category = kerusakan.getCategory();

        //alert ubah dan hapus
        AlertDialog.Builder dialogPesan = new AlertDialog.Builder(this);
        dialogPesan.setMessage("Pilih Aksi");
        dialogPesan.setCancelable(true);
        dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteData(id_damage);
                dialogInterface.dismiss();
//                Toast.makeText(KerusakanActivity.this, id_damage, Toast.LENGTH_SHORT).show();
            }
        });

        dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(KerusakanActivity.this, KerusakanFormsEditActivity.class);
                intent.putExtra("id_damage", id_damage);
                intent.putExtra("id_damage_category", id_damage_category);
                intent.putExtra("damage_unit", damage_unit);
                intent.putExtra("damage_amount", damage_amount);
                intent.putExtra("damage_types", damage_types);
                intent.putExtra("category", category);
                startActivity(intent);
                finish();
            }
        });

        dialogPesan.show();

    }

    public void deleteData(String id_damage){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DamageStoreResponse> call1 = apiInterface.deleteDamage("Bearer " + accessToken, id_damage);
        call1.enqueue(new Callback<DamageStoreResponse>() {
            @Override
            public void onResponse(Call<DamageStoreResponse> call, Response<DamageStoreResponse> response) {
                if (response.isSuccessful()){
                    StyleableToast.makeText(KerusakanActivity.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT, R.style.success).show();
                }else {
                    StyleableToast.makeText(KerusakanActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<DamageStoreResponse> call, Throwable t) {
                StyleableToast.makeText(KerusakanActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
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
                kerusakanRecViewAdapter.getFilter().filter(newText);
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
                Intent intent = new Intent(KerusakanActivity.this, MenusActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }



}