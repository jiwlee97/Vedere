package com.project.vedere.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

import com.project.vedere.R;
import com.project.vedere.datatypes.directionInfo;
import com.project.vedere.managers.AngleManager;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

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

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }

    @Override
    protected void onResume(){
        super.onResume();
//        mSensorManager.registerListener((SensorEventListener)this,
//                mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
//        mSensorManager.registerListener((SensorEventListener)this,
//                mMagneticField,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener((SensorEventListener)this);
    }

    public void onSensorChanged(SensorEvent event){
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic =
                    event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float[] R = new float[9];
            float[] I = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float[] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);
                angleManager.setAzimuth((float) Math.toDegrees(orientation[0]));
                mPitch = (float) Math.toDegrees(orientation[1]);
                mRoll = (float) Math.toDegrees(orientation[2]);
            }
        }
    }
}
