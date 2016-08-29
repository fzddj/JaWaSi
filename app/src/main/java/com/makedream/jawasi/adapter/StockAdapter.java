package com.makedream.jawasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.makedream.jawasi.R;
import com.makedream.jawasi.databinding.ItemMainBinding;
import com.makedream.jawasi.model.Stock;
import com.makedream.jawasi.ui.activity.StockNoteActivity;
import com.makedream.jawasi.ui.window.EditStockDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingdj on 2016/7/1.
 */

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.MainViewHolder> implements View.OnClickListener,
View.OnLongClickListener{

    private static final String TAG = "StockAdapter";

    private Context context;

    private LayoutInflater layoutInflater;

    private List<Stock> list = new ArrayList<>();


    public StockAdapter(Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = layoutInflater.inflate(R.layout.item_main, parent, false);
        MainViewHolder mainViewHolder = new MainViewHolder(view);
        return mainViewHolder;

    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        Stock stock = list.get(position);
        holder.bindItem(stock);
        //holder.itemView.setOnLongClickListener(this);
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ItemMainBinding itemMainBinding;
        public Stock stock;

        public MainViewHolder(View itemView) {
            super(itemView);
            itemMainBinding = DataBindingUtil.bind(itemView);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void bindItem(Stock stock) {
            this.stock = stock;
            itemMainBinding.tvName.setText(stock.getStockName());

            if(stock.getCurrentPrice() > 0 && stock.getCurrentPrice() < stock.getPlanBuyPrice()) {
                itemMainBinding.tvPlanBuyPrice.setTextColor(Color.RED);
            }else{
                itemMainBinding.tvPlanBuyPrice.setTextColor(Color.BLACK);
            }

            if(stock.getCurrentPrice() > 0 && stock.getCurrentPrice() < stock.getNextPlanBuyPrice()) {
                itemMainBinding.tvNextPlanBuyPrice.setTextColor(Color.RED);
            }else{
                itemMainBinding.tvNextPlanBuyPrice.setTextColor(Color.BLACK);
            }

            if(stock.getCurrentPrice()> 0 && stock.getPlanSalePrice() <= stock.getCurrentPrice()) {
                itemMainBinding.tvPlanSellPrice.setTextColor(Color.RED);
            }else{
                itemMainBinding.tvPlanSellPrice.setTextColor(Color.BLACK);
            }



            itemMainBinding.tvCurPrice.setText(stock.getCurrentPrice()+"");
            itemMainBinding.tvPlanBuyPrice.setText(stock.getPlanBuyPrice()+"");
            itemMainBinding.tvNextPlanBuyPrice.setText(stock.getNextPlanBuyPrice()+"");
            itemMainBinding.tvPlanSellPrice.setText(stock.getPlanSalePrice()+"");
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            Log.e("ddj", "onCreateContextMenu: ");
            menu.setHeaderTitle("操作");
            // add context menu item
            int groupId = stock.getId().intValue();
            menu.add(groupId, 1, Menu.NONE, "修改");
            menu.add(groupId, 2, Menu.NONE, "删除");
        }


    }


    public List<Stock> getList() {
        return list;
    }

    public void setList(List<Stock> list) {
        this.list = list;
    }

    public void onPause() {
        Log.e(TAG, "onPause: "+"pause time");
    }

    @Override
    public void onClick(View v) {
        MainViewHolder holder = (MainViewHolder) v.getTag();
        Intent intent = new Intent();
        intent.putExtra("stockid", holder.stock.getStockId());
        intent.setClassName(v.getContext(), StockNoteActivity.class.getCanonicalName());
        v.getContext().startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        MainViewHolder holder = (MainViewHolder) v.getTag();
        EditStockDialog dialog = new EditStockDialog(holder.stock, v.getContext());
        dialog.show();
        return true;
    }
}
