package com.example.pelaporanbencana;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.SocAssistShowaResponse.SocAssistShowaResponse;
import com.example.pelaporanbencana.model.SocialAssistanceStore.SocAssitStoreResponse;
import com.example.pelaporanbencana.model.Spinner.JenisBantuan;
import com.example.pelaporanbencana.model.Spinner.Satuan;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SumberBantuanFormsEditActivity extends AppCompatActivity {

    TextView tglDiterima;
    LinearLayout tglBantuanLayout;
    ImageButton btnPlus , btnMinus;
    TextInputEditText jmlBantuan, batch, pengirim;
    Button btnEditBantuan;
    Spinner spinnerJenisbantuan, spinnerSatuanBantuan;
    TextInputLayout tilJenisBantuan, tilSatuanBantuan;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sumber_bantuan_edit_forms);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        Intent intent = getIntent();
        String id_sa_types = intent.getStringExtra("id_sa_types");
        String batch_sa = intent.getStringExtra("batch");
        int idSaTypes = Integer.parseInt(id_sa_types);
        int batchSa = Integer.parseInt(batch_sa);

        tglDiterima = findViewById(R.id.tglDiterimaEdit);
        tglBantuanLayout = findViewById(R.id.tglBantuanEditLayout);
        btnPlus = findViewById(R.id.btnPlusBantuanEdit);
        btnMinus = findViewById(R.id.btnMinusBantuanEdit);
        jmlBantuan = findViewById(R.id.jmlBantuanEdit);
        btnEditBantuan = findViewById(R.id.btnEditBantuan);
        spinnerJenisbantuan = findViewById(R.id.spinnerJenisBantuanEdit);
        spinnerSatuanBantuan = findViewById(R.id.spinnerSatuanBantuanEdit);
        tilJenisBantuan = findViewById(R.id.tilJenisBantuanEdit);
        tilSatuanBantuan = findViewById(R.id.tilSatuanbantuanEdit);
        batch = findViewById(R.id.batchEdit);
        pengirim = findViewById(R.id.pengirimEdit);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SocAssistShowaResponse> call = apiInterface.getOneSocialAssistance("Bearer " + accessToken,
                id_sa_types, id_disasters, batch_sa);
        call.enqueue(new Callback<SocAssistShowaResponse>() {
            @Override
            public void onResponse(Call<SocAssistShowaResponse> call, Response<SocAssistShowaResponse> response) {
                SocAssistShowaResponse response1 = response.body();
                if (response1 != null) {
                    if (response1.isSuccess()){
                        pengirim.setText(response1.getData().getDonor());
                        batch.setText(String.valueOf(response1.getData().getBatch()));
                        jmlBantuan.setText(String.valueOf(response1.getData().getSocialAssistanceAmount()));
                        tglDiterima.setText(response1.getData().getDateReceived());

                        String jenis_bantuan = response1.getData().getSaTypesName();
                        int id_jenis_bantuan = response1.getData().getIdSaTypes();
                        String satuan = response1.getData().getSocialAssistanceUnit();

                        if (jenis_bantuan != null) {
                            jenisBantuanAddToList(id_jenis_bantuan, jenis_bantuan);
                        }else{
                            jenisBantuanAddToList(null , "None");
                        }

                        if(satuan != null) {
                            satuanAddToList(satuan);
                        }else{
                            satuanAddToList("None");
                        }
                    }else{
                        StyleableToast.makeText(SumberBantuanFormsEditActivity.this, "Gagal dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                        Log.d("retrofit", "error" + response.toString());
                    }
                }

            }

            @Override
            public void onFailure(Call<SocAssistShowaResponse> call, Throwable t) {
                StyleableToast.makeText(SumberBantuanFormsEditActivity.this, "Gagal", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });


        spinnerSatuanBantuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getSelectedItemId() == adapterView.getItemIdAtPosition(5)){
                    tilSatuanBantuan.setVisibility(View.VISIBLE);
                }else{
                    tilSatuanBantuan.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //DatePicker
        Calendar calendar1 = Calendar.getInstance();
        final int tahun = calendar1.get(Calendar.YEAR);
        final int bulan = calendar1.get(Calendar.MONTH);
        final int hari = calendar1.get(Calendar.DAY_OF_MONTH);
        //DateCalender
        tglBantuanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SumberBantuanFormsEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int tahun, int bulan, int hari) {
                        bulan = bulan + 1;
                        String tgl = tahun+"-"+bulan+"-"+hari;
                        tglDiterima.setText(tgl);
                    }
                },tahun, bulan, hari);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jmlBantuan.length() == 0){
                    int a1 = 0;
                    a1 = a1 + 1;
                    jmlBantuan.setText(String.valueOf(a1));

                }else if(jmlBantuan.length() != 0){
                    int a1 = Integer.parseInt(String.valueOf(jmlBantuan.getText()));
                    a1 = a1 + 1;
                    jmlBantuan.setText(String.valueOf(a1));

                }
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jmlBantuan.length() == 0){
                    Toast.makeText(SumberBantuanFormsEditActivity.this, "Jumlah Tersedia : Kosong", Toast.LENGTH_SHORT).show();
                }else if (jmlBantuan.length() != 0){
                    int a1 = Integer.parseInt(String.valueOf(jmlBantuan.getText()));
                    if (a1 <= 0){
                        jmlBantuan.setText("0");
                    }else{
                        a1 = a1 - 1;
                        jmlBantuan.setText(String.valueOf(a1));
                    }
                }

            }
        });

        btnEditBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JenisBantuan jenis_bantuan = (JenisBantuan) spinnerJenisbantuan.getSelectedItem();
                int id_sa_types = displayJenisBantuan(jenis_bantuan);
                String donor = pengirim.getText().toString();
                String date_received = tglDiterima.getText().toString();
                int sa_amount = Integer.parseInt(jmlBantuan.getText().toString());
                String sa_unit = spinnerSatuanBantuan.getSelectedItem().toString();
                int batch_bantuan = Integer.parseInt(batch.getText().toString());

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<SocAssitStoreResponse> call = apiInterface.updateSocialAssistance("Bearer " + accessToken,idSaTypes, id_disasters,batchSa,
                        id_sa_types, id_disasters, donor, date_received, sa_amount, sa_unit, batch_bantuan);

                call.enqueue(new Callback<SocAssitStoreResponse>() {
                    @Override
                    public void onResponse(Call<SocAssitStoreResponse> call, Response<SocAssitStoreResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(SumberBantuanFormsEditActivity.this, "Berhasil Mengubah Data", Toast.LENGTH_SHORT, R.style.success).show();
                            Log.d("retrofit", "success " + response.toString());
                        }else{
                            StyleableToast.makeText(SumberBantuanFormsEditActivity.this, "Gagal Mengubah Data", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error " + response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<SocAssitStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(SumberBantuanFormsEditActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });



            }
        });
    }

    private void satuanAddToList(String satuan) {
        //list satuan untuk spinnerSatuan
        List<Satuan> listsatuan = new ArrayList<>();
        Satuan satuan0 = new Satuan();
        satuan0.setId(satuan);
        satuan0.setUnits(satuan + " (pilihan saat ini)");
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

        spinnerSatuanBantuan.setAdapter(adapter);
    }

    private void jenisBantuanAddToList(Integer id_jenis_bantuan, String jenis_bantuan) {
        List<JenisBantuan> jenisBantuanList = new ArrayList<>();
        JenisBantuan jenisBantuan0 = new JenisBantuan(id_jenis_bantuan, jenis_bantuan + " (pilihan saat ini)");
        jenisBantuanList.add(jenisBantuan0);
        JenisBantuan jenisBantuan = new JenisBantuan(1, "Beras");
        jenisBantuanList.add(jenisBantuan);
        JenisBantuan jenisBantuan1 = new JenisBantuan(2, "Makanan Siap Saji");
        jenisBantuanList.add(jenisBantuan1);
        JenisBantuan jenisBantuan2 = new JenisBantuan(3, "Mie Instan");
        jenisBantuanList.add(jenisBantuan2);
        JenisBantuan jenisBantuan3 = new JenisBantuan(4, "Kid Ware");
        jenisBantuanList.add(jenisBantuan3);
        JenisBantuan jenisBantuan4 = new JenisBantuan(5, "Tenda/Terpal");
        jenisBantuanList.add(jenisBantuan4);
        JenisBantuan jenisBantuan5 = new JenisBantuan(6, "Family Kit");
        jenisBantuanList.add(jenisBantuan5);
        JenisBantuan jenisBantuan6 = new JenisBantuan(7, "Peralatan Sekolah");
        jenisBantuanList.add(jenisBantuan6);
        JenisBantuan jenisBantuan7 = new JenisBantuan(8, "Sandang");
        jenisBantuanList.add(jenisBantuan7);
        JenisBantuan jenisBantuan8 = new JenisBantuan(9, "Selimut");
        jenisBantuanList.add(jenisBantuan8);
        JenisBantuan jenisBantuan9 = new JenisBantuan(10, "Tikar");
        jenisBantuanList.add(jenisBantuan9);
        JenisBantuan jenisBantuan10 = new JenisBantuan(11, "Seragam Sekolah");
        jenisBantuanList.add(jenisBantuan10);
        JenisBantuan jenisBantuan11 = new JenisBantuan(12, "Kawat Beronjong");
        jenisBantuanList.add(jenisBantuan11);
        JenisBantuan jenisBantuan12 = new JenisBantuan(13, "Dapur Keluarga");
        jenisBantuanList.add(jenisBantuan12);
        JenisBantuan jenisBantuan13 = new JenisBantuan(14, "Matras");
        jenisBantuanList.add(jenisBantuan13);
        JenisBantuan jenisBantuan14 = new JenisBantuan(15, "Sarung");
        jenisBantuanList.add(jenisBantuan14);

        ArrayAdapter<JenisBantuan> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, jenisBantuanList);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerJenisbantuan.setAdapter(adapter);

    }

    private int displayJenisBantuan(JenisBantuan jenis_bantuan) {
        int idJenisBantuan = jenis_bantuan.getId_bantuan();
        return idJenisBantuan;
    }

}