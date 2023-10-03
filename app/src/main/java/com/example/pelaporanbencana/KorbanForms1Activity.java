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
import com.example.pelaporanbencana.model.PeopleOneShow.PeopleOneShowResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KorbanForms1Activity extends AppCompatActivity {
    TextInputEditText nikKorban, namaKorban, ahliWarisKorban, alamatKorban;
    LinearLayout tglLahirLayoutKorban;
    RadioButton radioBtnLk, radioBtnPr;
    RadioGroup rgJkKorban;
    TextView tglLahirKorban;
    Button btnTambahKorban;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korban_forms1);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        nikKorban = findViewById(R.id.nikKorban);
        namaKorban = findViewById(R.id.namaKorban);
        ahliWarisKorban = findViewById(R.id.ahliWarisKorban);
        alamatKorban = findViewById(R.id.alamatKorban);
        radioBtnLk = findViewById(R.id.radioBtnLk);
        radioBtnPr = findViewById(R.id.radioBtnPr);
        rgJkKorban = findViewById(R.id.rgJkKorban);
        tglLahirKorban = findViewById(R.id.tglLahirKorban);
        btnTambahKorban = findViewById(R.id.btnTambahKorban);
        tglLahirLayoutKorban = findViewById(R.id.tglLahirLayoutKorban);

        nikKorban.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                namaKorban.setText("");
                alamatKorban.setText("");
                ahliWarisKorban.setText("");
                tglLahirKorban.setText("");
                radioBtnLk.setChecked(false);
                radioBtnPr.setChecked(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                namaKorban.setText("");
                alamatKorban.setText("");
                ahliWarisKorban.setText("");
                tglLahirKorban.setText("");
                radioBtnLk.setChecked(false);
                radioBtnPr.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String nik_korban = nikKorban.getText().toString();

                //menampilkan data keajadian pada menuactivity (kode kejadian, tanggal, waktu, deskripsi kejadian )
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<PeopleOneShowResponse> call = apiInterface.getPeople("Bearer " + accessToken, nik_korban);
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

                            namaKorban.setText(name);
                            alamatKorban.setText(address);
                            ahliWarisKorban.setText(heir);
                            tglLahirKorban.setText(birthdate);

                            if (gender == null){
                                radioBtnLk.setChecked(false);
                                radioBtnPr.setChecked(false);

                            }else if(gender.equals("L") ){
                                radioBtnPr.setChecked(false);
                                radioBtnLk.setChecked(true);

                            }else if(gender.equals("P")){
                                radioBtnLk.setChecked(false);
                                radioBtnPr.setChecked(true);
                            }

//                            Toast.makeText(KorbanForms1Activity.this, "Data ditemukan!"+ "/n" + peopleResponse.getData().getName() +" " , Toast.LENGTH_SHORT).show();
                            StyleableToast.makeText(KorbanForms1Activity.this, "Data " + peopleResponse.getData().getName() +
                                    "-" + peopleResponse.getData().getNik() + " ditemukan!", Toast.LENGTH_SHORT, R.style.info).show();
                        }else {
                          //  StyleableToast.makeText(KorbanForms1Activity.this, "Tidak ada data yang sesuai NIK! Tambahkan Manual", Toast.LENGTH_LONG, R.style.error).show();
                            Log.d("retrofit", "error: " + response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<PeopleOneShowResponse> call, Throwable t) {
                        StyleableToast.makeText(KorbanForms1Activity.this, "Tidak ada data yang sesuai NIK! Tambahkan Manual", Toast.LENGTH_LONG, R.style.error).show();
                    }
                });
            }
        });

        //DatePicker
        Calendar calendar = Calendar.getInstance();
        final int tahun = calendar.get(Calendar.YEAR);
        final int bulan = calendar.get(Calendar.MONTH);
        final int hari = calendar.get(Calendar.DAY_OF_MONTH);

        //DateCalendar
        tglLahirLayoutKorban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        KorbanForms1Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int tahun, int bulan, int hari) {
                        bulan = bulan + 1;
                        String tgl = tahun+"-"+bulan+"-"+hari;
                        int tgl1 = tahun+bulan+hari;
                        tglLahirKorban.setText(tgl);
                    }
                }, tahun, bulan, hari);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        btnTambahKorban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nik_korban = nikKorban.getText().toString();
                String nama_korban = namaKorban.getText().toString();
                String ahli_waris_korban = ahliWarisKorban.getText().toString();
                String alamat_korban = alamatKorban.getText().toString();
                String gender_korban = gender();
                String tgl_lahir_korban = tglLahirKorban.getText().toString();

                Intent intent = new Intent(KorbanForms1Activity.this, KorbanForms2Activity.class);
                intent.putExtra("nik", nik_korban);
                intent.putExtra("nama", nama_korban);
                intent.putExtra("ahli_waris", ahli_waris_korban);
                intent.putExtra("alamat", alamat_korban);
                intent.putExtra("gender", gender_korban);
                intent.putExtra("tgl_lahir", tgl_lahir_korban);
                startActivity(intent);
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