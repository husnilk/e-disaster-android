package com.example.pelaporanbencana;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.databinding.ActivityKoordinatLokasiPengungsiBinding;
import com.example.pelaporanbencana.model.ShelterShowa.ShelterShowaResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KoordinatLokasiPengungsiEditActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityKoordinatLokasiPengungsiBinding binding;
    SearchView searchViewLocPengungsi;
    TextView latLocPengungsi, longLocPengungsi;
    Button btnNextKoordinat;
    FloatingActionButton myLocButton1;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextInputEditText editTextDetLokPengungsi, editTextNameLokPengungsi;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityKoordinatLokasiPengungsiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //token
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        Intent intent = getIntent();
        String id_shelter = intent.getStringExtra("id_shelter");

        latLocPengungsi = findViewById(R.id.latLocPengungsi);
        longLocPengungsi = findViewById(R.id.longLocPengungsi);
        editTextDetLokPengungsi = findViewById(R.id.editTextDetLokPengungsi);
        editTextNameLokPengungsi = findViewById(R.id.editTextNameLokPengungsi);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ShelterShowaResponse> callShow = apiInterface.getShelterShowa("Bearer " + accessToken, id_shelter);
        callShow.enqueue(new Callback<ShelterShowaResponse>() {
            @Override
            public void onResponse(Call<ShelterShowaResponse> call, Response<ShelterShowaResponse> response) {
                ShelterShowaResponse response1 = response.body();
                if (response1 != null && response.isSuccessful()){

                    double latPengungsi = Double.parseDouble(response1.getData().getLatLoc());
                    double longPengungsi = Double.parseDouble(response1.getData().getLongLoc());
                    String latPengungsi1 = response1.getData().getLatLoc();
                    String longPengungsi1 = response1.getData().getLongLoc();
                    String namaLok = response1.getData().getLocation();
                    String alamatPengungsi = response1.getData().getAddress();
                    String jml_kapasitas = String.valueOf(response1.getData().getCapacity());
                    String jenis_hunian = response1.getData().getHunianTypes();

                    latLocPengungsi.setText(latPengungsi1);
                    longLocPengungsi.setText(longPengungsi1);
                    editTextDetLokPengungsi.setText(alamatPengungsi);
                    editTextNameLokPengungsi.setText(namaLok);

                    gotoLocation(latPengungsi, longPengungsi);
                    passData(id_shelter, jml_kapasitas, jenis_hunian);

                }else{
                    StyleableToast.makeText(KoordinatLokasiPengungsiEditActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<ShelterShowaResponse> call, Throwable t) {
                StyleableToast.makeText(KoordinatLokasiPengungsiEditActivity.this, "Gagal Dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.koordinatPengungsiMap);
        mapFragment.getMapAsync(this);

        //Search Lokasi Pengungsi
        searchViewLocPengungsi = findViewById(R.id.searchViewLocPengungsi);
        searchViewLocPengungsi.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mMap.clear();
                String location = searchViewLocPengungsi.getQuery().toString();

                List<Address> addressList = null;
                if (addressList != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(KoordinatLokasiPengungsiEditActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    String alamat = address.getAddressLine(0);

                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Lokasi Pengungsi"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));

                    latLocPengungsi.setText(String.valueOf(address.getLatitude()));
                    longLocPengungsi.setText(String.valueOf(address.getLongitude()));
                    editTextDetLokPengungsi.setText(Html.fromHtml("<font></font>") + alamat);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);


        //jika tombol lokasi di klik
        myLocButton1 = findViewById(R.id.myLocButton1);
        myLocButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check Permission
                if (ActivityCompat.checkSelfPermission(KoordinatLokasiPengungsiEditActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //When Permission Granted
                    getLocation();
                } else {
                    //When Permission Denied
                    ActivityCompat.requestPermissions(KoordinatLokasiPengungsiEditActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        btnNextKoordinat = findViewById(R.id.btnNextKoordinat);
        btnNextKoordinat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void passData(String id_shelter, String jml_kapasitas, String jenis_hunian) {
        btnNextKoordinat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaLokasi = editTextNameLokPengungsi.getText().toString();
                String latLokasi = latLocPengungsi.getText().toString();
                String longLokasi = longLocPengungsi.getText().toString();
                String alamatLokasi = editTextDetLokPengungsi.getText().toString() ;

                Intent intent = new Intent(KoordinatLokasiPengungsiEditActivity.this, LokasiPengungsiFormsEditActivity.class);
                intent.putExtra("id_shelter", id_shelter);
                intent.putExtra("latLocPengungsi", latLokasi);
                intent.putExtra("longLocPengungsi", longLokasi);
                intent.putExtra("detLocPengungsi", alamatLokasi);
                intent.putExtra("nameLocPengungsi", namaLokasi);
                intent.putExtra("jml_kapasitas", jml_kapasitas);
                intent.putExtra("jenis_hunian", jenis_hunian);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                mMap.clear();
                Location location = task.getResult();
                gotoLocation(location.getLatitude(), location.getLongitude());
                if (location != null){
                    try {
                        Geocoder geocoder = new Geocoder(KoordinatLokasiPengungsiEditActivity.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        latLocPengungsi.setText(String.valueOf(location.getLatitude()));
                        longLocPengungsi.setText(String.valueOf(location.getLongitude()));

                        Address address = addresses.get(0);
                        String alamat = address.getAddressLine(0);
                        editTextDetLokPengungsi.setText(Html.fromHtml("<font></font>") + alamat);

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void gotoLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);

        CameraUpdate cameraUpdateFactory = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        mMap.moveCamera(cameraUpdateFactory);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        MarkerOptions options = new MarkerOptions().position(latLng).title("Lokasi Saya");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}