package com.rf17.soundify.library;

import android.app.Activity;

import com.rf17.soundify.library.utils.BytesUtils;
import com.rf17.soundify.library.receive.ReceiveThread;
import com.rf17.soundify.library.send.SendThread;

public class Soundify extends BytesUtils {

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
     * @param message Send's data.
     * @since 0.1
     */
    public void send(String message) {
        new SendThread(message).start();
    }

    /**
     * This implementation is required to treat the actions of the Soundify
     * @since 0.1
     */
    public interface SoundifyListener {

        /**
         * This is called when the Soundify receive a data.
         *
         * @param data data received.
         * @since 0.1
         */
        void OnReceiveData(String data);

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
