package com.rf17.soundify.utils;

import java.util.List;

public class ListUtils {

    /**
     * Convert List to Array
     *
     * @param list List to convert
     * @return Array
     */
    public static short[] convertListShortToArrayShort(List<Short> list){
        int size = list.size();
        short[] data = new short[size];
        for (int i = 0; i < size; i++) {
            data[i] = list.get(i);
        }

        return data;
    }

    /**
     * Convert List to Array
     *
     * @param list List of bytes to convert
     * @return Array of bytes
     */
    public static byte[] convertListBytesToArrayBytes(List<Byte> list){
        int size = list.size();
        byte[] data = new byte[size];
        for (int i = 0; i < size; i++) {
            data[i] = list.get(i);
        }

        return data;
    }

    /**
     * Convert Array of short to Array of float
     *
     * @param recordedData Array of short
     * @return Array of float
     */
    public static float[] convertArrayShortToArrayFloat(short[] recordedData){
        int size = recordedData.length;
        float[] floatData = new float[size];
        for (int i = 0; i < size; i++) {
            floatData[i] = recordedData[i];
        }

        return floatData;
    }

}
