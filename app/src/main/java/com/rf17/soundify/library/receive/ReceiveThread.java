package com.rf17.soundify.library.receive;

import android.app.Activity;

import com.rf17.soundify.library.Soundify;
import com.rf17.soundify.library.utils.DebugUtils;

import java.math.BigInteger;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class ReceiveThread {

    private Thread thread;
    private String returnValue;
    private Activity activity;

    public ReceiveThread(Activity activity) {
        this.activity = activity;
        initThread();
    }

    private void initThread() {
        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult result, AudioEvent e) {
                final Float pitchInHz = result.getPitch();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        translate(pitchInHz);
                    }
                });
            }
        };
        AudioProcessor audioProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, Soundify.SAMPLE_RATE, Soundify.BUFFER_SIZE, pdh);

        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(Soundify.SAMPLE_RATE, Soundify.BUFFER_SIZE, Soundify.BUFFER_SIZE / 2);
        dispatcher.addAudioProcessor(audioProcessor);
        thread = new Thread(dispatcher, "SoundifyListener");
    }

    public void startThread() {
        if(!thread.isAlive()){
            thread.start();
        }
    }

    public void stopThread() {
        if(thread.isAlive()) {
            thread.interrupt();
        }
    }

    private void translate(Float pitchInHz){
        decodeInitValue(pitchInHz);
        decodeZeroValue(pitchInHz);
        decodeOneValue(pitchInHz);
        decodeFinishValue(pitchInHz);
    }

    private boolean isCorrectedFrequency(int frequency, int hz){
        return frequency > hz - Soundify.HZ_TX_ERROR &&
                frequency < hz + Soundify.HZ_TX_ERROR;
    }

    private void decodeInitValue(Float frequencyInHz) {
        if(isCorrectedFrequency(frequencyInHz.intValue(), Soundify.HZ_BEGIN)){
            returnValue = "";
        }
    }

    private void decodeZeroValue(Float frequencyInHz) {
        if(isCorrectedFrequency(frequencyInHz.intValue(), Soundify.HZ_ZERO)){
            returnValue = returnValue.concat("0");
        }
    }

    private void decodeOneValue(Float frequencyInHz) {
        if(isCorrectedFrequency(frequencyInHz.intValue(), Soundify.HZ_ONE)){
            returnValue = returnValue.concat("1");
        }
    }

    private void decodeFinishValue(Float frequencyInHz) {
        if(isCorrectedFrequency(frequencyInHz.intValue(), Soundify.HZ_END)){
            thread.interrupt();
            if (thread.isInterrupted()) {
                DebugUtils.log("value received: '" + returnValue + "' ");
                if (returnValue == null) {
                    Soundify.soundifyListener.OnReceiveError(1, "Null value in return value!");
                } else if (returnValue.isEmpty()) {
                    Soundify.soundifyListener.OnReceiveError(2, "Empty value in return value!");
                } else {
                    byte[] bval = new BigInteger(returnValue, 2).toByteArray();
                    Soundify.soundifyListener.OnReceiveData(bval);
                }
            }
        }
    }

}
