package com.example.pelaporanbencana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.FacilityShowa.FacilityShowaResponse;
import com.example.pelaporanbencana.model.FacilityStore.FacilityStoreResponse;
import com.example.pelaporanbencana.model.Spinner.FasilitasKategori;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FasilitasFormsEditActivity extends AppCompatActivity {

    Button btnEditFas;
    TextInputEditText descFacilitiesEdit;
    Spinner spinnerFasilitasKategoriEdit;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasilitas_edit_forms);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        Intent intent = getIntent();
        String id_facilities = intent.getStringExtra("id_facilities");
        int idFacilities = Integer.parseInt(id_facilities);

        spinnerFasilitasKategoriEdit = findViewById(R.id.spinnerFasilitasKategoriEdit);
        descFacilitiesEdit = findViewById(R.id.descFacilitiesEdit);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<FacilityShowaResponse> call = apiInterface.getOneFacility("Bearer " + accessToken, id_disasters, id_facilities);
        call.enqueue(new Callback<FacilityShowaResponse>() {
            @Override
            public void onResponse(Call<FacilityShowaResponse> call, Response<FacilityShowaResponse> response) {
                FacilityShowaResponse response1 = response.body();
                if (response1.isSuccess()){
                    String facilities_category = response1.getData().getFacilitiesCategory();
                    fasilitasAddToList(idFacilities, facilities_category);
                    descFacilitiesEdit.setText(response1.getData().getDescription());
                }else {
                    StyleableToast.makeText(FasilitasFormsEditActivity.this, "Gagal Mengubah Data", Toast.LENGTH_SHORT,R.style.error).show();
                    Log.d("retrofit", "error: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<FacilityShowaResponse> call, Throwable t) {
                StyleableToast.makeText(FasilitasFormsEditActivity.this, "Gagal Mengubah Data", Toast.LENGTH_SHORT,R.style.error).show();
            }
        });


        btnEditFas = findViewById(R.id.btnEditFas);
        btnEditFas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FasilitasKategori fasilitasKategori = (FasilitasKategori) spinnerFasilitasKategoriEdit.getSelectedItem();
                int id_facilities = displayFasilitasKategori(fasilitasKategori);
                String desc_facilities = descFacilitiesEdit.getText().toString();

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<FacilityStoreResponse> call = apiInterface.updateFacility("Bearer " + accessToken, idFacilities, id_disasters, id_facilities,
                        id_disasters, desc_facilities);
                call.enqueue(new Callback<FacilityStoreResponse>() {
                    @Override
                    public void onResponse(Call<FacilityStoreResponse> call, Response<FacilityStoreResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(FasilitasFormsEditActivity.this, "Berhasil Mengubah Data", Toast.LENGTH_SHORT, R.style.success).show();
                            Log.d("retrofit", "success: " + response.toString());
                        }else {
                            StyleableToast.makeText(FasilitasFormsEditActivity.this, "Gagal Mengubah Data", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error: " + response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<FacilityStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(FasilitasFormsEditActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });

//                Intent intent = new Intent(FasilitasFormsActivity.this, MenusActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
    }

    private void fasilitasAddToList(int idFacilities, String facilities_category) {
        ArrayList<FasilitasKategori> fasilitasKategoris = new ArrayList<>();
        FasilitasKategori fasilitasKategori = new FasilitasKategori(idFacilities, facilities_category);
        fasilitasKategoris.add(fasilitasKategori);
        FasilitasKategori fasilitasKategori1 = new FasilitasKategori(1, "Access to Location");
        fasilitasKategoris.add(fasilitasKategori1);
        FasilitasKategori fasilitasKategori2 = new FasilitasKategori(2, "Electricity");
        fasilitasKategoris.add(fasilitasKategori2);
        FasilitasKategori fasilitasKategori3 = new FasilitasKategori(3, "Water");
        fasilitasKategoris.add(fasilitasKategori3);
        FasilitasKategori fasilitasKategori4 = new FasilitasKategori(4, "Transportation");
        fasilitasKategoris.add(fasilitasKategori4);
        FasilitasKategori fasilitasKategori5 = new FasilitasKategori(5, "Communication Line");
        fasilitasKategoris.add(fasilitasKategori5);
        FasilitasKategori fasilitasKategori6 = new FasilitasKategori(6, "Medical Facility");
        fasilitasKategoris.add(fasilitasKategori6);

        ArrayAdapter<FasilitasKategori> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, fasilitasKategoris);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        spinnerFasilitasKategoriEdit.setAdapter(adapter);
    }


    private int displayFasilitasKategori(FasilitasKategori fasilitasKategori) {
        int id_facilities = fasilitasKategori.getId_facilities();
        return id_facilities;
    }


}