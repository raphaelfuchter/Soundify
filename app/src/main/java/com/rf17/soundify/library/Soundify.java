package com.rf17.soundify.library;

import android.app.Activity;

import com.rf17.soundify.library.utils.BytesUtils;
import com.rf17.soundify.library.receive.ReceiveThread;
import com.rf17.soundify.library.send.SendThread;
import com.rf17.soundify.library.utils.DebugUtils;

public class Soundify extends BytesUtils {

    /**
     * The frequency, in HZ, of initialize value of transmission and receive a data
     */
    public final static int INIT_HZ = 2500;

    /**
     * The frequency, in HZ, of zero(0) value of transmission and receive a data
     */
    public final static int ZERO_HZ = 3000;

    /**
     * The frequency, in HZ, of one(0) value of transmission and receive a data
     */
    public final static int ONE_HZ = 4000;

    /**
     * The frequency, in HZ, of finish value of transmission and receive a data
     */
    public final static int FINISH_HZ = 4500;

    /**
     * //TODO Colocar comentario
     */
    public static final int SAMPLE_RATE = 22050;

    /**
     * The size of the buffer defines how muche samples are processed in one step. Common values are 1024,2048.
     */
    public static final int BUFFER_SIZE = 1024;

    private Activity activity;

    /**
     * This function is used to initialize the Soundify instance,
     * if you want to define the method of transmission use the other function.
     *
     * @param activity Current Activity of your app.
     */
    public Soundify(Activity activity) {
        this.activity = activity;
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
     *
     * @author Raphael
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
        //void OnReceiveError(int code, String msg); TODO
    }

    /**
     * This function is used to set your listener of Soundify' events.
     *
     * @param listener SoundifyListener instance.
     * @since 0.1
     */
    public void setSoundifyListener(SoundifyListener listener) {
        soundifyListener = listener;
        new ReceiveThread(activity);
    }

    public static SoundifyListener soundifyListener;//TODO FIND A BETTER WAY

}
