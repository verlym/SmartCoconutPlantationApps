package com.informaticsdeveloper.smartcoconutplantation;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class HomeMenu extends Fragment implements View.OnClickListener {


    String strLoc, strTime, strDate;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        pref = getActivity().getSharedPreferences("dateloctime", 0);
        setHasOptionsMenu(false);


        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Halaman Awal");

        Button btnSimpan = getView().findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(this);
    }

    private void displayDateTime(String date, String time,String loc) {
        TextView tvTime = getView().findViewById(R.id.tvCurrentTime);
        TextView tvLoc = getView().findViewById(R.id.tvCurrentLoc);
        TextView tvDate = getView().findViewById(R.id.tvCurrentDate);
        tvDate.setText(date);
        tvTime.setText(time);
        tvLoc.setText(loc);
    }

    private void setLoc() {
        EditText etLocation = getView().findViewById(R.id.etCurrentLoc);
        strLoc = etLocation.getText().toString();
    }

    private void setTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        strTime = mdformat.format(calendar.getTime());
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
        strDate = mdformat.format(calendar.getTime());
    }

    @Override
    public void onClick(View v) {
        setTime();
        setLoc();
        setDate();

        editor = pref.edit();
        editor.putString("lokasi",strLoc);
        editor.putString("waktu",strTime);
        editor.putString("tanggal",strDate);
        editor.apply();


        displayDateTime(strTime,strDate,strLoc);
        Toast.makeText(getActivity(), "berhasil disimpan", Toast.LENGTH_SHORT).show();

    }
}
