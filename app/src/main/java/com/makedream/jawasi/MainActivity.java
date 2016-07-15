package com.makedream.jawasi;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makedream.jawasi.adapter.MainAdapter;
import com.makedream.jawasi.config.Constant;
import com.makedream.jawasi.databinding.ActivityMainBinding;
import com.makedream.jawasi.model.IndexItem;
import com.makedream.util.HttpUtil;
import com.makedream.util.ThreadUtil;
import com.makedream.util.event.EventBus;
import com.makedream.util.event.EventSubscriber;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EventSubscriber {

    ActivityMainBinding binding;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new MainAdapter(this, this.getLayoutInflater());
        binding.recyclerView.setAdapter(adapter);
        EventBus.getInstance().subScribe(Constant.EventKey.LOAD_MAIN_DATA, this);
        load();
    }

    private void load() {
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                String response = HttpUtil.loadUrl(Constant.GOLD_URL);
                Type listType = new TypeToken<List<IndexItem>>(){}.getType();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Gson gson = new Gson();
                    ArrayList<IndexItem> indexItems = gson.fromJson(response, listType);
                    for (IndexItem indexItem : indexItems) {
                        indexItem.intervalTime = 5*60*1000;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(Constant.EventKey.LOAD_MAIN_DATA, indexItems);
                    Global.publishEventFromWorkThread(Constant.EventKey.LOAD_MAIN_DATA, bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void dealEvent(String eventKey, Bundle bundle) {
        if(Constant.EventKey.LOAD_MAIN_DATA.equals(eventKey)) {
            if(bundle != null) {
                ArrayList<IndexItem> data =
                        bundle.getParcelableArrayList(Constant.EventKey.LOAD_MAIN_DATA);
                adapter.setList(data);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unSubScribe(Constant.EventKey.LOAD_MAIN_DATA);
        adapter.onPause();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.onResume();
    }
}
