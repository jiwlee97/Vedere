package com.project.vedere;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.project.vedere.interfaces.TMapCallback;
import com.project.vedere.managers.TMapManager;
import com.project.vedere.model.DirectionModel;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity implements TMapCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TMapManager.getInstance().initManager(this, this, this);
        TMapManager.getInstance().findPOI("부산대학교");
        TMapManager.getInstance().findPath(new TMapPoint(37.569758,126.977022), new TMapPoint(37.570594,126.997589));
    }

    @Override
    public void onFindAllPOI(ArrayList<TMapPOIItem> arrayList) {
        for (TMapPOIItem temp : arrayList)
            Log.d("POI", temp.getPOIName());
    }

    @Override
    public void onFindPathDataAll(Document document) {
        DirectionModel model = new DirectionModel();
        model.updateModel(document);
        while (!model.getModel().isEmpty())
            Log.d("Model", model.getModel().poll().toString());
    }

    @Override
    public void onLocationChange(Location location) {

    }
}
