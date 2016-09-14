package com.rf17.soundify;

import android.app.Activity;

import com.rf17.soundify.exception.SoundifyException;
import com.rf17.soundify.receive.Receiver;
import com.rf17.soundify.send.Sender;
import com.rf17.soundify.utils.BytesUtils;

public class Soundify extends BytesUtils {

    private Activity activity;

    /**
     *
     */
    public static SoundifyListener soundifyListener;

    /**
     * This function is used to initialize the Soundify instance
     */
    public Soundify(Activity activity) {
        this.activity = activity;
    }

    /**
     * This function makes the library starts to listen.
     * @since 0.1
     * */
    public void startListening() throws SoundifyException {
        Receiver.getReceiver().inicializeReceiver();
    }

    /**
     * This function makes the library stop to listen.
     * @since 0.1
     * */
    public void stopListening() throws SoundifyException {
        Receiver.getReceiver().stopReceiver();
    }

    /**
     * This function transmit the message.
     *
     * @param data Send's data.
     * @since 0.1
     */
    public void send(byte[] data) {
        Sender.getSender().send(activity, data);
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
        void OnReceiveData(byte[] data);

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
