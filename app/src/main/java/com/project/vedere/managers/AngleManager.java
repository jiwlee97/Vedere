package com.project.vedere.managers;

public class AngleManager {
    public void setCorrectDirection(double startLat,double startLog,
                                      double gotoLat,double gotoLog,
                                      double latAfter5,double logAfter5){
        double angle = calculateAngle(startLat,startLog,gotoLat,gotoLog,latAfter5,logAfter5);
        String text = "";
        while( !(-5.0<= angle && angle <=5.0) ){
            if( angle<-5.0 )
                text = "왼쪽으로 "+angle+"만큼 도세요.";
            else if( angle>5.0 )
                text = "오른쪽으로 "+angle+"만큼 도세요.";
            angle = calculateAngle(startLat,startLog,gotoLat,gotoLog,latAfter5,logAfter5);
        }
        text = "올바른 방향입니다.";
    }

    private double calculateAngle(double startLat,double startLog,
                                 double gotoLat,double gotoLog,
                                 double latAfter5,double logAfter5){
        double angle = Math.atan2(gotoLat-startLat,gotoLog-startLog)-Math.atan2(latAfter5-startLat,logAfter5-startLog);
        angle *= 360.0/(2.0*Math.PI);
        // 0<=angle<=180이면 오른쪽으로 angle만큼 돌아야함
        // -180<=angle<=0이면 왼쪽으로 angle만큼 돌아야함
        return angle;
    }
}
