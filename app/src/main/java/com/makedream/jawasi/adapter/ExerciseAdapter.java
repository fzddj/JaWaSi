package com.makedream.jawasi.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ddj.commonkit.DateUtil;
import com.makedream.jawasi.R;
import com.makedream.jawasi.databinding.ItemExerciseBinding;
import com.makedream.jawasi.model.ExerciseItem;
import com.makedream.jawasi.model.ExerciseItemDao;
import com.makedream.jawasi.model.ExerciseItemDetail;
import com.makedream.jawasi.model.ExerciseItemDetailDao;
import com.makedream.util.event.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.makedream.jawasi.Global.getContext;

/**
 * Created by Administrator on 2016/8/30.
 */

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.MainViewHolder> implements View.OnClickListener {

    private List<ExerciseItem> datas = new ArrayList<>();

    private LayoutInflater layoutInflater;

    private ExerciseItemDetailDao exerciseItemDetailDao;

    private ExerciseItemDao exerciseItemDao;



    public ExerciseAdapter(Context context, LayoutInflater layoutInflater,
                           ExerciseItemDetailDao exerciseItemDetailDao,
                           ExerciseItemDao exerciseItemDao) {
        this.layoutInflater = layoutInflater;
        this.exerciseItemDetailDao = exerciseItemDetailDao;
        this.exerciseItemDao = exerciseItemDao;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_exercise, parent, false);
        MainViewHolder holder = new MainViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        ExerciseItem item = datas.get(position);
        holder.itemExerciseBinding.btnPlay.setOnClickListener(this);
        holder.bindItem(item);
        holder.itemExerciseBinding.btnPlay.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        public ItemExerciseBinding itemExerciseBinding;

        public MainViewHolder(View itemView) {
            super(itemView);
            itemExerciseBinding = DataBindingUtil.bind(itemView);
        }

        public void bindItem(ExerciseItem item) {
            itemExerciseBinding.setVo(item);
            if(item.isComplete()) {
                itemExerciseBinding.tvDayExeciseNum.setText("0");
            }else{
                itemExerciseBinding.tvDayExeciseNum.setText(item.getPerNum()+"");
            }
        }
    }


    public List<ExerciseItem> getDatas() {
        return datas;
    }

    public void setDatas(List<ExerciseItem> datas) {
        this.datas = datas;
    }


    @Override
    public void onClick(View view) {
        MainViewHolder holder = (MainViewHolder) view.getTag();
        long typeId = holder.itemExerciseBinding.getVo().getId();
        if(isTodayTaskComplete(typeId)) {
            Toast.makeText(getContext(), "mission complete", Toast.LENGTH_SHORT).show();
        } else {
            ExerciseItemDetail exerciseItemDetail = new ExerciseItemDetail();
            exerciseItemDetail.setDateKey(DateUtil.getStringDateShort());
            exerciseItemDetail.setItemId(typeId);
            exerciseItemDetailDao.insert(exerciseItemDetail);
            holder.itemExerciseBinding.getVo().setDays(holder.itemExerciseBinding.getVo().getDays()+1);
            holder.itemExerciseBinding.getVo().setTotalNum(holder.itemExerciseBinding.getVo().getTotalNum()+holder.itemExerciseBinding.getVo().getPerNum());
            exerciseItemDao.update(holder.itemExerciseBinding.getVo());
            EventBus.getInstance().publishEvent(EventBus.EXERCISE_RELOAD, null);
        }
    }


    private boolean isTodayTaskComplete(Long itemId) {
        List list = exerciseItemDetailDao.queryRaw("where " + ExerciseItemDetailDao.Properties.DateKey.columnName + " = ? and " +
                        ExerciseItemDetailDao.Properties.ItemId.columnName + " = ? ",
                new String[]{DateUtil.getStringDateShort(), itemId+""});
        return list != null && list.size() > 0;
    }
}
