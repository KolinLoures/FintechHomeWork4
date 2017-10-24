package com.example.kolin.fintechhomework;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kolin on 24.10.2017.
 */

public class StringRecyclerAdapter extends RecyclerView.Adapter<StringRecyclerAdapter.StringViewHolder> {

    private List<String> data = new ArrayList<>();

    @Override
    public StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StringViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(StringViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<String> data){
        int oldSize = this.data.size();
        this.data.addAll(data);
        notifyItemRangeInserted(oldSize, data.size());
    }

    public void clearData(){
        int oldSize = this.data.size();
        this.data.clear();
        notifyItemRangeRemoved(0, oldSize);
    }

    public List<String> getData() {
        return data;
    }

    class StringViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        StringViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.item_recycler_view_text);
        }
    }
}
