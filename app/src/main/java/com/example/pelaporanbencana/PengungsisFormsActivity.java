package com.example.pelaporanbencana;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.EvacueeStore.EvacueeStoreResponse;
import com.example.pelaporanbencana.model.PeopleOneShow.PeopleOneShowResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengungsisFormsActivity extends AppCompatActivity {

    TextInputEditText nikPengungsi, namaPengungsi, ahliWaris, alamatPengungsi;
    TextView tglLahir,textTglLahir;
    RadioButton radioButtonLk, radioButtonPr;
    RadioGroup rgJenisKelamin;
    Button btnTambahPengungsi;
    ApiInterface apiInterface;
    LinearLayout tglLahirLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengungsis_forms);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        Intent intent = getIntent();
        String kodeShelter = intent.getStringExtra("kode_shelter");
        Toast.makeText(this, kodeShelter, Toast.LENGTH_SHORT).show();

        nikPengungsi = findViewById(R.id.nikPengungsi);
        namaPengungsi = findViewById(R.id.namaPengungsi);
        textTglLahir = findViewById(R.id.textTglLahir);
        ahliWaris = findViewById(R.id.ahliWaris);
        alamatPengungsi = findViewById(R.id.alamatPengungsi);
        tglLahir = findViewById(R.id.tglLahir);
        tglLahirLayout = findViewById(R.id.tglLahirLayout);
        rgJenisKelamin = findViewById(R.id.rgJenisKelamin);
        radioButtonLk = findViewById(R.id.radioButtonLk);
        radioButtonPr = findViewById(R.id.radioButtonPr);
        btnTambahPengungsi = findViewById(R.id.btnTambahPengungsi);

        //DatePicker
        Calendar calendar = Calendar.getInstance();
        final int tahun = calendar.get(Calendar.YEAR);
        final int bulan = calendar.get(Calendar.MONTH);
        final int hari = calendar.get(Calendar.DAY_OF_MONTH);

        //DateCalendar
        tglLahirLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PengungsisFormsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int tahun, int bulan, int hari) {
                        bulan = bulan + 1;
                        String tgl = tahun+"-"+bulan+"-"+hari;
                        int tgl1 = tahun+bulan+hari;
                        tglLahir.setText(tgl);
                    }
                }, tahun, bulan, hari);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        nikPengungsi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                namaPengungsi.setText("");
                alamatPengungsi.setText("");
                ahliWaris.setText("");
                tglLahir.setText("");
                radioButtonLk.setChecked(false);
                radioButtonPr.setChecked(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                namaPengungsi.setText("");
                alamatPengungsi.setText("");
                ahliWaris.setText("");
                tglLahir.setText("");
                radioButtonLk.setChecked(false);
                radioButtonPr.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String nik = nikPengungsi.getText().toString();

                //menampilkan data keajadian pada menuactivity (kode kejadian, tanggal, waktu, deskripsi kejadian )
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<PeopleOneShowResponse> call = apiInterface.getPeople("Bearer " + accessToken, nik);
                call.enqueue(new Callback<PeopleOneShowResponse>() {
                    @Override
                    public void onResponse(Call<PeopleOneShowResponse> call, Response<PeopleOneShowResponse> response) {
                        PeopleOneShowResponse peopleResponse = response.body();
                        if(peopleResponse != null && peopleResponse.isSuccess()){

                            String name = peopleResponse.getData().getName();
                            String address = peopleResponse.getData().getAddress();
                            String gender = peopleResponse.getData().getGender();
                            String heir = peopleResponse.getData().getHeir();
//                            String picture= ;
                            String birthdate = peopleResponse.getData().getBirthdate();

                            namaPengungsi.setText(name);
                            alamatPengungsi.setText(address);
                            ahliWaris.setText(heir);
                            tglLahir.setText(birthdate);

                            if (gender == null){
                                radioButtonLk.setChecked(false);
                                radioButtonPr.setChecked(false);

                            }else if(gender.equals("L") ){
                                radioButtonPr.setChecked(false);
                                radioButtonLk.setChecked(true);

                            }else if(gender.equals("P")){
                                radioButtonLk.setChecked(false);
                                radioButtonPr.setChecked(true);
                            }

                            StyleableToast.makeText(PengungsisFormsActivity.this, "Data " + peopleResponse.getData().getName() +" ditemukan!" , Toast.LENGTH_SHORT, R.style.info).show();
                        }else {
                      //      StyleableToast.makeText(PengungsisFormsActivity.this, "Tidak ada data yang sesuai NIK! Tambahkan Manual", Toast.LENGTH_LONG, R.style.error).show();
                            Log.d("retrofit", "error: " + response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<PeopleOneShowResponse> call, Throwable t) {
                        StyleableToast.makeText(PengungsisFormsActivity.this, "Tidak ada data yang sesuai NIK! Tambahkan Manual", Toast.LENGTH_LONG, R.style.error).show();
                    }
                });
            }
        });

        btnTambahPengungsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nik= nikPengungsi.getText().toString();
//                String id_shelter= "1";
                String name= namaPengungsi.getText().toString();
                String address= alamatPengungsi.getText().toString();
                String gender = gender();
                String heir= ahliWaris.getText().toString();
                String picture= "disaod";
                String birthdate = tglLahir.getText().toString();

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<EvacueeStoreResponse> call = apiInterface.setEvacuee("Bearer " + accessToken,
                        nik, id_disasters, kodeShelter, name, address, gender, heir, picture, birthdate);

                call.enqueue(new Callback<EvacueeStoreResponse>() {
                    @Override
                    public void onResponse(Call<EvacueeStoreResponse> call, Response<EvacueeStoreResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(PengungsisFormsActivity.this, "Berhasil Menambahkan Data", Toast.LENGTH_SHORT, R.style.success).show();
                            Intent intent1 = new Intent(PengungsisFormsActivity.this, LokasiPengungsiActivity.class);
                            startActivity(intent1);
                            finish();

                        }else{
                            StyleableToast.makeText(PengungsisFormsActivity.this, "Data Pengungsi Sudah Ada", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error: " + response.toString());
                        }

                    }

                    @Override
                    public void onFailure(Call<EvacueeStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(PengungsisFormsActivity.this, "Gagal Mendambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });
            }
        });

    }

    private String gender() {
        if(radioButtonPr.isChecked()){
            String gender = "P";
            return gender;
        }else {
            String gender = "L";
            return gender;
        }
    }
}