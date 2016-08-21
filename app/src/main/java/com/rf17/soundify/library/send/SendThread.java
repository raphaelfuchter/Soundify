package com.rf17.soundify.library.send;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.rf17.soundify.library.Constants.ConstantsHz;
import com.rf17.soundify.library.Soundify;
import com.rf17.soundify.library.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;

public class SendThread extends Thread {

    private String data;

    public SendThread(String data) {
        this.data = data;
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
        List<Integer> frequencies = Encoder.encodeData(data);
        for (int freq : frequencies) {
            short[] samples = generateSamples(freq);
            track.write(samples, 0, 620);
        }
    }

    private static short[] generateSamples(float frequency) {
        final short sample[] = new short[Soundify.BUFFER_SIZE];
        final double increment = 50 * Math.PI * frequency / Soundify.SAMPLE_RATE;
        double angle = 0;
        for (int i = 0; i < sample.length; ++i) {
            sample[i] = (short) (Math.sin(angle) * Short.MAX_VALUE);
            angle += increment;
        }
        return sample;
    }



}
