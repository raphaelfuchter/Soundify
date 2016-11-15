package com.rf17.soundify.send;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.rf17.soundify.Config;
import com.rf17.soundify.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class Sender {

    /**
     * Variavel para armazenar a instancia do SoundifySender.
     */
    private static Sender sSender;

    /**
     * Variavel para armazenar a faixa de audio que vai tocar.
     */
    private AudioTrack mAudioTrack;

    /**
     * Metodo que utilizado para iniciar a instancia do SoundifySender.
     * @return Sender SoundifySender
     */
    public static Sender getSender() {
        if (sSender == null) {
            sSender = new Sender();
        }
        return sSender;
    }

    /**
     * Metodo que vai receber uma lista de bytes e <br/>
     * vai transformar os dados em sons e vai envia-los.
     *
     * @param activity Activity atual do app.
     * @param data Lista de bytes que vai ser enviada
     */
    public void send(Activity activity, byte[] data) {
        List<Short> list = new ArrayList<>();// Lista em forma de short (frequencias/sons)
        list = appendCommand(list, Config.START_COMMAND);// Adiciona o comando de inicio da transmissao
        for (byte b : data) {// Percorre todos os bytes que vão ser transmitidos
            list = appendByte(list, b);// Adiciona o valor do byte transformado em frequencia
        }
        list = appendCommand(list, Config.STOP_COMMAND);// Adiciona o comando de fim da transmissao

        sendData(activity, list);// Envia/toca a lista/dados
    }

    /**
     * Metodo que vai juntar os comandos de inicio e fim da <br/>
     * transmissao dos dados em forma de som.
     *
     * @param list Lista das frequencias já transformadas
     * @param command Qual o tipo de comando (frequencia dele)
     * @return Lista em forma de short (frequencias/sons)
     */
    private List<Short> appendCommand(List<Short> list, short command) {
        return appendByte(list, command);
    }

    /**
     * Metodo que vai juntar os bytes já transformados em <br/>
     * frequencias na lista final de transmissao.
     *
     * @param list Lista das frequencias já transformadas
     * @param byt Valor byte
     * @return Lista das frequencias já transformadas
     */
    private List<Short> appendByte(List<Short> list, short byt) {
        int freq = calcFreq(byt);// Calcula a frequencia baseada no byte
        for (int i = 0; i < Config.TIME_BAND; i++) {// Percorre o tamanho de cada banda de frequencia
            double angle = 2.0 * i * freq * Math.PI / Config.SAMPLE_RATE;// Realiza o calculo do angulo da frequencia
            list.add((short) (Math.sin(angle) * Config.MAX_SIGNAL_STRENGTH));
        }
        return list;
    }

    /**
     * Calcula a frequencia baseada no byte
     *
     * @param byt Valor byte
     * @return Short com o valor da frequencia
     */
    private short calcFreq(short byt) {
        return (short) (Config.BASE_FREQ + byt * Config.FREQ_STEP);
    }

    /**
     * Envia/toca os dados transformados.
     *
     * @param activity Activity atual do app.
     * @param list Lista dos das frequencias que vao ser transmitidas
     */
    private void sendData(Activity activity, final List<Short> list) {
        generateAudioTrack();// Verifica se o audioTrack esta inicializado

        // Thread que vai tocar/executar as frequencias (para nao travar o app)
        // TODO NÃO UTILIZAR LAMBDA! NOT USE LAMBDA WHERE! ANDROID BUG
        // TODO Error:Execution failed for task ':library:compileReleaseJavaWithJavac'. > Compilation failed; see the compiler error output for details.
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAudioTrack.play();// Começa a tocar
                mAudioTrack.write(ListUtils.convertListShortToArrayShort(list), 0, list.size());// Vai sobrescrevendo para tocar a lista de frequencias
            }
        });
    }

    /**
     * Inicializa a variavel da faixa de audio que vai tocar.
     */
    private void generateAudioTrack() {
        if (mAudioTrack == null || mAudioTrack.getState() != AudioTrack.STATE_INITIALIZED) {
            mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, Config.SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, Config.AUDIO_FORMAT, AudioTrack.getMinBufferSize(Config.SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT) * 4, AudioTrack.MODE_STREAM);
        }
    }

}
