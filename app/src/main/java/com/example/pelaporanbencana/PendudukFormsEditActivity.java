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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.PeopleOneShow.PeopleOneShowResponse;
import com.example.pelaporanbencana.model.PeopleStore.PeopleStoreResponse;
import com.example.pelaporanbencana.model.VictimStore.VictimStoreResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendudukFormsEditActivity extends AppCompatActivity {
    TextInputEditText nikPenduduk, namaPenduduk, ahliWarisPenduduk, alamatPenduduk;
    LinearLayout tglLahirLayoutPenduduk;
    RadioButton radioBtnLk, radioBtnPr;
    RadioGroup rgJkPenduduk;
    TextView tglLahirPenduduk;
    Button btnEditPenduduk;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penduduk_forms_edit);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        Intent intent = getIntent();
//        String name = intent.getStringExtra("name");
        String nik = intent.getStringExtra("nik");
//        String birthdateAngka = intent.getStringExtra("birthdateAngka");
//        String gender = intent.getStringExtra("gender");
//        String heir = intent.getStringExtra("heir");
//        String address = intent.getStringExtra("address");

        nikPenduduk = findViewById(R.id.nikPenduduk);
        namaPenduduk = findViewById(R.id.namaPenduduk);
        ahliWarisPenduduk = findViewById(R.id.ahliWarisPenduduk);
        alamatPenduduk = findViewById(R.id.alamatPenduduk);
        radioBtnLk = findViewById(R.id.radioBtnLk);
        radioBtnPr = findViewById(R.id.radioBtnPr);
        rgJkPenduduk = findViewById(R.id.rgJkPenduduk);
        tglLahirPenduduk = findViewById(R.id.tglLahirPenduduk);
        btnEditPenduduk = findViewById(R.id.btnEditPenduduk);
        tglLahirLayoutPenduduk = findViewById(R.id.tglLahirLayoutPenduduk);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PeopleOneShowResponse> callPeople = apiInterface.getPeople("Bearer " + accessToken, nik);
        callPeople.enqueue(new Callback<PeopleOneShowResponse>() {
            @Override
            public void onResponse(Call<PeopleOneShowResponse> call, Response<PeopleOneShowResponse> response) {
                PeopleOneShowResponse peopleResponse = response.body();
                if (peopleResponse.isSuccess()){
                    //set isi
                    nikPenduduk.setText(nik);
                    namaPenduduk.setText(peopleResponse.getData().getName());
                    ahliWarisPenduduk.setText(peopleResponse.getData().getHeir());
                    alamatPenduduk.setText(peopleResponse.getData().getAddress());

                    String gender = peopleResponse.getData().getGender();
                    if (gender != null){
                        if (gender.equals("P")){
                            radioBtnLk.setChecked(false);
                            radioBtnPr.setChecked(true);
                        }else if (gender.equals("L")){
                            radioBtnLk.setChecked(true);
                            radioBtnPr.setChecked(false);
                        }
                    }else{
                        radioBtnLk.setChecked(false);
                        radioBtnPr.setChecked(false);
                    }
                    tglLahirPenduduk.setText(peopleResponse.getData().getBirthdate());

                }else{
                    StyleableToast.makeText(PendudukFormsEditActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<PeopleOneShowResponse> call, Throwable t) {
                StyleableToast.makeText(PendudukFormsEditActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });

        //DatePicker
        Calendar calendar = Calendar.getInstance();
        final int tahun = calendar.get(Calendar.YEAR);
        final int bulan = calendar.get(Calendar.MONTH);
        final int hari = calendar.get(Calendar.DAY_OF_MONTH);

        //DateCalendar
        tglLahirLayoutPenduduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PendudukFormsEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int tahun, int bulan, int hari) {
                        bulan = bulan + 1;
                        String tgl = tahun+"-"+bulan+"-"+hari;
                        tglLahirPenduduk.setText(tgl);
                    }
                }, tahun, bulan, hari);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        btnEditPenduduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nik_pdd = nikPenduduk.getText().toString();
                String nama_pdd = namaPenduduk.getText().toString();
                String ahli_waris_pdd = ahliWarisPenduduk.getText().toString();
                String alamat_pdd = alamatPenduduk.getText().toString();
                String gender_pdd = gender();
                String tgl_lahir_pdd = tglLahirPenduduk.getText().toString();

                Call<PeopleStoreResponse> call = apiInterface.updatePeople("Bearer " + accessToken, nik,
                        nik_pdd, nama_pdd, alamat_pdd, gender_pdd, ahli_waris_pdd, tgl_lahir_pdd);
                call.enqueue(new Callback<PeopleStoreResponse>() {
                    @Override
                    public void onResponse(Call<PeopleStoreResponse> call, Response<PeopleStoreResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(PendudukFormsEditActivity.this, "Berhasil Mengubah Data", Toast.LENGTH_SHORT, R.style.success).show();
                            Log.d("retrofit", "success " + response.toString());
                        }else{
                            StyleableToast.makeText(PendudukFormsEditActivity.this, "Gagal Mengubah Data", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error " + response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<PeopleStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(PendudukFormsEditActivity.this, "Gagal Mengubah Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });
            }
        });

    }

    private String gender() {
        if(radioBtnPr.isChecked()){
            String gender = "P";
            return gender;
        }else if (radioBtnLk.isChecked()){
            String gender = "L";
            return gender;
        }else {
            String gender = "L";
            return gender;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}