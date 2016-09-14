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
        return bytesToLong(bytes, 0);
    }

    public static long bytesToLong(byte[] bytes, int index) {
        return ByteBuffer.wrap(bytes).getLong(index);
    }

    public static double bytesToDouble(byte[] bytes) {
        return bytesToDouble(bytes, 0);
    }

    public static double bytesToDouble(byte[] bytes, int index) {
        return ByteBuffer.wrap(bytes).getDouble(index);
    }

    public static int bytesToInt(byte[] bytes) {
        return bytesToInt(bytes, 0);
    }

    public static int bytesToInt(byte[] bytes, int index) {
        return ByteBuffer.wrap(bytes).getInt(index);
    }

    public static float bytesToFloat(byte[] bytes) {
        return bytesToFloat(bytes, 0);
    }

    public static float bytesToFloat(byte[] bytes, int index) {
        return ByteBuffer.wrap(bytes).getFloat(index);
    }

    public static short bytesToShort(byte[] bytes) {
        return bytesToShort(bytes, 0);
    }

    public static short bytesToShort(byte[] bytes, int index) {
        return ByteBuffer.wrap(bytes).getShort(index);
    }

    public static char bytesToChar(byte[] bytes) {
        return bytesToChar(bytes, 0);
    }

    public static char bytesToChar(byte[] bytes, int index) {
        return ByteBuffer.wrap(bytes).getChar(index);
    }

    public static String bytesArrayToString(byte[] bytes){
        String bytesString = "";
        for (byte b : bytes) {
            bytesString += Integer.toBinaryString(b & 255 | 256).substring(1);
        }
        return bytesString;
    }

}
