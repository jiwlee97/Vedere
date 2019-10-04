package com.project.vedere.datatypes;

import android.graphics.Point;

public class directionInfo {
    private Point priorStartPoint;
    private Point startPoint;
    private Point arrivePoint;
    private int turnInfo;

    public directionInfo() {
    }
    public directionInfo(Point startPoint, Point arrivePoint, int turnInfo) {
        this.startPoint = startPoint;
        this.arrivePoint = arrivePoint;
        this.turnInfo = turnInfo;
    }

    public Point getPriorStartPoint() {
        return priorStartPoint;
    }

    public void setPriorStartPoint(Point priorStartPoint) {
        this.priorStartPoint = priorStartPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getArrivePoint() {
        return arrivePoint;
    }

    public void setArrivePoint(Point arrivePoint) {
        this.arrivePoint = arrivePoint;
    }

    public int getTurnInfo() {
        return turnInfo;
    }

    public void setTurnInfo(int turnInfo) {
        this.turnInfo = turnInfo;
    }
}
