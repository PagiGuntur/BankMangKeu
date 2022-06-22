package com.example.mankeu;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AnalysisFragment extends Fragment {
    DBHelper DB;
    public AnalysisFragment() {
        // Required empty public constructor
    }

    public static AnalysisFragment newInstance(String param1, String param2) {
        AnalysisFragment fragment = new AnalysisFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB = new DBHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_analysis, container, false);
        PieChart pieChart = rootView.findViewById(R.id.pieChart);

        ArrayList<PieEntry> keluar = new ArrayList<>();
        Cursor cursor = DB.getPengeluaranByLabel();
        if(cursor.getCount()==0){
            keluar.add(new PieEntry(0, ""));
        }
        else {
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equals("PENGELUARAN")) {
                    int nilai = Integer.parseInt(cursor.getString(2));
                    keluar.add(new PieEntry(nilai, cursor.getString(1)));
                }
            }
        }

        PieDataSet pieDataSet = new PieDataSet(keluar, "Pengeluaran");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("pengeluaran");
        pieChart.animate();
        return rootView;
    }
}