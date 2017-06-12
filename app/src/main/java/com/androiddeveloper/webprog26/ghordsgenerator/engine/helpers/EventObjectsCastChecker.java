package com.androiddeveloper.webprog26.ghordsgenerator.engine.helpers;

import java.util.ArrayList;

/**
 * Created by webpr on 12.06.2017.
 */

public class EventObjectsCastChecker {

    public static boolean isArrayList(Object eventObject){
        return eventObject instanceof ArrayList;
    }

    public static boolean isString(Object eventObject){
        return eventObject instanceof String;
    }

    public static boolean isInteger(Object eventObject){
        return eventObject instanceof Integer;
    }
}
