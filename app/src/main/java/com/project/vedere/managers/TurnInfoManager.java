package com.project.vedere.managers;

import com.project.vedere.model.DirectionInfo;
import com.skt.Tmap.TMapPoint;


public class TurnInfoManager {

    public int setTurnInfo(DirectionInfo dirInfo){
        int turnInfo = dirInfo.getTurnInfo();
        if( turnInfo==0 ){  // turnInfo 정보가 없으면 setting 처리
            double angle = calculateAngle(dirInfo.getStartPoint(),dirInfo.getArrivePoint(),dirInfo.getPriorStartPoint());
            if( -15.0<angle && angle<15.0 )
                turnInfo = 11;    // 직진
            else if( 15.0<=angle && angle<45.0 )
                turnInfo = 180;   //1시방향 우회전
            else if( 45.0<=angle && angle<75.0 )
                turnInfo = 18;    // 2시방향 우회전
            else if( 75.0<=angle && angle<105.0 )
                turnInfo = 13;   // 3시방향 우회전
            else if( 105.0<=angle && angle<135.0 )
                turnInfo = 19;    // 4시방향 우회전
            else if( 135.0<=angle && angle<165.0 )
                turnInfo = 20;    // 5시방향 우회전
            else if( (165.0<=angle && angle<=180.0 ) || (-180<=angle && angle<=-165) )
                turnInfo = 14;    // 유턴
            else if( -45.0<=angle && angle<=-15 )
                turnInfo = 170;   // 11시 방향 좌회전
            else if( -75.0<=angle && angle<-45.0 )
                turnInfo = 17;    // 10시 방향 죄화전
            else if( -105.0<=angle && angle<-75.0 )
                turnInfo = 160;   // 9시 방향 좌회전
            else if( -135.0<=angle && angle<-105.0 )
                turnInfo = 16;    // 8시 방향 좌회전
            else if( -165.0<angle && angle<-135.0 )
                turnInfo = 15;    // 7시 방향 좌회전
        }
        return turnInfo;
    }

    public String returnAnnouncement(int turnInfo) {
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
            case 15:
                text = "7시 방향 좌회전하세요.";
                break;
            case 16:
                text = "8시 방향 좌회전하세요";
                break;
            case 160:
                text = "9시 방향 좌회전하세요";
                break;
            case 17:
                text = "10시 방향 좌회전하세요";
                break;
            case 170:
                text = "11시 방향 좌회전하세요";
                break;
            case 180:
                text = "1시방향 우회전하세요";
                break;
            case 18:
                text = "2시 방향 우회전하세요";
                break;
            case 19:
                text = "4시 방향 우회전하세요";
                break;
            case 20:
                text = "5시 방향 우회전하세요";
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

    private double calculateAngle(TMapPoint startPoint, TMapPoint arrivePoint, TMapPoint priorPoint){
        double angle = Math.atan2(arrivePoint.getLatitude()-startPoint.getLatitude(),arrivePoint.getLongitude()-startPoint.getLongitude())
                -Math.atan2(priorPoint.getLatitude()-startPoint.getLatitude(),priorPoint.getLongitude()-startPoint.getLongitude());
        angle *= 360.0/(2.0*Math.PI);
        // 0<=angle<=180이면 오른쪽으로 angle만큼 돌아야함
        // -180<=angle<=0이면 왼쪽으로 angle만큼 돌아야함
        return angle;
    }
}