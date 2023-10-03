package com.example.pelaporanbencana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelaporanbencana.adapter.TerdampakRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.AffectedStore.AffectedStoreResponse;
import com.example.pelaporanbencana.model.Penduduk;
import com.example.pelaporanbencana.model.PeopleOneShow.PeopleOneShowResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TerdampakFormsActivity extends AppCompatActivity {
    TextInputEditText nikTerdampak, namaTerdampak, ahliWarisTerdampak, alamatTerdampak;
    RadioGroup rgJkTerdampak;
    RadioButton radioBtnLkTerdampak, radioBtnPrTerdampak;
    LinearLayout tglLahirLayoutTerdampak;
    TextView tglLahirTerdampak;
    Button btnAddTerdampak;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terdampak_forms);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        nikTerdampak = findViewById(R.id.nikTerdampak);
        namaTerdampak = findViewById(R.id.namaTerdampak);
        ahliWarisTerdampak = findViewById(R.id.ahliWarisTerdampak);
        alamatTerdampak = findViewById(R.id.alamatTerdampak);
        rgJkTerdampak = findViewById(R.id.rgJkTerdampak);
        radioBtnLkTerdampak = findViewById(R.id.radioBtnLkTerdampak);
        radioBtnPrTerdampak = findViewById(R.id.radioBtnPrTerdampak);
        tglLahirLayoutTerdampak = findViewById(R.id.tglLahirLayoutTerdampak);
        tglLahirTerdampak = findViewById(R.id.tglLahirTerdampak);
        btnAddTerdampak = findViewById(R.id.btnAddTerdampak);

        nikTerdampak.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                namaTerdampak.setText("");
                alamatTerdampak.setText("");
                ahliWarisTerdampak.setText("");
                tglLahirTerdampak.setText("");
                radioBtnLkTerdampak.setChecked(false);
                radioBtnPrTerdampak.setChecked(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                namaTerdampak.setText("");
                alamatTerdampak.setText("");
                ahliWarisTerdampak.setText("");
                tglLahirTerdampak.setText("");
                radioBtnLkTerdampak.setChecked(false);
                radioBtnPrTerdampak.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String nik_korban = nikTerdampak.getText().toString();

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

                            namaTerdampak.setText(name);
                            alamatTerdampak.setText(address);
                            ahliWarisTerdampak.setText(heir);
                            tglLahirTerdampak.setText(birthdate);

                            if (gender == null){
                                radioBtnLkTerdampak.setChecked(false);
                                radioBtnPrTerdampak.setChecked(false);

                            }else if(gender.equals("L") ){
                                radioBtnPrTerdampak.setChecked(false);
                                radioBtnLkTerdampak.setChecked(true);

                            }else if(gender.equals("P")){
                                radioBtnLkTerdampak.setChecked(false);
                                radioBtnPrTerdampak.setChecked(true);
                            }

//                            Toast.makeText(KorbanForms1Activity.this, "Data ditemukan!"+ "/n" + peopleResponse.getData().getName() +" " , Toast.LENGTH_SHORT).show();
                            StyleableToast.makeText(TerdampakFormsActivity.this, "Data " + peopleResponse.getData().getName() +
                                    "-" + peopleResponse.getData().getNik() + " ditemukan!", Toast.LENGTH_SHORT, R.style.info).show();
                        }else {
                           // StyleableToast.makeText(TerdampakFormsActivity.this, "Tidak ada data yang sesuai NIK! Tambahkan Manual", Toast.LENGTH_LONG, R.style.error).show();
                            Log.d("retrofit", "error: " + response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<PeopleOneShowResponse> call, Throwable t) {
                        StyleableToast.makeText(TerdampakFormsActivity.this, "Tidak ada data yang sesuai NIK! Tambahkan Manual", Toast.LENGTH_LONG, R.style.error).show();
                    }
                });
            }
        });

        //DatePicker
        Calendar calendar = Calendar.getInstance();
        final int tahun = calendar.get(Calendar.YEAR);
        final int bulan = calendar.get(Calendar.MONTH);
        final int hari = calendar.get(Calendar.DAY_OF_MONTH);

        tglLahirLayoutTerdampak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TerdampakFormsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int tahun, int bulan, int hari) {
                        bulan = bulan + 1;
                        String tgl = tahun+"-"+bulan+"-"+hari;
                        int tgl1 = tahun+bulan+hari;
                        tglLahirTerdampak.setText(tgl);
                    }
                }, tahun, bulan, hari);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        btnAddTerdampak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nik = nikTerdampak.getText().toString();
                String name = namaTerdampak.getText().toString();
                String address = alamatTerdampak.getText().toString();
                String gender = gender();
                String heir = ahliWarisTerdampak.getText().toString();
                String birthdate = tglLahirTerdampak.getText().toString();

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<AffectedStoreResponse> call1 = apiInterface.setAffected("Bearer " + accessToken,
                        nik ,id_disasters, name, address, gender, heir, birthdate);
                call1.enqueue(new Callback<AffectedStoreResponse>() {
                    @Override
                    public void onResponse(Call<AffectedStoreResponse> call, Response<AffectedStoreResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(TerdampakFormsActivity.this, "Berhasil Menambahkan Data", Toast.LENGTH_SHORT, R.style.success).show();
                            Log.d("retrofit", "success " + response.toString());
                        }else {
                            StyleableToast.makeText(TerdampakFormsActivity.this, "Data Sudah Ada", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error " + response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<AffectedStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(TerdampakFormsActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });

            }
        });

    }

    private String gender() {
        String gender = "";
        if (radioBtnPrTerdampak.isChecked()){
            gender = "P";
        }else if (radioBtnLkTerdampak.isChecked()){
            gender = "L";
        }else {
            gender = "L";
        }

        return gender;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    public void setMode(int selectedMode) {
        switch (selectedMode){
            case R.id.actionhome:
                Intent intent = new Intent(TerdampakFormsActivity.this, MenusActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


}