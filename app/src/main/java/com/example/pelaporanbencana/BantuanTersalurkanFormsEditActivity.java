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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.SocAssistDistributed.SocAssistDistributedResponse;
import com.example.pelaporanbencana.model.SocAssistDistributedShowa.SocAssistDistributedShowaResponse;
import com.example.pelaporanbencana.model.SocAssistShowaResponse.SocAssistShowaResponse;
import com.example.pelaporanbencana.model.Spinner.JenisBantuan;
import com.example.pelaporanbencana.model.Spinner.Satuan;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BantuanTersalurkanFormsEditActivity extends AppCompatActivity {

    Spinner spinnerJenisbantuanT, spinnerSatuanBantuanT;
    TextInputLayout tilJenisBantuanT, tilSatuanBantuanT;
    Button btnEditBantuanT;
    TextInputEditText penerima,jmlBantuanT,batchTersalurkan;
    ImageButton btnPlusBantuanT, btnMinusBantuanT;
    TextView tglKirim;
    ApiInterface apiInterface;
    LinearLayout tglDiterimaLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan_tersalurkan_edit_forms);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        Intent intent = getIntent();
        String id_sa_types = intent.getStringExtra("id_sa_types");
        String batch_sa = intent.getStringExtra("batch");
        int idSaTypes = Integer.parseInt(id_sa_types);
        int batchSa = Integer.parseInt(batch_sa);

        spinnerJenisbantuanT = findViewById(R.id.spinnerJenisBantuanTEdit);
        spinnerSatuanBantuanT = findViewById(R.id.spinnerSatuanBantuanTEdit);
        tilJenisBantuanT = findViewById(R.id.tilJenisBantuanTEdit);
        tilSatuanBantuanT = findViewById(R.id.tilSatuanBantuanTEdit);
        btnEditBantuanT = findViewById(R.id.btnEditBantuanT);
        penerima = findViewById(R.id.penerimaEdit);
        tglKirim = findViewById(R.id.tglDikirimEdit);
        jmlBantuanT = findViewById(R.id.jmlBantuanTEdit);
        btnMinusBantuanT = findViewById(R.id.btnMinusBantuanTEdit);
        btnPlusBantuanT = findViewById(R.id.btnPlusBantuanTEdit);
        tglDiterimaLayout = findViewById(R.id.tglDiterimaLayoutEdit);
        batchTersalurkan = findViewById(R.id.batchTersalurkanEdit);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SocAssistDistributedShowaResponse> call = apiInterface.getOneSaDistributed("Bearer " + accessToken,
                idSaTypes, id_disasters, batchSa);
        call.enqueue(new Callback<SocAssistDistributedShowaResponse>() {
            @Override
            public void onResponse(Call<SocAssistDistributedShowaResponse> call, Response<SocAssistDistributedShowaResponse> response) {
                SocAssistDistributedShowaResponse response1 = response.body();
                if (response1 != null) {
                    if (response1.isSuccess()){
                        penerima.setText(response1.getData().getRecipient());
                        batchTersalurkan.setText(String.valueOf(response1.getData().getBatch()));
                        jmlBantuanT.setText(String.valueOf(response1.getData().getSaDistributedAmount()));
                        tglKirim.setText(response1.getData().getDateSent());

                        String jenis_bantuan = response1.getData().getSaTypesName();
                        int id_jenis_bantuan = response1.getData().getIdSaTypes();
                        String satuan = response1.getData().getSaDistributedUnits();

                        if (jenis_bantuan != null) {
                            jenisBantuanTAddToList(id_jenis_bantuan, jenis_bantuan);
                        }else{
                            jenisBantuanTAddToList(null , "None");
                        }

                        if(satuan != null) {
                            satuanTAddToList(satuan);
                        }else{
                            satuanTAddToList("None");
                        }
                    }else{
                        StyleableToast.makeText(BantuanTersalurkanFormsEditActivity.this, "Gagal dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                        Log.d("retrofit", "error" + response.toString());
                    }
                }

            }

            @Override
            public void onFailure(Call<SocAssistDistributedShowaResponse> call, Throwable t) {
                StyleableToast.makeText(BantuanTersalurkanFormsEditActivity.this, "Gagal", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });

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
                        BantuanTersalurkanFormsEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int tahun, int bulan, int hari) {
                        bulan = bulan + 1;
                        String tgl = tahun+"-"+bulan+"-"+hari;
                        int tgl1 = tahun+bulan+hari;
                        tglKirim.setText(tgl);

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


        btnEditBantuanT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JenisBantuan jenis_bantuan = (JenisBantuan) spinnerJenisbantuanT.getSelectedItem();
                int id_sa_types = displayJenisBantuan(jenis_bantuan);
                String recipient = penerima.getText().toString();
                String date_sent = tglKirim.getText().toString();
                String sa_distributed_units = spinnerSatuanBantuanT.getSelectedItem().toString();
                int sa_distributed_amount = Integer.parseInt(jmlBantuanT.getText().toString());
                int batch = Integer.parseInt(batchTersalurkan.getText().toString());

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<SocAssistDistributedResponse> call = apiInterface.updateSocialAssistDistributed("Bearer " + accessToken,idSaTypes,id_disasters,batchSa,
                        id_sa_types, id_disasters, recipient, date_sent, sa_distributed_amount, sa_distributed_units, batch);

                call.enqueue(new Callback<SocAssistDistributedResponse>() {
                    @Override
                    public void onResponse(Call<SocAssistDistributedResponse> call, Response<SocAssistDistributedResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(BantuanTersalurkanFormsEditActivity.this, "Berhasil Mengubah Data", Toast.LENGTH_SHORT, R.style.success).show();
                            Log.d("retrofit", "success: " + response.toString());
//                            Intent intent = new Intent(BantuanTersalurkanFormsActivity.this, BantuanTersalurkanActivity.class);
//                            startActivity(intent);
                        }else {
                            StyleableToast.makeText(BantuanTersalurkanFormsEditActivity.this, "Gagal Mengubah Data", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error: " + response.toString());
                        }

                    }

                    @Override
                    public void onFailure(Call<SocAssistDistributedResponse> call, Throwable t) {
                        StyleableToast.makeText(BantuanTersalurkanFormsEditActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }

                });

//
            }
        });

    }

    private void jenisBantuanTAddToList(Integer id_jenis_bantuan, String jenis_bantuan) {
        List<JenisBantuan> jenisBantuanList = new ArrayList<>();
        JenisBantuan jenisBantuan0 = new JenisBantuan(id_jenis_bantuan, jenis_bantuan + " (pilihan saat ini)");
        jenisBantuanList.add(jenisBantuan0);
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
    }

    private void satuanTAddToList(String satuan) {
        //list satuan untuk spinnerSatuan
        List<Satuan> listsatuan = new ArrayList<>();
        Satuan satuan0 = new Satuan();
        satuan0.setId(satuan);
        satuan0.setUnits(satuan + " (pilihan saat ini)");
        listsatuan.add(satuan0);

        Satuan satuan1 = new Satuan();
        satuan1.setId("Unit");
        satuan1.setUnits("Unit");
        listsatuan.add(satuan1);

        Satuan satuan2 = new Satuan();
        satuan2.setId("Buah");
        satuan2.setUnits("Buah");
        listsatuan.add(satuan2);

        Satuan satuan3 = new Satuan();
        satuan3.setId("Lembar");
        satuan3.setUnits("Lembar");
        listsatuan.add(satuan3);

        Satuan satuan4 = new Satuan();
        satuan4.setId("Paket");
        satuan4.setUnits("Paket");
        listsatuan.add(satuan4);

        Satuan satuan5 = new Satuan();
        satuan5.setId("Kotak");
        satuan5.setUnits("Kotak");
        listsatuan.add(satuan5);

        Satuan satuan6 = new Satuan();
        satuan6.setId("Kg");
        satuan6.setUnits("Kg");
        listsatuan.add(satuan6);

        ArrayAdapter<Satuan> adapter = new ArrayAdapter<Satuan>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listsatuan);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        spinnerSatuanBantuanT.setAdapter(adapter);
    }

    private int displayJenisBantuan(JenisBantuan jenis_bantuan) {
        int idJenisBantuan = jenis_bantuan.getId_bantuan();
        return idJenisBantuan;
    }
}