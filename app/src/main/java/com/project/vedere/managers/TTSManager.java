package com.project.vedere.managers;

import android.app.Activity;
import android.speech.tts.TextToSpeech;

import org.w3c.dom.Text;

public class TTSManager {
    private TextToSpeech tts;

    private TTSManager() {

    }

    public TextToSpeech getTTS() {
        return tts;

    }
    private static class TTSManagerHolder {
        private static TTSManager INSTANCE = new TTSManager();
    }

    public static TTSManager getInstance() {
        return TTSManagerHolder.INSTANCE;
    }

    public void initTTSManager(Activity activity, TextToSpeech.OnInitListener callback) {
        tts = new TextToSpeech(activity, callback);
    }

    public void speack(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
