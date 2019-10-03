package com.project.vedere.model;

import android.util.Log;


import com.skt.Tmap.TMapPoint;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.LinkedList;
import java.util.Queue;

public class DirectionModel {
    private Queue<DirectionInfo> mDirections = new LinkedList<>();

    public void updateModel(Document document) {
        mDirections.clear();
        DirectionInfo temp = new DirectionInfo();

        Element root = document.getDocumentElement();

        NodeList nodeList = root.getElementsByTagName("Placemark");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element elem = (Element) nodeList.item(i);
            String type = elem.getElementsByTagName("tmap:nodeType").item(0).getTextContent();
            if (type.equals("POINT")) {
                String turnType = elem.getElementsByTagName("tmap:turnType").item(0).getTextContent();
                temp.setTurnInfo(Integer.parseInt(turnType));
            }
            if (type.equals("LINE")) {
                String linePoints = elem.getElementsByTagName("LineString").item(0).getTextContent();
                String[] tempStr = linePoints.trim().split(" ");
                for (int k = 0; k < tempStr.length - 1; k++) {
                    temp.setPriorStartPoint(temp.getStartPoint());
                    String[] point = tempStr[k].split(",");

                    TMapPoint startPoint = new TMapPoint(Double.parseDouble(point[0]),
                            Double.parseDouble((point[1])));
                    temp.setStartPoint(startPoint);
                    point = tempStr[k + 1].split(",");
                    if (point.length != 2) {
                        point = tempStr[k + 2].split(",");
                        k++;
                    }
                    TMapPoint arrivePoint = new TMapPoint(Double.parseDouble(point[0]),
                            Double.parseDouble(point[1]));
                    temp.setArrivePoint(arrivePoint);
                    mDirections.add(temp.clone());
                    temp.setTurnInfo(0);
                }
            }
        }
        temp.setPriorStartPoint(temp.getStartPoint());
        temp.setStartPoint(temp.getArrivePoint());
        temp.setArrivePoint(new TMapPoint(0, 0));
        temp.setTurnInfo(201);
        mDirections.add(temp.clone());
        while (!mDirections.isEmpty()) {
            Log.d("Model", mDirections.poll().toString());
        }
    }
}
