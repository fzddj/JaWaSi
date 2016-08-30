package com.makedream.jawasi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.makedream.JaWaSiApplication;
import com.makedream.jawasi.R;
import com.makedream.jawasi.model.StockNote;
import com.makedream.jawasi.model.StockNoteDao;
import com.makedream.util.StringUtil;
import com.makedream.util.ThreadUtil;
import com.makedream.util.event.EventBus;
import com.makedream.util.event.EventSubscriber;

import java.util.Date;
import java.util.List;

/**
 * Created by ddj on 16-8-7.
 */
public class StockNoteActivity extends AppCompatActivity implements EventSubscriber, View.OnClickListener {

    public static final int Read = 0;

    public static final int Modify = 1;

    public static final int Add = 2;

    StockNoteDao stockNoteDao;

    StockNote stockNote;

    EditText etContent;

    Button actionBtn;


    /**
     * 股票ID
     */
    String stockId;

    int mode = Read;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_note);
        etContent = (EditText)findViewById(R.id.et_content);
        actionBtn = (Button)findViewById(R.id.btn_action);
        JaWaSiApplication application = ((JaWaSiApplication)getApplication());
        stockNoteDao = application.getDaoSession().getStockNoteDao();
        actionBtn.setOnClickListener(this);
        init(getIntent());
        EventBus.getInstance().subScribe(EventBus.STOCK_NOTE_LOAD_DATA, this);
        loadData(stockId);

    }

    /**
     * 加载数据
     */
    private void loadData(final String _stockId) {
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                if(!StringUtil.isEmpty(_stockId)) {
                    List<StockNote> list = stockNoteDao.queryRaw("where STOCK_ID = ?", _stockId);
                    if(list != null && list.size() > 0) {
                        StockNote note = list.get(0);
                        Bundle bundle = new Bundle();
                        bundle.putString("content", note.getStockContent());
                        bundle.putLong("id", note.getId());
                        EventBus.getInstance().publishEvent(EventBus.STOCK_NOTE_LOAD_DATA, bundle);
                    }else{
                        EventBus.getInstance().publishEvent(EventBus.STOCK_NOTE_LOAD_DATA, null);
                    }
                }
            }
        });
    }

    /**
     *
     */
    private void init(Intent intent){
        if(intent == null) return;
        stockId = intent.getStringExtra("stockid");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unSubScribe(EventBus.STOCK_NOTE_LOAD_DATA);
    }

    @Override
    public void dealEvent(String eventKey, Bundle bundle) {
        if(EventBus.STOCK_NOTE_LOAD_DATA.equals(eventKey)) {
            if(bundle != null) {
                String content = bundle.getString("content");
                long stockNoteId = bundle.getLong("id");
                stockNote = new StockNote();
                stockNote.setId(stockNoteId);
                stockNote.setStockId(stockId);
                etContent.setText(content);
                mode = Modify;
                actionBtn.setText("修改");
            }else{
                mode = Add;
                actionBtn.setText("保存");
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(mode == Add) {
            StockNote stockNote = new StockNote();
            stockNote.setStockId(stockId);
            stockNote.setStockContent(etContent.getText().toString());
            stockNote.setCreateDate(new Date());
            stockNoteDao.insert(stockNote);
            mode = Modify;
            Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
        }else if(mode == Modify) {
            if(stockNote != null) {
                stockNote.setStockContent(etContent.getText().toString());
                stockNoteDao.update(stockNote);
                Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
