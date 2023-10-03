package com.example.pelaporanbencana;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
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
import com.example.pelaporanbencana.model.SocAssistDistributed.SocAssistDistributedResponse;
import com.example.pelaporanbencana.model.Spinner.JenisBantuan;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BantuanTersalurkanFormsActivity extends AppCompatActivity {

    Spinner spinnerJenisbantuanT, spinnerSatuanBantuanT;
    TextInputLayout tilJenisBantuanT, tilSatuanBantuanT;
    Button btnTambahBantuanT;
    TextInputEditText penerima,jmlBantuanT,batchTersalurkan;
    ImageButton btnPlusBantuanT, btnMinusBantuanT;
    TextView tglDiterima;
    ApiInterface apiInterface;
    LinearLayout tglDiterimaLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan_tersalurkan_forms);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        spinnerJenisbantuanT = findViewById(R.id.spinnerJenisBantuanT);
        spinnerSatuanBantuanT = findViewById(R.id.spinnerSatuanBantuanT);
        tilJenisBantuanT = findViewById(R.id.tilJenisBantuanT);
        tilSatuanBantuanT = findViewById(R.id.tilSatuanBantuanT);
        btnTambahBantuanT = findViewById(R.id.btnTambahBantuanT);
        penerima = findViewById(R.id.penerima);
        tglDiterima = findViewById(R.id.tglDiterima);
        jmlBantuanT = findViewById(R.id.jmlBantuanT);
        btnMinusBantuanT = findViewById(R.id.btnMinusBantuanT);
        btnPlusBantuanT = findViewById(R.id.btnPlusBantuanT);
        tglDiterimaLayout = findViewById(R.id.tglDiterimaLayout);
        batchTersalurkan = findViewById(R.id.batchTersalurkan);

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
        spinnerJenisbantuanT.setAdapter(adapter);

        //jumlah bantuan
        jmlBantuanT.setText("1");
        btnPlusBantuanT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(jmlBantuanT.getText().toString());
                a = a + 1;
                jmlBantuanT.setText(String.valueOf(a));
            }
        });

        //DatePicker
        Calendar calendar1 = Calendar.getInstance();
        final int tahun = calendar1.get(Calendar.YEAR);
        final int bulan = calendar1.get(Calendar.MONTH);
        final int hari = calendar1.get(Calendar.DAY_OF_MONTH);
        //DateCalender
        tglDiterimaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        BantuanTersalurkanFormsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int tahun, int bulan, int hari) {
                        bulan = bulan + 1;
                        String tgl = tahun+"-"+bulan+"-"+hari;
                        int tgl1 = tahun+bulan+hari;
                        tglDiterima.setText(tgl);

                    }
                },tahun, bulan, hari);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }

        });

        btnMinusBantuanT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(jmlBantuanT.getText().toString());
                if (a <= 0){
                    jmlBantuanT.setText("0");
                }else if (a >= 0){
                    a = a - 1;
                    jmlBantuanT.setText(String.valueOf(a));
                }
            }
        });

        spinnerJenisbantuanT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getSelectedItemId() == adapterView.getItemIdAtPosition(15)){
                    tilJenisBantuanT.setVisibility(View.VISIBLE);
                }else{
                    tilJenisBantuanT.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSatuanBantuanT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getSelectedItemId() == adapterView.getItemIdAtPosition(5)){
                    tilSatuanBantuanT.setVisibility(View.VISIBLE);
                }else{
                    tilSatuanBantuanT.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnTambahBantuanT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JenisBantuan jenis_bantuan = (JenisBantuan) spinnerJenisbantuanT.getSelectedItem();
                int id_sa_types = displayJenisBantuan(jenis_bantuan);
                String recipient = penerima.getText().toString();
                String date_sent = tglDiterima.getText().toString();
                String sa_distributed_units = spinnerSatuanBantuanT.getSelectedItem().toString();
                int sa_distributed_amount = Integer.parseInt(jmlBantuanT.getText().toString());
                int batch = Integer.parseInt(batchTersalurkan.getText().toString());

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<SocAssistDistributedResponse> call = apiInterface.setSocialAssistDistributed("Bearer " + accessToken,
                        id_sa_types, id_disasters, recipient, date_sent, sa_distributed_amount, sa_distributed_units, batch);

                call.enqueue(new Callback<SocAssistDistributedResponse>() {
                    @Override
                    public void onResponse(Call<SocAssistDistributedResponse> call, Response<SocAssistDistributedResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(BantuanTersalurkanFormsActivity.this, "Berhasil Menambahkan Data", Toast.LENGTH_SHORT, R.style.success).show();
                            Log.d("retrofit", "success: " + response.toString());
//                            Intent intent = new Intent(BantuanTersalurkanFormsActivity.this, BantuanTersalurkanActivity.class);
//                            startActivity(intent);
                        }else {
                            StyleableToast.makeText(BantuanTersalurkanFormsActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error: " + id_sa_types+id_sa_types+recipient+date_sent+sa_distributed_amount+sa_distributed_units+batch);
                        }

                    }

                    @Override
                    public void onFailure(Call<SocAssistDistributedResponse> call, Throwable t) {
                        StyleableToast.makeText(BantuanTersalurkanFormsActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }

                });

//
            }
        });

    }
    private int displayJenisBantuan(JenisBantuan jenis_bantuan) {
        int idJenisBantuan = jenis_bantuan.getId_bantuan();
        return idJenisBantuan;
    }
}