package com.makedream.jawasi.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.makedream.JaWaSiApplication;
import com.makedream.jawasi.Global;
import com.makedream.jawasi.R;
import com.makedream.jawasi.adapter.StockAdapter;
import com.makedream.jawasi.config.Constant;
import com.makedream.jawasi.databinding.FragmentStockBinding;
import com.makedream.jawasi.model.Stock;
import com.makedream.jawasi.model.StockDao;
import com.makedream.jawasi.ui.window.AddStockDialog;
import com.makedream.jawasi.ui.window.EditStockDialog;
import com.makedream.util.HttpUtil;
import com.makedream.util.StockUtil;
import com.makedream.util.ThreadUtil;
import com.makedream.util.event.EventBus;
import com.makedream.util.event.EventSubscriber;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.codefalling.recyclerviewswipedismiss.SwipeDismissRecyclerViewTouchListener;

/**
 * Created by ddj on 16-8-6.
 */
public class StockFragment extends Fragment implements EventSubscriber {

    private StockDao stockDao;

    private FragmentStockBinding binding;

    private StockAdapter stockAdapter;

    public StockFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockAdapter = new StockAdapter(getContext(), getLayoutInflater(savedInstanceState));
        EventBus.getInstance().subScribe(EventBus.STOCK_LOAD_DATA, this);
        EventBus.getInstance().subScribe(EventBus.STOCK_RELOAD, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock, container, false);
        binding = DataBindingUtil.bind(view);
        binding.rcv.setAdapter(stockAdapter);
        binding.rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.header.header.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AddStockDialog dialog = new AddStockDialog(getContext());
                dialog.show();

                return true;
            }
        });
        JaWaSiApplication application = ((JaWaSiApplication)getActivity().getApplication());
        stockDao = application.getDaoSession().getStockDao();
        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void dealEvent(String eventKey, Bundle bundle) {
        if(EventBus.STOCK_LOAD_DATA.equals(eventKey)) {
            ArrayList<Stock> stocks = bundle.getParcelableArrayList("stocks");
            stockAdapter.getList().clear();
            stockAdapter.getList().addAll(stocks);
            stockAdapter.notifyDataSetChanged();
        }else if(EventBus.STOCK_RELOAD.equals(eventKey)) {
            stockAdapter.getList().clear();
            try {
                loadData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void loadData() throws Exception {
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                ArrayList<Stock> stocks = new ArrayList<Stock>(stockDao.loadAll());
                if(stocks.size() == 0) return;
                HashMap<String, Stock> map = new HashMap<String, Stock>();
                String[] stockIds = new String[stocks.size()];
                int i =0;
                for (Stock stock : stocks) {
                    map.put(stock.getStockId(), stock);
                    stockIds[i] = StockUtil.getStockExByStockId(stock.getStockId())+stock.getStockId();
                    Log.e("ddj", "run: "+stockIds[i]);
                    i++;
                }

                ArrayList<Stock> list = loadStocks(stockIds);
                for (Stock stock : list) {
                    map.get(stock.getStockId()+"").setCurrentPrice(stock.getCurrentPrice());
                    map.get(stock.getStockId()+"").setStockName(stock.getStockName());
                }
                Log.e("ddj", "run: "+ list.size());
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("stocks", stocks);
                Global.publishEventFromWorkThread(EventBus.STOCK_LOAD_DATA, bundle);
            }
        });

    }

    public ArrayList<Stock> loadStocks(String[] stockIds) {
        ArrayList<Stock> list = new ArrayList<>();
        String url = formatUrl(stockIds);
        System.out.println(url);
        String response = HttpUtil.loadUrl(url);
        String[] stockInfos = response.split(";");
        for (String stockInfo : stockInfos) {
            try {
                Stock stock = new Stock();
                int index = stockInfo.indexOf("=");
                if (index == -1) continue;
                String stockId = stockInfo.substring(index - 6, index);
                stock.setStockId(stockId);
                String stockContent = stockInfo.substring(index + 2, stockInfo.length() - 1);
                String[] stockItems = stockContent.split(",");
                stock.setStockName(stockItems[0]);
                stock.setCurrentPrice(Double.parseDouble(stockItems[3]));
                list.add(stock);
            }catch (Exception e) {
                continue;
            }
        }
        return list;

    }


    private String formatUrl(String[] stockIds) {
        if(stockIds == null) return Constant.STOCK_URL;
        String url = Constant.STOCK_URL;
        for (String stockId : stockIds) {
            url = url +stockId + ",";
        }
        return url;
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == 1){ //修改
            Log.e("ddj", "onContextItemSelected: "+1);
            long id = item.getGroupId();
            Stock stock = stockDao.load(id);
            EditStockDialog dialog = new EditStockDialog(stock, getContext());
            dialog.show();

        }else{ //删除
            long id = item.getGroupId();
            stockDao.deleteByKey(id);
            EventBus.getInstance().publishEvent(EventBus.STOCK_RELOAD, null);
        }
        return true;
    }
}
