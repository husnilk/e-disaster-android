package com.example.pelaporanbencana;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.databinding.ActivityMenusBinding;
import com.example.pelaporanbencana.model.DisasterShowInMenus.DisInMenusResponse;
import com.example.pelaporanbencana.model.Menus;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenusActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private ActivityMenusBinding binding;
    LinearLayout gambarMenu, kerusakanMenu, pendudukMenu, shelterMenu, bantuanMenu, fasilitasMenu, sumdaMenu, relawanMenu, perkembanganMenu;
    Button btnCekLokMenus,btnEditKej;
    TextView MenusKodeKej, menusNamabencana,MenusWaktuKej,menusTgl,deskripsiKejadian;
    ApiInterface apiInterface;
    FusedLocationProviderClient fusedLocationProviderClient;
    public double latitude;
    public double longitude;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String accessToken = pref.getString("access_token", "");
        String idDisasters = pref.getString("id_disasters", "");
//        String accessToken = "170|pv7akmRu4Fm8fYPNDBTARKmcMCraq6lVSp1hT8dK";
//        String idDisasters = "1";

        Intent intent = getIntent();
        String idDisasters1 = intent.getStringExtra("id_disasters_1");

        MenusKodeKej = findViewById(R.id.MenusKodeKej);

        if (idDisasters == null || idDisasters == ""){
            MenusKodeKej.setText(idDisasters1);
        }else {
            MenusKodeKej.setText(idDisasters);
        }

        menusTgl = findViewById(R.id.menusTgl);
        MenusWaktuKej = findViewById(R.id.MenusWaktuKej);
        menusNamabencana = findViewById(R.id.menusNamabencana);
        deskripsiKejadian = findViewById(R.id.deskripsiKejadian);

        String id_disasters = MenusKodeKej.getText().toString();

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref1.edit();
        editor.putString("id_disasters",id_disasters);
        editor.apply();

        //menampilkan data keajadian pada menuactivity (kode kejadian, tanggal, waktu, deskripsi kejadian )
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
                    double disasterLat = Double.parseDouble(disasterinmenu.getData().getDisastersLat());
                    double disasterLong = Double.parseDouble(disasterinmenu.getData().getDisastersLong());

                    getLocation(disasterLat, disasterLong);

                    menusNamabencana.setText(disasters_types);
                    menusTgl.setText(disasters_date);
                    MenusWaktuKej.setText(disasters_time);
//                    deskripsiKejadian.setText(disasters_desc);
                    Toast.makeText(MenusActivity.this, "Berhasil" + accessToken + id_disasters, Toast.LENGTH_SHORT).show();
                    Log.d("retrofit", "success" + response.toString());

                }else{
                    Toast.makeText(MenusActivity.this, "Gagal dapat data" + response.body() + id_disasters, Toast.LENGTH_SHORT).show();
                    Log.d("retrofit", "error" + response.toString());
                }
            }

            @Override
            public void onFailure(Call<DisInMenusResponse> call, Throwable t) {
                Toast.makeText(MenusActivity.this, "Gagal" , Toast.LENGTH_SHORT).show();

            }

        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapMenus);
        mapFragment.getMapAsync(this);

        kerusakanMenu = findViewById(R.id.kerusakanMenu);
        pendudukMenu = findViewById(R.id.pendudukMenu);
        shelterMenu = findViewById(R.id.shelterMenu);
        bantuanMenu = findViewById(R.id.bantuanMenu);
        fasilitasMenu = findViewById(R.id.fasilitasMenu);
        sumdaMenu = findViewById(R.id.sumdaMenu);
        btnCekLokMenus = findViewById(R.id.btnCekLokMenus);
        relawanMenu = findViewById(R.id.relawanMenu);
        btnEditKej = findViewById(R.id.btnEditKej);
        perkembanganMenu = findViewById(R.id.perkembanganMenu);
        gambarMenu = findViewById(R.id.gambarMenu);


        perkembanganMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MenusActivity.this, DetailKejadianActivity.class);
                startActivity(intent1);
            }
        });

        gambarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MenusActivity.this, CameraActivity.class);
                startActivity(intent1);
            }
        });

        kerusakanMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenusActivity.this, KerusakanActivity.class);
                intent.putExtra("idDisasters", id_disasters);
                intent.putExtra("access_token", accessToken);
                startActivity(intent);
                finish();
            }
        });

        pendudukMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenusActivity.this, PendudukActivity.class);
                startActivity(intent);
                finish();
            }
        });

        shelterMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String disasters_type = menusNamabencana.getText().toString();

                Intent intent = new Intent(MenusActivity.this, LokasiPengungsiActivity.class);
                intent.putExtra("disasters_type", disasters_type);
                startActivity(intent);
                finish();
            }
        });

        bantuanMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenusActivity.this, BantuanActivity.class);
                startActivity(intent);
                finish();
            }
        });

        fasilitasMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenusActivity.this, FasilitasActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sumdaMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenusActivity.this, SumberDayaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnCekLokMenus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenusActivity.this, CekLokasiActivity.class);
                startActivity(intent);
                finish();
            }
        });

        relawanMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenusActivity.this, VolunteerOrgActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnEditKej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenusActivity.this, KoordinatKejadianEditActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public static boolean isLocationEnabled(Context context) {
        //...............
        return true;
    }

    private void getLocation(double disasterLat, double disasterLong) {
        //jika saya di lokasi
        if (isLocationEnabled(MenusActivity.this)) {
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
                gotoLocation(latitude, longitude);
                Toast.makeText(MenusActivity.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();

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

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        mMap.moveCamera(cameraUpdate);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        MarkerOptions options = new MarkerOptions().position(latLng).title("Lokasi Bencana");
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
        gotoLocation(latitude,longitude);
        Toast.makeText(MenusActivity.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Log.d("Latitude","disable");
    }
}