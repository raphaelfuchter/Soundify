package com.rf17.soundify.receive;

import com.rf17.soundify.Config;
import com.rf17.soundify.receive.fft.FFT;

class ReceiverUtils {

    static short calcData(short freq){
        return (short) ((freq - Config.BASE_FREQ + Config.FREQ_STEP / 2) / Config.FREQ_STEP);
    }

    static short calcFreq(float[] floatData) {
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

    private static int calcFftSize(int size) {
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
