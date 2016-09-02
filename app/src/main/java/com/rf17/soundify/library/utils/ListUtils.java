package com.rf17.soundify.library.utils;

import java.util.List;

public class ListUtils {

    /**
     * Convert List to Array
     *
     * @param list List to convert
     * @return Array
     */
    public static short[] convertListToArray(List<Short> list){
        int size = list.size();
        short[] data = new short[size];
        for (int i = 0; i < size; i++) {
            data[i] = list.get(i);
        }

        return data;
    }

}
