package com.example.pelaporanbencana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.DamageShowa.DamageShowaResponse;
import com.example.pelaporanbencana.model.DamageStore.DamageStoreResponse;
import com.example.pelaporanbencana.model.Spinner.BidangKerusakan;
import com.example.pelaporanbencana.model.Spinner.Satuan;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KerusakanFormsEditActivity extends AppCompatActivity {

    TextInputEditText jmlKerusakan, detailKerusakanEdit;
    ImageButton btnPlus, btnMinus;
    Button btnEditKerusakan;
    RadioGroup rgJenisKerusakan;
    RadioButton radioButtonRingan, radioButtonSedang, radioButtonBerat;
    Spinner spinnerSatuan, spinnerBidangKerusakan;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerusakan_forms_edit);

        //token
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        //kode_kejadian
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        //id_damage
        Intent intent = getIntent();
        String id_damage = intent.getStringExtra("id_damage");
        String id_damage_category = intent.getStringExtra("id_damage_category");
        String damage_unit = intent.getStringExtra("damage_unit");
        String damage_types = intent.getStringExtra("damage_types");
        String category = intent.getStringExtra("category");
        String damage_amount = intent.getStringExtra("damage_amount");

        jmlKerusakan = findViewById(R.id.jmlKerusakan);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnEditKerusakan = findViewById(R.id.btnEditKerusakan);
        spinnerSatuan = findViewById(R.id.spinnerSatuan);
        spinnerBidangKerusakan = findViewById(R.id.spinnerBidangKerusakan);
        radioButtonRingan = findViewById(R.id.radioButtonRingan);
        radioButtonSedang = findViewById(R.id.radioButtonSedang);
        radioButtonBerat = findViewById(R.id.radioButtonBerat);
        detailKerusakanEdit = findViewById(R.id.detailKerusakanEdit);


        //button + jumlah kerusakan
        jmlKerusakan.setText("1");
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(String.valueOf(jmlKerusakan.getText()));
                a = a + 1;
                jmlKerusakan.setText(String.valueOf(a));
            }
        });

        //button - jumlah kerusakan
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int b = Integer.parseInt(String.valueOf(jmlKerusakan.getText()));
                if (b <= 0) {
                    jmlKerusakan.setText("0");
                } else {
                    b = b - 1;
                    jmlKerusakan.setText(String.valueOf(b));
                }
            }
        });

        //list satuan untuk spinnerSatuan
        List<Satuan> listsatuan = new ArrayList<>();
        Satuan satuan = new Satuan();
        satuan.setId(damage_unit);
        satuan.setUnits(damage_unit);
        listsatuan.add(satuan);

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

        spinnerSatuan.setAdapter(adapter);


        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DamageShowaResponse> callResponse = apiInterface.getDamageShowOne("Bearer " + accessToken, id_damage);
        callResponse.enqueue(new Callback<DamageShowaResponse>() {
            @Override
            public void onResponse(Call<DamageShowaResponse> call, Response<DamageShowaResponse> response) {
                DamageShowaResponse damageShowaResponse = response.body();
                if (response.isSuccessful()){

                    int damageAmount = damageShowaResponse.getData().getDamageAmount();
                    String damageTypes = damageShowaResponse.getData().getDamageTypes();
                    int id_damages = damageShowaResponse.getData().getIdDamages();
                    String damageName = damageShowaResponse.getData().getDamageName();
                    detailKerusakanEdit.setText(damageName);

                    //setDariIntent
                    jmlKerusakan.setText(String.valueOf(damageAmount));

                    if (damageTypes.equals("1") ){
                        radioButtonRingan.setChecked(true);
                        radioButtonSedang.setChecked(false);
                        radioButtonBerat.setChecked(false);
                    }else if(damageTypes.equals("2") ){
                        radioButtonRingan.setChecked(false);
                        radioButtonSedang.setChecked(true);
                        radioButtonBerat.setChecked(false);
                    }else if(damageTypes.equals("3") ){
                        radioButtonRingan.setChecked(false);
                        radioButtonSedang.setChecked(false);
                        radioButtonBerat.setChecked(true);
                    }

                    String idDamageCategory = damageShowaResponse.getData().getIdDamageCategory();
                    String damageCategory = damageShowaResponse.getData().getCategory();
                    damageCategoryAddToList(idDamageCategory, damageCategory);

                    BidangKerusakan bidangKerusakan = (BidangKerusakan) spinnerBidangKerusakan.getSelectedItem();
                    displayBidangKerusakan(bidangKerusakan);

                    Satuan satuan = (Satuan) spinnerSatuan.getSelectedItem();
                    displaySatuanData(satuan);


                    //button tambah kerusakan ke server
                    btnEditKerusakan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            BidangKerusakan bidangKerusakan = (BidangKerusakan) spinnerBidangKerusakan.getSelectedItem();
                            String id_damage_category1 = displayBidangKerusakan(bidangKerusakan);

                            String damage_types = jenisKerusakan();
                            int damage_amount = Integer.parseInt(jmlKerusakan.getText().toString());

                            Satuan satuan = (Satuan) spinnerSatuan.getSelectedItem();
                            String damage_units = displaySatuanData(satuan);

                            String damage_name = detailKerusakanEdit.getText().toString();

                            apiInterface = ApiClient.getClient().create(ApiInterface.class);
                            Call<DamageStoreResponse> call = apiInterface.setDamageUpdate("Bearer " + accessToken, id_damages, id_damage_category,id_disasters, damage_types,
                                    id_damages, id_damage_category1, id_disasters,  damage_types, damage_name, damage_amount, damage_units);

                            call.enqueue(new Callback<DamageStoreResponse>() {
                                @Override
                                public void onResponse(Call<DamageStoreResponse> call, Response<DamageStoreResponse> response) {
                                    if (response.isSuccessful()) {
                                        StyleableToast.makeText(KerusakanFormsEditActivity.this, "Berhasil Mengubah Data", Toast.LENGTH_SHORT, R.style.success).show();
                                        Log.d("retrofit", "success :" + response.toString() + id_damage_category + damage_types + id_disasters);
                                        Intent intent = new Intent(KerusakanFormsEditActivity.this, KerusakanActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        StyleableToast.makeText(KerusakanFormsEditActivity.this, "Gagal Mengubah Data", Toast.LENGTH_SHORT, R.style.error).show();
                                        Log.d("retrofit", "error :" + response.toString() + id_damage_category + damage_types + id_disasters);
                                    }
                                }

                                @Override
                                public void onFailure(Call<DamageStoreResponse> call, Throwable t) {
                                    StyleableToast.makeText(KerusakanFormsEditActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                                }
                            });

                        }
                    });

                }else{
                    StyleableToast.makeText(KerusakanFormsEditActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error :" + response.toString() + id_damage_category + damage_types + id_disasters);
                }
            }

            @Override
            public void onFailure(Call<DamageShowaResponse> call, Throwable t) {
                StyleableToast.makeText(KerusakanFormsEditActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });

    }

    private void damageCategoryAddToList(String idDamageCategory, String damageCategory) {
        //list damage category spinner Bidang Kerusakan
        List<BidangKerusakan> listBidangKerusakan = new ArrayList<>();
        BidangKerusakan bidangKerusakan = new BidangKerusakan(idDamageCategory, damageCategory + " (pilihan saat ini)");
        listBidangKerusakan.add(bidangKerusakan);

        BidangKerusakan bidangKerusakan1 = new BidangKerusakan("A1", "Pemukiman");
        listBidangKerusakan.add(bidangKerusakan1);

        BidangKerusakan bidangKerusakan2 = new BidangKerusakan("A2", "Jalan Lingkungan");
        listBidangKerusakan.add(bidangKerusakan2);

        BidangKerusakan bidangKerusakan3 = new BidangKerusakan("A3", "Sistem Air Minum");
        listBidangKerusakan.add(bidangKerusakan3);

        BidangKerusakan bidangKerusakan4 = new BidangKerusakan("B1", "Transportasi Darat");
        listBidangKerusakan.add(bidangKerusakan4);

        BidangKerusakan bidangKerusakan5 = new BidangKerusakan("B2", "Transportasi Air");
        listBidangKerusakan.add(bidangKerusakan5);

        BidangKerusakan bidangKerusakan6 = new BidangKerusakan("B3", "Transportasi Udara");
        listBidangKerusakan.add(bidangKerusakan6);

        BidangKerusakan bidangKerusakan7 = new BidangKerusakan("B4", "Sistem Drainase");
        listBidangKerusakan.add(bidangKerusakan7);

        BidangKerusakan bidangKerusakan8 = new BidangKerusakan("B5", "Sistem Listrik");
        listBidangKerusakan.add(bidangKerusakan8);

        BidangKerusakan bidangKerusakan9 = new BidangKerusakan("C1", "Pertanian");
        listBidangKerusakan.add(bidangKerusakan9);

        BidangKerusakan bidangKerusakan10 = new BidangKerusakan("C2", "Perkebunan");
        listBidangKerusakan.add(bidangKerusakan10);

        BidangKerusakan bidangKerusakan11 = new BidangKerusakan("C3", "Peternakan");
        listBidangKerusakan.add(bidangKerusakan11);

        BidangKerusakan bidangKerusakan12 = new BidangKerusakan("C4", "Perikanan");
        listBidangKerusakan.add(bidangKerusakan12);

        BidangKerusakan bidangKerusakan13 = new BidangKerusakan("C5", "Perdagangan");
        listBidangKerusakan.add(bidangKerusakan13);

        BidangKerusakan bidangKerusakan15 = new BidangKerusakan("C6", "Perindustrian");
        listBidangKerusakan.add(bidangKerusakan15);

        BidangKerusakan bidangKerusakan16 = new BidangKerusakan("C7", "Pariwisata");
        listBidangKerusakan.add(bidangKerusakan16);

        BidangKerusakan bidangKerusakan17 = new BidangKerusakan("D1", "Pendidikan");
        listBidangKerusakan.add(bidangKerusakan17);

        BidangKerusakan bidangKerusakan18 = new BidangKerusakan("D2", "Keagamaan");
        listBidangKerusakan.add(bidangKerusakan18);

        BidangKerusakan bidangKerusakan19 = new BidangKerusakan("D3", "Kesehatan");
        listBidangKerusakan.add(bidangKerusakan19);

        BidangKerusakan bidangKerusakan20 = new BidangKerusakan("D3", "Kesehatan");
        listBidangKerusakan.add(bidangKerusakan20);

        BidangKerusakan bidangKerusakan21 = new BidangKerusakan("E1", "Perkantoran");
        listBidangKerusakan.add(bidangKerusakan21);

        BidangKerusakan bidangKerusakan22 = new BidangKerusakan("E2", "Perbankan");
        listBidangKerusakan.add(bidangKerusakan22);

        BidangKerusakan bidangKerusakan23 = new BidangKerusakan("E3", "Lingkungan");
        listBidangKerusakan.add(bidangKerusakan23);

        BidangKerusakan bidangKerusakan24 = new BidangKerusakan("F", "Lainnya");
        listBidangKerusakan.add(bidangKerusakan24);

        ArrayAdapter<BidangKerusakan> adapter1 = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listBidangKerusakan);
        adapter1.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        spinnerBidangKerusakan.setAdapter(adapter1);

    }

    private String displaySatuanData(Satuan satuan) {
        String id = satuan.getId();
        String units = satuan.getUnits();

        String satuanData = "units" + units;

        return id;
    }

    private String displayBidangKerusakan(BidangKerusakan bidangKerusakan) {
        String id = bidangKerusakan.getId();
        String bidang_kerusakan = bidangKerusakan.getBidang_kerusakan();

        String data = "id: " + id;

        return id;
    }

    private String jenisKerusakan() {
        if (radioButtonRingan.isChecked()) {
            String jenis_kerusakan = "1";
            return jenis_kerusakan;
        } else if (radioButtonSedang.isChecked()) {
            String jenis_kerusakan = "2";
            return jenis_kerusakan;
        } else if (radioButtonBerat.isChecked()) {
            String jenis_kerusakan = "3";
            return jenis_kerusakan;
        }

        return "0";
    }
}