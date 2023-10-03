package com.example.pelaporanbencana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class KejadianForms2Activity extends AppCompatActivity {
    private Button btnKej2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kejadian_forms2);

        btnKej2 = findViewById(R.id.btnKej2);

        btnKej2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KejadianForms2Activity.this, MenusActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}