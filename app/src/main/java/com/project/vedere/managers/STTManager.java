package com.project.vedere.managers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class STTManager {
    private Activity activity;
    // STT
    Intent intent;
    SpeechRecognizer mRecognizer;
    Button sttBtn;
    TextView textView;
    final int PERMISSION = 1;

    public RecognitionListener getListener(){
        return listener;
    }
    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(activity.getApplicationContext(),"음성인식을 시작합니다.",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int error) {
            String message;
            switch (error){
                case SpeechRecognizer.ERROR_AUDIO: {
                    message = "오디오 에러입니다.";
                    break;
                }
                case SpeechRecognizer.ERROR_CLIENT: {
                    message = "클라이언트 에러입니다.";
                    break;
                }
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:{
                    message = "퍼미션이 없습니다.";
                    break;
                }
                case SpeechRecognizer.ERROR_NETWORK:{
                    message = "네트워크 에러입니다.";
                    break;
                }
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:{
                    message = "네트워크 타임아웃 에러입니다.";
                    break;
                }
                case SpeechRecognizer.ERROR_NO_MATCH:{
                    message = "찾을 수 없습니다.";
                    break;
                }
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:{
                    message = "RECOGNIZER가 바쁩니다.";
                    break;
                }
                case SpeechRecognizer.ERROR_SERVER:{
                    message = "서버가 이상합니다.";
                    break;
                }
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:{
                    message = "말하는 시간이 초과되었습니다.";
                    break;
                }
                default: {
                    message = "알 수 없는 오류가 발생했습니다.";
                    break;
                }
            }
            Toast.makeText(activity.getApplicationContext(),"에러가 발생하였습니다.: "+message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            //말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줍니다.
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for(int i=0;i<matches.size();++i)
                textView.setText(matches.get(i));
        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    };
}
