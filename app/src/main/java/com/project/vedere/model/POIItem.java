package com.project.vedere.model;

import com.skt.Tmap.TMapPoint;

public class POIItem implements Comparable<POIItem> {
    private TMapPoint point;
    private String name;
    private String address;
    private double distance;

    public POIItem(TMapPoint point, String name, String address, double distance) {
        this.point = point;
        this.name = name;
        this.address = address;
        this.distance = distance;
    }

    public TMapPoint getPoint() {
        return point;
    }

    public void setPoint(TMapPoint point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "POIItem{" +
                "point=" + point +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", distance=" + distance +
                '}';
    }

    @Override
    public int compareTo(POIItem poiItem) {
        return Double.compare(this.distance, poiItem.distance);
    }
}
