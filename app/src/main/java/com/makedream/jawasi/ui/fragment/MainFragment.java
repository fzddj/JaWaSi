package com.makedream.jawasi.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddj.commonkit.DateUtil;
import com.makedream.JaWaSiApplication;
import com.makedream.jawasi.Global;
import com.makedream.jawasi.R;
import com.makedream.jawasi.adapter.ExerciseAdapter;
import com.makedream.jawasi.model.ExerciseItem;
import com.makedream.jawasi.model.ExerciseItemDao;
import com.makedream.jawasi.model.ExerciseItemDetailDao;
import com.makedream.util.ThreadUtil;
import com.makedream.util.event.EventBus;
import com.makedream.util.event.EventSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements EventSubscriber {


    private ExerciseItemDao mExeciseItemDao;

    ExerciseItemDetailDao mExerciseItemDetailDao;

    private ExerciseAdapter exerciseAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



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
        JaWaSiApplication application = ((JaWaSiApplication)getActivity().getApplication());
        mExeciseItemDao = application.getDaoSession().getExerciseItemDao();
        mExerciseItemDetailDao = application.getDaoSession().getExerciseItemDetailDao();
        exerciseAdapter = new ExerciseAdapter(getContext(), getLayoutInflater(savedInstanceState), mExerciseItemDetailDao
        , mExeciseItemDao);
        EventBus.getInstance().subScribe(EventBus.MAIN_LOAD_DATA, this);
        EventBus.getInstance().subScribe(EventBus.MISSION_COMPLETE, this);
        EventBus.getInstance().subScribe(EventBus.EXERCISE_RELOAD, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rcv);
        recyclerView.setAdapter(exerciseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadData();
        return view;
    }

    private void loadData() {
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                ArrayList<ExerciseItem> items = new ArrayList<ExerciseItem>(mExeciseItemDao.loadAll());
                if(items.size() == 0) return;
                for (ExerciseItem item : items) {
                    item.setComplete(isTodayTaskComplete(item.getId()));
                    Log.e("ddj", "run: "+item.isComplete());
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("items", items);
                Global.publishEventFromWorkThread(EventBus.MAIN_LOAD_DATA, bundle);
            }
        });
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
            ArrayList<ExerciseItem> items = bundle.getParcelableArrayList("items");
            exerciseAdapter.getDatas().clear();
            exerciseAdapter.getDatas().addAll(items);
            exerciseAdapter.notifyDataSetChanged();
        }else if(EventBus.MISSION_COMPLETE.equals(eventKey)) {

        }else if(EventBus.EXERCISE_RELOAD.equals(eventKey)) {
            loadData();
        }
    }

    private boolean isTodayTaskComplete(Long itemId) {
        List list = mExerciseItemDetailDao.queryRaw("where " + ExerciseItemDetailDao.Properties.DateKey.columnName + " = ? and " +
                        ExerciseItemDetailDao.Properties.ItemId.columnName + " = ? ",
                new String[]{DateUtil.getStringDateShort(), itemId+""});
        return list != null && list.size() > 0;
    }
}
