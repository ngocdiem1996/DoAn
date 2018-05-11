package com.pdtrong.final_android.Search.ItemAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pdtrong.final_android.R;
import com.pdtrong.final_android.Search.ItemClickListen.OnRecyclerViewItemClickListener;
import com.pdtrong.final_android.Search.ItemInfo.ItemInfo;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<ItemInfo> itemsList;
    private OnRecyclerViewItemClickListener mListener;

    public ItemAdapter(List<ItemInfo> itemsList) {
        this.itemsList = itemsList;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        TextView tvName;
        ImageView ibMap;
        ImageView ibList;
        LinearLayout mLinearLayout;

        public ViewHolder( View itemView ) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout_icon_recyclerview);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            ibList = (ImageView) itemView.findViewById(R.id.ib_list);
            ibMap = (ImageView) itemView.findViewById(R.id.ib_map);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener mListener){
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_search, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( @NonNull final ViewHolder holder, final int position ) {
        ItemInfo itemInfo = itemsList.get(position);
        holder.ivIcon.setImageResource(itemInfo.getImage());
        holder.tvName.setText(itemInfo.getName());
        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                mListener.onRecyclerViewItemClicked(position,v.getId());
            }
        });

        holder.ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                mListener.onRecyclerViewItemClicked(position, v.getId());
            }
        });

        holder.ibMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                mListener.onRecyclerViewItemClicked(position,v.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


}
