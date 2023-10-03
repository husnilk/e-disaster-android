package com.example.pelaporanbencana;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.CameraCaptureSession;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.DisasterStore.DisasterStoreResponse;
import com.example.pelaporanbencana.model.Spinner.JenisBencana;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KejadianForms1Activity extends AppCompatActivity {

    TextView waktuKejadian, tglKejadian, lokKejadian, tvKodeKejadian;
    EditText editTextDetLok;
    Spinner spinnerjenisBencana;
    LinearLayout waktuKejadianLayout, tglKejadianLayout, lokKejadianLayout, latlongKejadianLayout;
    int jam, menit;
    ImageView imgKejadianBencana;
    FloatingActionButton buttonCamera;
    Button btnKej1;
    ProgressDialog progressDialog;
    TextInputEditText descKejadian;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kejadian_forms1);

        waktuKejadian = findViewById(R.id.waktuKejadian);
        waktuKejadianLayout = findViewById(R.id.waktuKejadianLayout);
        tglKejadian = findViewById(R.id.tglKejadian);
        tglKejadianLayout = findViewById(R.id.tglKejadianLayout);
        imgKejadianBencana = findViewById(R.id.imgKejadianBencana);
        buttonCamera = findViewById(R.id.buttonCamera);
        btnKej1 = findViewById(R.id.btnKej1);
        lokKejadianLayout = findViewById(R.id.lokKejadianLayout);
        latlongKejadianLayout = findViewById(R.id.latlongKejadianLayout);
        editTextDetLok = findViewById(R.id.editTextDetLok);
        spinnerjenisBencana = findViewById(R.id.spinnerjenisBencana);
        descKejadian = findViewById(R.id.descKejadian);
        progressDialog = new ProgressDialog((KejadianForms1Activity.this));

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");
        String id_disasters = pref.getString("id_disasters", "");

        Intent intent = getIntent();
        String urbanVillageName = intent.getStringExtra("urban_village_name");
        String idUrbanVillage = intent.getStringExtra("id_urban_village");
        String disastersVillage = intent.getStringExtra("jorong");
        String latDisasters = intent.getStringExtra("lat_bencana");
        String longDisasters = intent.getStringExtra("long_bencana");

        lokKejadian = findViewById(R.id.lokKejadian);
        lokKejadian.setText(idUrbanVillage +"-" + urbanVillageName);

        tvKodeKejadian = findViewById(R.id.tvKodeKejadian);
        tvKodeKejadian.setText(id_disasters);

        List<JenisBencana> bencanaList = new ArrayList<>();
        JenisBencana bencana1 = new JenisBencana("1", "Gempa Bumi");
        bencanaList.add(bencana1);
        JenisBencana bencana2 = new JenisBencana("2", "Letusan Gunung Api");
        bencanaList.add(bencana2);
        JenisBencana bencana3 = new JenisBencana("3", "Tsunami");
        bencanaList.add(bencana3);
        JenisBencana bencana4 = new JenisBencana("4", "Tanah Longsor");
        bencanaList.add(bencana4);
        JenisBencana bencana5 = new JenisBencana("5", "Banjir");
        bencanaList.add(bencana5);
        JenisBencana bencana6 = new JenisBencana("6", "Banjir Bandang");
        bencanaList.add(bencana6);
        JenisBencana bencana7 = new JenisBencana("7", "Kekeringan");
        bencanaList.add(bencana7);
        JenisBencana bencana8 = new JenisBencana("8", "Kebakaran");
        bencanaList.add(bencana8);
        JenisBencana bencana9 = new JenisBencana("9", "Kebakaran Hutan dan Lahan");
        bencanaList.add(bencana9);
        JenisBencana bencana10 = new JenisBencana("10", "Angin Puting Beliungn");
        bencanaList.add(bencana10);
        JenisBencana bencana11 = new JenisBencana("11", "Gelombang Pasang");
        bencanaList.add(bencana11);
        JenisBencana bencana12 = new JenisBencana("12", "Abrasi");
        bencanaList.add(bencana12);

        ArrayAdapter<JenisBencana> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, bencanaList);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerjenisBencana.setAdapter(adapter);

        //TimePicker
        waktuKejadianLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        KejadianForms1Activity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                jam = hour;
                                menit = minute;

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0,0,0,jam, menit);
                                waktuKejadian.setText(DateFormat.format("HH:mm:ss", calendar));

                            }
                        },24, 0, true
                );
                timePickerDialog.updateTime(jam, menit);
                timePickerDialog.show();
            }
        });

        //DatePicker
        Calendar calendar1 = Calendar.getInstance();
        final int tahun = calendar1.get(Calendar.YEAR);
        final int bulan = calendar1.get(Calendar.MONTH);
        final int hari = calendar1.get(Calendar.DAY_OF_MONTH);
        //DateCalender
        tglKejadianLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        KejadianForms1Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int tahun, int bulan, int hari) {
                        bulan = bulan + 1;
                        String tgl = tahun+"-"+bulan+"-"+hari;
                        int tgl1 = tahun+bulan+hari;
                        tglKejadian.setText(tgl);

                    }
                },tahun, bulan, hari);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }

        });

        //Camera
        //Request Permissions
        if (ContextCompat.checkSelfPermission(KejadianForms1Activity.this,
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(KejadianForms1Activity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },100);
        }
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,100);

                Intent intent1 = new Intent(KejadianForms1Activity.this, CameraActivity.class);
                startActivity(intent1);
            }
        });

        //pilih kelurahan
        lokKejadianLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("access_token", accessToken);
                editor.apply();

                Intent intent = new Intent(KejadianForms1Activity.this, LokasiBencanaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        latlongKejadianLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KejadianForms1Activity.this, KoordinatKejadianActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnKej1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Inserting your data on server");
                progressDialog.show();

                String disasters_types = spinnerjenisBencana.getSelectedItem().toString();
                String disasters_date = tglKejadian.getText().toString();
                String disasters_time = waktuKejadian.getText().toString();
                String status_kejadian = "1";
                String desc_kejadian = descKejadian.getText().toString();

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<DisasterStoreResponse> call = apiInterface.setDisaster("Bearer " + accessToken,
                        id_disasters, idUrbanVillage, disasters_types, disasters_date, disasters_time, longDisasters,
                        latDisasters, disastersVillage, desc_kejadian, status_kejadian);


                call.enqueue(new Callback<DisasterStoreResponse>() {
                    @Override
                    public void onResponse(Call<DisasterStoreResponse> call, Response<DisasterStoreResponse> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()){
                            Toast.makeText(KejadianForms1Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(KejadianForms1Activity.this, MenusActivity.class);
                            intent1.putExtra("id_disasters_1", id_disasters);
                            startActivity(intent1);
                            finish();

                        }else{
                            Toast.makeText(KejadianForms1Activity.this,  disasters_types + "Gagal", Toast.LENGTH_SHORT).show();
                            Log.d("retrofit", "error: " + response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<DisasterStoreResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(KejadianForms1Activity.this,  id_disasters + "Gagal Bruh" + accessToken , Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            imgKejadianBencana.setImageBitmap(captureImage);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}