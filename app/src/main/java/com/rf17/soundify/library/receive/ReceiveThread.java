package com.rf17.soundify.library.receive;

import android.app.Activity;
import android.util.Log;

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

    public Thread thread;
    public String returnValue;
    private Activity activity;

    public ReceiveThread(Activity activity) {
        this.activity = activity;
        startThread();
    }

    private void startThread(){
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(Soundify.SAMPLE_RATE, Soundify.BUFFER_SIZE, Soundify.BUFFER_SIZE/2);
        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult result, AudioEvent e) {
                final Float pitchInHz = result.getPitch();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        decodeInitValue(pitchInHz);
                        decodeZeroValue(pitchInHz);
                        decodeOneValue(pitchInHz);
                        decodeFinishValue(pitchInHz);
                    }
                });
            }
        };
        AudioProcessor p = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, Soundify.SAMPLE_RATE, Soundify.BUFFER_SIZE, pdh);
        dispatcher.addAudioProcessor(p);
        thread = new Thread(dispatcher, "SoundifyListener");
        thread.start();
    }

    private void decodeInitValue(Float frequencyInHz) {
        if (frequencyInHz.intValue() > Soundify.INIT_HZ-100 && frequencyInHz.intValue() < Soundify.INIT_HZ+100) {
            returnValue = "";
        }
    }

    private void decodeZeroValue(Float frequencyInHz) {
        if (frequencyInHz.intValue() > Soundify.ZERO_HZ-100 && frequencyInHz.intValue() < Soundify.ZERO_HZ+100) {
            returnValue = returnValue.concat("0");
        }
    }

    private void decodeOneValue(Float frequencyInHz) {
        if (frequencyInHz.intValue() > Soundify.ONE_HZ-100 && frequencyInHz.intValue() < Soundify.ONE_HZ+100) {
            returnValue = returnValue.concat("1");
        }
    }

    private void decodeFinishValue(Float frequencyInHz) {
        if ((frequencyInHz.intValue() > Soundify.FINISH_HZ-50) && (frequencyInHz.intValue() < Soundify.FINISH_HZ+50)) {
            thread.interrupt();
            if(thread.isInterrupted()){
                Log.v("##TESTE##", "returnValue: "+returnValue);//TODO REMOVE THIS!

                if(returnValue == null){
                    Soundify.soundifyListener.OnReceiveError(1, "Null value in return value!");
                }else if(returnValue.isEmpty()){
                    Soundify.soundifyListener.OnReceiveError(2, "Empty value in return value!");
                }else {
                    byte[] bval = new BigInteger(returnValue, 2).toByteArray();
                    Soundify.soundifyListener.OnReceiveData(bval);
                }
            }
        }
    }

}
