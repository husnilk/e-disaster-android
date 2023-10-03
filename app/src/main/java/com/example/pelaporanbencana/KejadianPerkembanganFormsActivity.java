package com.example.pelaporanbencana;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.DevDisastersStore.DevDisastersStoreResponse;
import com.example.pelaporanbencana.model.Spinner.JenisCuaca;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KejadianPerkembanganFormsActivity extends AppCompatActivity {
    Button btnKejPerkembangan;
    TextView tglKejadianPerkembangan, waktuKejadianPerkembangan;
    TextInputEditText cakupanDampakPerkembangan, penyebabKejadianPerkembangan, upayaKejadianPerkembangan, potensiKejadianPerkembangan, deskripsiPerkembangan;
    Spinner spinnerjeniscuacaPerkembangan;
    LinearLayout tglKejadianPerkembanganLayout, waktuKejadianPerkembanganLayout;
    ApiInterface apiInterface;
    int jam, menit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kejadian_perkembangan_forms);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String access_token = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        btnKejPerkembangan = findViewById(R.id.btnKejPerkembangan);
        tglKejadianPerkembangan = findViewById(R.id.tglKejadianPerkembangan);
        waktuKejadianPerkembangan = findViewById(R.id.waktuKejadianPerkembangan);
        cakupanDampakPerkembangan = findViewById(R.id.cakupanDampakPerkembangan);
        penyebabKejadianPerkembangan = findViewById(R.id.penyebabKejadianPerkembangan);
        upayaKejadianPerkembangan = findViewById(R.id.upayaKejadianPerkembangan);
        potensiKejadianPerkembangan = findViewById(R.id.potensiKejadianPerkembangan);
        spinnerjeniscuacaPerkembangan = findViewById(R.id.spinnerjeniscuacaPerkembangan);
        deskripsiPerkembangan = findViewById(R.id.deskripsiPerkembangan);
        tglKejadianPerkembanganLayout = findViewById(R.id.tglKejadianPerkembanganLayout);
        waktuKejadianPerkembanganLayout = findViewById(R.id.waktuKejadianPerkembanganLayout);

        List<JenisCuaca> cuacaList = new ArrayList<>();

        JenisCuaca jenisCuaca1  = new JenisCuaca("Cerah");
        cuacaList.add(jenisCuaca1);
        JenisCuaca jenisCuaca2  = new JenisCuaca("Cerah Berawan");
        cuacaList.add(jenisCuaca2);
        JenisCuaca jenisCuaca3  = new JenisCuaca("Berawan");
        cuacaList.add(jenisCuaca3);
        JenisCuaca jenisCuaca4  = new JenisCuaca("Berawan Tebal");
        cuacaList.add(jenisCuaca4);
        JenisCuaca jenisCuaca5  = new JenisCuaca("Kabut");
        cuacaList.add(jenisCuaca5);
        JenisCuaca jenisCuaca6  = new JenisCuaca("Kabut Tebal");
        cuacaList.add(jenisCuaca6);
        JenisCuaca jenisCuaca7  = new JenisCuaca("Hujan Lokal");
        cuacaList.add(jenisCuaca7);
        JenisCuaca jenisCuaca8  = new JenisCuaca("Hujan Ringan");
        cuacaList.add(jenisCuaca8);
        JenisCuaca jenisCuaca9  = new JenisCuaca("Hujan Sedang");
        cuacaList.add(jenisCuaca9);
        JenisCuaca jenisCuaca10  = new JenisCuaca("Hujan Lebat");
        cuacaList.add(jenisCuaca10);
        JenisCuaca jenisCuaca11  = new JenisCuaca("Hujan Petir");
        cuacaList.add(jenisCuaca11);
        JenisCuaca jenisCuaca12  = new JenisCuaca("Salju");
        cuacaList.add(jenisCuaca12);

        ArrayAdapter<JenisCuaca> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cuacaList);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerjeniscuacaPerkembangan.setAdapter(adapter);

        //DatePicker
        Calendar calendar1 = Calendar.getInstance();
        final int tahun = calendar1.get(Calendar.YEAR);
        final int bulan = calendar1.get(Calendar.MONTH);
        final int hari = calendar1.get(Calendar.DAY_OF_MONTH);
        //DateCalender
        tglKejadianPerkembanganLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        KejadianPerkembanganFormsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int tahun, int bulan, int hari) {
                        bulan = bulan + 1;
                        String tgl = tahun+"-"+bulan+"-"+hari;
                        int tgl1 = tahun+bulan+hari;
                        tglKejadianPerkembangan.setText(tgl);

                    }
                },tahun, bulan, hari);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }

        });

        //TimePicker
        waktuKejadianPerkembanganLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        KejadianPerkembanganFormsActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                jam = hour;
                                menit = minute;

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0,0,0,jam, menit);
                                waktuKejadianPerkembangan.setText(DateFormat.format("HH:mm:ss", calendar));

                            }
                        },24, 0, true
                );
                timePickerDialog.updateTime(jam, menit);
                timePickerDialog.show();
            }
        });


        btnKejPerkembangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tgl = tglKejadianPerkembangan.getText().toString();
                String waktu = waktuKejadianPerkembangan.getText().toString();
                String deskripsi = deskripsiPerkembangan.getText().toString();
                String cakupan = cakupanDampakPerkembangan.getText().toString();
                String penyebab = penyebabKejadianPerkembangan.getText().toString();
                String upaya = upayaKejadianPerkembangan.getText().toString();
                String potensi = potensiKejadianPerkembangan.getText().toString();
                String cuaca = spinnerjeniscuacaPerkembangan.getSelectedItem().toString();

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<DevDisastersStoreResponse> call = apiInterface.setDevDisaster("Bearer " + access_token, id_disasters,
                        tgl, waktu, deskripsi, cakupan, penyebab, cuaca, potensi, upaya);

                call.enqueue(new Callback<DevDisastersStoreResponse>() {
                    @Override
                    public void onResponse(Call<DevDisastersStoreResponse> call, Response<DevDisastersStoreResponse> response) {
                        if (response.body()!=null && response.body().isSuccess()){
                            Log.d("retrofit", "error: " + response.toString());
                            StyleableToast.makeText(KejadianPerkembanganFormsActivity.this, "Berhasil Menambahkan Data", Toast.LENGTH_SHORT, R.style.success).show();
                        }else {
                            StyleableToast.makeText(KejadianPerkembanganFormsActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DevDisastersStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(KejadianPerkembanganFormsActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });

            }
        });


    }
}