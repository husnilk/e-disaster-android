package com.example.pelaporanbencana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pelaporanbencana.adapter.KerusakanKategoriRecViewAdapter;
import com.example.pelaporanbencana.adapter.KerusakanRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.DamageCategoryiShowall.DamageCategoryResponse;
import com.example.pelaporanbencana.model.DamageCategoryiShowall.DataItem;
import com.example.pelaporanbencana.model.DamageShowIdMax.DamageShowIdMaxResponse;
import com.example.pelaporanbencana.model.DamageStore.DamageStoreResponse;
import com.example.pelaporanbencana.model.KerusakanKategori;
import com.example.pelaporanbencana.model.Spinner.BidangKerusakan;
import com.example.pelaporanbencana.model.Spinner.Satuan;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KerusakanFormsActivity extends AppCompatActivity implements KerusakanKategoriRecViewAdapter.KerusakanKategoriOnClickListener {

    Button btnNextKerusakan;
//    Spinner spinnerBidangKerusakan;
    RecyclerView recViewKategoriKerusakan;
    KerusakanKategoriRecViewAdapter kategoriRecViewAdapter;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerusakan_forms);

        //token
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        //kode_kejadian
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        //spinnerBidangKerusakan = findViewById(R.id.spinnerBidangKerusakan);
        recViewKategoriKerusakan = findViewById(R.id.recViewKategoriKerusakan);
        kategoriRecViewAdapter = new KerusakanKategoriRecViewAdapter();
        kategoriRecViewAdapter.setListener(this);
        recViewKategoriKerusakan.setAdapter(kategoriRecViewAdapter);
        recViewKategoriKerusakan.setLayoutManager(new GridLayoutManager(this,1));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DamageCategoryResponse> call = apiInterface.getDamageCategory("Bearer " + accessToken);
        call.enqueue(new Callback<DamageCategoryResponse>() {
            @Override
            public void onResponse(Call<DamageCategoryResponse> call, Response<DamageCategoryResponse> response) {
                DamageCategoryResponse response1 = response.body();
                ArrayList<KerusakanKategori> listData = new ArrayList<>();

                if (response1 != null && response1.isSuccess()){
                    List<DataItem> itemList = response1.getData();

                    for (DataItem item : itemList){
                        KerusakanKategori kerusakanKategori = new KerusakanKategori(
                                item.getIdDamageCategory(),
                                item.getCategory()
                        );
                        listData.add(kerusakanKategori);
                    }

                }else{
                    StyleableToast.makeText(KerusakanFormsActivity.this, "Gagal dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                }
                kategoriRecViewAdapter.setKerusakanKategoris(listData);
            }

            @Override
            public void onFailure(Call<DamageCategoryResponse> call, Throwable t) {
                StyleableToast.makeText(KerusakanFormsActivity.this, "Gagal dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });

        //list damage category spinner Bidang Kerusakan
//        List<BidangKerusakan> listBidangKerusakan = new ArrayList<>();
//        BidangKerusakan bidangKerusakan1 = new BidangKerusakan("A1", "Pemukiman");
//        listBidangKerusakan.add(bidangKerusakan1);
//
//        BidangKerusakan bidangKerusakan2 = new BidangKerusakan("B2", "Jalan Lingkungan");
//        listBidangKerusakan.add(bidangKerusakan2);
//
//        BidangKerusakan bidangKerusakan3 = new BidangKerusakan("A3", "Pertanian");
//        listBidangKerusakan.add(bidangKerusakan3);
//
//        ArrayAdapter<BidangKerusakan> adapter1 = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listBidangKerusakan);
//        adapter1.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
//
//        spinnerBidangKerusakan.setAdapter(adapter1);
//
//        //Get Data
//        BidangKerusakan bidangKerusakan = (BidangKerusakan) spinnerBidangKerusakan.getSelectedItem();
//        String id_damage_category = displayBidangKerusakan(bidangKerusakan);

//        //button next kerusakan ke server
//        btnNextKerusakan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }

//    private String displayBidangKerusakan(BidangKerusakan bidangKerusakan) {
//        String id = bidangKerusakan.getId();
//        String bidang_kerusakan = bidangKerusakan.getBidang_kerusakan();
//
//        String data = "id: " + id;
//
//        return id;
//    }

    @Override
    public void onClick(View view, KerusakanKategori kerusakanKategori) {
        String id_damage_category = kerusakanKategori.getId_damage_category();
        Intent intent = new Intent(KerusakanFormsActivity.this, KerusakanForms2Activity.class);
        intent.putExtra("id_damage_category", id_damage_category);
        startActivity(intent);
    }
}