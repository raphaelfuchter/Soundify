package com.rf17.soundify.library.send;

import com.rf17.soundify.library.Constants.ConstantsHz;
import com.rf17.soundify.library.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;

public class Encoder {

    public static List<Integer> encodeData(String stringData) {
        DebugUtils.log("value to send: '" + stringData + "' ");
        List<Integer> frequencies = new ArrayList<>();
        frequencies.add(ConstantsHz.BEGIN.getHz());
        for (int i = 0; i < stringData.length(); i++) {
            frequencies.add(encoderCharInHz(stringData.charAt(i)));
        }
        frequencies.add(ConstantsHz.END.getHz());
        return frequencies;
    }

    private static int encoderCharInHz(char carac) {
        for (ConstantsHz hz : ConstantsHz.values()) {
            if (hz.getDesc() == carac) {
                return hz.getHz();
            }
        }
        return 0;// FIXME
    }

}
