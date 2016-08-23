package com.rf17.soundify.library.send;

import com.rf17.soundify.library.Constants.ConstantsHz;
import com.rf17.soundify.library.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;

public class Encoder {

    /**
     *
     * @param data stringData
     * @return desc
     */
    public static List<Integer> encodeStringToHz(String data) {
        DebugUtils.log("value to send: '" + data + "' ");
        List<Integer> frequencies = new ArrayList<>();
        frequencies.add(2500);
        for (int i = 0; i < data.length(); i++) {
            frequencies.add(3000);
        }
        frequencies.add(4500);
        return frequencies;
    }

    /**
     *
     * @param carac carac
     * @return desc
     */
    private static int encoderCharInHz(char carac) {
        for (ConstantsHz hz : ConstantsHz.values()) {
            if (hz.getDesc() == carac) {
                return hz.getHz();
            }
        }
        return 0;// FIXME
    }

}
