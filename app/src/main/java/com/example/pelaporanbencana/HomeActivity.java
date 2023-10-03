package com.example.pelaporanbencana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelaporanbencana.adapter.HomeRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.Disaster.DataItem;
import com.example.pelaporanbencana.model.Disaster.DisasterResponse;
import com.example.pelaporanbencana.model.DisasterShowInMenus.DisInMenusResponse;
import com.example.pelaporanbencana.model.DisasterUserShowConstribution.ShowContributionResponse;
import com.example.pelaporanbencana.model.Home;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements HomeRecViewAdapter.HomeClickListener{
    private RecyclerView HomeRecView;
    private HomeRecViewAdapter homeRecViewAdapter;
    FloatingActionButton fbTambahKej;
    TextView textUname, textKontribusi, textProgress, textKejadian, instansiUser;
    ApiInterface apiInterface;
    EditText editTextIdKej;
    Button btnTbhKejHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(
                "MyPref", Context.MODE_PRIVATE
        );

        String accessToken = pref.getString("access_token", "");
        String name = pref.getString("name", "");
        String tokenType = pref.getString("token_type", "");
        String id_user = pref.getString("id_user", "");
        String institution = pref.getString("institution", "");
        int id_user_int = Integer.parseInt(id_user);

        textUname = findViewById(R.id.textUname);
        textUname.setText(name);
        textKontribusi = findViewById(R.id.textKontribusi);
        textProgress = findViewById(R.id.textProgress);
        textKejadian = findViewById(R.id.textKejadian);
        instansiUser = findViewById(R.id.instansiUser);
        instansiUser.setText(institution);

        homeRecViewAdapter = new HomeRecViewAdapter();
        homeRecViewAdapter.setListener(this);

        HomeRecView = findViewById(R.id.HomeRecView);
        HomeRecView.setAdapter(homeRecViewAdapter);
        HomeRecView.setLayoutManager(new GridLayoutManager(this, 1));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DisasterResponse> call = apiInterface.getDisaster("Bearer " + accessToken, id_user);
            call.enqueue(new Callback<DisasterResponse>() {
            @Override
            public void onResponse(Call<DisasterResponse> call, Response<DisasterResponse> response) {
                DisasterResponse disasterResponse = response.body();
                textKontribusi.setText(String.valueOf(disasterResponse.getDataSelesai().getCount()));
                textProgress.setText(String.valueOf(disasterResponse.getDataOnProgress().getJumlahkontribusi()));
                textKejadian.setText(String.valueOf(disasterResponse.getDataDisasters().getCount()));

                ArrayList<Home> listData = new ArrayList<>();

                if (disasterResponse != null && response.isSuccessful()){
                    List<DataItem> itemList = disasterResponse.getData();

                    for (DataItem item : itemList){
                        Home home = new Home(
                                item.getIdDisasters(),
                                item.getDisastersTypes(),
                                item.getDisastersVillage(),
                                item.getDisastersDate(),
                                item.getDisastersTime(),
                                item.getUrbanVillageName(),
                                item.getSubDistrictName(),
                                item.getDistrictName(),
                                item.getDisasters_types_name()
                        );

                        listData.add(home);
                        Log.d("retrofit", "success : " + response.toString());
                    }
                }else{
                    StyleableToast.makeText(HomeActivity.this, "Gagal Dapat Data" + accessToken, Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error : " + response.toString());
            }
                homeRecViewAdapter.setHomes(listData);
            }

            @Override
            public void onFailure(Call<DisasterResponse> call, Throwable t) {
                StyleableToast.makeText(HomeActivity.this, "Gagal", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });

//        kontribusi(accessToken, id_user_int);

        fbTambahKej = findViewById(R.id.fbTambahKej);
        fbTambahKej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getTgl
//                Date d = new Date();
//                String tgl_today = (String) DateFormat.format("yyMMdd", d.getTime());

                //random number
                Random number = new Random();
                String randomnumber = String.valueOf(number.nextInt(9999-100) + 100);

//                String id_disasters = tgl_today+randomnumber;

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("access_token", accessToken);
                editor.putString("id_disasters", randomnumber);
                editor.apply();

                Intent intent = new Intent(HomeActivity.this, KoordinatKejadianActivity.class);
                startActivity(intent);
            }
        });

        HomeRecView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MenusActivity.class);
                startActivity(intent);
            }
        });

        editTextIdKej = findViewById(R.id.editTextIdKej);
        btnTbhKejHome = findViewById(R.id.btnTbhKejHome);
        btnTbhKejHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idKej = editTextIdKej.getText().toString();

                //jika kode kej kosong
                if (idKej.isEmpty()){
                    editTextIdKej.setError("Kode Kejadian is required");
                    editTextIdKej.requestFocus();
                    return;
                }

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<DisInMenusResponse> call1 = apiInterface.getDisasterInMenu("Bearer " + accessToken, idKej);
                call1.enqueue(new Callback<DisInMenusResponse>() {
                    @Override
                    public void onResponse(Call<DisInMenusResponse> call, Response<DisInMenusResponse> response) {
                        if (response.isSuccessful()){
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("access_token", accessToken);
                            editor.putString("id_disasters", idKej);
                            editor.apply();

                            Intent intent = new Intent(HomeActivity.this, MenusActivity.class);
                            startActivity(intent);
                        }else{
                            StyleableToast.makeText(HomeActivity.this, "Kode Kejadian Salah", Toast.LENGTH_SHORT, R.style.error).show();
                            Log.d("retrofit", "error " + response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<DisInMenusResponse> call, Throwable t) {
                        StyleableToast.makeText(HomeActivity.this, "Kode Kejadian Salah", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });




            }
        });
    }

//    private void kontribusi(String accessToken, int id_user_int) {
//        apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<ShowContributionResponse> call11 = apiInterface.getShowContribution("Bearer " + accessToken, id_user_int);
//        call11.enqueue(new Callback<ShowContributionResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<ShowContributionResponse> call1, @NonNull Response<ShowContributionResponse> response1) {
//                ShowContributionResponse responses1 = response1.body();
//                if (responses1 != null) {
//                    if (responses1.isSuccess()){
//                        tvTes.setText(responses1.getData().getJumlahkontribusi());
//                        Log.d("retrofit", "success " + response1.toString());
//                    }else {
//                        Log.d("retrofit", "error " + response1.toString());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ShowContributionResponse> call1, Throwable t1) {
//                StyleableToast.makeText(HomeActivity.this, "Gagal", Toast.LENGTH_SHORT, R.style.error).show();
//            }
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.actionsearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                homeRecViewAdapter.getFilter().filter(newText);
                return false;
            }
        });

        MenuItem logoutItem = menu.findItem(R.id.actionlogout);
        logoutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                SharedPreferences prefLogout = getSharedPreferences("MyPref", MODE_PRIVATE);
                prefLogout.edit().remove("access_token").commit();
                prefLogout.edit().remove("name").commit();
                prefLogout.edit().remove("token_types").commit();
                prefLogout.edit().apply();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                StyleableToast.makeText(HomeActivity.this, "Anda Berhasil Logout", Toast.LENGTH_SHORT, R.style.success).show();
                finish();

                return false;
            }
        });

        return true;
    }

    @Override
    public void onClick(View view, Home home) {

    }
}