package com.project.vedere.managers;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class TTSManager {
    private TextToSpeech tts;
    private int status = 0;
    private HashMap mTTSMap = new HashMap();

    private TTSManager() {
        mTTSMap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,"unique_id");
    }

    public TextToSpeech getTTS() {
        return tts;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setListner(UtteranceProgressListener utteranceProgressListener){
        tts.setOnUtteranceProgressListener(utteranceProgressListener);
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

    public void speak(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, mTTSMap);
    }

    public void destroy(){
        tts.shutdown();
    }
}
