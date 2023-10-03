package com.example.pelaporanbencana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class RincianKegiatanActivity extends AppCompatActivity {
    TextView tvIdKejadianRincian, tvTglKejadianRincian, tvWaktuKejadianRincian,
    deskripsiRincian, penyebabRincian, dampakRincian, cuacaRincian, upayaRincian, potensiRincian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_kegiatan);

        tvIdKejadianRincian = findViewById(R.id.tvIdKejadianRincian);
        tvTglKejadianRincian = findViewById(R.id.tvTglKejadianRincian);
        tvWaktuKejadianRincian = findViewById(R.id.tvWaktuKejadianRincian);
        deskripsiRincian = findViewById(R.id.deskripsiRincian);
        penyebabRincian = findViewById(R.id.penyebabRincian);
        dampakRincian = findViewById(R.id.dampakRincian);
        cuacaRincian = findViewById(R.id.cuacaRincian);
        upayaRincian = findViewById(R.id.upayaRincian);
        potensiRincian = findViewById(R.id.potensiRincian);

        Intent intent = getIntent();
        String id_disasters = intent.getStringExtra("id_disasters");
        String disasters_date = intent.getStringExtra("disasters_date");
        String disasters_time = intent.getStringExtra("disasters_time");
        String disasters_desc = intent.getStringExtra("disasters_desc");
        String disasters_causes = intent.getStringExtra("disasters_causes");
        String disasters_impact = intent.getStringExtra("disasters_impact");
        String weather_conditions = intent.getStringExtra("weather_conditions");
        String disasters_potential = intent.getStringExtra("disasters_potential");
        String disasters_effort = intent.getStringExtra("disasters_effort");

        tvIdKejadianRincian.setText(id_disasters);
        tvTglKejadianRincian.setText(disasters_date);
        tvWaktuKejadianRincian.setText(disasters_time);
        deskripsiRincian.setText(disasters_desc);
        penyebabRincian.setText(disasters_causes);
        dampakRincian.setText(disasters_impact);
        cuacaRincian.setText(weather_conditions);
        upayaRincian.setText(disasters_effort);
        potensiRincian.setText(disasters_potential);
    }
}