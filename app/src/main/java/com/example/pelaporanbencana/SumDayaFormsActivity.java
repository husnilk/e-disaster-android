package com.example.pelaporanbencana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.ResourcesStore.ResourcesStoreResponse;
import com.example.pelaporanbencana.model.Spinner.SumberDaya;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SumDayaFormsActivity extends AppCompatActivity {

    private Spinner spinnercth;
    private TextInputLayout textInputLayoutSumDa;
    TextInputEditText jmlTersedia, jmlKekurangan, jmlDiperlukan, txtSumDa;
    ImageButton btnPlus1, btnPlus2,  btnMinus1, btnMinus2;
    Button btnTambahSumda;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_daya_forms);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        spinnercth = findViewById(R.id.spinnercth);
        textInputLayoutSumDa = findViewById(R.id.textInputLayoutSumDa);
        jmlDiperlukan = findViewById(R.id.jmlDiperlukan);
        jmlKekurangan = findViewById(R.id.jmlKekurangan);
        jmlTersedia = findViewById(R.id.jmlTersedia);
        btnPlus1 = findViewById(R.id.btnPlus1);
        btnPlus2 = findViewById(R.id.btnPlus2);
        btnMinus1 = findViewById(R.id.btnMinus1);
        btnMinus2 = findViewById(R.id.btnMinus2);
        btnTambahSumda = findViewById(R.id.btnTambahSumda);
        txtSumDa = findViewById(R.id.txtSumDa);

        jmlTersedia.setText("0");
        jmlTersedia.addTextChangedListener(KekuranganTextWatcher);
        jmlDiperlukan.setText("0");
        jmlDiperlukan.addTextChangedListener(KekuranganTextWatcher);
        jmlKekurangan.setText("0");

        btnPlus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jmlTersedia.length() == 0){
                    int a1 = 0;
                    a1 = a1 + 1;
                    jmlTersedia.setText(String.valueOf(a1));

                }else if(jmlTersedia.length() != 0){
                    int a1 = Integer.parseInt(String.valueOf(jmlTersedia.getText()));
                    a1 = a1 + 1;
                    jmlTersedia.setText(String.valueOf(a1));

                }
            }
        });

        List<SumberDaya> sumberDayas = new ArrayList<>();
//        SumberDaya sumberDaya1 = new SumberDaya(1, "Dana");
//        sumberDayas.add(sumberDaya1);
        SumberDaya sumberDaya2 = new SumberDaya(2, "SDM");
        sumberDayas.add(sumberDaya2);
        SumberDaya sumberDaya3 = new SumberDaya(3, "Peralatan");
        sumberDayas.add(sumberDaya3);
        SumberDaya sumberDaya4 = new SumberDaya(4, "Logistik");
        sumberDayas.add(sumberDaya4);
        SumberDaya sumberDaya5 = new SumberDaya(5, "Sarana dan Prasarana");
        sumberDayas.add(sumberDaya5);

        ArrayAdapter<SumberDaya> adapter1 = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sumberDayas);
        adapter1.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnercth.setAdapter(adapter1);

        btnMinus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jmlTersedia.length() == 0){
                    Toast.makeText(SumDayaFormsActivity.this, "Jumlah Tersedia : Kosong", Toast.LENGTH_SHORT).show();
                }else if (jmlTersedia.length() != 0){
                    int a1 = Integer.parseInt(String.valueOf(jmlTersedia.getText()));
                    if (a1 <= 0){
                        jmlTersedia.setText("0");
                    }else{
                        a1 = a1 - 1;
                        jmlTersedia.setText(String.valueOf(a1));
                    }
                }

            }
        });

        btnPlus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jmlDiperlukan.length() == 0){
                    int a1 = 0;
                    a1 = a1 + 1;
                    jmlDiperlukan.setText(String.valueOf(a1));

                }else if(jmlDiperlukan.length() != 0){
                    int a1 = Integer.parseInt(String.valueOf(jmlDiperlukan.getText()));
                    a1 = a1 + 1;
                    jmlDiperlukan.setText(String.valueOf(a1));

                }
            }
        });

        btnMinus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jmlDiperlukan.length() == 0){
                    Toast.makeText(SumDayaFormsActivity.this, "Jumlah Diperlukan : Kosong", Toast.LENGTH_SHORT).show();
                }else if (jmlDiperlukan.length() != 0){
                    int a1 = Integer.parseInt(String.valueOf(jmlDiperlukan.getText()));
                    if (a1 <= 0){
                        jmlDiperlukan.setText("0");
                    }else{
                        a1 = a1 - 1;
                        jmlDiperlukan.setText(String.valueOf(a1));
                    }
                }
            }
        });

//        spinnercth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                textInputLayoutSumDa.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        btnTambahSumda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SumberDaya sumberDaya = (SumberDaya) spinnercth.getSelectedItem();
                int id_resources = displaySumberDaya(sumberDaya);
                String additional_info = txtSumDa.getText().toString();
                int resources_available = Integer.parseInt(jmlTersedia.getText().toString());
                int resources_required = Integer.parseInt(jmlDiperlukan.getText().toString());
                int lack_of_resources = Integer.parseInt(jmlKekurangan.getText().toString());

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<ResourcesStoreResponse> call = apiInterface.setResources("Bearer " + accessToken, id_disasters
                        , id_resources, resources_available,resources_required,lack_of_resources,additional_info);
                call.enqueue(new Callback<ResourcesStoreResponse>() {
                    @Override
                    public void onResponse(Call<ResourcesStoreResponse> call, Response<ResourcesStoreResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(SumDayaFormsActivity.this, "Berhasil Menambahkan Data", Toast.LENGTH_SHORT, R.style.success).show();
                        }else {
                            StyleableToast.makeText(SumDayaFormsActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error" + response.toString()+ id_disasters
                                    + id_resources+ resources_available+resources_required+lack_of_resources+additional_info);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResourcesStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(SumDayaFormsActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });

//
//                Intent intent = new Intent(SumDayaFormsActivity.this, SumberDayaActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

    }

    private int displaySumberDaya(SumberDaya sumberDaya) {
        int id_sumber_daya = sumberDaya.getId_sumber_daya();
        return id_sumber_daya;
    }

    private TextWatcher KekuranganTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            jmlKekurangan.setText("0");
            if (jmlTersedia.length() != 0 && jmlDiperlukan.length() != 0){
                int a1 = Integer.parseInt(String.valueOf(jmlTersedia.getText()));
                int a2 = Integer.parseInt(String.valueOf(jmlDiperlukan.getText()));
                if (a2>a1){
                    int total = a2 - a1;
                    jmlKekurangan.setText(String.valueOf(total));
                }else if (a1>a2){
                    jmlKekurangan.setText("0");
                }
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    };




}