package com.rf17.soundify.library;

import android.app.Activity;

import com.rf17.soundify.library.utils.BytesUtils;
import com.rf17.soundify.library.receive.ReceiveThread;
import com.rf17.soundify.library.send.SendThread;

public class Soundify extends BytesUtils {

    /**
     * The frequency, in HZ, used as begin transmission.
     */
    public final static int HZ_BEGIN = 1976;

    /**
     * The frequency, in HZ, used as send a binary 0.
     */
    public final static int HZ_ZERO = 1174;

    /**
     * The frequency, in HZ, used as send a binary 1.
     */
    public final static int HZ_ONE = 784;

    /**
     * The frequency, in HZ, used as a separator between repeated digits
     */
    public final static int HZ_SEPARATOR = 988;

    /**
     * The frequency, in HZ, used as end transmission.
     */
    public final static int HZ_END = 1568;

    /**
     *
     */
    public final static int HZ_TX_ERROR = 50;

    /**
     * //TODO Colocar comentario
     */
    public static final int SAMPLE_RATE = 22050;

    /**
     * The size of the buffer defines how much samples are processed in one step. Common values are 1024,2048.
     */
    public static final int BUFFER_SIZE = 1024;

    /**
     *
     */
    public static SoundifyListener soundifyListener;

    /**
     *
     */
    private ReceiveThread receiveThread;

    /**
     * This function is used to initialize the Soundify instance,
     * @param activity Current Activity of your app.
     */
    public Soundify(Activity activity) {
        receiveThread = new ReceiveThread(activity);
    }

    /**
     * This function makes the library starts to listen.
     * @since 0.1
     * */
    public void startListening(){
        receiveThread.startThread();
    }

    /**
     * This function makes the library stop to listen.
     * @since 0.1
     * */
    public void stopListening(){
        receiveThread.stopThread();
    }

    /**
     * This function transmit the message.
     *
     * @param bytes Send's data.
     * @since 0.1
     */
    public void send(byte bytes[]) {
        new SendThread(bytes).start();
    }

    /**
     * This implementation is required to treat the actions of the Soundify
     * @since 0.1
     */
    public interface SoundifyListener {

        /**
         * This is called when the Soundify receive a data.
         *
         * @param bytes data received.
         * @since 0.1
         */
        void OnReceiveData(byte[] bytes);

        /**
         * This is called when the Soundify receive an error.
         *
         * @param code Error code.
         * @param msg  Error message.
         * @since 0.1
         */
        void OnReceiveError(int code, String msg);
    }

    /**
     * This function is used to set your listener of Soundify events.
     *
     * @param listener SoundifyListener instance.
     * @since 0.1
     */
    public void setSoundifyListener(SoundifyListener listener) {
        soundifyListener = listener;
    }

}
