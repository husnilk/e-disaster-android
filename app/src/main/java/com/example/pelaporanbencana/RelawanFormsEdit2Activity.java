package com.example.pelaporanbencana;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.VolunteerShowa.VolunteerShowaResponse;
import com.example.pelaporanbencana.model.VolunteerShowa1.VolunteerShowa1Response;
import com.example.pelaporanbencana.model.VolunteerStore.VolunteerStoreResponse;
import com.google.android.material.textfield.TextInputEditText;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RelawanFormsEdit2Activity extends AppCompatActivity {
    TextInputEditText placementRelawan, tugasRelawan;
    Button btnTambahRelawan;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relawan_edit_forms2);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        Intent intent = getIntent();
        String idVolunteer = intent.getStringExtra("idVolunteer");
        String id_volunteers = intent.getStringExtra("id_volunteer");
        String id_volunteer = intent.getStringExtra("id_volunteer");
        String volunteer_name = intent.getStringExtra("volunteer_name");
        String volunteer_skill = intent.getStringExtra("volunteer_skill");
        String volunteer_birthdate = intent.getStringExtra("volunteer_birthdate");
        String volunteer_gender = intent.getStringExtra("volunteer_gender");
        String id_volunteer_org = intent.getStringExtra("id_volunteer_org");

        placementRelawan = findViewById(R.id.placementRelawan);
        tugasRelawan = findViewById(R.id.tugasRelawan);
        btnTambahRelawan = findViewById(R.id.btnTambahRelawan);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<VolunteerShowa1Response> call = apiInterface.getOneVolunteer1("Bearer " + accessToken, id_volunteer, id_disasters);
        call.enqueue(new Callback<VolunteerShowa1Response>() {
            @Override
            public void onResponse(Call<VolunteerShowa1Response> call, Response<VolunteerShowa1Response> response) {
                VolunteerShowa1Response response1 = response.body();
                if (response.isSuccessful()){
                    placementRelawan.setText(response1.getData().getPlacement());
                    tugasRelawan.setText(response1.getData().getAssignment());

                }else{
//                            StyleableToast.makeText(RelawanFormsActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<VolunteerShowa1Response> call, Throwable t) {
                StyleableToast.makeText(RelawanFormsEdit2Activity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });

        btnTambahRelawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String placement = placementRelawan.getText().toString();
                String assignment = tugasRelawan.getText().toString();

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<VolunteerStoreResponse> call = apiInterface.updateVolunteer("Bearer " + accessToken,
                        idVolunteer,id_volunteers, id_volunteer_org, volunteer_name,volunteer_birthdate, volunteer_gender,
                        volunteer_skill, id_disasters, id_volunteer, placement, assignment);
                call.enqueue(new Callback<VolunteerStoreResponse>() {
                    @Override
                    public void onResponse(Call<VolunteerStoreResponse> call, Response<VolunteerStoreResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(RelawanFormsEdit2Activity.this, "Berhasil Mengubah Data", Toast.LENGTH_SHORT, R.style.success).show();
                            Log.d("retrofit", "success " + response.toString());
                        }else{
                            StyleableToast.makeText(RelawanFormsEdit2Activity.this, "Gagal Mengubah Data", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error " + response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<VolunteerStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(RelawanFormsEdit2Activity.this, "Gagal Mengubah Data", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });
            }
        });
    }
}