package com.rf17.soundify.library;

import android.app.Activity;

import com.rf17.soundify.library.receive.ReceiverThread;
import com.rf17.soundify.library.utils.BytesUtils;
import com.rf17.soundify.library.receive.Receiver;
import com.rf17.soundify.library.send.Sender;

public class Soundify extends BytesUtils {

    /**
     *
     */
    public static SoundifyListener soundifyListener;

    /**
     *
     */
    private ReceiverThread receiveThread;

    /**
     * This function is used to initialize the Soundify instance,
     * @param activity Current Activity of your app.
     */
    public Soundify(Activity activity) {
        receiveThread = new ReceiverThread(activity);
        Sender.getSender();
    }

    /**
     * This function makes the library starts to listen.
     * @since 0.1
     * */
    public void startListening(){
        receiveThread.inicializeReceiver();
    }

    /**
     * This function makes the library stop to listen.
     * @since 0.1
     * */
    public void stopListening(){
        receiveThread.stopReceiver();
    }

    /**
     * This function transmit the message.
     *
     * @param data Send's data.
     * @since 0.1
     */
    public void send(byte[] data) {
        Sender.getSender().send(data);
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
