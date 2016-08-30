package com.makedream.util.event;

import android.os.Bundle;


import com.makedream.util.StringUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * description: 消息总线 进行事件的分发和注册<br/>
 * author: dingdj<br/>
 * date: 2015/3/3<br/>
 */
public class EventBus {

    public static final String MAIN_LOAD_DATA = "main_load_data";
    public static final String STOCK_LOAD_DATA = "stock_load_data";
    public static final String STOCK_NOTE_LOAD_DATA = "stock_note_load_data";
    public static final String STOCK_RELOAD = "stock_reload";
    public static final String MISSION_COMPLETE = "mission_complete";
    public static final String EXERCISE_RELOAD = "exercise_reload";

    private static EventBus instance;

    private EventBus() {
    }

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    //事件和事件订阅者容器
    private HashMap<String, List<WeakReference<EventSubscriber>>> events = new HashMap<String, List<WeakReference<EventSubscriber>>>();


    //订阅事件
    public void subScribe(String eventKey, EventSubscriber subscriber) {
        if (StringUtil.isEmpty(eventKey) || subscriber == null) return;
        if (events.get(eventKey) == null) {
            List<WeakReference<EventSubscriber>> list = new ArrayList<WeakReference<EventSubscriber>>();
            list.add(new WeakReference<EventSubscriber>(subscriber));
            events.put(eventKey, list);
        } else {
            events.get(eventKey).add(new WeakReference<EventSubscriber>(subscriber));
        }

    }


    //取消订阅事件
    public void unSubScribe(String eventKey) {
        if (StringUtil.isEmpty(eventKey)) return;
        events.remove(eventKey);
    }

    //取消订阅事件
    public void unSubScribe(String eventKey, EventSubscriber subscriber) {
        if (StringUtil.isEmpty(eventKey)) return;
        if (events.get(eventKey) != null) {
            List<WeakReference<EventSubscriber>> list = events.get(eventKey);
            for (WeakReference<EventSubscriber> weakReference : list) {
                if (subscriber == weakReference.get()) {
                    list.remove(weakReference);
                    return;
                }
            }
        }
    }

    //发布事件
    public void publishEvent(String eventKey, Bundle bundle) {
        if (StringUtil.isEmpty(eventKey)) return;
        List<WeakReference<EventSubscriber>> references = events.get(eventKey);
        if (references != null) {
            for (WeakReference<EventSubscriber> reference : references) {
                EventSubscriber subscriber = reference.get();
                if (subscriber != null) {
                    try {
                        subscriber.dealEvent(eventKey, bundle);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
