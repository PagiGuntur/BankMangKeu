package com.example.mankeu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Transaksi> {

    Context context;
    int layoutResource;
    ArrayList<Transaksi> data;

    public ListAdapter(Context context, int layoutResource,ArrayList<Transaksi> transaksiArrayList){
        super(context,layoutResource, transaksiArrayList);
        this.context = context;
        this.layoutResource = layoutResource;
        this.data = transaksiArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Transaksi trans = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_transaksi, parent, false);
            TextView tanggal_tr=convertView.findViewById(R.id.tanggal);
            TextView cat_tr=convertView.findViewById(R.id.inout);
            TextView ket_tr=convertView.findViewById(R.id.keterangan);
            TextView jumlah=convertView.findViewById(R.id.jumlah);
            tanggal_tr.setText(trans.getTanggal_transaksi());
            cat_tr.setText(trans.getcategori_transaksi());
            ket_tr.setText(trans.getLabel_transaksi());
            jumlah.setText(trans.getjumlah());
        }
        return convertView;
    }
}
