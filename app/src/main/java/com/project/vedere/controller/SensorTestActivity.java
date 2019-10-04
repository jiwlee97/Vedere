package com.project.vedere.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Button;
import android.widget.TextView;

import com.project.vedere.R;
import com.project.vedere.managers.AngleManager;
import com.project.vedere.managers.STTManager;
import com.skt.Tmap.TMapPoint;


public class SensorTestActivity extends AppCompatActivity implements SensorEventListener{

    private Vibrator vibrator;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagneticField;
    private float mPitch, mRoll;
    private AngleManager angleManager;
    float[] mGravity;
    float[] mGeomagnetic;

    // STT
    Intent intent;
    SpeechRecognizer mRecognizer;
    Button sttBtn;
    TextView textView;
    final int PERMISSION = 1;
    private STTManager sttManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        angleManager = new AngleManager(this );
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if( Build.VERSION.SDK_INT>=23 ){    //퍼미션체크
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.INTERNET,Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }
        textView = (TextView)findViewById(R.id.sttResult);
        sttBtn = (Button)findViewById(R.id.sttStart);

        // 사용자에게 음성을 요구하고 음성 인식기를 통해 전송하는 활동 시작
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // 음성 인식을 위한 음성 인식기의 의도에 사용되는 여분의 키
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
        // 음성을 번역할 언어를 설정
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");
        sttBtn.setOnClickListener(v -> {
                mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
                mRecognizer.setRecognitionListener(sttManager.getListener());
                mRecognizer.startListening(intent);
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener((SensorEventListener)this,
                mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener((SensorEventListener)this,
                mMagneticField,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener((SensorEventListener)this);
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float[] R = new float[9];
            float[] I = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float[] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);
                float azimuth = (float) Math.toDegrees(orientation[0]);

                TMapPoint startPoint = new TMapPoint(37569758,126977022);
                TMapPoint arrivePoint = new TMapPoint(37570594,126997589);
                angleManager.setStartDirection(azimuth,startPoint,arrivePoint);
                mPitch = (float) Math.toDegrees(orientation[1]);
                mRoll = (float) Math.toDegrees(orientation[2]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
