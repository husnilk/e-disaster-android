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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.databinding.ActivityKoordinatKejadianBinding;
import com.example.pelaporanbencana.databinding.ActivityKoordinatKejadianEditBinding;
import com.example.pelaporanbencana.model.DisasterShowInMenus.DisInMenusResponse;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KoordinatKejadianEditActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private @NonNull ActivityKoordinatKejadianEditBinding binding;
    private Button btnEditKoordinat;
    FloatingActionButton myLocButton;
    SearchView searchViewLoc;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView latLokasiKejadianEdit, longLokasiKejadianEdit, kelurahanKejadian;
    EditText editTextDetLokEdit;
//    LinearLayout kelurahanKejadianLayout;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityKoordinatKejadianEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.koordinatMapEdit);
        mapFragment.getMapAsync(this);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        latLokasiKejadianEdit = findViewById(R.id.latLokasiKejadianEdit);
        longLokasiKejadianEdit = findViewById(R.id.longLokasiKejadianEdit);
        editTextDetLokEdit = findViewById(R.id.editTextDetLokEdit);
//        kelurahanKejadianLayout = findViewById(R.id.kelurahanKejadianLayout);
//        kelurahanKejadian = findViewById(R.id.kelurahanKejadian);
        btnEditKoordinat = findViewById(R.id.btnEditKoordinat);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DisInMenusResponse> call = apiInterface.getDisasterInMenu("Bearer " + accessToken, id_disasters);
        call.enqueue(new Callback<DisInMenusResponse>() {
            @Override
            public void onResponse(Call<DisInMenusResponse> call, Response<DisInMenusResponse> response) {
                DisInMenusResponse disasterinmenu = response.body();

                if(response.isSuccessful()){
                    String disasters_types = disasterinmenu.getData().getDisastersTypes();
                    String disasters_date = disasterinmenu.getData().getDisastersDate();
                    String disasters_time = disasterinmenu.getData().getDisastersTime();
//                    String disasters_desc = disasterinmenu.getData().getDisastersDesc();
                    String id_urban_village = disasterinmenu.getData().getIdUrbanVillage();
                    String disasters_village = disasterinmenu.getData().getDisastersVillage();
                    String disasters_lat = disasterinmenu.getData().getDisastersLat();
                    double disasterLat = Double.parseDouble(disasterinmenu.getData().getDisastersLat());
                    String disasters_long = disasterinmenu.getData().getDisastersLong();
                    double disasterLong = Double.parseDouble(disasterinmenu.getData().getDisastersLong());
                    String urban_village_name = disasterinmenu.getData().getUrbanVillageName();


                    latLokasiKejadianEdit.setText(disasters_lat);
                    longLokasiKejadianEdit.setText(disasters_long);
                    editTextDetLokEdit.setText(disasters_village);
//                    kelurahanKejadian.setText(urban_village_name);

                    getLocation2(disasterLat, disasterLong);

                    Log.d("retrofit", "success" + response.toString());

                    btnEditKoordinat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(KoordinatKejadianEditActivity.this, KejadianEditForms1Activity.class);
                            intent.putExtra("id_disasters", id_disasters);
                            intent.putExtra("latBecana", latLokasiKejadianEdit.getText().toString());
                            intent.putExtra("longBecana", longLokasiKejadianEdit.getText().toString());
                            intent.putExtra("jorong", editTextDetLokEdit.getText().toString());
                            intent.putExtra("disasters_types", disasters_types);
                            intent.putExtra("disasters_date", disasters_date);
                            intent.putExtra("disasters_time", disasters_time);
//                            intent.putExtra("disasters_desc", disasters_desc);
                            intent.putExtra("id_urban_village", id_urban_village);
                            intent.putExtra("disasters_village", disasters_village);
                            intent.putExtra("urban_village_name", urban_village_name);
                            startActivity(intent);
                            finish();

                            Toast.makeText(KoordinatKejadianEditActivity.this, latLokasiKejadianEdit.getText().toString() +
                                    longLokasiKejadianEdit.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(KoordinatKejadianEditActivity.this, "Gagal dapat data" + response.body() + id_disasters, Toast.LENGTH_SHORT).show();
                    Log.d("retrofit", "error" + response.toString());
                }
            }

            @Override
            public void onFailure(Call<DisInMenusResponse> call, Throwable t) {
                Toast.makeText(KoordinatKejadianEditActivity.this, "Gagal" , Toast.LENGTH_SHORT).show();
            }

        });


        //SEARCH LOKASI
        searchViewLoc = findViewById(R.id.searchViewLocEdit);
        searchViewLoc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mMap.clear();
                String location = searchViewLoc.getQuery().toString();

                List<Address> addressList = null;
                if (addressList != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(KoordinatKejadianEditActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Lokasi Bencana"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));

                    latLokasiKejadianEdit.setText(String.valueOf(address.getLatitude()));
                    longLokasiKejadianEdit.setText(String.valueOf(address.getLongitude()));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);


        //LOKASI SAYA saat klik button
        myLocButton = findViewById(R.id.myLocButtonEdit);
        myLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check permission
                if (ActivityCompat.checkSelfPermission(KoordinatKejadianEditActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //When Permission Granted
                    getLocation();
                } else {
                    //When Permission Denied
                    ActivityCompat.requestPermissions(KoordinatKejadianEditActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

    }

    private void getLocation2(double disasterLat, double disasterLong) {
        //jika saya di lokasi
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
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
                gotoLocation(disasterLat, disasterLong);
                latLokasiKejadianEdit.setText(String.valueOf(disasterLat));
                longLokasiKejadianEdit.setText(String.valueOf(disasterLong));
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(KoordinatKejadianEditActivity.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
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
                latLokasiKejadianEdit.setText(String.valueOf(location.getLatitude()));
                longLokasiKejadianEdit.setText(String.valueOf(location.getLongitude()));
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(KoordinatKejadianEditActivity.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void gotoLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        mMap.moveCamera(cameraUpdate);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        MarkerOptions options = new MarkerOptions().position(latLng).title("Lokasi Saya");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,8));
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