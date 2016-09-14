package com.rf17.soundify.utils;

import android.util.Log;

import com.rf17.soundify.Config;

public class DebugUtils {

    public static void log(String log){
        if(Config.DEBUG_ON) {
            Log.v("SOUNDIFY LOG", log);
        }
    }

}
