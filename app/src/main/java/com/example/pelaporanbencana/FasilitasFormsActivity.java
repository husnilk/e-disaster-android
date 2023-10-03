package com.example.pelaporanbencana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.FacilityStore.FacilityStoreResponse;
import com.example.pelaporanbencana.model.Spinner.FasilitasKategori;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FasilitasFormsActivity extends AppCompatActivity {

    Button btnTambahFas;
    TextInputEditText descFacilities;
    Spinner spinnerFasilitasKategori;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasilitas_forms);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        spinnerFasilitasKategori = findViewById(R.id.spinnerFasilitasKategori);
        descFacilities = findViewById(R.id.descFacilities);

        ArrayList<FasilitasKategori> fasilitasKategoris = new ArrayList<>();
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

        spinnerFasilitasKategori.setAdapter(adapter);

        btnTambahFas = findViewById(R.id.btnTambahFas);
        btnTambahFas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FasilitasKategori fasilitasKategori = (FasilitasKategori) spinnerFasilitasKategori.getSelectedItem();
                int id_facilities = displayFasilitasKategori(fasilitasKategori);
                String desc_facilities = descFacilities.getText().toString();

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<FacilityStoreResponse> call = apiInterface.setFacility("Bearer " + accessToken, id_facilities,
                        id_disasters, desc_facilities);
                call.enqueue(new Callback<FacilityStoreResponse>() {
                    @Override
                    public void onResponse(Call<FacilityStoreResponse> call, Response<FacilityStoreResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(FasilitasFormsActivity.this, "Berhasil Menambahkan Data", Toast.LENGTH_SHORT, R.style.success).show();
                            Log.d("retrofit", "success: " + response.toString());
                        }else {
                            StyleableToast.makeText(FasilitasFormsActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error: " + response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<FacilityStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(FasilitasFormsActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });

//                Intent intent = new Intent(FasilitasFormsActivity.this, MenusActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
    }

    private int displayFasilitasKategori(FasilitasKategori fasilitasKategori) {
        int id_facilities = fasilitasKategori.getId_facilities();
        return id_facilities;
    }


}