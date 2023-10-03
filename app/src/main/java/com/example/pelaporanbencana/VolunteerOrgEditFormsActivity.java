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
import com.example.pelaporanbencana.model.Spinner.JenisOrg;
import com.example.pelaporanbencana.model.VolunteerOrgShowa.VolunteerOrgShowaResponse;
import com.example.pelaporanbencana.model.VolunteerOrgStore.VolunteerOrgStoreResponse;
import com.example.pelaporanbencana.model.VolunteerShowa.VolunteerShowaResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerOrgEditFormsActivity extends AppCompatActivity {
    TextInputEditText idVolunteerOrg, nameVolunteerOrg, addressVolunteerOrg;
    Spinner spinnerJenisOrg;
    Button btnTambahOrganisasi;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_org_edit_forms);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        Intent intent = getIntent();
        String idVolunteer_org = intent.getStringExtra("id_volunteer_org");

        idVolunteerOrg = findViewById(R.id.idVolunteerOrg);
        nameVolunteerOrg = findViewById(R.id.nameVolunteerOrg);
        addressVolunteerOrg = findViewById(R.id.addressVolunteerOrg);
        spinnerJenisOrg = findViewById(R.id.spinnerJenisOrg);
        btnTambahOrganisasi = findViewById(R.id.btnTambahOrganisasi);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<VolunteerOrgShowaResponse> call = apiInterface.getOneVolunteerOrg("Bearer " + accessToken, idVolunteer_org);
        call.enqueue(new Callback<VolunteerOrgShowaResponse>() {
            @Override
            public void onResponse(Call<VolunteerOrgShowaResponse> call, Response<VolunteerOrgShowaResponse> response) {
                if (response.isSuccessful()){
                    VolunteerOrgShowaResponse response1 = response.body();

                    idVolunteerOrg.setText(idVolunteer_org);
                    nameVolunteerOrg.setText(response1.getData().getVolunteerOrgName());
                    addressVolunteerOrg.setText(response1.getData().getVolunteerOrgAddress());

                    String jenisOrg = response1.getData().getVolunteerOrgStatus();
                    String idJenisOrg = "0";
                    if (jenisOrg != null){
                        if (jenisOrg.equals("Organisasi Dalam Negeri")){
                            idJenisOrg = "1";
                        }else if(jenisOrg.equals("Organisasi Luar Negeri")){
                            idJenisOrg = "2";}
                    }else{
                        idJenisOrg = "0";
                    }

                    jenisOrgAddToList(idJenisOrg,jenisOrg);

                }else{
                    StyleableToast.makeText(VolunteerOrgEditFormsActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());

                }
            }

            @Override
            public void onFailure(Call<VolunteerOrgShowaResponse> call, Throwable t) {
                StyleableToast.makeText(VolunteerOrgEditFormsActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });

        //randomNumber
        Random number = new Random();
        String randomnumber = String.valueOf(number.nextInt(200000-100000) + 100000);

       idVolunteerOrg.setText(randomnumber);

        btnTambahOrganisasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_volunteer_org = idVolunteerOrg.getText().toString();
                String volunteer_org_name = nameVolunteerOrg.getText().toString();
                String volunteer_org_address = addressVolunteerOrg.getText().toString();
                JenisOrg jenisOrg = (JenisOrg) spinnerJenisOrg.getSelectedItem();
                String volunteer_org_status = displayJenisOrg(jenisOrg);

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<VolunteerOrgStoreResponse> call = apiInterface.updateVolunteerOrg("Bearer " + accessToken, idVolunteer_org,
                        id_volunteer_org, volunteer_org_name, volunteer_org_address, volunteer_org_status);
                call.enqueue(new Callback<VolunteerOrgStoreResponse>() {
                    @Override
                    public void onResponse(Call<VolunteerOrgStoreResponse> call, Response<VolunteerOrgStoreResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(VolunteerOrgEditFormsActivity.this, "Berhasilkan Mengubah Data", Toast.LENGTH_SHORT, R.style.success).show();
                            Log.d("retrofit", "success" + response.toString());
                        }else{
                            StyleableToast.makeText(VolunteerOrgEditFormsActivity.this, "Gagal Mengubah Data", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error" + response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<VolunteerOrgStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(VolunteerOrgEditFormsActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });
            }
        });
    }

    private void jenisOrgAddToList(String idJenisOrg,String jenisOrg) {
        //spinner
        List<JenisOrg> jenisOrgList = new ArrayList<>();
        JenisOrg jenisOrgg = new JenisOrg(idJenisOrg, jenisOrg);
        jenisOrgList.add(jenisOrgg);
        JenisOrg jenisOrg0 = new JenisOrg("0", "None");
        jenisOrgList.add(jenisOrg0);
        JenisOrg jenisOrg1 = new JenisOrg("1", "Organisasi Dalam Negeri");
        jenisOrgList.add(jenisOrg1);
        JenisOrg jenisOrg2 = new JenisOrg("2", "Organisasi Luar Negeri");
        jenisOrgList.add(jenisOrg2);

        ArrayAdapter<JenisOrg> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, jenisOrgList);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerJenisOrg.setAdapter(adapter);

    }

    private String displayJenisOrg(JenisOrg jenisOrg) {
        String id = jenisOrg.getName();
        return id;
    }
}