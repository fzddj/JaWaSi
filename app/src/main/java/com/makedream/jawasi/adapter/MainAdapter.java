package com.makedream.jawasi.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makedream.jawasi.R;
import com.makedream.jawasi.databinding.ItemMainBinding;
import com.makedream.jawasi.databinding.ItemMainHeaderBinding;
import com.makedream.jawasi.model.IndexItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingdj on 2016/7/1.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private static final String TAG = "MainAdapter";

    public static final int HEADER = 0;

    public static final int ITEM = 1;

    private Context context;

    private LayoutInflater layoutInflater;

    private List<IndexItem> list = new ArrayList<>();

    public MainAdapter(Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HEADER) {
            View view  = layoutInflater.inflate(R.layout.item_main_header, null);
            MainViewHolder mainViewHolder = new MainViewHolder(view, HEADER);
            return mainViewHolder;
        }else{
            Log.e(TAG, "onCreateViewHolder: " + viewType);
            View view = layoutInflater.inflate(R.layout.item_main, parent, false);
            MainViewHolder mainViewHolder = new MainViewHolder(view, ITEM);
            return mainViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        if(holder.itemType == ITEM){
            Log.e(TAG, "onBindViewHolder: " + position);
            IndexItem indexItem = list.get(position-1);
            holder.bindItem(indexItem);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return HEADER;
        }
        return ITEM;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        int itemType;
        ItemMainBinding binding;
        ItemMainHeaderBinding headerBinding;

        public MainViewHolder(View itemView, int type) {
            super(itemView);
            this.itemType = type;
            if(type == HEADER) {
                headerBinding = DataBindingUtil.bind(itemView);
            }else{
                binding = DataBindingUtil.bind(itemView);
            }

        }

        public void bindItem(IndexItem indexItem) {
            if(itemType == ITEM) {
                binding.tvCode.setText(indexItem.getCode());
            }
        }

        public int getItemType() {
            return itemType;
        }

    }

    public List<IndexItem> getList() {
        return list;
    }

    public void setList(List<IndexItem> list) {
        this.list = list;
    }
}
