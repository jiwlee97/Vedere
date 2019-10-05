package com.project.vedere.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;

import com.project.vedere.R;
import com.project.vedere.managers.STTManager;
import com.project.vedere.managers.TTSManager;
import com.skt.Tmap.TMapPOIItem;

import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private STTManager sttManager;
    private TMapPOIItem destination;
    private Button sttBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 초기화
        TTSManager.getInstance().initTTSManager(this,this);
        sttManager = new STTManager(this);
        sttManager.initManager();

        sttBtn = findViewById(R.id.sttStart);

        sttBtn.setOnClickListener(v -> {
            sttManager.runmRecognizer();
        });
    }

    @Override
    public void onInit(int status) {
        if(status != ERROR) {
            TTSManager.getInstance().getTTS().setLanguage(Locale.KOREAN);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(destination == null) {
            TTSManager.getInstance().speak("안녕하세요. Vedere 입니다.");
        }
        else{
            String text = "현재 설정된 목적지는 " + destination.getPOIName() + "입니다. 안내를 계속 받으시겠습니까?";
            TTSManager.getInstance().speak(text);
            sttManager.runmRecognizer();
        }

    }
}
