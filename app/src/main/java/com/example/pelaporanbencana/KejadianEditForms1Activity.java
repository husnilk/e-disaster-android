package com.example.pelaporanbencana;

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
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.DisasterShowInMenus.DisInMenusResponse;
import com.example.pelaporanbencana.model.DisasterStore.DisasterStoreResponse;
import com.example.pelaporanbencana.model.Spinner.JenisBantuan;
import com.example.pelaporanbencana.model.Spinner.JenisBencana;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KejadianEditForms1Activity extends AppCompatActivity {

    TextView waktuKejadian, tglKejadian, tvKodeKejadian, latLokKejadian, longLokKejadian, desaKejadian, kelurahanKejadian, idKelurahanKejadian ;
    Spinner spinnerjenisBencana;
    LinearLayout waktuKejadianLayout, tglKejadianLayout, tempatKejadianLayoutEdit, koordinatLayoutEdit;
    int jam, menit;
    ImageView imgKejadianBencana;
    FloatingActionButton buttonCamera;
    Button btnKej1;
    TextView tvKodeKejadianEdit;
//    TextInputEditText descKejadian;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kejadian_edit_forms1);

        //token
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        waktuKejadian = findViewById(R.id.waktuKejadianEdit);
        waktuKejadianLayout = findViewById(R.id.waktuKejadianLayoutEdit);
        tglKejadian = findViewById(R.id.tglKejadianEdit);
        tglKejadianLayout = findViewById(R.id.tglKejadianLayoutEdit);
        imgKejadianBencana = findViewById(R.id.imgKejadianBencanaEdit);
        buttonCamera = findViewById(R.id.buttonCameraEdit);
        btnKej1 = findViewById(R.id.btnKej1Edit);
        tempatKejadianLayoutEdit = findViewById(R.id.tempatKejadianLayoutEdit);
        koordinatLayoutEdit = findViewById(R.id.koordinatLayoutEdit);
        spinnerjenisBencana = findViewById(R.id.spinnerjenisBencanaEdit);
//        descKejadian = findViewById(R.id.descKejadianEdit);
        latLokKejadian = findViewById(R.id.latLokKejadianEdit);
        longLokKejadian = findViewById(R.id.longLokKejadianEdit);
        desaKejadian = findViewById(R.id.desaKejadianEdit);
        kelurahanKejadian = findViewById(R.id.kelurahanKejadianEdit);
        idKelurahanKejadian = findViewById(R.id.idKelurahanKejadianEdit);
        tvKodeKejadian = findViewById(R.id.tvKodeKejadianEdit);

        Intent intent = getIntent();
        String id_disasters = intent.getStringExtra("id_disasters");
        String id_urban_village = intent.getStringExtra("id_urban_village");
        String disasters_village = intent.getStringExtra("jorong");
        String lat_disasters = intent.getStringExtra("latBecana");
        String long_disasters = intent.getStringExtra("longBecana");
        String disasters_types = intent.getStringExtra("disasters_types");
        String disasters_date = intent.getStringExtra("disasters_date");
        String disasters_time = intent.getStringExtra("disasters_time");
        String disasters_desc = intent.getStringExtra("disasters_desc");
        String urban_village_name = intent.getStringExtra("urban_village_name");

//        tvKodeKejadian.setText(id_disasters);
        idKelurahanKejadian.setText(id_urban_village);
        kelurahanKejadian.setText(urban_village_name);
        desaKejadian.setText(disasters_village);
        latLokKejadian.setText(lat_disasters);
        longLokKejadian.setText(long_disasters);
        disastersTypesAddToList(disasters_types);
        tglKejadian.setText(disasters_date);
        waktuKejadian.setText(disasters_time);
//        descKejadian.setText(disasters_desc);

        //TimePicker
        waktuKejadianLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        KejadianEditForms1Activity.this,
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
                        KejadianEditForms1Activity.this, new DatePickerDialog.OnDateSetListener() {
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
        if (ContextCompat.checkSelfPermission(KejadianEditForms1Activity.this,
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(KejadianEditForms1Activity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },100);
        }
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });

        //pilih kelurahan
        koordinatLayoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KejadianEditForms1Activity.this, KoordinatKejadianEditActivity.class);
                intent.putExtra("jorong", desaKejadian.getText().toString());
                intent.putExtra("lat_bencana", latLokKejadian.getText().toString());
                intent.putExtra("long_bencana", longLokKejadian.getText().toString());
                startActivity(intent);
                finish();
            }
        });

        tempatKejadianLayoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KejadianEditForms1Activity.this, LokasiBencanaEditActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnKej1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status_kejadian = "1";
                String id_urban_village = idKelurahanKejadian.getText().toString();
                String urban_village_name = kelurahanKejadian.getText().toString();
                String disasters_village = desaKejadian.getText().toString();

                JenisBencana jenisBencana = (JenisBencana) spinnerjenisBencana.getSelectedItem();
                String disasters_types = displayJenisBencana(jenisBencana);
                String disasters_date = tglKejadian.getText().toString();
                String disasters_time = waktuKejadian.getText().toString();
