//package com.example.pelaporanbencana;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.ProgressDialog;
//import android.content.BroadcastReceiver;
//import android.content.IntentFilter;
//import android.net.ConnectivityManager;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Spinner;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.example.pelaporanbencana.model.Penduduk;
//import com.google.android.material.textfield.TextInputEditText;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class KorbanFormsSQLiteActivity extends AppCompatActivity implements View.OnClickListener {
//
//    public static final String data_url = "https://disasterta.herokuapp.com/victim/store";
//    private DatabaseHelper db;
//    private Button btnAddKorban;
//    private TextInputEditText nik;
//    private TextInputEditText idDisasters;
//    private Spinner spinnerStatusKorban;
//    private Spinner spinnerStatusMedis;
//    private TextInputEditText namaRS;
//
//    //1 means data is synced and 0 means data is not synced
//    public static final int NAME_SYNCED_WITH_SERVER = 1;
//    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
//
//    //a broadcast to know weather the data is synced or not
//    public static final String DATA_SAVED_BROADCAST = "com.example.pelaporanbencana.datasaved";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_korban_forms_sqlite);
//
//        db = new DatabaseHelper(this);
//
//        btnAddKorban = findViewById(R.id.btnAddKorban);
//        nik = findViewById(R.id.nik);
//        idDisasters = findViewById(R.id.idDisasters);
//        spinnerStatusKorban = findViewById(R.id.spinnerStatusKorban);
//        spinnerStatusMedis = findViewById(R.id.spinnerStatusMedis);
//        namaRS = findViewById(R.id.namaRS);
//
//        btnAddKorban.setOnClickListener(this);
//
//        registerReceiver(new NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//    }
//
//    @Override
//    public void onClick(View view) {
//        saveVictimToServer();
//    }
//
//    private void saveVictimToServer() {
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Saving Name...");
//        progressDialog.show();
//
//        final String nikVictim = nik.getText().toString().trim();
//        final String idDisastersVictim = idDisasters.getText().toString().trim();
//        final String spinnerStatusVictim = spinnerStatusKorban.getSelectedItem().toString().trim();
//        final String spinnerStatusMedisVictim = spinnerStatusMedis.getSelectedItem().toString().trim();
//        final String namaRSVictim = namaRS.getText().toString().trim();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, data_url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressDialog.dismiss();
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                            if (!obj.getBoolean("error")) {
//                                //if there is a success
//                                //storing the name to sqlite with status synced
//                                saveNameToLocalStorage(nikVictim, idDisastersVictim, spinnerStatusVictim, spinnerStatusMedisVictim, namaRSVictim, NAME_SYNCED_WITH_SERVER);
//                            } else {
//                                //if there is some error
//                                //saving the name to sqlite with status unsynced
//                                saveNameToLocalStorage(nikVictim, idDisastersVictim, spinnerStatusVictim, spinnerStatusMedisVictim, namaRSVictim, NAME_NOT_SYNCED_WITH_SERVER);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        //on error storing the name to sqlite with status unsynced
//                        saveNameToLocalStorage(nikVictim, idDisastersVictim, spinnerStatusVictim, spinnerStatusMedisVictim, namaRSVictim, NAME_NOT_SYNCED_WITH_SERVER);
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("nik", nikVictim);
//                params.put("id_disasters", idDisastersVictim);
//                params.put("victim_status", spinnerStatusVictim);
//                params.put("medical_status", spinnerStatusMedisVictim);
//                params.put("hospital", namaRSVictim);
//                return params;
//            }
//        };
//
//        Controller.getInstance().addToRequestQueue(stringRequest);
//    }
//
//    //saving the name to local storage
//    private void saveNameToLocalStorage(String nik1, String idDisaster1, String statusKorban1, String statusMedis1, String namaRS1, int status1) {
//        nik.setText("");
//        idDisasters.setText("");
//        db.addVictim(nik1, idDisaster1, statusKorban1, statusMedis1,namaRS1, status1);
//    }
//}