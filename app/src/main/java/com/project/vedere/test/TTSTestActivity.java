package com.project.vedere.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import com.project.vedere.R;
import com.project.vedere.managers.TTSManager;

import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class TTSTestActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttstest);
        TTSManager.getInstance().initTTSManager(this, this);
    }

    @Override
    public void onInit(int status) {
        if (status != ERROR)
            TTSManager.getInstance().getTTS().setLanguage(Locale.KOREAN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TTSManager.getInstance().speack("안녕하세요");
    }
}
