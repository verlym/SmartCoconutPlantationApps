package com.informaticsdeveloper.smartcoconutplantation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.informaticsdeveloper.smartcoconutplantation.R;

import java.util.ArrayList;

//import static net.simplifiedcoding.navigationdrawerexample.History.*;


public class ListAdapter extends RecyclerView.Adapter {


//    public ListAdapter(ArrayList<History> dataList) {
//        this.dataList = dataList;
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvLocation, tvTime, tvDate;


        public ListViewHolder(View itemView) {
            super(itemView);

            tvLocation = itemView.findViewById(R.id.tvListLoc);
            tvDate = itemView.findViewById(R.id.tvListDate);
            tvTime = itemView.findViewById(R.id.tvListTime);
        }

        public void bindView(int position) {
            tvDate.setText("24/07/2018");
            tvTime.setText("10:05:33");
            tvLocation.setText("Kebun teh Pangalengan");
        }

        @Override
        public void onClick(View v) {

        }
    }
}
