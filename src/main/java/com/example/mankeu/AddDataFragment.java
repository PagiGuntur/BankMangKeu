package com.example.mankeu;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddDataFragment extends Fragment {
    EditText etDate;
    Spinner inoutTrans;
    EditText addKet;
    EditText addMoney;
    Button create;
    int jumlah;
    DBHelper DB;

    public AddDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_data, container, false);
        etDate = rootView.findViewById(R.id.etdate);
        inoutTrans = rootView.findViewById(R.id.kategori);
        addKet = rootView.findViewById(R.id.keterangan);
        addMoney = rootView.findViewById(R.id.addMoney);
        create = (Button) rootView.findViewById(R.id.addInOut);
        DB = new DBHelper(getActivity());
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int date = calendar.get(Calendar.DAY_OF_MONTH);

        DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
        Date dateF = new Date();
        String today = dateFormat.format(dateF);
        etDate.setText(today);
        addKet.setText("");
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        rootView.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String dates = dayOfMonth+"/"+month+"/"+year;
                        etDate.setText(dates);
                    }
                },year,month,date);
                datePickerDialog.show();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tanggal = etDate.getText().toString();
                String label = inoutTrans.getSelectedItem().toString();
                String keterangan = addKet.getText().toString();
                if (!label.equals("PENDAPATAN")){
                    label = "PENGELUARAN";
                }
                if (TextUtils.isEmpty(addMoney.getText().toString())){
                    jumlah = 0;
                    addData(tanggal, label, keterangan, jumlah);
                }else{
                    jumlah = Integer.parseInt(addMoney.getText().toString());
                    addData(tanggal, label, keterangan, jumlah);
                }
            }

            private void addData(String tanggal, String label, String keterangan, int jumlah) {
                boolean tambah = DB.insertTransaksi(tanggal, label, keterangan, jumlah);
                if (tambah==true){
                    Toast.makeText(getActivity(), "Data Telah Masuk", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Data Tidak Masuk", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}