package com.project.vedere.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;

import com.project.vedere.R;
import com.project.vedere.managers.AngleManager;
import com.skt.Tmap.TMapPoint;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private Vibrator vibrator;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagneticField;
    private float mPitch, mRoll;
    private AngleManager angleManager;

    float[] mGravity;
    float[] mGeomagnetic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        angleManager = new AngleManager(this );
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
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
