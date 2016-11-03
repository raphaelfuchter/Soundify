package com.rf17.soundify.app.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Classe que contém metódos que são iguais em todas as telas, para evitar duplicidade de código, utilizar os métodos daqui.
 *
 * @author Raphael
 */
public class AndroidUtils {

    /**
     * Esconde o teclado do dispositivo
     *
     * @param activity - Activity/tela que será utilizado para esconder o teclado
     * @param view     - ...
     */
    public static void hideKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    /**
     * Mostra Toast/Mensagem de erro na tela
     *
     * @param activity - Activity/tela que será utilizado o Toast
     * @param e        - Exception com o erro/mensagem/aviso
     */
    public static void showToast(Activity activity, Exception e) {
        e.printStackTrace();
        Toast.makeText(activity, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }

}
