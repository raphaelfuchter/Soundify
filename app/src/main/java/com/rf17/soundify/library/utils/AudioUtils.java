package com.rf17.soundify.library.utils;

import com.rf17.soundify.library.Constants.ConstantsAudio;
import com.rf17.soundify.library.Constants.ConstantsHz;

public class AudioUtils {

    public static short[] generateSamples(float frequency) {
        final short sample[] = new short[ConstantsAudio.BUFFER_SIZE.getValue()];
        final double increment = 50 * Math.PI * frequency / ConstantsAudio.SAMPLE_RATE.getValue();
        double angle = 0;
        for (int i = 0; i < sample.length; ++i) {
            sample[i] = (short) (Math.sin(angle) * Short.MAX_VALUE);
            angle += increment;
        }
        return sample;
    }

    public static boolean isCorrectedFrequency(int frequency, int hz){
        return frequency > hz - ConstantsHz.TX_ERROR.getHz() &&
                frequency < hz + ConstantsHz.TX_ERROR.getHz();
    }


}
