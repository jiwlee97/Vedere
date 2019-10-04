package com.project.vedere.managers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.project.vedere.interfaces.PermissionCallback;
import com.project.vedere.interfaces.TMapCallback;
import com.project.vedere.utils.MyUtil;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.io.IOException;
import java.util.Map;

/**
 * TMap API 매니저
 * 싱글톤 객체
 */
public class TMapManager{
    private Activity mActivity;
    private Context mContext;

    private TMapCallback mCallback;

    private TMapGpsManager gps;
    private TMapData mTMapData;
    private TMapView mTMapView;

    private static boolean initMap = false;

    private static class TMapManagerHolder {
        public static TMapManager INSTANCE = new TMapManager();
    }

    public static TMapManager getInstance() {
        return TMapManagerHolder.INSTANCE;
    }

    private TMapManager() {
    }

    public TMapPoint getLocation() {
        return gps.getLocation();
    }

    public void initManager(Activity activity, Context context, TMapCallback callback) {
        mActivity = activity;
        mContext = context;
//        mPathCallback = callback;
//        mPoiCallback = callback;
        mCallback = callback;
        mTMapData = new TMapData();
        setMap();
    }

    public TMapView getTMapView() {
        return mTMapView;
    }
    /**
     * GPS 초기화
     */
    public void setGPS() {
        PermissionManager.getInstance().setActivity(mActivity);
        if (PermissionManager.getInstance().check(Manifest.permission.ACCESS_FINE_LOCATION)) {
            initGPS();
        }
        else {
            prepareGPS();
        }
    }

    /**
     * 길 찾기
     * @param startPoint : 시작 위치
     * @param endPoint : 도착 위치
     */
    public void findPath(TMapPoint startPoint, TMapPoint endPoint) {
        mTMapData.findPathDataAllType(TMapData.TMapPathType.PEDESTRIAN_PATH, startPoint, endPoint, mCallback);
    }

    public void findPOI(String target) {
        mTMapData.findAllPOI(target, mCallback);
    }

    public void destory() {
        if (gps != null) gps.CloseGps();
    }

    /**
     * TMapView 초기화, 최초 생성시 키 인증
     */
    private void setMap() {
        mTMapView = new TMapView(mContext);
        if (!initMap)
            configureMap();
    }

    /**
     * TMapView 첫 생성 시 키설정
     */
    private void configureMap() {
        Map<String, Object> keys = null;
        try {
            keys = YamlParser.getInstance().parseYaml(mContext, MyUtil.KEYS);
        } catch (IOException e) {
            Log.e("Yaml", e.getMessage());
        }

        mTMapView.setSKTMapApiKey((String) keys.get(MyUtil.TMAPKEY));
        initMap = true;
    }

    /**
     * GPS 초기화
     * GPS를 이용하여 GPS 사용
     */
    private void initGPS() {
        gps = new TMapGpsManager(mContext);
        gps.setMinTime(1000);
        gps.setMinDistance(5);
        gps.setProvider(gps.GPS_PROVIDER);
        gps.OpenGps();
    }

    /**
     * GPS 사용 허가 확인 후 GPS 초기화
     */
    private void prepareGPS() {
        PermissionManager.getInstance().request(MyUtil.GPS_PERMISSION, new PermissionCallback() {
            @Override
            public void granted() {
                initGPS();
            }

            @Override
            public void denied() {
                Log.w("GPS", "GPS permission denied!");
            }
        });
    }
}




