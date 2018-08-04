package com.informaticsdeveloper.smartcoconutplantation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.informaticsdeveloper.smartcoconutplantation.R;

import java.util.ArrayList;

//import static net.simplifiedcoding.navigationdrawerexample.History.*;


public class ListAdapter extends RecyclerView.Adapter {

    private ArrayList<History> dataList;
    private static ListAdapter.MyClickListener sClickListener;
    private Context mContext;

    public ListAdapter(Context context, ArrayList<History> dataList) {
        this.dataList = dataList;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((ListViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    void deleteItem(int index) {
        dataList.remove(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index,dataList.size());
    }


     class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvLocation, tvTime, tvDate;


        public ListViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            tvLocation = itemView.findViewById(R.id.tvListLoc);
            tvDate = itemView.findViewById(R.id.tvListDate);
            tvTime = itemView.findViewById(R.id.tvListTime);
        }

        public void bindView(int position) {
            tvDate.setText(dataList.get(position).getTanggal());
            tvTime.setText(dataList.get(position).getWaktu());
            tvLocation.setText(dataList.get(position).getLokasi());
        }

        @Override
        public void onClick(View v) {
            sClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    interface MyClickListener {
        void onItemClick(int position, View v);
    }

    void setOnItemClickListener(ListAdapter.MyClickListener myClickListener) {
        this.sClickListener = myClickListener;
    }
}
