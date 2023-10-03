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
import com.example.pelaporanbencana.model.Spinner.JenisOrg;
import com.example.pelaporanbencana.model.VolunteerOrgStore.VolunteerOrgStoreResponse;
import com.example.pelaporanbencana.model.VolunteerStore.VolunteerStoreResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerOrgFormsActivity extends AppCompatActivity {
    TextInputEditText idVolunteerOrg, nameVolunteerOrg, addressVolunteerOrg;
    Spinner spinnerJenisOrg;
    Button btnTambahOrganisasi;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_org_forms);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        idVolunteerOrg = findViewById(R.id.idVolunteerOrg);
        nameVolunteerOrg = findViewById(R.id.nameVolunteerOrg);
        addressVolunteerOrg = findViewById(R.id.addressVolunteerOrg);
        spinnerJenisOrg = findViewById(R.id.spinnerJenisOrg);
        btnTambahOrganisasi = findViewById(R.id.btnTambahOrganisasi);

        //randomNumber
        Random number = new Random();
        String randomnumber = String.valueOf(number.nextInt(200000-100000) + 100000);

       idVolunteerOrg.setText(randomnumber);

       //spinner
        List<JenisOrg> jenisOrgList = new ArrayList<>();
        JenisOrg jenisOrg1 = new JenisOrg("1", "Organisasi Dalam Negeri");
        jenisOrgList.add(jenisOrg1);
        JenisOrg jenisOrg2 = new JenisOrg("2", "Organisasi Luar Negeri");
        jenisOrgList.add(jenisOrg2);

        ArrayAdapter<JenisOrg> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, jenisOrgList);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerJenisOrg.setAdapter(adapter);

        btnTambahOrganisasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_volunteer_org = idVolunteerOrg.getText().toString();
                String volunteer_org_name = nameVolunteerOrg.getText().toString();
                String volunteer_org_address = addressVolunteerOrg.getText().toString();
                JenisOrg jenisOrg = (JenisOrg) spinnerJenisOrg.getSelectedItem();
                String volunteer_org_status = displayJenisOrg(jenisOrg);

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<VolunteerOrgStoreResponse> call = apiInterface.setVolunteerOrg("Bearer " + accessToken,
                        id_volunteer_org, volunteer_org_name, volunteer_org_address, volunteer_org_status);
                call.enqueue(new Callback<VolunteerOrgStoreResponse>() {
                    @Override
                    public void onResponse(Call<VolunteerOrgStoreResponse> call, Response<VolunteerOrgStoreResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(VolunteerOrgFormsActivity.this, "Berhasilkan Menambahkan Data", Toast.LENGTH_SHORT, R.style.success).show();
                            Log.d("retrofit", "success" + response.toString());
                        }else{
                            StyleableToast.makeText(VolunteerOrgFormsActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error" + response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<VolunteerOrgStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(VolunteerOrgFormsActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });
            }
        });
    }

    private String displayJenisOrg(JenisOrg jenisOrg) {
        String id = jenisOrg.getId();
        return id;
    }
}