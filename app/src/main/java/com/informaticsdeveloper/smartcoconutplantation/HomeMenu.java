package com.informaticsdeveloper.smartcoconutplantation;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.informaticsdeveloper.smartcoconutplantation.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Belal on 18/09/16.
 */


public class HomeMenu extends Fragment implements View.OnClickListener {


    String strLoc, strTime, strDate;
    public static ArrayList<History> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_menu_1, container, false);


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Halaman Awal");

        Button btnGetCurrentTime = getView().findViewById(R.id.btnGetCurrentTime);
        btnGetCurrentTime.setOnClickListener(this);
    }

    private void displayDateTime(String date, String time) {
        TextView tvTime = getView().findViewById(R.id.tvCurrentTime);
        TextView tvDate = getView().findViewById(R.id.tvCurrentDate);
        tvDate.setText(date);
        tvTime.setText(time);
    }

    private void setLoc() {
        TextView tvLocation = getView().findViewById(R.id.tvCurrentLocation);
        strLoc = tvLocation.getText().toString();
    }

    private void setTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        strTime = "Waktu : " + mdformat.format(calendar.getTime());
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        strDate = "Tanggal : " + mdformat.format(calendar.getTime());
    }

    @Override
    public void onClick(View v) {
        setTime();
        setLoc();
        setDate();

//        History history = new History(strLoc,strDate,strTime);
//        history.setLokasi(strLoc);
//        history.setTanggal(strDate);
//        history.setWaktu(strTime);
//        list.add(history);
//
//        displayDateTime(history.getWaktu(), history.getTanggal());
        Toast.makeText(getActivity(), "berhasil disimpan", Toast.LENGTH_SHORT).show();

    }
}
