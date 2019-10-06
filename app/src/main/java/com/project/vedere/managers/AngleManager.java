package com.project.vedere.managers;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Vibrator;
import android.util.Log;

import com.skt.Tmap.TMapPoint;

import static android.content.Context.SENSOR_SERVICE;

public class AngleManager{

    private Vibrator vibrator;
    private float[] mGravity;
    private float[] mGeomagnetic;

    public AngleManager(Activity activity) {
        vibrator = (Vibrator)activity.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void callSetStartDirection(SensorEvent event,TMapPoint startPoint,TMapPoint arrivePoint){
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
                if( azimuth<0.0f )
                    azimuth += 360.f;
                setStartDirection(azimuth,startPoint,arrivePoint);
            }
        }
    }

    private void setStartDirection(float azimuth, TMapPoint startPoint, TMapPoint arrivePoint) {
        Log.d("azimuth",Float.toString(azimuth));
        Log.d("각도",(Float.toString(CalculateBearingAngle(startPoint,arrivePoint))));
        if(Math.abs(azimuth-CalculateBearingAngle(startPoint,arrivePoint))<=10.0f) {    // 나침반 방향과 가야할 방향이 같으면
            vibrator.vibrate(1000 ); // 1초간 진동
            Log.d("direction","올바른방향");
        }
        else{   // 다르면
            vibrator.cancel();
        }
    }

    // 두 점 사이의 방위각을 계산
    private float CalculateBearingAngle(TMapPoint startPoint,TMapPoint arrivePoint){
        Location userLoc = new Location("service Provider");
        userLoc.setLatitude(startPoint.getLatitude());
        userLoc.setLongitude(startPoint.getLongitude());

        Location destLoc = new Location("service Provider");
        destLoc.setLatitude(arrivePoint.getLatitude());
        destLoc.setLongitude(arrivePoint.getLongitude());
        float bearTo = userLoc.bearingTo(destLoc);
        if( bearTo < 0.0f )
            bearTo += 360.0f;
        return bearTo;
    }
}