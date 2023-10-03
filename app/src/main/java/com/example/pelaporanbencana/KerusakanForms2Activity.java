package com.example.pelaporanbencana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.DamageShowIdMax.DamageShowIdMaxResponse;
import com.example.pelaporanbencana.model.DamageStore.DamageStoreResponse;
import com.example.pelaporanbencana.model.Spinner.BidangKerusakan;
import com.example.pelaporanbencana.model.Spinner.Satuan;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KerusakanForms2Activity extends AppCompatActivity {

    TextInputEditText jmlKerusakan, detailKerusakan;
    ImageButton btnPlus, btnMinus;
    Button btnTambahKerusakan;
    RadioGroup rgJenisKerusakan;
    RadioButton radioButtonRingan, radioButtonSedang, radioButtonBerat;
    Spinner spinnerSatuan;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerusakan2_forms);

        //token
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        //kode_kejadian
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        //get Intent
        Intent intent = getIntent();
        String id_damage_category = intent.getStringExtra("id_damage_category");

        jmlKerusakan = findViewById(R.id.jmlKerusakan);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnTambahKerusakan = findViewById(R.id.btnTambahKerusakan);
        spinnerSatuan = findViewById(R.id.spinnerSatuan);
        radioButtonRingan = findViewById(R.id.radioButtonRingan);
        radioButtonSedang = findViewById(R.id.radioButtonSedang);
        radioButtonBerat = findViewById(R.id.radioButtonBerat);
        detailKerusakan = findViewById(R.id.detailKerusakan);

        //button + jumlah kerusakan
        jmlKerusakan.setText("1");
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(String.valueOf(jmlKerusakan.getText()));
                a = a + 1;
                jmlKerusakan.setText(String.valueOf(a));
            }
        });

        //button - jumlah kerusakan
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int b = Integer.parseInt(String.valueOf(jmlKerusakan.getText()));
                if (b <= 0) {
                    jmlKerusakan.setText("0");
                } else {
                    b = b - 1;
                    jmlKerusakan.setText(String.valueOf(b));
                }
            }
        });

        //list satuan untuk spinnerSatuan
        List<Satuan> listsatuan = new ArrayList<>();
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
        spinnerSatuan.setAdapter(adapter);

        //Get ID MAX
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DamageShowIdMaxResponse> call1 = apiInterface.getIdDamageShow("Bearer " + accessToken, id_damage_category, id_disasters);
        call1.enqueue(new Callback<DamageShowIdMaxResponse>() {
            @Override
            public void onResponse(Call<DamageShowIdMaxResponse> call, Response<DamageShowIdMaxResponse> response) {
                int idDamageMax = 1;

                if (response.body() != null) {
                    if (response.body().isSuccess()){
                        int idDamage = response.body().getData();
                        idDamageMax = idDamage + 1;

                    }else{
                        StyleableToast.makeText(KerusakanForms2Activity.this, "Gagal dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                }else{
                    idDamageMax = 1;
                }

                //button tambah kerusakan ke server
                int finalIdDamageMax = idDamageMax;
                btnTambahKerusakan.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String damage_types = jenisKerusakan();
                        int damage_amount = Integer.parseInt(jmlKerusakan.getText().toString());
                        Satuan satuan = (Satuan) spinnerSatuan.getSelectedItem();
                        String damage_units = displaySatuanData(satuan);
                        String detail_kerusakan = detailKerusakan.getText().toString();

                        Call<DamageStoreResponse> call = apiInterface.setDamage("Bearer " + accessToken, finalIdDamageMax, id_damage_category
                                , id_disasters, detail_kerusakan, damage_types, damage_amount, damage_units);

                        call.enqueue(new Callback<DamageStoreResponse>() {
                            @Override
                            public void onResponse(Call<DamageStoreResponse> call, Response<DamageStoreResponse> response) {
                                if (response.isSuccessful()) {
                                    StyleableToast.makeText(KerusakanForms2Activity.this, "Berhasil Menambahkan Data", Toast.LENGTH_SHORT, R.style.success).show();
                                    Log.d("retrofit", "success :" + response.toString() + finalIdDamageMax + damage_types);
                                    Intent intent = new Intent(KerusakanForms2Activity.this, KerusakanActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    StyleableToast.makeText(KerusakanForms2Activity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                                    Log.d("retrofit", "error :" + response.toString() + finalIdDamageMax + damage_types);
                                }
                            }

                            @Override
                            public void onFailure(Call<DamageStoreResponse> call, Throwable t) {
                                StyleableToast.makeText(KerusakanForms2Activity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                            }
                        });

                    }
                });

            }

            @Override
            public void onFailure(Call<DamageShowIdMaxResponse> call, Throwable t) {
                StyleableToast.makeText(KerusakanForms2Activity.this, "Gagal", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });

    }

    private String displaySatuanData(Satuan satuan) {
        String id = satuan.getId();
        String units = satuan.getUnits();

        String satuanData = "units" + units;

        return id;
    }

    private String jenisKerusakan() {
        if (radioButtonRingan.isChecked()) {
            String jenis_kerusakan = "1";
            return jenis_kerusakan;
        } else if (radioButtonSedang.isChecked()) {
            String jenis_kerusakan = "2";
            return jenis_kerusakan;
        } else if (radioButtonBerat.isChecked()) {
            String jenis_kerusakan = "3";
            return jenis_kerusakan;
        }

        return "1";
    }
}