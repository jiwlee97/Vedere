package com.project.vedere.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Button;

import com.project.vedere.R;
import com.project.vedere.interfaces.TMapCallback;
import com.project.vedere.managers.AngleManager;
import com.project.vedere.managers.PermissionManager;
import com.project.vedere.managers.STTManager;
import com.project.vedere.managers.TMapManager;
import com.project.vedere.managers.TTSManager;
import com.project.vedere.managers.TurnInfoManager;
import com.project.vedere.model.DirectionInfo;
import com.project.vedere.model.DirectionModel;
import com.project.vedere.model.POIItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.PriorityQueue;


import static android.speech.tts.TextToSpeech.ERROR;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, TMapCallback, SensorEventListener {

    private STTManager sttManager;
    private POIItem destination = null;
    private Button stopVibrator;
    private UtteranceProgressListener utteranceProgressListener;
    private PriorityQueue<POIItem> arrPOIItem;
    private DirectionModel directionModel;
    private String finalAnswer;
    private AngleManager angleManager;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagneticField;
    private int mode;
    private TurnInfoManager turnInfoManager;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 초기화
        TTSManager.getInstance().initTTSManager(this,this);
        sttManager = new STTManager(this);
        turnInfoManager = new TurnInfoManager();

        PermissionManager.getInstance().setActivity(this);
        sttManager.initManager();
        angleManager = new AngleManager(this );
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        TMapManager.getInstance().initManager(this,this,this);
        TMapManager.getInstance().setGPS();
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("checklocation","akajajajaj");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        stopVibrator = findViewById(R.id.stopVibrator);

        stopVibrator.setOnClickListener(v -> {
            mSensorManager.unregisterListener(this,mAccelerometer);
            mSensorManager.unregisterListener(this,mMagneticField);
            mode = 1;
        });

        utteranceProgressListener = new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                Log.d("start","start");
            }

            @Override
            public void onDone(String utteranceId) {
                String answer="dfdf";
                if(TTSManager.getInstance().getStatus() != 5)
                    answer = returnAnswer();
                Log.d("목적지", answer);
                Log.d("status",Integer.toString(TTSManager.getInstance().getStatus()));
                if(TTSManager.getInstance().getStatus() == 1) {
                    if( checkYes(answer) ){
                        Log.d("네","계속안내");
                        // 목적지 그대로 시작점 바꿔서 시작
                        TTSManager.getInstance().setStatus(5);
                        TTSManager.getInstance().speak("설정하신 목적지 "+destination.getAddress()+"에 있는"+destination.getName()+"로 안내를 시작합니다.");
                    }
                    else if( answer.length()>=2 && answer.substring(0,2).equals("아니") ) {
                        Log.d("아니요", "다시 목적지 입력");
                        // 다시 목적지 입력
                        TTSManager.getInstance().setStatus(2);
                        TTSManager.getInstance().speak("목적지를 말씀해주세요.");
                    }
                    else{
                        TTSManager.getInstance().speak("다시 말씀해주세요.");
                    }
                }
                else if(TTSManager.getInstance().getStatus() == 2) {
                    TTSManager.getInstance().setStatus(4);
                    TTSManager.getInstance().speak("설정하신 목적지가 "+answer+"이 맞습니까?");
                    finalAnswer = answer;
                }
                else if(TTSManager.getInstance().getStatus() == 3) {    // 목적지 list에서 설정
                    POIItem temp = arrPOIItem.poll();
                    if( checkYes(answer) ){
                        destination = temp;
                        TTSManager.getInstance().setStatus(5);
                        TTSManager.getInstance().speak("설정하신 목적지 "+destination.getAddress()+"에 있는"+destination.getName()+"로 안내를 시작합니다.");
                    }
                    else if(answer.length()>=2 && answer.substring(0,2).equals("아니")){
                        if( arrPOIItem.isEmpty() ){
                            TTSManager.getInstance().setStatus(2);
                            TTSManager.getInstance().speak("주변"+answer+"를 모두 확인했습니다. 목적지를 다시 말씀해주세요.");
                        }
                        else{
                            TTSManager.getInstance().speak("목적지를"+arrPOIItem.peek().getAddress()+"에 있는"+arrPOIItem.peek().getName()+"로 설정하시겠습니까?");
                        }
                    }
                }
                else if(TTSManager.getInstance().getStatus() == 4) {
                    if( checkYes(answer) )
                        TMapManager.getInstance().findPOI(finalAnswer);
                    else if(answer.length()>=2 && answer.substring(0,2).equals("아니")){
                        TTSManager.getInstance().setStatus(2);
                        TTSManager.getInstance().speak("목적지를 다시 말씀해주세요.");
                    }
                    else{
                        TTSManager.getInstance().speak("다시 말씀해주세요.");
                    }
                }
                else if(TTSManager.getInstance().getStatus() == 5){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TMapManager.getInstance().findPath(TMapManager.getInstance().getLocation(),destination.getPoint());
                        }
                    });
                }
                else if(TTSManager.getInstance().getStatus() == 6){
                    finishAffinity();
                }
            }

            @Override
            public void onError(String utteranceId) {

            }
        };
    }

    public String returnAnswer(){
        sttManager.runmRecognizer();
        while (sttManager.getStatus() == 0)
            ;
        return sttManager.getAnswer();
    }

    public boolean checkYes(String answer){
        return answer.equals("네")||answer.equals("녜")||answer.equals("예")||answer.equals("넹")||answer.equals("그래")||answer.equals("응")||answer.equals("어");
    }

    @Override
    public void onInit(int status) {
        if(status != ERROR) {
            TTSManager.getInstance().getTTS().setLanguage(Locale.KOREAN);
            TTSManager.getInstance().setListner(utteranceProgressListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(destination == null) {
            TTSManager.getInstance().setStatus(2);
            TTSManager.getInstance().speak("안녕하세요. 베데레 입니다. 목적지를 말씀해주세요.");
        }
        else{
            String text = "현재 설정된 목적지는 " + destination.getName() + "입니다. 안내를 계속 받으시겠습니까?";
            TTSManager.getInstance().setStatus(1);
            TTSManager.getInstance().speak(text);
        }
    }

    @Override
    protected void onDestroy() {
        TTSManager.getInstance().destroy();
        super.onDestroy();
    }

    @Override
    public void onFindAllPOI(ArrayList<TMapPOIItem> arrayList) {
        arrPOIItem = new PriorityQueue<>();
        TMapPoint currentLoc = TMapManager.getInstance().getLocation();
        if( arrayList.size()==0 ) {
            TTSManager.getInstance().setStatus(2);
            TTSManager.getInstance().speak("목적지를 찾을 수 없습니다. 목적지를 다시 말씀해주세요.");
        }
        else {
            for (TMapPOIItem temp : arrayList) {
                arrPOIItem.add(new POIItem(temp.getPOIPoint(), temp.getPOIName(), temp.getPOIAddress().replace("null", "").trim(), temp.getDistance(currentLoc)));

            }
            TTSManager.getInstance().setStatus(3);
            TTSManager.getInstance().speak("목적지를"+arrPOIItem.peek().getAddress()+"에 있는"+arrPOIItem.peek().getName()+"로 설정하시겠습니까?");
        }
    }

    @Override
    public void onFindPathDataAll(Document document) {
        directionModel = new DirectionModel();
        directionModel.updateModel(document);
        Log.d("check","check");
        forAnnounce();
    }

    @Override
    public void onLocationChange(Location location) {
        Log.d("checklocation","akajajajaj");
        if(mode == 1) {// 안내모드
            TMapPolyLine temp = new TMapPolyLine();
            temp.addLinePoint(new TMapPoint(location.getLatitude(),location.getLongitude()));
            temp.addLinePoint(directionModel.getModel().peek().getArrivePoint());
            int changedTurnInfo = 0;
            boolean arrive = false;
            if( temp.getDistance()<0.5 ){
                directionModel.getModel().poll();
                if( directionModel.getModel().isEmpty() ){  //최종목적지 도착
                    mode = 0;
                    TTSManager.getInstance().setStatus(6);
                    TTSManager.getInstance().speak("최종목적지에 도착했습니다. 안내를 종료합니다.");
                }
                else {
                    changedTurnInfo = turnInfoManager.setTurnInfo(directionModel.getModel().peek());
                }
                arrive = true;
            }
            else{
                DirectionInfo tempDirectionInfo = new DirectionInfo(TMapManager.getInstance().getLocation(),new TMapPoint(location.getLatitude(),location.getLongitude()),
                        directionModel.getModel().peek().getArrivePoint(),0);
                changedTurnInfo = turnInfoManager.setTurnInfo(tempDirectionInfo);
            }
            if( (changedTurnInfo == 11 && !arrive) || mode==0 )
                ;
            else {
                TTSManager.getInstance().setStatus(0);
                TTSManager.getInstance().speak(turnInfoManager.returnAnnouncement(changedTurnInfo));
            }
        }
        TMapManager.getInstance().getTMapView().setLocationPoint(location.getLongitude(),location.getLatitude());
    }

    public void forAnnounce(){
        TTSManager.getInstance().setStatus(0);
        TTSManager.getInstance().speak("출발방향 설정을 시작합니다. 설정완료시 화면을 터치해주세요.");
        mSensorManager.registerListener((SensorEventListener)this,
                mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener((SensorEventListener)this,
                mMagneticField,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        angleManager.callSetStartDirection(event,TMapManager.getInstance().getLocation(),directionModel.getModel().peek().getArrivePoint());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
