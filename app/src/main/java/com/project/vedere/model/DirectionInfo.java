package com.project.vedere.model;

import com.skt.Tmap.TMapPoint;

public class DirectionInfo {
    private TMapPoint priorStartPoint;
    private TMapPoint startPoint;
    private TMapPoint arrivePoint;
    private int turnInfo;

    public DirectionInfo() {
        priorStartPoint = new TMapPoint(0, 0);
        startPoint = new TMapPoint(0, 0);
        arrivePoint = new TMapPoint(0, 0);
        turnInfo = 0;
    }

    public DirectionInfo(TMapPoint priorStartPoint, TMapPoint startPoint, TMapPoint arrivePoint, int turnInfo) {
        this.priorStartPoint = priorStartPoint;
        this.startPoint = startPoint;
        this.arrivePoint = arrivePoint;
        this.turnInfo = turnInfo;
    }

    public TMapPoint getPriorStartPoint() {
        return priorStartPoint;
    }

    public void setPriorStartPoint(TMapPoint priorStartPoint) {
        this.priorStartPoint = priorStartPoint;
    }

    public TMapPoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(TMapPoint startPoint) {
        this.startPoint = startPoint;
    }

    public TMapPoint getArrivePoint() {
        return arrivePoint;
    }

    public void setArrivePoint(TMapPoint arrivePoint) {
        this.arrivePoint = arrivePoint;
    }

    public int getTurnInfo() {
        return turnInfo;
    }

    public void setTurnInfo(int turnInfo) {
        this.turnInfo = turnInfo;
    }

    public DirectionInfo(DirectionInfo directionInfo) {
        this.priorStartPoint = directionInfo.priorStartPoint;
        this.startPoint = directionInfo.startPoint;
        this.arrivePoint = directionInfo.arrivePoint;
        this.turnInfo = directionInfo.turnInfo;
    }

    public DirectionInfo clone() {
        return new DirectionInfo(this);
    }

    @Override
    public String toString() {
        return "DirectionInfo{" +
                "priorStartPoint=" + priorStartPoint.getLatitude() + "," + priorStartPoint.getLongitude() +
                ", startPoint=" + startPoint.getLatitude() + "," + startPoint.getLongitude() +
                ", arrivePoint=" + arrivePoint.getLatitude() + "," + arrivePoint.getLongitude() +
                ", turnInfo=" + turnInfo +
                '}';
    }
}
