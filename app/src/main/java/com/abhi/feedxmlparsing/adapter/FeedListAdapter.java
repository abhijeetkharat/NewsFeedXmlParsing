package com.abhi.feedxmlparsing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhi.feedxmlparsing.model.Entry;
import com.abhi.feedxmlparsing.R;

import java.util.ArrayList;

/**
 * Created by abhijeet on 1/2/18.
 */

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.MyViewHolder>{

    private Context mContext;
    private ArrayList<Entry> mItems;
    public FeedListAdapter(Context context, ArrayList<Entry> items){
        mContext=context;
        mItems=items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.list_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindView(mItems.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle,txtSummary;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txtTitle);
            txtSummary=itemView.findViewById(R.id.txtSummery);

        }

        public void bindView(Entry entry,int position){
            txtTitle.setText(entry.title);
            txtSummary.setText(entry.summary);
        }
    }
}
