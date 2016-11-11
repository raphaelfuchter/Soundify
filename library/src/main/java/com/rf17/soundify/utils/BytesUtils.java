package com.rf17.soundify.utils;

import java.nio.ByteBuffer;

public class BytesUtils {

    public static byte[] stringToBytes(String value) {
        return value.getBytes();
    }

    public static byte[] longToBytes(long value) {
        return ByteBuffer.allocate(8).putLong(value).array();
    }

    public static byte[] doubleToBytes(double value) {
        return ByteBuffer.allocate(8).putDouble(value).array();
    }

    public static byte[] intToBytes(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    public static byte[] floatToBytes(float value) {
        return ByteBuffer.allocate(4).putFloat(value).array();
    }

    public static byte[] shortToBytes(short value) {
        return ByteBuffer.allocate(2).putShort(value).array();
    }

    public static byte[] charToBytes(char value) {
        return ByteBuffer.allocate(2).putChar(value).array();
    }

    public static String bytesToString(byte[] bytes) {
        return new String(bytes);
    }

    public static long bytesToLong(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getLong(0);
    }

    public static double bytesToDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble(0);
    }

    public static int bytesToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt(0);
    }

    public static float bytesToFloat(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getFloat(0);
    }

    public static short bytesToShort(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getShort(0);
    }

    public static char bytesToChar(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getChar(0);
    }

}
