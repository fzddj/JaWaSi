package com.makedream.jawasi.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makedream.jawasi.R;
import com.makedream.jawasi.databinding.ItemMainBinding;
import com.makedream.jawasi.databinding.ItemMainHeaderBinding;
import com.makedream.jawasi.model.IndexItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private SparseIntArray sparseIntArray = new SparseIntArray();

    private MyCountDownTimer timer;

    public MainAdapter(Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        timer = new MyCountDownTimer(5*60*1000, 1*1000);
        timer.start();
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

    public class MainViewHolder extends RecyclerView.ViewHolder implements TimeTickSubscribe {
        int itemType;
        ItemMainBinding binding;
        ItemMainHeaderBinding headerBinding;
        IndexItem indexItem;

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
                timer.register(indexItem.hashCode()+"", this);
                this.indexItem = indexItem;
            }
        }

        public int getItemType() {
            return itemType;
        }

        @Override
        public void notifyTick(int tick) {
            if(itemType == ITEM) {
                int originalTick = sparseIntArray.get(indexItem.getCode().hashCode());
                int time = originalTick - tick;
                if(indexItem.getCode().hashCode() % 2 == 0) {
                    binding.tvCode.setText((indexItem.intervalTime / 1000 - time) + "");
                }else{
                    binding.tvCode.setText(indexItem.getCode());
                }
            }

        }
    }


    public class MyCountDownTimer extends CountDownTimer {

        public int tick;
        public HashMap<String, WeakReference<MainViewHolder>> map = new HashMap<String, WeakReference<MainViewHolder>>();

        public MyCountDownTimer( long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tick = (int)millisUntilFinished / 1000;
            for(Map.Entry<String, WeakReference<MainViewHolder>> entry : map.entrySet()) {
                WeakReference<MainViewHolder> value = entry.getValue();
                MainViewHolder viewHolder = value.get();
                if(viewHolder != null) {
                    viewHolder.notifyTick(tick);
                }
            }
        }

        @Override
        public void onFinish() {

        }

        public int getTick() {
            return tick;
        }

        public void register(String key, MainViewHolder subscribe) {
            map.put(key, new WeakReference<MainViewHolder>(subscribe));
        }



    }


    public interface TimeTickSubscribe {
        abstract public void notifyTick(int tick);
    }




    public List<IndexItem> getList() {
        return list;
    }

    public void setList(List<IndexItem> list) {
        this.list = list;
        for (IndexItem indexItem : this.list) {
            sparseIntArray.append(indexItem.getCode().hashCode(), timer.getTick());
        }
    }

    public void onPause() {
        Log.e(TAG, "onPause: "+"pause time");
        if(timer != null) {
            timer.cancel();
        }
    }

    public void onResume() {
        Log.e(TAG, "onResume: "+"resume time");
        timer = new MyCountDownTimer(5*60*1000, 1*1000);
        timer.start();
        for (IndexItem indexItem : this.list) {
            sparseIntArray.append(indexItem.getCode().hashCode(), timer.getTick());
        }
    }
}
