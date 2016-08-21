package com.rf17.soundify.library.receive;

import com.rf17.soundify.library.Constants.ConstantsHz;

public class Decoder {

    public static String decoderHzInChar(Float pitchHz) {
        for (ConstantsHz hz : ConstantsHz.values()) {
            if (hz.getHz() == pitchHz) {
                return String.valueOf(hz.getDesc());
            }
        }
        return "";// FIXME
    }

}
