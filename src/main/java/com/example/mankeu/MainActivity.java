package com.example.mankeu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FloatingActionButton addBottomView;
    DBHelper DB;

    public static int pendapatan;
    public static int pengeluaran;
    public static int saldo;
    public static ArrayList<Transaksi> arrayTransaksi;
    public static ArrayList<String> id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottonNavMenuView);
        addBottomView = findViewById(R.id.add_data);
        addBottomView.setBackground(null);
        addBottomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new AddDataFragment())
                        .commit();
                }
        });
        bottomNavigationView.setBackground(null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new HomeFragment())
                .commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch  (item.getItemId()){
                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.history:
                        selectedFragment = new AnalysisFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit();
                return true;
            }
        });
        pendapatan = 0;
        pengeluaran = 0;
        saldo = 0;
        id = new ArrayList<>();
        DB = new DBHelper(MainActivity.this);
        displayData();
    }
    public void hitung(String cat, String jumlah){
        int bayar = Integer.parseInt(jumlah);
        if (cat.equals("PENDAPATAN")){
            pendapatan += bayar;
        }else{
            pengeluaran += bayar;
        }
        saldo = pendapatan - pengeluaran;
    }

    public void displayData(){
        Cursor cursor = DB.getData();
        arrayTransaksi = new ArrayList<>();
        if(cursor.getCount()!=0){
            while(cursor.moveToNext()){
                if(cursor.getString(2).equals("PENDAPATAN")){
                    Transaksi trans = new Transaksi(cursor.getString(0), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4));
                    arrayTransaksi.add(trans);
                }else{
                    Transaksi trans = new Transaksi(cursor.getString(0), cursor.getString(1),"PENGELUARAN", cursor.getString(3), cursor.getString(4));
                    arrayTransaksi.add(trans);
                }
                id.add(cursor.getString(0));
                hitung(cursor.getString(2), cursor.getString(4));
            }
        }
    };
}