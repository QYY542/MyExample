package com.myexample.utils;/*
 **Created by 24606 at 15:02 2022.
 */

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class speech_E {
    private Context context;


    private static final String TAG = "SpeechUtils";
    private static speech_E singleton;

    private TextToSpeech textToSpeech; // TTS对象

    public static speech_E getInstance(Context context) {
        if (singleton == null) {
            synchronized (speech_E.class) {
                if (singleton == null) {
                    singleton = new speech_E(context);
                }
            }
        }
        return singleton;
    }

    public speech_E(Context context) {
        this.context = context;
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.setPitch(1.0f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                    textToSpeech.setSpeechRate(1.0f);
                }
            }
        });
    }

    public void speakText(String text) {
//        textToSpeech.stop();
        if (singleton != null) {
            singleton.textToSpeech.speak(text,
                    TextToSpeech.QUEUE_FLUSH, null);
        }

    }

    public void stop() {
        if (singleton != null) {
            singleton.textToSpeech.stop();
        }
    }


    public boolean isSpeaking() {
        if (singleton != null) {
            return singleton.textToSpeech.isSpeaking();
        }
        return false;
    }


}
