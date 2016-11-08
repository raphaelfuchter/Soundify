package com.rf17.soundify.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rf17.soundify.utils.DebugUtils;
import com.rf17.soundifyapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {
    private List<String> mDataset;
    private static MyClickListener myClickListener;

    private char typeMsg;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

    static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView label;
        TextView dateTime;

        DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.txtMessage);
            dateTime = (TextView) itemView.findViewById(R.id.txtDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener clickListener) {
        myClickListener = clickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<String> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (typeMsg) {
            case 'R':
                DebugUtils.log("R");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_right, parent, false);
                return new DataObjectHolder(view);
            case 'L':
                DebugUtils.log("L");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_left, parent, false);
                return new DataObjectHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position));
        holder.dateTime.setText(sdf.format(new Date()));
    }

    public void addItem(String msg, char type) {
        typeMsg = type;
        mDataset.add(getItemCount(), msg);
        notifyItemInserted(getItemCount());
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }
}