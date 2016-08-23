package com.rf17.soundify.library.receive;

import android.app.Activity;

import com.rf17.soundify.library.Constants.ConstantsHz;
import com.rf17.soundify.library.Soundify;
import com.rf17.soundify.library.utils.AudioUtils;
import com.rf17.soundify.library.utils.DebugUtils;

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
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050,1024,0);

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult result,AudioEvent e) {
                final float pitchInHz = result.getPitch();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        decodeValues(pitchInHz);
                    }
                });
            }
        };
        AudioProcessor p = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(p);
        new Thread(dispatcher,"Audio Dispatcher").start();
    }

    /*public void startThread() {
        if(!thread.isAlive()){
            thread.start();
        }
    }

    public void stopThread() {
        if(thread.isAlive()) {
            thread.interrupt();
        }
    }*/

    private void decodeValues(Float pitchInHz){
        decodeInitValue(pitchInHz);
        decodeNormalValue(pitchInHz);
        decodeFinishValue(pitchInHz);
    }

    private void decodeInitValue(Float frequencyInHz) {
        if(AudioUtils.isCorrectedFrequency(frequencyInHz.intValue(), ConstantsHz.BEGIN.getHz())){
            DebugUtils.log("init");
            returnValue = "";
        }
    }

    private void decodeNormalValue(Float frequencyInHz) {
        //if(isCorrectedFrequency(frequencyInHz.intValue(), Soundify.HZ_ONE)){
        if(frequencyInHz > 800.0) {
            DebugUtils.log("freqrec: " + frequencyInHz);
            returnValue = Decoder.decodeHzInChar(frequencyInHz);
        }
        //}
    }

    private void decodeFinishValue(Float frequencyInHz) {
        if(AudioUtils.isCorrectedFrequency(frequencyInHz.intValue(), ConstantsHz.END.getHz())){
            DebugUtils.log("finish");
            thread.interrupt();
            if (thread.isInterrupted()) {
                DebugUtils.log("value received: '" + returnValue + "' ");
                if (returnValue == null) {
                    Soundify.soundifyListener.OnReceiveError(1, "Null value in return value!");
                } else if (returnValue.isEmpty()) {
                    Soundify.soundifyListener.OnReceiveError(2, "Empty value in return value!");
                } else {
                    Soundify.soundifyListener.OnReceiveData(returnValue);
                }
            }
        }
    }

}
