package com.takwolf.android.loopviewpagerdemo.adapter;

import android.app.Activity;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.takwolf.android.loopviewpagerdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private int count;

    public DataListAdapter(@NonNull Activity activity) {
        inflater = LayoutInflater.from(activity);
    }

    public void setCount(@IntRange(from = 0) int count) {
        this.count = count;
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_data, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(String.valueOf(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_text)
        TextView tvText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(String text) {
            tvText.setText(text);
        }

    }

}
