package com.example.pelaporanbencana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.pelaporanbencana.adapter.PengungsiRecViewAdapter;
import com.example.pelaporanbencana.model.Penduduk;

import java.util.ArrayList;

public class PengungsiFormsActivity extends AppCompatActivity implements PengungsiRecViewAdapter.ChangeStatusListener{

    RecyclerView recViewPengungsi = null;
    PengungsiRecViewAdapter pengungsiRecViewAdapter = null;
    ArrayList<Penduduk> pengungsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengungsi_forms);

        recViewPengungsi = findViewById(R.id.recViewPengungsi);

        pengungsis = new ArrayList<>();

//        Penduduk penduduk1 = new Penduduk();
//        penduduk1.setName("Jon2jfkd DJiosf");
//        penduduk1.setNik("743826943024");
//        penduduk1.setAge("14");
//        penduduk1.setGender("perempuan");
//        penduduk1.setSelected(false);
//        pengungsis.add(penduduk1);
//
//        Penduduk penduduk2 = new Penduduk();
//        penduduk2.setName("Jon2jfie Hdfhw");
//        penduduk2.setNik("7438253902423");
//        penduduk2.setAge("14");
//        penduduk2.setGender("laki-laki");
//        penduduk2.setSelected(false);
//        pengungsis.add(penduduk2);
//
//        Penduduk penduduk3 = new Penduduk();
//        penduduk3.setName("Jon2");
//        penduduk3.setNik("743824");
//        penduduk3.setAge("14");
//        penduduk3.setGender("laki-laki");
//        penduduk3.setSelected(false);
//        pengungsis.add(penduduk3);
//
//        Penduduk penduduk4 = new Penduduk();
//        penduduk4.setName("Jon4");
//        penduduk4.setNik("743823");
//        penduduk4.setAge("14");
//        penduduk4.setSelected(false);
//        pengungsis.add(penduduk4);
//
//        Penduduk penduduk5 = new Penduduk();
//        penduduk5.setName("Jon5");
//        penduduk5.setNik("743822");
//        penduduk5.setAge("14");
//        penduduk5.setSelected(false);
//        pengungsis.add(penduduk5);
//
//        Penduduk penduduk6 = new Penduduk();
//        penduduk6.setName("Jon6");
//        penduduk6.setNik("743821");
//        penduduk6.setAge("14");
//        penduduk6.setSelected(false);
//        pengungsis.add(penduduk6);

        pengungsiRecViewAdapter = new PengungsiRecViewAdapter(pengungsis, PengungsiFormsActivity.this, this);
        recViewPengungsi.setAdapter(pengungsiRecViewAdapter);
        recViewPengungsi.setLayoutManager(new GridLayoutManager(this, 1));

    }

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
                pengungsiRecViewAdapter.getFilter().filter(newText);
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
                Intent intent = new Intent(PengungsiFormsActivity.this, MenusActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void OnItemChangeListener(int position, Penduduk penduduk) {
        try {
            pengungsis.set(position, penduduk);
        }catch (Exception e){

        }
    }

}