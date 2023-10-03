package com.example.pelaporanbencana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.Spinner.StatusKorban;
import com.example.pelaporanbencana.model.Spinner.StatusMedis;
import com.example.pelaporanbencana.model.VictimStore.VictimStoreResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KorbanForms2Activity extends AppCompatActivity {

    Spinner spinnerStatusKorban, spinnerStatusMedis;
    TextInputLayout textInputKetTerkait;
    TextInputEditText ketTerkaitKorban;
    LinearLayout layoutStatusMedis;
    ApiInterface apiInterface;
    Button btnAddKorban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korban_forms2);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        Intent intent = getIntent();
        String nik_korban = intent.getStringExtra("nik");
        String nama_korban = intent.getStringExtra("nama");
        String ahli_waris_korban = intent.getStringExtra("ahli_waris");
        String alamat_korban = intent.getStringExtra("alamat");
        String gender_korban = intent.getStringExtra("gender");
        String tgl_lahir_korban = intent.getStringExtra("tgl_lahir");


        spinnerStatusKorban = findViewById(R.id.spinnerStatusKorban);
        spinnerStatusMedis = findViewById(R.id.spinnerStatusMedis);
        textInputKetTerkait = findViewById(R.id.textInputKetTerkait);
        layoutStatusMedis = findViewById(R.id.layoutStatusMedis);
        btnAddKorban = findViewById(R.id.btnAddKorban);
        ketTerkaitKorban = findViewById(R.id.ketTerkaitKorban);

        spinnerStatusKorban.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getSelectedItem() == adapterView.getItemAtPosition(0)){
                    layoutStatusMedis.setVisibility(View.GONE);
                    textInputKetTerkait.setVisibility(View.GONE);
                }else if (adapterView.getSelectedItem() == adapterView.getItemAtPosition(1)){
                    layoutStatusMedis.setVisibility(View.GONE);
                    textInputKetTerkait.setVisibility(View.GONE);
                }else if(adapterView.getSelectedItem() == adapterView.getItemAtPosition(2)){
                    layoutStatusMedis.setVisibility(View.GONE);
                    textInputKetTerkait.setVisibility(View.VISIBLE);
                }else if(adapterView.getSelectedItem() == adapterView.getItemAtPosition(3)){
                    layoutStatusMedis.setVisibility(View.VISIBLE);
                    textInputKetTerkait.setVisibility(View.VISIBLE);
                }else if(adapterView.getSelectedItem() == adapterView.getItemAtPosition(4)){
                    layoutStatusMedis.setVisibility(View.VISIBLE);
                    textInputKetTerkait.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //set list pada spinner status korban
        List<StatusKorban> statusKorbans = new ArrayList<>();
        StatusKorban statusKorban = new StatusKorban("0", "Pilih Status Korban");
        statusKorbans.add(statusKorban);
        StatusKorban statusKorban1 = new StatusKorban("1", "Meninggal");
        statusKorbans.add(statusKorban1);
        StatusKorban statusKorban2 = new StatusKorban("2", "Hilang");
        statusKorbans.add(statusKorban2);
        StatusKorban statusKorban3 = new StatusKorban("3", "Luka Ringan");
        statusKorbans.add(statusKorban3);
        StatusKorban statusKorban4 = new StatusKorban("4", "Luka Berat");
        statusKorbans.add(statusKorban4);

        ArrayAdapter<StatusKorban> adapterStatusKorban = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, statusKorbans);
        adapterStatusKorban.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerStatusKorban.setAdapter(adapterStatusKorban);

        //set list pada spinner status medis
        List<StatusMedis> statusMedisList = new ArrayList<>();
        StatusMedis statusMedis = new StatusMedis("0", "Pilih Status Medis");
        statusMedisList.add(statusMedis);
        StatusMedis statusMedis1 = new StatusMedis("1", "Dirujuk");
        statusMedisList.add(statusMedis1);
        StatusMedis statusMedis2 = new StatusMedis("2", "Dirawat");
        statusMedisList.add(statusMedis2);

        ArrayAdapter<StatusMedis> adapterStatusMedis = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, statusMedisList);
        adapterStatusMedis.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerStatusMedis.setAdapter(adapterStatusMedis);



        btnAddKorban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatusKorban statusKorbann = (StatusKorban) spinnerStatusKorban.getSelectedItem();
                String victim_status = displayStatusKorban(statusKorbann);

                StatusMedis statusMediss = (StatusMedis) spinnerStatusMedis.getSelectedItem();
                String medical_status = displayStatusMedis(statusMediss);

                String hospital = ketTerkaitKorban.getText().toString();
                String additional_info = ketTerkaitKorban.getText().toString();

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<VictimStoreResponse> call = apiInterface.setVictim("Bearer " + accessToken, nik_korban, id_disasters,
                        victim_status, medical_status, hospital, additional_info, nama_korban, alamat_korban, gender_korban, ahli_waris_korban, tgl_lahir_korban);
                call.enqueue(new Callback<VictimStoreResponse>() {
                    @Override
                    public void onResponse(Call<VictimStoreResponse> call, Response<VictimStoreResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(KorbanForms2Activity.this, "Berhasil Menambahkan Data", Toast.LENGTH_SHORT, R.style.success).show();
                            Log.d("retrofit", "success" + response.body());
                        }else {
                            StyleableToast.makeText(KorbanForms2Activity.this, "Data Sudah Ada", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error" + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<VictimStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(KorbanForms2Activity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });
            }
        });
    }

    private String displayStatusMedis(StatusMedis statusMediss) {
        String id = statusMediss.getId_status_medis();

        return id;
    }

    private String displayStatusKorban(StatusKorban statusKorbann) {
        String id = statusKorbann.getId_status_korban();
        String name = statusKorbann.getName_status_korban();

        return id;
    }
}