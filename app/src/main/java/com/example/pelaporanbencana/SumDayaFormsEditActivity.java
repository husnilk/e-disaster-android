package com.example.pelaporanbencana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.ResourceShowa.ResourcesShowaResponse;
import com.example.pelaporanbencana.model.ResourcesStore.ResourcesStoreResponse;
import com.example.pelaporanbencana.model.Spinner.Satuan;
import com.example.pelaporanbencana.model.Spinner.SumberDaya;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SumDayaFormsEditActivity extends AppCompatActivity {

    private Spinner spinnercth, spinnerSatuanSumDaEdit;
    private TextInputLayout textInputLayoutSumDa;
    TextInputEditText jmlTersedia, jmlKekurangan, jmlDiperlukan, txtSumDa;
    ImageButton btnPlus1, btnPlus2,  btnMinus1, btnMinus2;
    Button btnTambahSumda;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_daya_edit_forms);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        Intent intent = getIntent();
        String id_resources = intent.getStringExtra("id_resources");
        int idResources = Integer.parseInt(id_resources);
        String id_disasters_resources = intent.getStringExtra("id_disasters_resources");
        int id_dr_int = Integer.parseInt(id_disasters_resources);

        spinnercth = findViewById(R.id.spinnercthEdit);
        textInputLayoutSumDa = findViewById(R.id.textInputLayoutSumDaEdit);
        jmlDiperlukan = findViewById(R.id.jmlDiperlukanEdit);
        jmlKekurangan = findViewById(R.id.jmlKekuranganEdit);
        jmlTersedia = findViewById(R.id.jmlTersediaEdit);
        btnPlus1 = findViewById(R.id.btnPlus1Edit);
        btnPlus2 = findViewById(R.id.btnPlus2Edit);
        btnMinus1 = findViewById(R.id.btnMinus1Edit);
        btnMinus2 = findViewById(R.id.btnMinus2Edit);
        btnTambahSumda = findViewById(R.id.btnEditSumda);
        txtSumDa = findViewById(R.id.txtSumDaEdit);
        spinnerSatuanSumDaEdit = findViewById(R.id.spinnerSatuanSumDaEdit);

        jmlTersedia.setText("0");
        jmlTersedia.addTextChangedListener(KekuranganTextWatcher);
        jmlDiperlukan.setText("0");
        jmlDiperlukan.addTextChangedListener(KekuranganTextWatcher);
        jmlKekurangan.setText("0");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResourcesShowaResponse> call = apiInterface.getOneResources( "Bearer " + accessToken,idResources,id_disasters,id_dr_int);
        call.enqueue(new Callback<ResourcesShowaResponse>() {
            @Override
            public void onResponse(Call<ResourcesShowaResponse> call, Response<ResourcesShowaResponse> response) {
                ResourcesShowaResponse response1 = response.body();
                int required = response1.getData().getResources_required();
                int lackOfResources = response1.getData().getLackOfResources();
                int available = response1.getData().getResourcesAvailable();
                int id_resources = response1.getData().getId_resources();
                String resources = response1.getData().getResourcesTypes();
                String units = response1.getData().getResourcesUnits();

                jmlDiperlukan.setText(String.valueOf(required));
                jmlKekurangan.setText(String.valueOf(lackOfResources));
                jmlTersedia.setText(String.valueOf(available));
                txtSumDa.setText(String.valueOf(response1.getData().getAdditionalInfo()));

                if(resources != null) {
                    sumberDayaAddToList(id_resources, resources);
                }else{
                    sumberDayaAddToList(0, "None");
                }


                if(units != null) {
                    satuanAddToList(units);
                }else{
                    satuanAddToList("None");
                }

            }

            @Override
            public void onFailure(Call<ResourcesShowaResponse> call, Throwable t) {

            }
        });


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

        btnMinus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jmlTersedia.length() == 0){
                    Toast.makeText(SumDayaFormsEditActivity.this, "Jumlah Tersedia : Kosong", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(SumDayaFormsEditActivity.this, "Jumlah Diperlukan : Kosong", Toast.LENGTH_SHORT).show();
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
                int id_resources1 = displaySumberDaya(sumberDaya);
                String additional_info = txtSumDa.getText().toString();
                int resources_available = Integer.parseInt(jmlTersedia.getText().toString());
                int resources_required = Integer.parseInt(jmlDiperlukan.getText().toString());
                int lack_of_resources = Integer.parseInt(jmlKekurangan.getText().toString());

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<ResourcesStoreResponse> call = apiInterface.updateResources("Bearer " + accessToken, id_disasters, idResources, id_dr_int, id_disasters
                        , id_resources1, resources_available, resources_required, lack_of_resources, additional_info);
                call.enqueue(new Callback<ResourcesStoreResponse>() {
                    @Override
                    public void onResponse(Call<ResourcesStoreResponse> call, Response<ResourcesStoreResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(SumDayaFormsEditActivity.this, "Berhasil Mengubah Data", Toast.LENGTH_SHORT, R.style.success).show();
                        }else {
                            StyleableToast.makeText(SumDayaFormsEditActivity.this, "Gagal Mengubah Data", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error" + response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResourcesStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(SumDayaFormsEditActivity.this, "Gagal Mengubah Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });

//
//                Intent intent = new Intent(SumDayaFormsActivity.this, SumberDayaActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

    }

    private void satuanAddToList(String units) {
        //list satuan untuk spinnerSatuan
        List<Satuan> listsatuan = new ArrayList<>();
        Satuan satuan0 = new Satuan();
        satuan0.setId(units);
        satuan0.setUnits(units + " (pilihan saat ini)");
        listsatuan.add(satuan0);

        Satuan satuan1 = new Satuan();
        satuan1.setId("Unit");
        satuan1.setUnits("Unit");
        listsatuan.add(satuan1);

        Satuan satuan2 = new Satuan();
        satuan2.setId("Buah");
        satuan2.setUnits("Buah");
        listsatuan.add(satuan2);

        Satuan satuan3 = new Satuan();
        satuan3.setId("Lembar");
        satuan3.setUnits("Lembar");
        listsatuan.add(satuan3);

        Satuan satuan4 = new Satuan();
        satuan4.setId("Paket");
        satuan4.setUnits("Paket");
        listsatuan.add(satuan4);

        Satuan satuan5 = new Satuan();
        satuan5.setId("Kotak");
        satuan5.setUnits("Kotak");
        listsatuan.add(satuan5);

        Satuan satuan6 = new Satuan();
        satuan6.setId("Kg");
        satuan6.setUnits("Kg");
        listsatuan.add(satuan6);

        ArrayAdapter<Satuan> adapter = new ArrayAdapter<Satuan>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listsatuan);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        spinnerSatuanSumDaEdit.setAdapter(adapter);
    }

    private void sumberDayaAddToList(int id_resources, String resources) {
        List<SumberDaya> sumberDayas = new ArrayList<>();
        SumberDaya sumberDaya1 = new SumberDaya(id_resources, resources + " (pilihan saat ini)");
        sumberDayas.add(sumberDaya1);
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