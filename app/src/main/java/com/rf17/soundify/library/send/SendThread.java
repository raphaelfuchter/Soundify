package com.rf17.soundify.library.send;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.rf17.soundify.library.Constants.ConstantsAudio;
import com.rf17.soundify.library.Soundify;
import com.rf17.soundify.library.utils.AudioUtils;
import com.rf17.soundify.library.utils.DebugUtils;

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
                ConstantsAudio.SAMPLE_RATE.getValue(),
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                ConstantsAudio.BUFFER_SIZE.getValue(),
                AudioTrack.MODE_STREAM
        );
        track.play();
        List<Integer> frequencies = Encoder.encodeStringToHz(data);
        for (int freq : frequencies) {
            DebugUtils.log("freqsend: "+freq);
            short[] samples = AudioUtils.generateSamples(freq);
            track.write(samples, 0, 620);//FIXME
            try {
                Thread.sleep(1000L);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
