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

import com.example.pelaporanbencana.adapter.RelawanRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.Relawan;
import com.example.pelaporanbencana.model.ResourcesStore.ResourcesStoreResponse;
import com.example.pelaporanbencana.model.VolunteerShowall.DataItem;
import com.example.pelaporanbencana.model.VolunteerShowall.VolunteerShowallResponse;
import com.example.pelaporanbencana.model.VolunteerStore.VolunteerStoreResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RelawanActivity extends AppCompatActivity implements RelawanRecViewAdapter.RelawanClickOnListener {
    RecyclerView recViewFormsRelawan;
    RelawanRecViewAdapter relawanRecViewAdapter;
    ApiInterface apiInterface;
    FloatingActionButton fbAddRelawan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relawan);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        Intent intent = getIntent();
        String id_volunteer_org = intent.getStringExtra("idVolunteerOrg");

        fbAddRelawan = findViewById(R.id.fbAddRelawan);
        recViewFormsRelawan = findViewById(R.id.recViewFormsRelawan);
        relawanRecViewAdapter = new RelawanRecViewAdapter();
        relawanRecViewAdapter.setListener(this);
        recViewFormsRelawan.setAdapter(relawanRecViewAdapter);
        recViewFormsRelawan.setLayoutManager(new GridLayoutManager(this,1));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<VolunteerShowallResponse> call = apiInterface.getVolunteer("Bearer " + accessToken,
                id_disasters, id_volunteer_org);
        call.enqueue(new Callback<VolunteerShowallResponse>() {
            @Override
            public void onResponse(Call<VolunteerShowallResponse> call, Response<VolunteerShowallResponse> response) {
                VolunteerShowallResponse volunteerResponse = response.body();
                ArrayList<Relawan> listData = new ArrayList<>();

                if (volunteerResponse.isSuccess()){
                    List<DataItem> itemList = volunteerResponse.getData();
                    for (DataItem item : itemList){
                        Relawan relawan = new Relawan(
                                item.getIdVolunteers(),
                                item.getVolunteersName(),
                                item.getVolunteersBirthdate(),
                                item.getVolunteersGender(),
                                item.getVolunteersSkill()
                        );
                        listData.add(relawan);
                    }
                }else {
                    StyleableToast.makeText(RelawanActivity.this,"Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
                relawanRecViewAdapter.setRelawanArrayList(listData);
            }

            @Override
            public void onFailure(Call<VolunteerShowallResponse> call, Throwable t) {
                StyleableToast.makeText(RelawanActivity.this,"Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });

        fbAddRelawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(RelawanActivity.this, RelawanFormsActivity.class);
                intent1.putExtra("idVolunteerOrg", id_volunteer_org);
                startActivity(intent1);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        MenuItem searchitemR = menu.findItem(R.id.actionsearch);

        SearchView searchViewR = (SearchView) searchitemR.getActionView();
        searchViewR.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchViewR.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                relawanRecViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onClick(View view, Relawan relawan) {
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        String id_volunteer = String.valueOf(relawan.getId_volunteers());
        String id_dv = id_volunteer + id_disasters;

        Intent intent = getIntent();
        String id_volunteer_org = intent.getStringExtra("idVolunteerOrg");

        //longClick
        AlertDialog.Builder dialogPesan = new AlertDialog.Builder(this);
        dialogPesan.setMessage("Pilih Aksi");
        dialogPesan.setCancelable(true);
        dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteData(id_dv);
                dialogInterface.dismiss();
            }
        });

        dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(RelawanActivity.this, RelawanFormsEditActivity.class);
                intent.putExtra("id_volunteer", id_volunteer);
                intent.putExtra("id_volunteer_org", id_volunteer_org);
                startActivity(intent);
            }
        });

        dialogPesan.show();
    }

    private void deleteData(String id_dv) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<VolunteerStoreResponse> call = apiInterface.deleteVolunteer("Bearer " + accessToken, id_dv);
        call.enqueue(new Callback<VolunteerStoreResponse>() {
            @Override
            public void onResponse(Call<VolunteerStoreResponse> call, Response<VolunteerStoreResponse> response) {
                if (response.isSuccessful()){
                    StyleableToast.makeText(RelawanActivity.this, "Berhasil Hapus Data", Toast.LENGTH_SHORT, R.style.success).show();
                    Log.d("retrofit", "error " + response.toString() + id_dv);
                }else{
                    StyleableToast.makeText(RelawanActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<VolunteerStoreResponse> call, Throwable t) {
                StyleableToast.makeText(RelawanActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });
    }

//    @Override
//    public void OnItemChangeListener(int position, Relawan relawan) {
//        try {
//            relawanArrayList.set(position, relawan);
//        }catch (Exception e){
//
//        }
//    }
}