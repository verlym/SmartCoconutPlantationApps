package com.informaticsdeveloper.smartcoconutplantation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ListFragment extends Fragment implements ListAdapter.MyClickListener {

    ArrayList<History> data = new ArrayList<>();
    ListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);

        History h1 = new History("24/07/2018","10:05:33","Bandung");
        History h2 = new History("25/07/2018","10:01:09","Bandung");
        History h3 = new History("26/07/2018","10:56:54","Bandung");
        data.add(h1);
        data.add(h2);
        data.add(h3);

        RecyclerView recyclerView = view.findViewById(R.id.rvHistory);
        adapter = new ListAdapter(getContext(),data);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        adapter.setOnItemClickListener(this);
        return view;
    }



    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemClick(final int position, View v) {
        adapter.deleteItem(position);
    }
}
