package com.informaticsdeveloper.smartcoconutplantation.HistoryMenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.informaticsdeveloper.smartcoconutplantation.R;

import java.util.ArrayList;

//import static net.simplifiedcoding.navigationdrawerexample.History.*;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<History> dataList;
//    private static ListAdapter.MyClickListener sClickListener;
    private Context mContext;

    public ListAdapter( ArrayList<History> dataList, Context mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.cvList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteItem(position);
                return true;
            }
        });
        holder.tvPH.setText(dataList.get(position).getPh());
        holder.tvMoisture.setText(dataList.get(position).getMoisture());
        holder.tvLocation.setText(dataList.get(position).getLocation());
        holder.tvDate.setText(dataList.get(position).getTanggal());
        holder.tvTime.setText(dataList.get(position).getWaktu());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvLocation, tvTime, tvDate;
        public TextView tvPH, tvMoisture;
        public CardView cvList;

        public ViewHolder(View itemView) {
            super(itemView);
            cvList = itemView.findViewById(R.id.cvList);
            tvLocation = itemView.findViewById(R.id.tvListLoc);
            tvPH = itemView.findViewById(R.id.tvPH);
            tvMoisture = itemView.findViewById(R.id.tvMoisture);
            tvDate = itemView.findViewById(R.id.tvListDate);
            tvTime = itemView.findViewById(R.id.tvListTime);
        }
    }

    void deleteItem(int index) {
        dataList.remove(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index,dataList.size());
    }

}
