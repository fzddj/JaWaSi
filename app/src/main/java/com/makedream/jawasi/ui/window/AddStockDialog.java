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
public class AddStockDialog extends Dialog {

    DialogAddStockBinding binding;

    public AddStockDialog(Context context) {
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout. dialog_add_stock, null, false);
        setContentView(binding.getRoot());
        init();
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void init() {
        setTitle("增加股票");
        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Stock stock = new Stock();

                try{
                    long stockId = Long.parseLong(binding.etStockId.getText().toString());
                    stock.setStockId(binding.etStockId.getText().toString());
                    double buyPrice = Double.parseDouble(binding.etPlanBuyPrice.getText().toString());
                    stock.setPlanBuyPrice(buyPrice);
                    double nextBuyPrice = Double.parseDouble(binding.etNextPlanBuyPrice.getText().toString());
                    stock.setNextPlanBuyPrice(nextBuyPrice);
                    double salePrice = Double.parseDouble(binding.etPlanSalePrice.getText().toString());
                    stock.setPlanSalePrice(salePrice);
                    JaWaSiApplication application = ((JaWaSiApplication)Global.mContext);
                    StockDao stockDao = application.getDaoSession().getStockDao();
                    stockDao.insert(stock);

                    EventBus.getInstance().publishEvent(EventBus.STOCK_RELOAD, null);

                }catch(Exception e){

                }

                AddStockDialog.this.dismiss();
            }
        });

        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AddStockDialog.this.dismiss();
            }
        });
    }

}
