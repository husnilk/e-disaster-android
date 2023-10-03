package com.example.pelaporanbencana;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.VolunteerShowa.VolunteerShowaResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RelawanFormsEditActivity extends AppCompatActivity {
    TextInputEditText idRelawan, namaRelawan, skillRelawan;
    RadioGroup rgJenisKelaminRelawan;
    RadioButton radioBtnLkRelawan, radioBtnPrRelawan;
    LinearLayout tglLahirLayoutRelawan;
    TextView tglLhrRelawan;
    Button btnTambahRelawan;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relawan_forms);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        Intent intent = getIntent();
        String id_volunteer = intent.getStringExtra("id_volunteer");
        String id_volunteer_org = intent.getStringExtra("id_volunteer_org");

        idRelawan = findViewById(R.id.idRelawan);
        namaRelawan = findViewById(R.id.namaRelawan);
        skillRelawan = findViewById(R.id.skillRelawan);
        rgJenisKelaminRelawan = findViewById(R.id.rgJenisKelaminRelawan);
        radioBtnLkRelawan = findViewById(R.id.radioBtnLkRelawan);
        radioBtnPrRelawan = findViewById(R.id.radioBtnPrRelawan);
        tglLahirLayoutRelawan = findViewById(R.id.tglLahirLayoutRelawan);
        tglLhrRelawan = findViewById(R.id.tglLhrRelawan);
        btnTambahRelawan = findViewById(R.id.btnTambahRelawan);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<VolunteerShowaResponse> call = apiInterface.getOneVolunteer("Bearer " + accessToken, id_volunteer, id_volunteer_org);
        call.enqueue(new Callback<VolunteerShowaResponse>() {
            @Override
            public void onResponse(Call<VolunteerShowaResponse> call, Response<VolunteerShowaResponse> response) {
                VolunteerShowaResponse volunteerShowaResponse = response.body();
                if (response.isSuccessful()){
                    idRelawan.setText(id_volunteer);
                    namaRelawan.setText(volunteerShowaResponse.getData().getVolunteersName());
                    skillRelawan.setText(volunteerShowaResponse.getData().getVolunteersSkill());
                    tglLhrRelawan.setText(volunteerShowaResponse.getData().getVolunteersBirthdate());
                    String gender = volunteerShowaResponse.getData().getVolunteersGender();
                    if (gender == null || gender.equals("")){
                        radioBtnLkRelawan.setChecked(false);
                        radioBtnPrRelawan.setChecked(false);
                    }else if (gender.equals("P")){
                        radioBtnLkRelawan.setChecked(false);
                        radioBtnPrRelawan.setChecked(true);
                    }else if (gender.equals("L")){
                        radioBtnLkRelawan.setChecked(true);
                        radioBtnPrRelawan.setChecked(false);
                    }

                    StyleableToast.makeText(RelawanFormsEditActivity.this, "Data " + volunteerShowaResponse.getData().getVolunteersName() +" ditemukan!" , Toast.LENGTH_SHORT, R.style.info).show();

                }else{
//                            StyleableToast.makeText(RelawanFormsActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<VolunteerShowaResponse> call, Throwable t) {
                StyleableToast.makeText(RelawanFormsEditActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });

        //DatePicker
        Calendar calendar = Calendar.getInstance();
        final int tahun = calendar.get(Calendar.YEAR);
        final int bulan = calendar.get(Calendar.MONTH);
        final int hari = calendar.get(Calendar.DAY_OF_MONTH);

        //DateCalendar
        tglLahirLayoutRelawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RelawanFormsEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int tahun, int bulan, int hari) {
                        bulan = bulan + 1;
                        String tgl = tahun+"-"+bulan+"-"+hari;
                        int tgl1 = tahun+bulan+hari;
                        tglLhrRelawan.setText(tgl);
                    }
                }, tahun, bulan, hari);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        btnTambahRelawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_relawan = idRelawan.getText().toString();
                String nama_relawan = namaRelawan.getText().toString();
                String skill_relawan = skillRelawan.getText().toString();
                String tgl_lahir = tglLhrRelawan.getText().toString();
                String gender = getGender();

                Intent intent = new Intent(RelawanFormsEditActivity.this, RelawanFormsEdit2Activity.class);
                intent.putExtra("idVolunteer", id_volunteer);
                intent.putExtra("id_volunteer", id_relawan);
                intent.putExtra("volunteer_name", nama_relawan);
                intent.putExtra("volunteer_skill", skill_relawan);
                intent.putExtra("volunteer_birthdate", tgl_lahir);
                intent.putExtra("volunteer_gender", gender);
                intent.putExtra("id_volunteer_org", id_volunteer_org);
                startActivity(intent);
            }
        });




    }

    private String getGender() {
        if (radioBtnPrRelawan.isChecked()){
            String gender = "P";
            return gender;
        }else {
            String gender = "L";
            return gender;
        }
    }
}