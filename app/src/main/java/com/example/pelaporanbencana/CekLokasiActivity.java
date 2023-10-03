package com.example.pelaporanbencana;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.UserLocStore.UserLocStoreResponse;
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
import com.example.pelaporanbencana.databinding.ActivityCekLokasiBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.github.muddz.styleabletoast.StyleableToast;
import kotlinx.coroutines.CoroutineExceptionHandlerImplKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CekLokasiActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private ActivityCekLokasiBinding binding;
    EditText editLokasiUser;
    Button btnCekLokasi;
    TextView longKejadian, latKejadian;
    FusedLocationProviderClient fusedLocationProviderClient;
    ApiInterface apiInterface;
    FloatingActionButton btnLokasiKejadian;
    public double latitude;
    public double longitude;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCekLokasiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String access_token = pref.getString("access_token", "");
        String id_user = pref.getString("id_user", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.kejadianFormsMap);
        mapFragment.getMapAsync(this);

        btnCekLokasi = findViewById(R.id.btnCekLokasi);
        editLokasiUser = findViewById(R.id.editLokasiUser);
        longKejadian = findViewById(R.id.longKejadian);
        latKejadian = findViewById(R.id.latKejadian);
        editLokasiUser = findViewById(R.id.editLokasiUser);
        btnLokasiKejadian = findViewById(R.id.btnLokasiKejadian);


        btnLokasiKejadian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });


        btnCekLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = df.format(c);

                String long_staff = longKejadian.getText().toString();
                String lat_staff = latKejadian.getText().toString();
                String lokasi_staff =  editLokasiUser.getText().toString();
                int idUser = Integer.parseInt(id_user);

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<UserLocStoreResponse> call = apiInterface.setUserLoc("Bearer " + access_token , idUser,
                        id_disasters, lat_staff, long_staff, lokasi_staff, formattedDate);
                call.enqueue(new Callback<UserLocStoreResponse>() {
                    @Override
                    public void onResponse(Call<UserLocStoreResponse> call, Response<UserLocStoreResponse> response) {
                        if (response.isSuccessful()){
                            StyleableToast.makeText(CekLokasiActivity.this, "Berhasil Update Lokasi", Toast.LENGTH_SHORT, R.style.success).show();
                            Log.d("retrofit", "success : " + response.toString() + idUser + id_disasters + formattedDate);
                        }else{
                            StyleableToast.makeText(CekLokasiActivity.this, "Gagal Update Lokasi" , Toast.LENGTH_SHORT, R.style.error).show();

                            Log.d("retrofit", "error : " + response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserLocStoreResponse> call, Throwable t) {
                        StyleableToast.makeText(CekLokasiActivity.this, "Gagal Update Lokasi", Toast.LENGTH_SHORT, R.style.error).show();
                    }
                });
            }
        });

    }
    public static boolean isLocationEnabled(Context context) {
        //...............
        return true;
    }

    private void getLocation() {
        if (isLocationEnabled(CekLokasiActivity.this)) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

            //You can still do this if you like, you might get lucky:
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
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                Log.e("TAG", "GPS is on");
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                longKejadian.setText(String.valueOf(longitude));
                latKejadian.setText(String.valueOf(latitude));
                gotoLocation(latitude, longitude);
                Toast.makeText(CekLokasiActivity.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();

            }
            else{
                //This is what you need:
                locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
            }
        }
        else
        {
            //prompt user to enable location....
            //.................
        }
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

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //Hey, a non null location! Sweet!

        //remove location callback:
        locationManager.removeUpdates(this);

        //open the map:
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Toast.makeText(CekLokasiActivity.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();

    }
}