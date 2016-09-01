package com.rf17.soundify.library.receive;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

import com.rf17.soundify.library.Soundify;
import com.rf17.soundify.library.Config;

import java.util.ArrayList;
import java.util.List;

public class Receiver {

    private AudioRecord audioRecord;
    private Thread thread;
    private boolean threadRunning = true;
    private static Receiver sReceiver;

    private List<Byte> list = new ArrayList<>();
    private short[] recordedData = new short[Config.TIME_BAND];

    public static Receiver getReceiver() {
        if (sReceiver == null) {
            sReceiver = new Receiver();
        }
        return sReceiver;
    }

    public Receiver() {
        initThread();
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

    public short parseRecData(short[] recordedData) {
        int size = recordedData.length;
        float[] floatData = new float[size];
        for (int i = 0; i < size; i++) {
            floatData[i] = recordedData[i];
        }
        short freq = ReceiverUtils.calcFreq(floatData);
        short data = (short) ((freq - Config.BASE_FREQ + Config.FREQ_STEP / 2) / Config.FREQ_STEP);
        Log.v("ParseRecData", "freq is " + freq + " | data is " + data);
        switch (data) {
            case Config.START_COMMAND:
                list = new ArrayList<>();
                return Config.NONSENSE_DATA;
            case Config.STOP_COMMAND:
                int sizeRet = list.size();
                byte[] retByte = new byte[sizeRet];
                for (int i = 0; i < sizeRet; i++) {
                    retByte[i] = list.get(i);
                }
                Soundify.soundifyListener.OnReceiveData(retByte);
                return Config.NONSENSE_DATA;
            default:
                //return !mStarted || data >= Command.STOP_COMMAND ? NONSENSE_DATA : data;
                return data >= Config.STOP_COMMAND ? Config.NONSENSE_DATA : data;
        }
    }

    public void inicializeReceiver() {
        if (audioRecord == null || audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, Config.SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, Config.AUDIO_FORMAT, AudioTrack.getMinBufferSize(Config.SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT) * 4);
        }
        if (audioRecord.getState() != AudioRecord.RECORDSTATE_RECORDING) {
            audioRecord.startRecording();
        }
        if(!thread.isAlive()){
            thread.start();
        }
    }

    public void stopReceiver() {
        if (audioRecord != null && audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
            audioRecord.stop();
            audioRecord.release();
        }
        if(thread.isAlive()) {
            thread.interrupt();
        }
    }

}
