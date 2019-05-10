package com.informaticsdeveloper.smartcoconutplantation.RecommendMenu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.informaticsdeveloper.smartcoconutplantation.Model.Plants;
import com.informaticsdeveloper.smartcoconutplantation.R;

import java.util.ArrayList;

public class RecomendationMenu extends Fragment {
    ImageView imgTanaman;
    TextView tvTanaman;
    String phString,liString,feString,moString;
    Float fe,ph,mo,li;
    SharedPreferences preferences;

    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Plants> dataSet;

    private RadioGroup rgMeasure1, rgMeasure2;
    private RadioButton rbMeasure;
    private Button btnCek;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recommend_plants,container,false);
        setHasOptionsMenu(false);
        preferences = getActivity().getSharedPreferences("measure", 0);
        phString = preferences.getString("ph", "0");
        moString = preferences.getString("mo", "0");
        liString = preferences.getString("Li", "0");
        feString = preferences.getString("Fe", "0");

        ph = Float.valueOf(phString);
        li = Float.valueOf(liString);
        mo = Float.valueOf(moString);
        fe = Float.valueOf(feString);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Rekomendasi");

        btnCek = view.findViewById(R.id.btnCekMeasure);
        rgMeasure1 = view.findViewById(R.id.radioMeasure);
        rgMeasure2 = view.findViewById(R.id.radioMeasure2);

        dataSet = new ArrayList<>();
        addListenerRadioBtn(view);

//        imgTanaman = getView().findViewById(R.id.imgTanaman);
//        tvTanaman = getView().findViewById(R.id.tvNamaTanaman);
//
//        if ((ph>=7.0)&&(ph<=10.0)){
//            imgTanaman.setImageResource(R.mipmap.carrot);
//            tvTanaman.setText("WORTEL");
//        }else if ((ph>=3.0)&&(ph<7.0)){
//            imgTanaman.setImageResource(R.mipmap.potatoes);
//            tvTanaman.setText("KENTANG");
//        }else if ((ph>=0.0)&&(ph<3.0)){
//            imgTanaman.setImageResource(R.mipmap.chili_pepper);
//            tvTanaman.setText("CABAI");
//        }

    }

    private void initDataset() {
        dataSet.add(new Plants("Wortel",ph.toString()));
//        dataSet.add(new Plants("Kacang Tanah","4.7"));
    }
    private void addListenerRadioBtn(final View view){
        btnCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rgMeasure1.getCheckedRadioButtonId();
                int selectedId2 = rgMeasure2.getCheckedRadioButtonId();

                if ((selectedId2==0) || (selectedId==0)){
                    Toast.makeText(getContext(), "silahkan pilih", Toast.LENGTH_SHORT).show();
                }
                else if (selectedId != 0){
                    rbMeasure = view.findViewById(selectedId);
                    Toast.makeText(getContext(),
                            rbMeasure.getText(),
                            Toast.LENGTH_SHORT).show();
                    rvPack(view);

                }else if (selectedId2 != 0){
                    rbMeasure = view.findViewById(selectedId2);
                    Toast.makeText(getContext(),
                            rbMeasure.getText(), Toast.LENGTH_SHORT).show();
                    rvPack(view);
                }
            }
        });
    }

    private void rvPack(View view){
        initDataset();

        rvView = view.findViewById(R.id.rvListPlants);
        rvView.setHasFixedSize(true);

        /**
         * Kita menggunakan LinearLayoutManager untuk list standar
         * yang hanya berisi daftar item
         * disusun dari atas ke bawah
         */
        layoutManager = new LinearLayoutManager(getActivity());
        rvView.setLayoutManager(layoutManager);

        adapter = new RecommendListAdapter(dataSet);
        adapter.notifyDataSetChanged();
        rvView.setAdapter(adapter);
    }
}
