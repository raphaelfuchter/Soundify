package com.rf17.soundify.library.send;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.rf17.soundify.library.Command;
import com.rf17.soundify.library.fm.Config;

import java.util.ArrayList;
import java.util.List;

public class Sender {

    private static Sender sSender;
    private AudioTrack mAudioTrack;
    private static final int COMMAND_REPEAT_NUM = 5;
    private static final int MAX_SIGNAL_STRENGTH = 65535;

    public static Sender getSender() {
        if (sSender == null) {
            sSender = new Sender();
        }
        return sSender;
    }

    public void send(byte[] data) {
        List<Short> list = new ArrayList<>();
        appendCommand(list, Command.START_COMMAND);
        for (byte b : data) {
            append(list, b);
        }
        appendCommand(list, Command.STOP_COMMAND);
        sendData(list);
    }

    private void sendData(List<Short> list) {
        int size = list.size();
        short[] data = new short[size];
        for (int i = 0; i < size; i++) {
            data[i] = list.get(i);
        }
        generateAudioTrackIfNecessary();
        mAudioTrack.write(data, 0, size);
        mAudioTrack.play();
    }

    private void append(List<Short> list, short b) {
        int freq = calcFreq(b);
        for (int i = 0; i < Config.TIME_BAND; i++) {
            double angle = 2.0 * i * freq * Math.PI / Config.SAMPLE_RATE;
            list.add((short) (Math.sin(angle) * MAX_SIGNAL_STRENGTH));
        }
    }

    private void appendCommand(List<Short> list, short command) {
        for (int i = 0; i < COMMAND_REPEAT_NUM; i++) {
            append(list, command);
        }
    }

    private short calcFreq(short b) {
        return (short) (Config.BASE_FREQ + b * Config.FREQ_STEP);
    }

    private void generateAudioTrackIfNecessary() {
        if (mAudioTrack == null || mAudioTrack.getState() != AudioTrack.STATE_INITIALIZED) {
            mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, Config.SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, Config.AUDIO_FORMAT, AudioTrack.getMinBufferSize(Config.SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT) * 4, AudioTrack.MODE_STREAM);
        }
    }

}
