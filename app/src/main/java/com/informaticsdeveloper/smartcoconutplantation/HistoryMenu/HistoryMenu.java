package com.informaticsdeveloper.smartcoconutplantation.HistoryMenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.informaticsdeveloper.smartcoconutplantation.R;

import java.util.ArrayList;

public class HistoryMenu extends Fragment {

    ArrayList<History> data = new ArrayList<>();
    SharedPreferences pref1, pref2;
    ListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        setHasOptionsMenu(false);

        pref1 = getActivity().getSharedPreferences("dateloctime", 0);
        pref2 = getActivity().getSharedPreferences("measure", 0);
        String s1 = pref1.getString("lokasi", "kosong");
        String s2 = pref1.getString("waktu", "kosong");
        String s3 = pref1.getString("tanggal", "kosong");
        String s4 = pref2.getString("ph", "kosong");
        String s5 = pref2.getString("mo", "kosong");
        String s6 = pref2.getString("Li", "kosong");
        String s7 = pref2.getString("Fe", "kosong");
        //String s6 = pref2.getString("kondisi","kosong");

        if ((s4!="kosong" && s5!="kosong") || (s4!="kosong") || (s5!="kosong")){
            data.add(new History(s1, s2, s3, s4, s5));
        }

        RecyclerView recyclerView = view.findViewById(R.id.rvHistory);
        adapter = new ListAdapter(data, getContext());
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
