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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.ShelterStore.ShelterStoreResponse;
import com.example.pelaporanbencana.model.Spinner.JenisHunian;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LokasiPengungsiFormsActivity extends AppCompatActivity {

    Spinner spinnerJenisHunian;
    TextInputLayout tiJenisHunianTambahan;
    TextInputEditText jmlKapasitas, kodeLokasiPengungsi;
    ImageButton btnPlus, btnMinus;
    Button btnTambahLokasi;
    LinearLayout latlongPengungsiLayout;
    TextView latLocShelter, longLocShelte;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_pengungsi_forms);

        //token
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        //kode_kejadian
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        //lat, long loc pengungsi
        Intent intent = getIntent();
        String latloc = intent.getStringExtra("latLocPengungsi");
        String longloc = intent.getStringExtra("longLocPengungsi");
        String detLoc = intent.getStringExtra("detLocPengungsi");
        String nameLoc = intent.getStringExtra("nameLocPengungsi");

        Toast.makeText(this, accessToken, Toast.LENGTH_SHORT).show();

        kodeLokasiPengungsi = findViewById(R.id.kodeLokasiPengungsi);
        spinnerJenisHunian = findViewById(R.id.spinnerJenisHunian);
        tiJenisHunianTambahan = findViewById(R.id.tiJenisHunianTambahan);
        jmlKapasitas = findViewById(R.id.jmlKapasitas);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnTambahLokasi = findViewById(R.id.btnTambahLokasi);

        spinnerJenisHunian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getSelectedItemId() == adapterView.getItemIdAtPosition(5)){
                    tiJenisHunianTambahan.setVisibility(View.VISIBLE);
                }else{
                    tiJenisHunianTambahan.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jmlKapasitas.length() == 0){
                    int a1 = 0;
                    a1 = a1 + 1;
                    jmlKapasitas.setText(String.valueOf(a1));

                }else if(jmlKapasitas.length() != 0){
                    int a1 = Integer.parseInt(String.valueOf(jmlKapasitas.getText()));
                    a1 = a1 + 1;
                    jmlKapasitas.setText(String.valueOf(a1));

                }
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jmlKapasitas.length() == 0){
                    Toast.makeText(LokasiPengungsiFormsActivity.this, "Jumlah Kapasitas : Kosong", Toast.LENGTH_SHORT).show();
                }else if (jmlKapasitas.length() != 0){
                    int a1 = Integer.parseInt(String.valueOf(jmlKapasitas.getText()));
                    if (a1 <= 0){
                        jmlKapasitas.setText("0");
                    }else{
                        a1 = a1 - 1;
                        jmlKapasitas.setText(String.valueOf(a1));
                    }
                }
            }
        });

        //spinner Jenis Hunian
        List<JenisHunian> hunianList = new ArrayList<>();
        JenisHunian hunian1 = new JenisHunian("Rumah Ibadah","Rumah Ibadah");
        hunianList.add(hunian1);

        JenisHunian hunian2 = new JenisHunian("Sekolah","Sekolah");
        hunianList.add(hunian2);

        JenisHunian hunian3 = new JenisHunian("Tenda Darurat","Tenda Darurat");
        hunianList.add(hunian3);

        ArrayAdapter<JenisHunian> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, hunianList);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        spinnerJenisHunian.setAdapter(adapter);
        Toast.makeText(this, longloc + latloc + nameLoc + detLoc, Toast.LENGTH_SHORT).show();

        btnTambahLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String kode_lokasi = kodeLokasiPengungsi.getText().toString();

                String lat_loc = latloc;
                String long_loc = longloc;
                String name_loc = nameLoc;
                String det_loc = detLoc;

                int capacity = Integer.parseInt(jmlKapasitas.getText().toString());

                JenisHunian jenisHunian = (JenisHunian) spinnerJenisHunian.getSelectedItem();
                String jenis_hunian = displayJenisHunian(jenisHunian);

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<ShelterStoreResponse> call = apiInterface.setShelter("Bearer " + accessToken,
                        kode_lokasi, long_loc, lat_loc, capacity, jenis_hunian, name_loc, det_loc);
                call.enqueue(new Callback<ShelterStoreResponse>() {
                    @Override
                    public void onResponse(Call<ShelterStoreResponse> call, Response<ShelterStoreResponse> response) {
                        if (response.body()!=null || response.isSuccessful()){
                            Toast.makeText(LokasiPengungsiFormsActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            Log.d("retrofit", "success :" + response.toString() + accessToken);

                            Intent intent = new Intent(LokasiPengungsiFormsActivity.this, LokasiPengungsiActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(LokasiPengungsiFormsActivity.this, "Gagal dapat data", Toast.LENGTH_SHORT).show();
                            Log.d("retrofit" , "error: " + response.toString());

                        }
                    }

                    @Override
                    public void onFailure(Call<ShelterStoreResponse> call, Throwable t) {
                        Toast.makeText(LokasiPengungsiFormsActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private String displayJenisHunian(JenisHunian jenisHunian) {
        String jenis_hunian = jenisHunian.getJenis_hunian();
        return jenis_hunian;
    }

}