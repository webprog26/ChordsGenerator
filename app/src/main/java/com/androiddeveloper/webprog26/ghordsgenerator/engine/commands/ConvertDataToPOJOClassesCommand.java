package com.androiddeveloper.webprog26.ghordsgenerator.engine.commands;

import com.androiddeveloper.webprog26.ghordsgenerator.engine.realizers.ConvertDataToPOJOClassesRealizer;

/**
 * Created by webpr on 31.05.2017.
 */

public class ConvertDataToPOJOClassesCommand implements Command {

    private final String mJSONString;

    public ConvertDataToPOJOClassesCommand(String mJSONString) {
        this.mJSONString = mJSONString;
    }

    @Override
    public void execute() {
        new ConvertDataToPOJOClassesRealizer(getJSONString()).realize();
    }

    private String getJSONString(){
        return mJSONString;
    }
}
