package com.example.administrator.japanhouse.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.japanhouse.R;

/**
 * Created by   admin on 2018/4/16.
 */

public class OwnerWikipediaAdapter extends RecyclerView.Adapter{

    private Activity activity;

    public OwnerWikipediaAdapter(Activity activity) {
        this.activity = activity;
    }

    private onItemClickListener clickListener;

    public void setOnItemClickListener(onItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_owner_wikipedia, null);
        return new OwnerWikipediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof OwnerWikipediaViewHolder) {
            OwnerWikipediaViewHolder holder = (OwnerWikipediaViewHolder) viewHolder;

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class OwnerWikipediaViewHolder extends RecyclerView.ViewHolder {
        ImageView ivShow;
        TextView tvTitle;
        TextView tvTime;
        TextView tvNum;
        LinearLayout layout;

        public OwnerWikipediaViewHolder(View itemView) {
            super(itemView);
            ivShow = (ImageView) itemView.findViewById(R.id.item_owner_wikipedia_img);
            tvTitle = (TextView) itemView.findViewById(R.id.item_owner_wikipedia_title);
            tvTime = (TextView) itemView.findViewById(R.id.item_owner_wikipedia_time);
            tvNum = (TextView) itemView.findViewById(R.id.item_owner_wikipedia_num);
            layout = (LinearLayout) itemView.findViewById(R.id.item_owner_wikipedia_lt);
        }
    }

    public interface onItemClickListener{
        void onItemClick();
    }
}
