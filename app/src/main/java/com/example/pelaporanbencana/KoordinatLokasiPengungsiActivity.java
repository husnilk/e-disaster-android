package com.example.pelaporanbencana;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

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
import com.example.pelaporanbencana.databinding.ActivityKoordinatLokasiPengungsiBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class KoordinatLokasiPengungsiActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityKoordinatLokasiPengungsiBinding binding;
    SearchView searchViewLocPengungsi;
    TextView latLocPengungsi, longLocPengungsi;
    Button btnNextKoordinat;
    FloatingActionButton myLocButton1;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextInputEditText editTextDetLokPengungsi, editTextNameLokPengungsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityKoordinatLokasiPengungsiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        latLocPengungsi = findViewById(R.id.latLocPengungsi);
        longLocPengungsi = findViewById(R.id.longLocPengungsi);
        editTextDetLokPengungsi = findViewById(R.id.editTextDetLokPengungsi);
        editTextNameLokPengungsi = findViewById(R.id.editTextNameLokPengungsi);

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
                    Geocoder geocoder = new Geocoder(KoordinatLokasiPengungsiActivity.this);
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

        //Jika saya di lokasi pengungsi
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
                gotoLocation(location.getLatitude(), location.getLongitude());
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(KoordinatLokasiPengungsiActivity.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        latLocPengungsi.setText(String.valueOf(location.getLatitude()));
                        longLocPengungsi.setText(String.valueOf(location.getLongitude()));

                        Address address = addresses.get(0);
                        String alamat = address.getAddressLine(0);
                        editTextDetLokPengungsi.setText(Html.fromHtml("<font></font>") + alamat);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //jika tombol lokasi di klik
        myLocButton1 = findViewById(R.id.myLocButton1);
        myLocButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check Permission
                if (ActivityCompat.checkSelfPermission(KoordinatLokasiPengungsiActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //When Permission Granted
                    getLocation();
                } else {
                    //When Permission Denied
                    ActivityCompat.requestPermissions(KoordinatLokasiPengungsiActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        btnNextKoordinat = findViewById(R.id.btnNextKoordinat);
        btnNextKoordinat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KoordinatLokasiPengungsiActivity.this, LokasiPengungsiFormsActivity.class);
                intent.putExtra("latLocPengungsi", latLocPengungsi.getText().toString());
                intent.putExtra("longLocPengungsi", longLocPengungsi.getText().toString());
                intent.putExtra("detLocPengungsi", editTextDetLokPengungsi.getText().toString());
                intent.putExtra("nameLocPengungsi", editTextNameLokPengungsi.getText().toString());
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
                        Geocoder geocoder = new Geocoder(KoordinatLokasiPengungsiActivity.this,
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