package com.makedream.jawasi.ui.window;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.makedream.JaWaSiApplication;
import com.makedream.jawasi.Global;
import com.makedream.jawasi.R;
import com.makedream.jawasi.databinding.DialogAddExerciseBinding;
import com.makedream.jawasi.model.ExerciseItem;
import com.makedream.jawasi.model.ExerciseItemDao;
import com.makedream.util.event.EventBus;

/**
 * Created by Administrator on 2016/8/30.
 */

public class AddExerciseDialog extends Dialog {

    DialogAddExerciseBinding binding;

    public AddExerciseDialog(Context context) {
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout. dialog_add_exercise, null, false);
        setContentView(binding.getRoot());
        init();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void init() {
        setTitle("增加训练项目");
        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ExerciseItem item = new ExerciseItem();
                try{
                    item.setTypeName(binding.etExerciseName.getText().toString());
                    item.setPerNum(Integer.parseInt(binding.etExercisePerNum.getText().toString()));
                    JaWaSiApplication application = ((JaWaSiApplication) Global.mContext);
                    ExerciseItemDao itemDao = application.getDaoSession().getExerciseItemDao();
                    itemDao.insert(item);
                    EventBus.getInstance().publishEvent(EventBus.EXERCISE_RELOAD, null);
                }catch(Exception e){
                    e.printStackTrace();
                }

                AddExerciseDialog.this.dismiss();
            }
        });

        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AddExerciseDialog.this.dismiss();
            }
        });
    }

}
