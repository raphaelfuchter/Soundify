package com.rf17.soundify.library.send;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.rf17.soundify.library.Soundify;
import com.rf17.soundify.library.utils.BytesUtils;

import java.util.ArrayList;
import java.util.List;

public class SendThread extends Thread {

    private String bytes;

    public void startThread(byte[] bytes) {
        this.bytes = BytesUtils.bytesArrayToString(bytes);
        this.start();
    }

    @Override
    public void run() {
        final AudioTrack track = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                Soundify.SAMPLE_RATE,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                Soundify.BUFFER_SIZE,
                AudioTrack.MODE_STREAM
        );
        track.play();
        List<Integer> frequencies = encodeBytes(bytes);
        for (int freq : frequencies) {
            short[] samples = generateSamples(freq);
            track.write(samples, 0, 620);//FIXME TODO
        }
    }

    static short[] generateSamples(float frequency) {
        final short sample[] = new short[Soundify.BUFFER_SIZE];
        final double increment = 2 * Math.PI * frequency / Soundify.SAMPLE_RATE;
        double angle = 0;
        for (int i = 0; i < sample.length; ++i) {
            sample[i] = (short) (Math.sin(angle) * Short.MAX_VALUE);
            angle += increment;
        }
        return sample;
    }

    static List<Integer> encodeBytes(String bytes) {
        List<Integer> frequencies = new ArrayList<>();
        frequencies.add(Soundify.INIT_HZ);
        for(int i = 0; i < bytes.length(); i++){
            if(bytes.substring(i, i+1).equals("0")){
                frequencies.add(Soundify.ZERO_HZ);
            }else{
                frequencies.add(Soundify.ONE_HZ);
            }
        }
        frequencies.add(Soundify.FINISH_HZ);
        return frequencies;
    }

}
