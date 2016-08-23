package com.rf17.soundify.library.receive;

import android.app.Activity;

import com.rf17.soundify.library.Constants.ConstantesAudio;
import com.rf17.soundify.library.Constants.ConstantsHz;
import com.rf17.soundify.library.Soundify;
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
        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult result, AudioEvent e) {
                final Float pitchInHz = result.getPitch();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        decodeInitValue(pitchInHz);
                        decodeValue(pitchInHz);
                        decodeFinishValue(pitchInHz);
                    }
                });
            }
        };
        AudioProcessor audioProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, ConstantesAudio.SAMPLE_RATE.getValue(), ConstantesAudio.BUFFER_SIZE.getValue(), pdh);

        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(ConstantesAudio.SAMPLE_RATE.getValue(), ConstantesAudio.BUFFER_SIZE.getValue(), ConstantesAudio.BUFFER_SIZE.getValue() / 2);
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

    private boolean isCorrectedFrequency(int frequency, int hz){
        return frequency > hz - ConstantsHz.TX_ERROR.getHz() &&
                frequency < hz + ConstantsHz.TX_ERROR.getHz();
    }

    public void decodeInitValue(Float frequencyInHz) {
        if(isCorrectedFrequency(frequencyInHz.intValue(), ConstantsHz.BEGIN.getHz())){
            DebugUtils.log("init");
            returnValue = "";
        }
    }

    public void decodeValue(Float frequencyInHz) {
        //if(isCorrectedFrequency(frequencyInHz.intValue(), Soundify.HZ_ONE)){
            returnValue = Decoder.decoderHzInChar(frequencyInHz);
        //}
    }

    public void decodeFinishValue(Float frequencyInHz) {
        if(isCorrectedFrequency(frequencyInHz.intValue(), ConstantsHz.END.getHz())){
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
