package com.example.pelaporanbencana;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.PeopleOneShow.PeopleOneShowResponse;
import com.example.pelaporanbencana.model.Spinner.StatusKorban;
import com.example.pelaporanbencana.model.Spinner.StatusMedis;
import com.example.pelaporanbencana.model.VictimShowa.VictimShowOneResponse;
import com.example.pelaporanbencana.model.VictimStore.VictimStoreResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KorbanFormsEdit2Activity extends AppCompatActivity {

    Spinner spinnerStatusKorban, spinnerStatusMedis;
    TextInputLayout textInputKetTerkait;
    TextInputEditText ketTerkaitKorban, lokasiHilang;
    LinearLayout layoutStatusMedis;
    ApiInterface apiInterface;
    Button btnAddKorban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korban_forms2_edit);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        Intent intent = getIntent();
        String nik_korban = intent.getStringExtra("nik");
        String id_victims = intent.getStringExtra("id_victims");


        spinnerStatusKorban = findViewById(R.id.spinnerStatusKorbanEdit);
        spinnerStatusMedis = findViewById(R.id.spinnerStatusMedisEdit);
        textInputKetTerkait = findViewById(R.id.textInputKetTerkaitEdit);
        layoutStatusMedis = findViewById(R.id.layoutStatusMedisEdit);
        btnAddKorban = findViewById(R.id.btnEdit2Korban);
        ketTerkaitKorban = findViewById(R.id.ketTerkaitKorbanEdit);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<VictimShowOneResponse> callPeople = apiInterface.getOneVictim("Bearer " + accessToken, nik_korban, id_disasters);
        callPeople.enqueue(new Callback<VictimShowOneResponse>() {
            @Override
            public void onResponse(Call<VictimShowOneResponse> call, Response<VictimShowOneResponse> response) {
                VictimShowOneResponse victimResponse = response.body();
                String name_status_korban,id_status_korban, id_status_medis,name_status_medis;

                if (victimResponse != null ? victimResponse.isSuccess() : false){
                    id_status_korban = victimResponse.getData().getVictimsStatus();
                    id_status_medis = victimResponse.getData().getMedicalStatus();

                    if (id_status_korban != null){
                        if (id_status_korban.equals("1")){
                            name_status_korban = "Meninggal";
                        }else if (id_status_korban.equals("2")){
                            name_status_korban = "Hilang";
                        }else if (id_status_korban.equals("3")){
                            name_status_korban = "Luka Ringan";
                        }else{
                            name_status_korban = "Luka Berat";
                        }
                    }else{
                        name_status_korban = "-";
                    }

                    StatusKorbanAddToList(id_status_korban, name_status_korban);

                    if (id_status_medis != null){
                        if (id_status_medis.equals("1")){
                            name_status_medis = "Dirujuk";
                        }else if(id_status_medis.equals("2")){
                            name_status_medis = "Dirawat";
                        }else{
                            name_status_medis = "None";
                        }
                    }else{
                        name_status_medis = "";
                    }

                    StatusMedisAddToList(id_status_medis, name_status_medis);

                    String info = victimResponse.getData().getAdditionalInfo();
                    String hospital = victimResponse.getData().getHospital();

                    if(info != null) {
                        ketTerkaitKorban.setText(info);
                    }else {
                        ketTerkaitKorban.setText(hospital);
                    }

                }else{
                    StyleableToast.makeText(KorbanFormsEdit2Activity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
            }


            @Override
            public void onFailure(Call<VictimShowOneResponse> call, Throwable t) {
                StyleableToast.makeText(KorbanFormsEdit2Activity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });


        spinnerStatusKorban.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getSelectedItem().equals("0")){
                    layoutStatusMedis.setVisibility(View.GONE);
                    textInputKetTerkait.setVisibility(View.GONE);
                }else if (adapterView.getSelectedItem().equals("1")){
                    layoutStatusMedis.setVisibility(View.GONE);
                    textInputKetTerkait.setVisibility(View.GONE);
                }else if(adapterView.getSelectedItem().equals("2")){
                    layoutStatusMedis.setVisibility(View.GONE);
                    textInputKetTerkait.setVisibility(View.VISIBLE);
                }else if(adapterView.getSelectedItem().equals("3")){
                    layoutStatusMedis.setVisibility(View.VISIBLE);
                    textInputKetTerkait.setVisibility(View.VISIBLE);
                }else if(adapterView.getSelectedItem().equals("4")){
                    layoutStatusMedis.setVisibility(View.VISIBLE);
                    textInputKetTerkait.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnAddKorban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatusKorban statusKorbann = (StatusKorban) spinnerStatusKorban.getSelectedItem();
                String victim_status = displayStatusKorban(statusKorbann);

                StatusMedis statusMediss = (StatusMedis) spinnerStatusMedis.getSelectedItem();
                String medical_status = displayStatusMedis(statusMediss);

                String hospital = ketTerkaitKorban.getText().toString();
                String additional_info = lokasiHilang.getText().toString();

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<VictimStoreResponse> call = apiInterface.updateVictim("Bearer " + accessToken, id_victims, nik_korban, id_disasters,
                        victim_status, medical_status, hospital, additional_info);
                call.enqueue(new Callback<VictimStoreResponse>() {
                    @Override
                    public void onResponse(Call<VictimStoreResponse> call, Response<VictimStoreResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(KorbanFormsEdit2Activity.this, "Berhasil Mengubah Status", Toast.LENGTH_SHORT, R.style.success).show();
                            Log.d("retrofit", "success" + response.body());
                        }else {
                            StyleableToast.makeText(KorbanFormsEdit2Activity.this, "Gagal Mengubah Status", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error" + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<VictimStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(KorbanFormsEdit2Activity.this, "Gagal Mengubah Status", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });
            }
        });
    }

    private void StatusMedisAddToList(String id_status_medis, String name_status_medis) {
        //set list pada spinner status medis
        List<StatusMedis> statusMedisList = new ArrayList<>();
        StatusMedis statusMedis = new StatusMedis(id_status_medis, name_status_medis + " (pilihan saat ini)");
        statusMedisList.add(statusMedis);
        StatusMedis statusMedis0 = new StatusMedis("0", "None");
        statusMedisList.add(statusMedis0);
        StatusMedis statusMedis1 = new StatusMedis("1", "Dirujuk");
        statusMedisList.add(statusMedis1);
        StatusMedis statusMedis2 = new StatusMedis("2", "Dirawat");
        statusMedisList.add(statusMedis2);

        ArrayAdapter<StatusMedis> adapterStatusMedis = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, statusMedisList);
        adapterStatusMedis.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerStatusMedis.setAdapter(adapterStatusMedis);
    }

    private void StatusKorbanAddToList(String id_status_korban, String name_status_korban) {
        //set list pada spinner status korban
        List<StatusKorban> statusKorbans = new ArrayList<>();
        StatusKorban statusKorban = new StatusKorban(id_status_korban, name_status_korban + " (pilihan saat ini)");
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