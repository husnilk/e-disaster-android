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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.DevDisastersStore.DevDisastersStoreResponse;
import com.example.pelaporanbencana.model.DisasterStore.DisasterStoreResponse;
import com.example.pelaporanbencana.model.Spinner.JenisBencana;
import com.example.pelaporanbencana.model.Spinner.JenisCuaca;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KejadianEditForms2Activity extends AppCompatActivity {
    private Button btnKej2;
    ApiInterface apiInterface;
    TextInputEditText cakupanDampak,penyebabKejadian, upayaKejadian, potensiKejadian, deskripsiKejadianEdit;
    TextView tvTglKejadianRincian, tvWaktuKejadianRincian;
    Spinner spinnerjeniscuaca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kejadian_forms2);

        //token
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

//        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
//        String id_disasters = pref1.getString("id_disasters", "");

        btnKej2 = findViewById(R.id.btnKej2);
        cakupanDampak = findViewById(R.id.cakupanDampak);
        penyebabKejadian = findViewById(R.id.penyebabKejadian);
        spinnerjeniscuaca = findViewById(R.id.spinnerjeniscuaca);
        upayaKejadian = findViewById(R.id.upayaKejadian);
        potensiKejadian = findViewById(R.id.potensiKejadian);
        deskripsiKejadianEdit = findViewById(R.id.deskripsiKejadianEdit);
//        tvIdKejadianRincian = findViewById(R.id.tvIdKejadianRincian);
        tvTglKejadianRincian = findViewById(R.id.tvTglKejadianRincian);
        tvWaktuKejadianRincian = findViewById(R.id.tvWaktuKejadianRincian);

        Intent intent = getIntent();
        String id_disasters = intent.getStringExtra("id_disasters");
        String disasters_date = intent.getStringExtra("disasters_date");
        String disasters_time = intent.getStringExtra("disasters_time");
        String disasters_desc = intent.getStringExtra("disasters_desc");
        String disasters_causes = intent.getStringExtra("disasters_causes");
        String disasters_impact = intent.getStringExtra("disasters_impact");
        String weather_conditions = intent.getStringExtra("weather_conditions");
        String disasters_potential = intent.getStringExtra("disasters_potential");
        String disasters_effort = intent.getStringExtra("disasters_effort");

//        tvIdKejadianRincian.setText(id_disasters);
        tvTglKejadianRincian.setText(disasters_date);
        tvWaktuKejadianRincian.setText(disasters_time);
        deskripsiKejadianEdit.setText(disasters_desc);
        penyebabKejadian.setText(disasters_causes);
        cakupanDampak.setText(disasters_impact);
        upayaKejadian.setText(disasters_effort);
        potensiKejadian.setText(disasters_potential);


        List<JenisCuaca> cuacaList = new ArrayList<>();

        JenisCuaca jenisCuaca  = new JenisCuaca(weather_conditions);
        cuacaList.add(jenisCuaca);
        JenisCuaca jenisCuaca1  = new JenisCuaca("Cerah");
        cuacaList.add(jenisCuaca1);
        JenisCuaca jenisCuaca2  = new JenisCuaca("Cerah Berawan");
        cuacaList.add(jenisCuaca2);
        JenisCuaca jenisCuaca3  = new JenisCuaca("Berawan");
        cuacaList.add(jenisCuaca3);
        JenisCuaca jenisCuaca4  = new JenisCuaca("Berawan Tebal");
        cuacaList.add(jenisCuaca4);
        JenisCuaca jenisCuaca5  = new JenisCuaca("Kabut");
        cuacaList.add(jenisCuaca5);
        JenisCuaca jenisCuaca6  = new JenisCuaca("Kabut Tebal");
        cuacaList.add(jenisCuaca6);
        JenisCuaca jenisCuaca7  = new JenisCuaca("Hujan Lokal");
        cuacaList.add(jenisCuaca7);
        JenisCuaca jenisCuaca8  = new JenisCuaca("Hujan Ringan");
        cuacaList.add(jenisCuaca8);
        JenisCuaca jenisCuaca9  = new JenisCuaca("Hujan Sedang");
        cuacaList.add(jenisCuaca9);
        JenisCuaca jenisCuaca10  = new JenisCuaca("Hujan Lebat");
        cuacaList.add(jenisCuaca10);
        JenisCuaca jenisCuaca11  = new JenisCuaca("Hujan Petir");
        cuacaList.add(jenisCuaca11);
        JenisCuaca jenisCuaca12  = new JenisCuaca("Salju");
        cuacaList.add(jenisCuaca12);

        ArrayAdapter<JenisCuaca> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cuacaList);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerjeniscuaca.setAdapter(adapter);

        btnKej2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cakupan_dampak = cakupanDampak.getText().toString();
                String penyebab_kejadian = penyebabKejadian.getText().toString();
                String upaya_kejadian = upayaKejadian.getText().toString();
                String potensi_kejadian = potensiKejadian.getText().toString();
                String deskripsi_kejadian = deskripsiKejadianEdit.getText().toString();

                JenisCuaca jenisCuaca = (JenisCuaca) spinnerjeniscuaca.getSelectedItem();
                String cuaca = displayJenisCuaca(jenisCuaca);

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<DevDisastersStoreResponse> call = apiInterface.updateDevOfDisaster("Bearer " + accessToken, id_disasters, disasters_date, disasters_time,
                        id_disasters, disasters_date, disasters_time, deskripsi_kejadian, cakupan_dampak, penyebab_kejadian,
                        cuaca, potensi_kejadian, upaya_kejadian
                        );
                call.enqueue(new Callback<DevDisastersStoreResponse>() {
                    @Override
                    public void onResponse(Call<DevDisastersStoreResponse> call, Response<DevDisastersStoreResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(KejadianEditForms2Activity.this, "Berhasil Mengubah Data", Toast.LENGTH_SHORT, R.style.success).show();
                            Log.d("retrofit", "success " + response.toString() );
                        }else{
                            StyleableToast.makeText(KejadianEditForms2Activity.this, "Gagal Mengubah Data", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error " + response.toString() );
                        }
                    }

                    @Override
                    public void onFailure(Call<DevDisastersStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(KejadianEditForms2Activity.this, "Gagal", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });
            }
        });


    }

    private String displayJenisCuaca(JenisCuaca jenisCuaca) {
        String cuaca1 = jenisCuaca.getCuaca();
        return cuaca1;
    }
}