//                String disasters_desc = descKejadian.getText().toString();
                String lat_disasters = latLokKejadian.getText().toString();
                String long_disasters = longLokKejadian.getText().toString();

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<DisasterStoreResponse> call = apiInterface.updateDisaster("Bearer " + accessToken, id_disasters,
                        id_disasters, id_urban_village, disasters_types, disasters_date, disasters_time, long_disasters,
                        lat_disasters, disasters_village, status_kejadian );
                call.enqueue(new Callback<DisasterStoreResponse>() {
                    @Override
                    public void onResponse(Call<DisasterStoreResponse> call, Response<DisasterStoreResponse> response) {

                        if (response.isSuccessful()){
                            StyleableToast.makeText(KejadianEditForms1Activity.this, "Berhasil Mengubah Data", Toast.LENGTH_SHORT, R.style.success).show();

                        }else{
                            StyleableToast.makeText(KejadianEditForms1Activity.this,  disasters_types + "Gagal Mengubah Data", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error: " + response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<DisasterStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(KejadianEditForms1Activity.this,  id_disasters + "Gagal Bruh" + accessToken , Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });

            }
        });

        }

    private String displayJenisBencana(JenisBencana jenisBencana) {
        String jenis_bencana = jenisBencana.getIdJenisKejadian();
        return jenis_bencana;
    }

    private void disastersTypesAddToList(String disasters_types) {
        List<JenisBencana> bencanaList = new ArrayList<>();
        JenisBencana bencana = new JenisBencana(disasters_types, disasters_types + " (pilihan saat ini)");
        bencanaList.add(bencana);
        JenisBencana bencana1 = new JenisBencana("Gempa Bumi", "Gempa Bumi");
        bencanaList.add(bencana1);
        JenisBencana bencana2 = new JenisBencana("Letusan Gunung Api", "Letusan Gunung Api");
        bencanaList.add(bencana2);
        JenisBencana bencana3 = new JenisBencana("Tsunami", "Tsunami");
        bencanaList.add(bencana3);
        JenisBencana bencana4 = new JenisBencana("Tanah Longsor", "Tanah Longsor");
        bencanaList.add(bencana4);
        JenisBencana bencana5 = new JenisBencana("Banjir", "Banjir");
        bencanaList.add(bencana5);
        JenisBencana bencana6 = new JenisBencana("Banjir Bandang", "Banjir Bandang");
        bencanaList.add(bencana6);
        JenisBencana bencana7 = new JenisBencana("Kekeringan", "Kekeringan");
        bencanaList.add(bencana7);
        JenisBencana bencana8 = new JenisBencana("Kebakaran", "Kebakaran");
        bencanaList.add(bencana8);
        JenisBencana bencana9 = new JenisBencana("Kebakaran Hutan dan Lahan", "Kebakaran Hutan dan Lahan");
        bencanaList.add(bencana9);
        JenisBencana bencana10 = new JenisBencana("Angin Puting Beliung", "Angin Puting Beliungn");
        bencanaList.add(bencana10);
        JenisBencana bencana11 = new JenisBencana("Gelombang Pasang", "Gelombang Pasang");
        bencanaList.add(bencana11);
        JenisBencana bencana12 = new JenisBencana("Abrasi", "Abrasi");
        bencanaList.add(bencana12);

        ArrayAdapter<JenisBencana> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, bencanaList);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerjenisBencana.setAdapter(adapter);
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