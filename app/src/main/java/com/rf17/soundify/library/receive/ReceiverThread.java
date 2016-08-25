package com.rf17.soundify.library.receive;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;

import com.rf17.soundify.library.Soundify;
import com.rf17.soundify.library.fm.Config;
import com.rf17.soundify.library.utils.DebugUtils;

import java.io.UnsupportedEncodingException;

public class ReceiverThread {

    private  AudioRecord audioRecord;
    private String returnValue;
    private Activity activity;

    public ReceiverThread(Activity activity) {
        this.activity = activity;
        initThread();
    }

    private void initThread() {
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, Config.SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, Config.AUDIO_FORMAT, AudioTrack.getMinBufferSize(Config.SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT) * 4);
        audioRecord.startRecording();
        new Thread() {
            @Override
            public void run() {
                DebugUtils.log("thread executando");
                final byte[] data = new Receiver().receive(audioRecord);
                try{
                    returnValue = new String(data, 0, data.length, "utf-8");
                    Soundify.soundifyListener.OnReceiveData(returnValue);
                } catch (UnsupportedEncodingException e) {
                    Soundify.soundifyListener.OnReceiveError(0, e.getMessage());
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void startThread() {

        DebugUtils.log("recording: "+AudioRecord.RECORDSTATE_RECORDING);

        //if(!thread.isAlive()){
            //audioRecord.startRecording();
        //}
    }
    public void stopThread() {
        //if(thread.isAlive()) {
            audioRecord.stop();
            audioRecord.release();
        //}
    }

}
