package com.makedream.jawasi.ui.fragment;


import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ddj.commonkit.DateUtil;
import com.makedream.JaWaSiApplication;
import com.makedream.jawasi.Global;
import com.makedream.jawasi.R;
import com.makedream.jawasi.databinding.FragmentMainBinding;
import com.makedream.jawasi.model.ExeciseItem;
import com.makedream.jawasi.model.ExeciseItemDao;
import com.makedream.util.ThreadUtil;
import com.makedream.util.event.EventBus;
import com.makedream.util.event.EventSubscriber;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements EventSubscriber {


    private ExeciseItemDao mExeciseItemDao;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentMainBinding binding;


    public MainFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        EventBus.getInstance().subScribe(EventBus.MAIN_LOAD_DATA, this);
        EventBus.getInstance().subScribe(EventBus.MISSION_COMPLETE, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        binding = DataBindingUtil.bind(view);
        JaWaSiApplication application = ((JaWaSiApplication)getActivity().getApplication());
        mExeciseItemDao = application.getDaoSession().getExeciseItemDao();
        binding.btnPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //判断今天是否已完成
                if(isTodayTaskComplete()) {
                    Toast.makeText(getContext(), "mission complete", Toast.LENGTH_SHORT).show();
                }else{
                    ExeciseItem execiseItem = new ExeciseItem();
                    execiseItem.setNum(60);
                    execiseItem.setCreateDate(new Date());
                    execiseItem.setType(1);
                    execiseItem.setDateKey(DateUtil.getStringDateShort());
                    mExeciseItemDao.insert(execiseItem);
                    loadData();
                }
            }
        });
        loadData();
        return view;
    }

    private void loadData() {
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                long day = mExeciseItemDao.count();
                String sumQuery = String.format("select sum(%s) from %s",
                        new Object[]{ExeciseItemDao.Properties.Num.columnName, ExeciseItemDao.TABLENAME});
                Cursor cursor = mExeciseItemDao.getDatabase().rawQuery(sumQuery, null);
                if(cursor != null) {
                    cursor.moveToNext();
                    int nums = cursor.getInt(0);
                    ExerciseVo vo = new ExerciseVo();
                    vo.day = day;
                    vo.nums = nums;
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("vo", vo);
                    Global.publishEventFromWorkThread(EventBus.MAIN_LOAD_DATA, bundle);
                }
                if(isTodayTaskComplete()) {
                    Global.publishEventFromWorkThread(EventBus.MISSION_COMPLETE, null);
                }
            }
        });
    }

    private boolean isTodayTaskComplete() {
        List list = mExeciseItemDao.queryRaw("where "+ExeciseItemDao.Properties.DateKey.columnName+" = ? ",
                new String[]{DateUtil.getStringDateShort()});
        return list != null && list.size() > 0;
    }


    public static class ExerciseVo implements Parcelable {

        public long day;

        public int nums;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.day);
            dest.writeInt(this.nums);
        }

        public ExerciseVo() {
        }

        protected ExerciseVo(Parcel in) {
            this.day = in.readLong();
            this.nums = in.readInt();
        }

        public static final Creator<ExerciseVo> CREATOR = new Creator<ExerciseVo>() {
            @Override
            public ExerciseVo createFromParcel(Parcel source) {
                return new ExerciseVo(source);
            }

            @Override
            public ExerciseVo[] newArray(int size) {
                return new ExerciseVo[size];
            }
        };
    }

    @Override
    public void onDestroyView() {
        EventBus.getInstance().unSubScribe(EventBus.MAIN_LOAD_DATA, this);
        EventBus.getInstance().unSubScribe(EventBus.MISSION_COMPLETE, this);
        super.onDestroyView();
    }

    @Override
    public void dealEvent(String eventKey, Bundle bundle) {
        if(EventBus.MAIN_LOAD_DATA.equals(eventKey)) {
            if(bundle == null) return;
            ExerciseVo vo = bundle.getParcelable("vo");
            binding.setVo(vo);
            binding.tvDay.setText(vo.day+"");
            binding.tvNum.setText(vo.nums + "");
        }else if(EventBus.MISSION_COMPLETE.equals(eventKey)) {
            binding.tvDayExeciseNum.setText("0");
        }
    }
}
