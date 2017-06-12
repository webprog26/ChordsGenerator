package com.androiddeveloper.webprog26.ghordsgenerator.engine.events_handlers;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.EventsSubscriber;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by webpr on 12.06.2017.
 */

public abstract class AppEventsHandler implements EventsSubscriber{

    @Override
    public void subscribe() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unsubscribe() {
        EventBus.getDefault().unregister(this);
    }
}
