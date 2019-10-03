package com.project.vedere.managers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.os.Vibrator;
import android.util.Log;

import com.project.vedere.datatypes.directionInfo;
import com.skt.Tmap.TMapPoint;


public class AngleManager{

    private Activity activity;
    private Vibrator vibrator;
    private directionInfo dirInfo;

    public AngleManager(Activity activity) {
        this.activity = activity;
        vibrator = (Vibrator)activity.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void setStartDirection(float azimuth, TMapPoint startPoint, TMapPoint arrivePoint) {
        Log.d("azimuth",Float.toString(azimuth));
        Log.d("angle",Float.toString(CalculateBearingAngle(startPoint,arrivePoint)));
        if(azimuth-CalculateBearingAngle(startPoint,arrivePoint)<=0.00001) {    // 나침반 방향과 가야할 방향이 같으면
            vibrator.vibrate(1000 ); // 1초간 진동
            Log.d("directionright","올바른방향");

        }
        else if(azimuth-CalculateBearingAngle(startPoint,arrivePoint)>0.00001) {   // 다르면
            vibrator.cancel();
            Log.d("directionwrong","틀린방향");
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
        if( bearTo < 0 )
            bearTo += 360;
        return bearTo;
    }

    public String setGotoDirection(directionInfo dirInfo) {
        double priorLat = dirInfo.getPriorStartPoint().x;
        double priorLog = dirInfo.getPriorStartPoint().y;
        double startLat = dirInfo.getStartPoint().x;
        double startLog = dirInfo.getStartPoint().y;
        double gotoLat = dirInfo.getArrivePoint().x;
        double gotoLog = dirInfo.getArrivePoint().y;
        int turnInfo = dirInfo.getTurnInfo();

        //double angle = calculateAngle(gotoLat,gotoLog,startLat,startLog,priorLat,priorLog);
        String text = "";
        switch (turnInfo){
            case 11:
                text = "다음 안내가 나올 때 까지 직진하세요.";
                break;
            case 12:
                text = "좌회전 하세요";
                break;
            case 13:
                text = "우회전 하세요";
                break;
            case 14:
                text = "유턴 하세요";
                break;
            case 16:
                text = "8시 방향 좌회전하세요";
                break;
            case 17:
                text = "10시 방향 죄회전하세요";
                break;
            case 18:
                text = "2시 방향 우회전하세요";
                break;
            case 19:
                text = "4시 방향 우회전하세요";
                break;
            case 125:
                text = "육교입니다";
                break;
            case 126:
                text = "지하보도입니다";
                break;
            case 127:
                text = "계단에 진입합니다";
                break;
            case 128:
                text = "경사로에 진입합니다";
                break;
            case 129:
                text = "계단과 경사로에 진입합니다.";
                break;
            case 201:
                text = "목적지에 도착했습니다. 안내를 종료합니다.";
                break;
            case 211:
                text = "횡단보도입니다.";
                break;
            case 212:
                text = "좌측 횡단보도입니다.";
                break;
            case 213:
                text = "우측 횡단보도입니다.";
                break;
            case 214:
                text = "8시 방향 횡단보도입니다.";
                break;
            case 215:
                text = "10시 방향 횡단보도입니다.";
                break;
            case 216:
                text = "2시 방향 횡단보도입니다.";
                break;
            case 217:
                text = "4시 방향 횡단보도입니다.";
                break;
            case 218:
                text = "엘리베이터입니다.";
                break;
            case 233:
                text = "직진 임시 입니다.";
                break;
        }
        return text;
    }

//    private double calculateAngle(double startLat,double startLog,
//                                  double gotoLat,double gotoLog,
//                                  double latAfter5,double logAfter5){
//        double angle = Math.atan2(gotoLat-startLat,gotoLog-startLog)-Math.atan2(latAfter5-startLat,logAfter5-startLog);
//        angle *= 360.0/(2.0*Math.PI);
//        // 0<=angle<=180이면 오른쪽으로 angle만큼 돌아야함
//        // -180<=angle<=0이면 왼쪽으로 angle만큼 돌아야함
//        return angle;
//    }

}
