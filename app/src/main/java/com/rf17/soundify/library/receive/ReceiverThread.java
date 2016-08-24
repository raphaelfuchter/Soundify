package com.rf17.soundify.library.receive;

import android.app.Activity;

import com.rf17.soundify.library.Soundify;

import java.io.UnsupportedEncodingException;

public class ReceiverThread {

    private Thread thread;
    private String returnValue;
    private Activity activity;

    public ReceiverThread(Activity activity) {
        this.activity = activity;
        initThread();
    }


    private void initThread() {
        thread = new Thread() {
            @Override
            public void run() {
                final byte[] data = new Receiver().receive();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            returnValue = new String(data, 0, data.length, "utf-8");
                            Soundify.soundifyListener.OnReceiveData(returnValue);
                        } catch (UnsupportedEncodingException e) {
                            Soundify.soundifyListener.OnReceiveError(0, e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
    }

    public void startThread() {
        if(!thread.isAlive()){
            thread.start();
        }
    }
    public void stopThread() {
        if(thread.isAlive()) {
            thread.interrupt();
        }
    }

}
