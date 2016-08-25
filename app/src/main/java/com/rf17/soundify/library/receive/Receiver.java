package com.rf17.soundify.library.receive;

import android.media.AudioRecord;
import android.util.Log;

import com.rf17.soundify.library.Command;
import com.rf17.soundify.library.fft.FFT;
import com.rf17.soundify.library.fm.Config;

import java.util.ArrayList;
import java.util.List;

public class Receiver {

    private boolean mStoped;
    private boolean mStarted;
    private static short NONSENSE_DATA = 256;

    public byte[] receive(AudioRecord audioRecord) {
        //AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, Config.SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, Config.AUDIO_FORMAT, AudioTrack.getMinBufferSize(Config.SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT) * 4);
        //audioRecord.startRecording();
        mStoped = false;
        mStarted = false;
        List<Byte> list = new ArrayList<>();
        short[] recordedData = new short[Config.TIME_BAND];
        while (!mStoped) {
            audioRecord.read(recordedData, 0, Config.TIME_BAND);
            short parsedData = parseRecData(recordedData);
            if (parsedData != NONSENSE_DATA) {
                list.add((byte) parsedData);
            }
        }
        //audioRecord.stop();
        //audioRecord.release();
        int size = list.size();
        byte[] retByte = new byte[size];
        for (int i = 0; i < size; i++) {
            retByte[i] = list.get(i);
        }
        return retByte;
    }

    private short parseRecData(short[] recordedData) {
        int size = recordedData.length;
        float[] floatData = new float[size];
        for (int i = 0; i < size; i++) {
            floatData[i] = recordedData[i];
        }
        short freq = calcFreq(floatData);
        short data = (short) ((freq - Config.BASE_FREQ + Config.FREQ_STEP / 2) / Config.FREQ_STEP);
        Log.v("ParseRecData", "freq is " + freq + " | data is " + data);
        switch (data) {
            case Command.START_COMMAND:
                mStarted = true;
                return NONSENSE_DATA;
            case Command.STOP_COMMAND:
                mStoped = true;
                return NONSENSE_DATA;
            default:
                return !mStarted || data >= Command.STOP_COMMAND ? NONSENSE_DATA : data;
        }
    }

    private short calcFreq(float[] floatData) {
        int size = floatData.length;
        int fftSize = calcFftSize(size);
        FFT fft = new FFT(fftSize, Config.SAMPLE_RATE);
        fft.forward(floatData);
        float maxAmp = 0;
        short index = 0;
        for (short i = Config.BASE_FREQ; i < Config.BASE_FREQ + Config.FREQ_STEP * 140; i++) {
            float curAmp = fft.getFreq(i);
            if (curAmp > maxAmp) {
                maxAmp = curAmp;
                index = i;
            }
        }
        return index;
    }

    private int calcFftSize(int size) {
        int count = 0;
        int i;
        for (i = 0; i < 32 && size != 0; i++) {
            if ((size & 1) == 1) {
                count++;
            }
            size >>= 1;
        }
        int r = count == 1 ? i - 1 : i;
        return 1 << r;
    }

}
