package com.project.vedere.managers;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

import com.project.vedere.interfaces.PermissionCallback;

import java.util.ArrayList;

public class STTManager {

    private Activity activity;

    private Intent intent;
    private SpeechRecognizer mRecognizer;
    private final int PERMISSION = 1;
    private int status;
    private ArrayList<String> matches;
    private String answer="";


    public STTManager(Activity activity){
        this.activity = activity;
    }

    public void initManager(){
        boolean internetCheck = PermissionManager.getInstance().check(Manifest.permission.INTERNET);
        boolean recordAudioCheck = PermissionManager.getInstance().check(Manifest.permission.RECORD_AUDIO);

        if( internetCheck && recordAudioCheck ){
            setSTT();
        }
        else if(!internetCheck && !recordAudioCheck){
            String[] permissionArr = new String[]{Manifest.permission.INTERNET,Manifest.permission.RECORD_AUDIO};
            PermissionManager.getInstance().request(permissionArr, new PermissionCallback() {
                @Override
                public void granted() {
                    setSTT();
                }
                @Override
                public void denied() {
                    TTSManager.getInstance().speak("권한 확인 후 앱을 다시 실행해주세요");
                    activity.finishAffinity();
                }
            });
        }
        else {
            String[] permissionArr = !internetCheck ? new String[]{Manifest.permission.INTERNET} : new String[]{Manifest.permission.RECORD_AUDIO};
            PermissionManager.getInstance().request(permissionArr, new PermissionCallback() {
                @Override
                public void granted() {
                    setSTT();
                }
                @Override
                public void denied() {
                    TTSManager.getInstance().speak("권한 확인 후 앱을 다시 실행해주세요");
                    activity.finishAffinity();
                }
            });
        }
    }

    private void setSTT(){
        // 사용자에게 음성을 요구하고 음성 인식기를 통해 전송하는 활동 시작
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // 음성 인식을 위한 음성 인식기의 의도에 사용되는 여분의 키
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,activity.getPackageName());
        // 음성을 번역할 언어를 설정
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        if(SpeechRecognizer.isRecognitionAvailable(activity)) {
            SpeechRecognizer sr = SpeechRecognizer.createSpeechRecognizer(activity);
        } else {
            // SOME SORT OF ERROR
            // Todo: TTS 추가
            Toast.makeText(activity.getApplicationContext(),"구글 어플 사용을 설정해주세요.",Toast.LENGTH_SHORT).show();
        }
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(activity);
        mRecognizer.setRecognitionListener(listener);
    }


    public void runmRecognizer(){
        status = 0;
        if(Looper.myLooper() == Looper.getMainLooper())
            mRecognizer.startListening(intent);
        else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRecognizer.startListening(intent);
                }
            });
        }
    }

    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            // ToDo: TTS 추가
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
            status = 2;
            String message;
            switch (error){
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
            TTSManager.getInstance().speak(message);
        }

        @Override
        public void onResults(Bundle results) {
            answer = "";
            //말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줍니다.
            matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            answer = matches.get(0);
            status = 1;
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
        }
    };

    public int getStatus() {
        return status;
    }

    public String getAnswer() {
        return answer;
    }

}