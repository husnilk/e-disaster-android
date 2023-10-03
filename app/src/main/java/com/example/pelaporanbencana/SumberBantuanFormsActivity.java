package com.example.pelaporanbencana;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.SocialAssistanceStore.SocAssitStoreResponse;
import com.example.pelaporanbencana.model.Spinner.JenisBantuan;
import com.example.pelaporanbencana.model.Spinner.SumberDaya;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SumberBantuanFormsActivity extends AppCompatActivity {

    TextView tglBantuan;
    LinearLayout tglBantuanLayout;
    ImageButton btnPlus , btnMinus;
    TextInputEditText jmlBantuan, batch, pengirim;
    Button btnTambahBantuan;
    Spinner spinnerJenisbantuan, spinnerSatuanBantuan;
    TextInputLayout tilJenisBantuan, tilSatuanBantuan;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sumber_bantuan_forms);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        tglBantuan = findViewById(R.id.tglDiterima);
        tglBantuanLayout = findViewById(R.id.tglBantuanLayout);
        btnPlus = findViewById(R.id.btnPlusBantuan);
        btnMinus = findViewById(R.id.btnMinusBantuan);
        jmlBantuan = findViewById(R.id.jmlBantuan);
        btnTambahBantuan = findViewById(R.id.btnTambahBantuan);
        spinnerJenisbantuan = findViewById(R.id.spinnerJenisBantuan);
        spinnerSatuanBantuan = findViewById(R.id.spinnerSatuanBantuan);
        tilJenisBantuan = findViewById(R.id.tilJenisBantuan);
        tilSatuanBantuan = findViewById(R.id.tilSatuanbantuan);
        batch = findViewById(R.id.batch);
        pengirim = findViewById(R.id.pengirim);

//        spinnerJenisbantuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (adapterView.getSelectedItemId() == adapterView.getItemIdAtPosition(15)){
//                    tilJenisBantuan.setVisibility(View.VISIBLE);
//                }else{
//                    tilJenisBantuan.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        List<JenisBantuan> jenisBantuanList = new ArrayList<>();
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
                        SumberBantuanFormsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int tahun, int bulan, int hari) {
                        bulan = bulan + 1;
                        String tgl = tahun+"-"+bulan+"-"+hari;
                        tglBantuan.setText(tgl);
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
                    Toast.makeText(SumberBantuanFormsActivity.this, "Jumlah Tersedia : Kosong", Toast.LENGTH_SHORT).show();
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

        btnTambahBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JenisBantuan jenis_bantuan = (JenisBantuan) spinnerJenisbantuan.getSelectedItem();
                int id_sa_types = displayJenisBantuan(jenis_bantuan);
                String donor = pengirim.getText().toString();
                String date_received = tglBantuan.getText().toString();
                int sa_amount = Integer.parseInt(jmlBantuan.getText().toString());
                String sa_unit = spinnerSatuanBantuan.getSelectedItem().toString();
                int batch_bantuan = Integer.parseInt(batch.getText().toString());

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<SocAssitStoreResponse> call = apiInterface.setSocialAssistance("Bearer " + accessToken,
                        id_sa_types, id_disasters, donor, date_received, sa_amount, sa_unit, batch_bantuan);

                call.enqueue(new Callback<SocAssitStoreResponse>() {
                    @Override
                    public void onResponse(Call<SocAssitStoreResponse> call, Response<SocAssitStoreResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(SumberBantuanFormsActivity.this, "Berhasil Menambahkan Data", Toast.LENGTH_SHORT, R.style.success).show();
                            Log.d("retrofit", "success " + response.toString());
                        }else{
                            StyleableToast.makeText(SumberBantuanFormsActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error " + id_disasters+id_sa_types+donor+date_received+sa_amount+sa_unit+batch_bantuan);
                        }
                    }

                    @Override
                    public void onFailure(Call<SocAssitStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(SumberBantuanFormsActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });



            }
        });
    }

    private int displayJenisBantuan(JenisBantuan jenis_bantuan) {
        int idJenisBantuan = jenis_bantuan.getId_bantuan();
        return idJenisBantuan;
    }

}