package com.example.pelaporanbencana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class PendudukActivity extends AppCompatActivity {

    LinearLayout layoutKorban, layoutTerdampak, layoutPengungsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penduduk);

        layoutKorban = findViewById(R.id.layoutKorban);
       // layoutPengungsi = findViewById(R.id.layoutPengungsi);
        layoutTerdampak = findViewById(R.id.layoutTerdampak);

        layoutKorban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PendudukActivity.this, KorbanActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        layoutPengungsi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(PendudukActivity.this, LokasiPengungsiActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        layoutTerdampak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PendudukActivity.this, TerdampakActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}