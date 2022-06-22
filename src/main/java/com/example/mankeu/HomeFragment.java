package com.example.mankeu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ListView listTransaksi;
    DBHelper DB;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        TextView insaldor = (TextView) rootView.findViewById(R.id.inSaldo);
        DB = new DBHelper(getActivity());
        TextView outsaldor = (TextView) rootView.findViewById(R.id.outSaldo);
        TextView sumsaldo = (TextView) rootView.findViewById(R.id.sumSaldo);
        insaldor.setText(String.valueOf(MainActivity.pendapatan));
        outsaldor.setText(String.valueOf(MainActivity.pengeluaran));
        sumsaldo.setText(String.valueOf(MainActivity.saldo));
        listTransaksi =(ListView) rootView.findViewById(R.id.listTransaksi);
        ListAdapter adapter = new ListAdapter(getActivity(), R.layout.list_transaksi, MainActivity.arrayTransaksi);
        listTransaksi.setAdapter(adapter);
        listTransaksi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Delete entry");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        hapusData(String.valueOf(MainActivity.id.get(i)));
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }

            private void hapusData(String value) {
                boolean hapus = DB.deleteTransaksi(value);
                if (hapus==true){
                    Toast.makeText(getActivity(), "Data Telah Dihapus", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Data Tidak Dihapus", Toast.LENGTH_SHORT).show();
                }
                getActivity().recreate();
            }
        });
        return rootView;
    }
}