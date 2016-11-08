package com.rf17.soundify.receive;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;

import com.rf17.soundify.Config;
import com.rf17.soundify.Soundify;
import com.rf17.soundify.exception.SoundifyException;
import com.rf17.soundify.utils.DebugUtils;
import com.rf17.soundify.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class Receiver {

    /**
     * This variable contains the audioRecord
     */
    private AudioRecord audioRecord;

    /**
     *
     */
    private Thread thread;

    /**
     *
     */
    private boolean threadRunning = true;

    /**
     *
     */
    private static Receiver sReceiver;

    private List<Byte> list = new ArrayList<>();
    private short[] recordedData = new short[Config.TIME_BAND];

    /**
     * This function is used to initialize the soundify receiver instance.
     * @return Receiver Soundify Receiver
     */
    public static Receiver getReceiver() {
        if (sReceiver == null) {
            sReceiver = new Receiver();
        }
        return sReceiver;
    }

    private void initThread() {
        thread = new Thread() {
            @Override
            public void run() {
                while (threadRunning) {
                    audioRecord.read(recordedData, 0, Config.TIME_BAND);
                    short parsedData = parseRecData(recordedData);
                    if (parsedData != Config.NONSENSE_DATA) {
                        list.add((byte) parsedData);
                    }
                }
            }
        };
    }

    private short parseRecData(short[] recordedData) {
        float[] floatData = ListUtils.convertArrayShortToArrayFloat(recordedData);
        short freq = ReceiverUtils.calcFreq(floatData);
        short data = ReceiverUtils.calcData(freq);
        //DebugUtils.log("Freq: " + freq + "  |  data: " + data);
        switch (data) {
            case Config.START_COMMAND:
                list = new ArrayList<>();
                return Config.NONSENSE_DATA;
            case Config.STOP_COMMAND:
                if(!list.isEmpty()) {
                    byte[] retByte = ListUtils.convertListBytesToArrayBytes(list);

                    DebugUtils.log("retByte: "+retByte);

                    if (retByte != null) {
                        Soundify.soundifyListener.OnReceiveData(retByte);
                    }
                    list = new ArrayList<>();
                }
                return Config.NONSENSE_DATA;
            default:
                if(data >= Config.STOP_COMMAND){
                    return Config.NONSENSE_DATA;
                }else{
                    return data;
                }
        }
    }

    public void inicializeReceiver() throws SoundifyException {
        if (audioRecord == null || audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, Config.SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, Config.AUDIO_FORMAT, AudioTrack.getMinBufferSize(Config.SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT) * 4);
        }
        if (audioRecord.getState() != AudioRecord.RECORDSTATE_RECORDING) {
            audioRecord.startRecording();
        }
        if(!threadRunning){
            threadRunning = true;
        }
        if(thread == null){
            initThread();
        }
        if (thread.getState() == Thread.State.NEW) {
            thread.start();
        }
        if (thread.getState() == Thread.State.TERMINATED) {
            initThread();
            thread.start();
        }
    }

    public void stopReceiver() throws SoundifyException {
        if (audioRecord != null && audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
            audioRecord.stop();
            audioRecord.release();
        }
        if(threadRunning){
            threadRunning = false;
        }
        if (thread.isAlive()) {
            thread.interrupt();
        }
    }

}
