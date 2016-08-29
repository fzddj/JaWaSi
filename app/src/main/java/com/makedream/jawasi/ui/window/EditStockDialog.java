package com.makedream.jawasi.ui.window;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.makedream.JaWaSiApplication;
import com.makedream.jawasi.Global;
import com.makedream.jawasi.R;
import com.makedream.jawasi.databinding.DialogAddStockBinding;
import com.makedream.jawasi.model.Stock;
import com.makedream.jawasi.model.StockDao;
import com.makedream.util.event.EventBus;

/**
 * Created by ddj on 16-8-6.
 */
public class EditStockDialog  extends Dialog {

    DialogAddStockBinding binding;

    Stock stock;

    public EditStockDialog(Stock stock, Context context) {
        super(context);
        this.stock = stock;
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout. dialog_add_stock, null, false);
        setContentView(binding.getRoot());
        init();
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void init() {
        setTitle("修改股票");
        binding.etStockId.setEnabled(false);
        binding.etStockId.setText(stock.getStockId()+stock.getStockName());
        binding.etPlanBuyPrice.setText(stock.getPlanBuyPrice()+"");
        binding.etNextPlanBuyPrice.setText(stock.getNextPlanBuyPrice()+"");
        binding.etPlanSalePrice.setText(stock.getPlanSalePrice()+"");

        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                try{

                    double buyPrice = Double.parseDouble(binding.etPlanBuyPrice.getText().toString());
                    stock.setPlanBuyPrice(buyPrice);
                    double nextBuyPrice = Double.parseDouble(binding.etNextPlanBuyPrice.getText().toString());
                    stock.setNextPlanBuyPrice(nextBuyPrice);
                    double salePrice = Double.parseDouble(binding.etPlanSalePrice.getText().toString());
                    stock.setPlanSalePrice(salePrice);
                    JaWaSiApplication application = ((JaWaSiApplication) Global.mContext);
                    StockDao stockDao = application.getDaoSession().getStockDao();
                    stockDao.update(stock);

                    EventBus.getInstance().publishEvent(EventBus.STOCK_RELOAD, null);

                }catch(Exception e){
                    e.printStackTrace();
                }

                EditStockDialog.this.dismiss();
            }
        });

        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditStockDialog.this.dismiss();
            }
        });
    }

}
