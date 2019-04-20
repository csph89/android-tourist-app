package com.unipi.gkriketos.unipitouristapp;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class MyTextToSpeech {

    private TextToSpeech tts;

    public MyTextToSpeech(Context context) {
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS)
                    tts.setLanguage(Locale.ENGLISH);
            }
        });
    }

    public void mySpeak(String text) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, null, null);
    }
}
