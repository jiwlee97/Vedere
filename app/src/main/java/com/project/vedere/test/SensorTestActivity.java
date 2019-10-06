package com.project.vedere.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.vedere.R;
import com.project.vedere.managers.AngleManager;
import com.project.vedere.managers.STTManager;
import com.skt.Tmap.TMapPoint;

import java.util.ArrayList;
import java.util.Locale;


public class SensorTestActivity {
//extends AppCompatActivity implements SensorEventListener

//    private SensorManager mSensorManager;
//    private Sensor mAccelerometer;
//    private Sensor mMagneticField;
//    private AngleManager angleManager;
//    private STTManager sttManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        angleManager = new AngleManager(this );
//        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//
//        sttManager = new STTManager(this );
//
//    }
//
//    @Override
//    protected void onResume(){
//        super.onResume();
//        mSensorManager.registerListener((SensorEventListener)this,
//                mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
//        mSensorManager.registerListener((SensorEventListener)this,
//                mMagneticField,SensorManager.SENSOR_DELAY_NORMAL);
//        sttManager.callSTTManager();
//    }
//
//    @Override
//    protected void onPause(){
//        super.onPause();
//        mSensorManager.unregisterListener((SensorEventListener)this);
//    }
//
//    @Override
//    public void onSensorChanged(SensorEvent event){
//        angleManager.callSetStartDirection(event);
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//    }
}