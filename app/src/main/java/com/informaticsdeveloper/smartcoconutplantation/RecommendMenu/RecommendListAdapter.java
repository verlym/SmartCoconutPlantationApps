package com.informaticsdeveloper.smartcoconutplantation.RecommendMenu;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.informaticsdeveloper.smartcoconutplantation.Model.Plants;
import com.informaticsdeveloper.smartcoconutplantation.R;

import java.util.ArrayList;

public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.ViewHolder> {
    @NonNull

    private ArrayList<Plants> list;

    public RecommendListAdapter(@NonNull ArrayList<Plants> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //item xml yang dipakai
        public TextView tvNamaTanaman;
        public TextView tvPH;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNamaTanaman = itemView.findViewById(R.id.tvNamaTanaman);
            tvPH = itemView.findViewById(R.id.tvJumlahPH);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plants_list_item, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - mengambil elemen dari dataset (ArrayList) pada posisi tertentu
        // - mengeset isi view dengan elemen dari dataset tersebut
        String name = list.get(position).getNama();
        holder.tvNamaTanaman.setText(name);
        String ph = list.get(position).getPh();
        holder.tvPH.setText(ph);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
