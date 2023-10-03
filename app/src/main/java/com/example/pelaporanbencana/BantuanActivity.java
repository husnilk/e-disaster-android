package com.example.pelaporanbencana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class BantuanActivity extends AppCompatActivity {

    LinearLayout layoutSumberBantuan, layoutBantuanTersalurkan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);

        layoutSumberBantuan = findViewById(R.id.layoutSumberBantuan);
        layoutBantuanTersalurkan = findViewById(R.id.layoutBantuanTersalurkan);

        layoutSumberBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BantuanActivity.this, SumberBantuanActivity.class);
                startActivity(intent);
            }
        });

        layoutBantuanTersalurkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BantuanActivity.this, BantuanTersalurkanActivity.class);
                startActivity(intent);
            }
        });


    }
}