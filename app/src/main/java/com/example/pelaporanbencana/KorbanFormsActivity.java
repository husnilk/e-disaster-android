package com.example.pelaporanbencana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

//import com.android.volley.NetworkError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.Volley;
import com.example.pelaporanbencana.adapter.KorbanRecViewAdapter;
import com.example.pelaporanbencana.model.Penduduk;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KorbanFormsActivity extends AppCompatActivity implements KorbanRecViewAdapter.ChangeStatusListener, View.OnClickListener {

    ProgressDialog pd;
    RecyclerView recViewKorban;
    KorbanRecViewAdapter korbanRecViewAdapter;
    Button btnTambahkorban;
    Button btnReset;
    ArrayList<Penduduk> korbanList;
    ImageView ImageView5;
    FloatingActionButton fbTambahPenduduk;
    private static final String data_url = "https://disasterta.herokuapp.com/victim/showall";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korban_forms);

        pd = new ProgressDialog(KorbanFormsActivity.this);
        recViewKorban = findViewById(R.id.recViewKorban);
        btnTambahkorban = findViewById(R.id.buttonAddKorban);
        ImageView5 = findViewById(R.id.imageView5);
        btnReset = findViewById(R.id.btnReset);
        fbTambahPenduduk = findViewById(R.id.fbTambahPenduduk);
        btnReset.setOnClickListener(
                KorbanFormsActivity.this
        );

        korbanList = new ArrayList<>();

//        Penduduk penduduk2 = new Penduduk();
//        penduduk2.setName("Jon2jfie Hdfhw");
//        penduduk2.setNik("7438253902423");
//        penduduk2.setAge("14");
//        penduduk2.setGender("laki-laki");
//        penduduk2.setSelected(false);
//        korbanList.add(penduduk2);
//
//        Penduduk penduduk3 = new Penduduk();
//        penduduk3.setNamaPenduduk("Jon2");
//        penduduk3.setNikPenduduk("743824");
//        penduduk3.setUsiaPenduduk("14");
//        penduduk3.setJkPenduduk("laki-laki");
//        penduduk3.setSelected(false);
//        korbanList.add(penduduk3);
//
//        Penduduk penduduk4 = new Penduduk();
//        penduduk4.setNamaPenduduk("Jon4");
//        penduduk4.setNikPenduduk("743823");
//        penduduk4.setUsiaPenduduk("14");
//        penduduk4.setSelected(false);
//        korbanList.add(penduduk4);
//
//        Penduduk penduduk5 = new Penduduk();
//        penduduk5.setNamaPenduduk("Jon5");
//        penduduk5.setNikPenduduk("743822");
//        penduduk5.setUsiaPenduduk("14");
//        penduduk5.setSelected(false);
//        korbanList.add(penduduk5);
//
//        Penduduk penduduk6 = new Penduduk();
//        penduduk6.setNamaPenduduk("Jon6");
//        penduduk6.setNikPenduduk("743821");
//        penduduk6.setUsiaPenduduk("14");
//        penduduk6.setSelected(false);
//        korbanList.add(penduduk6);

        korbanRecViewAdapter = new KorbanRecViewAdapter(korbanList, KorbanFormsActivity.this, this);
        recViewKorban.setAdapter(korbanRecViewAdapter);
        recViewKorban.setLayoutManager(new GridLayoutManager(this, 1));

//        loadjson();

        fbTambahPenduduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KorbanFormsActivity.this, KorbanForms2Activity.class);
                startActivity(intent);
            }
        });
    }

//    private void loadjson() {
//        pd.setMessage("Mengambil LoginData");
//        pd.setCancelable(false);
//        pd.show();
//
//        JsonArrayRequest arrayRequest = new JsonArrayRequest(data_url, new Response.Listener<JSONArray>() {
//
//            @Override
//            public void onResponse(JSONArray response) {
//                pd.cancel();
//                Log.d("volley", "response : " + response.toString());
//                for (int i=0; i < response.length(); i++){
//                    try {
//                        JSONObject data = response.getJSONObject(i);
//                        Penduduk p = new Penduduk();
//                        p.setName(data.getString("name")); // memanggil nama array yang kita buat
//                        p.setGender(data.getString("gender"));
//                        p.setNik(data.getString("nik"));
//                        p.setAge(data.getString("age"));
//                        p.setSelected(false);
//                        korbanList.add(p);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                korbanRecViewAdapter.notifyDataSetChanged();
//            }
//        }, new Response.ErrorListener(){
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                pd.cancel();
//                Log.d("volley", "error : " + error.getMessage());
//            }
//        });
//        //menambah request ke request queue
//        Controller.getInstance().addToRequestQueue(arrayRequest);
//
//
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
                korbanRecViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    public void setMode(int selectedMode) {
        switch (selectedMode){
            case R.id.actionhome:
                Intent intent = new Intent(KorbanFormsActivity.this, MenusActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void OnItemChangeListener(int position, Penduduk penduduk) {
        try {
            korbanList.set(position, penduduk);
        }catch (Exception e){

        }
    }

    @Override
    public void onClick(View view) {
        updateList();
    }

    private void updateList() {
        ArrayList<Penduduk> temp = new ArrayList<>();
        try {
            for (int i=0; i<korbanList.size(); i++){
//                if (!korbanList.get(i).isSelected()){
//                    temp.add(korbanList.get(i));
//                }
            }
        }catch (Exception e){

        }
        korbanList = temp;
        if(korbanList.size()==0){
            recViewKorban.setVisibility(View.INVISIBLE);
            //gambar halaman kosong
            ImageView5.setVisibility(View.VISIBLE);

        }
        korbanRecViewAdapter.setKorbanList(korbanList);
        korbanRecViewAdapter.notifyDataSetChanged();
    }

}